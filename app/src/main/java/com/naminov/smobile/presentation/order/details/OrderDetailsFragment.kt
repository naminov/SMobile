package com.naminov.smobile.presentation.order.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.naminov.smobile.R
import com.naminov.smobile.app.App
import com.naminov.smobile.databinding.OrderDetailsFragmentBinding
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.presentation.adapter.OrderDetailsProductsAdapter
import com.naminov.smobile.presentation.customer.CustomersFragment
import com.naminov.smobile.presentation.extension.hideKeyboard
import com.naminov.smobile.presentation.product.ProductsFragment
import javax.inject.Inject

class OrderDetailsFragment : Fragment() {

    private var _binding: OrderDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: OrderDetailsViewModelFactory
    private val viewModel: OrderDetailsViewModel by viewModels { viewModelFactory }

    private val args: OrderDetailsFragmentArgs by navArgs()

    private val productsAdapter = OrderDetailsProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { handleState(it) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.action.collect { handleAction(it) }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnExitClick)
            }
        }

        if (savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                val event = UiEvent.OnLoad(
                    args.new,
                    args.order,
                    args.customer
                )
                viewModel.event.emit(event)
            }
        }
    }

    private fun initViews() {
        initAppBar()

        initCustomer()

        initComment()

        initProducts()

        initProductsAdd()

        initSave()

        binding.refreshSrl.isEnabled = false
    }

    private fun initAppBar() {
        binding.appBar.setNavigationOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnExitClick)
            }
        }

        binding.appBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.copy -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.event.emit(UiEvent.OnCopyClick)
                    }
                    true
                }
                R.id.remove -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.event.emit(UiEvent.OnRemoveClick)
                    }
                    true
                }
                R.id.settings -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.event.emit(UiEvent.OnSettingsClick)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun initCustomer() {
        binding.customerEt.editText?.setOnClickListener {
            if (!it.isClickable) return@setOnClickListener
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnCustomerClick)
            }
        }
    }

    private fun initComment() {
        binding.commentEt.editText?.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    isCursorVisible = false
                    post {
                        isCursorVisible = true
                        setSelection(text.length)
                    }
                } else {
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.event.emit(UiEvent.OnCommentChange(text.toString()))
                    }
                    hideKeyboard()
                }
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus()
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun initProducts() {
        binding.productsRv.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = productsAdapter
        }

        productsAdapter.onProductNumberChangeListener =
            OrderDetailsProductsAdapter.OnProductNumberChangeListener { product, number ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnProductNumberChange(product, number))
                }
            }

        productsAdapter.onProductRemoveListener =
            OrderDetailsProductsAdapter.OnProductRemoveListener { product ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnProductRemove(product))
                }
            }
    }

    private fun initProductsAdd() {
        binding.productsAddBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnProductAddClick)
            }
        }
    }


    private fun initSave() {
        binding.saveFab.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnSaveClick)
            }
        }
    }

    private fun handleState(state: UiState) {
        val orderDetails = state.orderDetails
        binding.run {
            refreshSrl.isRefreshing = state.loading
            numberEt.editText?.setText(orderDetails.number)
            dateEt.editText?.setText(orderDetails.date)
            customerEt.editText?.apply {
                isClickable = orderDetails.editable
                setText(orderDetails.customer.name)
            }
            commentEt.editText?.apply {
                isFocusable = orderDetails.editable
                isCursorVisible = orderDetails.editable
                setTextIsSelectable(orderDetails.editable)
                if (!state.loading) setText(orderDetails.comment)
            }
            completedIv.isVisible = orderDetails.completed
            noPaymentIv.isVisible = !orderDetails.payment
            noDocumentsIv.isVisible = !orderDetails.documents
            sumEt.editText?.setText(orderDetails.sum)
            productsAddBtn.isVisible = orderDetails.editable
            saveFab.isVisible = orderDetails.editable
            val copyItem = appBar.menu
                .findItem(R.id.copy)
            copyItem?.isVisible = !orderDetails.new
            val removeItem = appBar.menu
                .findItem(R.id.remove)
            removeItem?.isVisible = !orderDetails.new
        }
        productsAdapter.items = orderDetails.products
        productsAdapter.editable = orderDetails.editable
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            UiAction.NavigateToCustomers -> handleActionNavigateToCustomers()
            UiAction.NavigateToProducts -> handleActionNavigateToProducts()
            UiAction.NavigateToSettings -> handleActionNavigateToSettings()
            is UiAction.ShowMessage -> handleActionShowMessage(action)
            UiAction.CopyConfirm -> handleActionCopyConfirm()
            UiAction.RemoveConfirm -> handleActionRemoveConfirm()
            UiAction.ExitConfirmWithoutSave -> handleActionExitConfirmWithoutSave()
            UiAction.Exit -> handleActionExit()
        }
    }

    private fun handleActionNavigateToCustomers() {
        clearFragmentResultListener(CustomersFragment.REQUEST_KEY)
        setFragmentResultListener(CustomersFragment.REQUEST_KEY) { _, bundle ->
            bundle.getParcelable<Customer>((CustomersFragment.ARGUMENT_CUSTOMER))?.let { customer ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnCustomerChange(customer))
                }
            }
            clearFragmentResultListener(CustomersFragment.REQUEST_KEY)
        }

        findNavController()
            .navigate(
                OrderDetailsFragmentDirections
                    .actionToCustomers()
            )
    }

    private fun handleActionNavigateToProducts() {
        clearFragmentResultListener(ProductsFragment.REQUEST_KEY)
        setFragmentResultListener(ProductsFragment.REQUEST_KEY) { _, bundle ->
            bundle.getParcelable<Product>((ProductsFragment.ARGUMENT_PRODUCT))?.let { product ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnProductAdd(product))
                }
            }
            clearFragmentResultListener(ProductsFragment.REQUEST_KEY)
        }

        findNavController()
            .navigate(
                OrderDetailsFragmentDirections
                    .actionToProducts()
            )
    }

    private fun handleActionNavigateToSettings() {
        findNavController()
            .navigate(
                OrderDetailsFragmentDirections
                    .actionToSettings()
            )
    }

    private fun handleActionShowMessage(action: UiAction.ShowMessage) {
        Snackbar
            .make(binding.root, action.messageId, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun handleActionCopyConfirm() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.order_details_screen_copy_confirm_title))
            .setMessage(getString(R.string.order_details_screen_copy_confirm_without_save))
            .setNegativeButton(getString(R.string.order_details_screen_copy_confirm_negative), null)
            .setPositiveButton(getString(R.string.order_details_screen_copy_confirm_positive)) { _, _ ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnCopyConfirm)
                }
            }
            .show()
    }

    private fun handleActionRemoveConfirm() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.order_details_screen_remove_confirm_title))
            .setMessage(getString(R.string.order_details_screen_remove_confirm))
            .setNegativeButton(getString(R.string.order_details_screen_remove_confirm_negative), null)
            .setPositiveButton(getString(R.string.order_details_screen_remove_confirm_positive)) { _, _ ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnRemoveConfirm)
                }
            }
            .show()
    }

    private fun handleActionExitConfirmWithoutSave() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.order_details_screen_exit_confirm_title))
            .setMessage(getString(R.string.order_details_screen_exit_confirm_without_save))
            .setNegativeButton(getString(R.string.order_details_screen_exit_confirm_negative), null)
            .setPositiveButton(getString(R.string.order_details_screen_exit_confirm_positive)) { _, _ ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnExitConfirmWithoutSave)
                }
            }
            .show()
    }

    private fun handleActionExit() {
        findNavController()
            .navigateUp()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}