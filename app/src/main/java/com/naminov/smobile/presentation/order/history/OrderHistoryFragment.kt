package com.naminov.smobile.presentation.order.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.naminov.smobile.R
import com.naminov.smobile.app.App
import com.naminov.smobile.databinding.OrderHistoryFragmentBinding
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.presentation.adapter.OrderHistoryAdapter
import com.naminov.smobile.presentation.extension.hideKeyboard
import com.naminov.smobile.presentation.glide.GlideApp
import javax.inject.Inject
import kotlin.math.abs

class OrderHistoryFragment: Fragment() {

    private var _binding: OrderHistoryFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: OrderHistoryViewModelFactory
    private val viewModel: OrderHistoryViewModel by viewModels { viewModelFactory }

    private val orderAdapter = OrderHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderHistoryFragmentBinding.inflate(inflater, container, false)

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

        if (savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnLoad)
            }
        }
    }

    private fun initViews() {
        initAppBar()

        initSearch()

        initFilterCustomer()

        initFilterNoPayment()

        initFilterNoDocuments()

        initRefresh()

        initOrders()

        initCreate()
    }

    private fun initAppBar() {
        binding.appBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.event.emit(UiEvent.OnSettingsClick)
                    }
                    true
                }
                else -> false
            }
        }

        binding.appBarLayout.addOnOffsetChangedListener{ appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                binding.createFab.shrink()
            } else if (verticalOffset == 0) {
                binding.createFab.extend()
            }
        }

        val searchItem = binding.appBar.menu
            .findItem(R.id.search)

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var isKeyboardHided = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        if (isKeyboardHided) return
                        hideKeyboard()
                        isKeyboardHided = true
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        isKeyboardHided = false
                    }
                }
            }
        }

        searchItem.setOnActionExpandListener(object : OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                ViewCompat.setNestedScrollingEnabled(binding.orderRv, false)
                binding.orderRv.addOnScrollListener(onScrollListener)
                binding.appBarLayout.setExpanded(false)
                binding.createFab.hide()
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                ViewCompat.setNestedScrollingEnabled(binding.orderRv, true)
                binding.orderRv.removeOnScrollListener(onScrollListener)
                val expanded = !binding.orderRv.canScrollVertically(-1)
                binding.appBarLayout.setExpanded(expanded)
                binding.createFab.show()
                return true
            }
        })
    }

    private fun initSearch() {
        val searchItem = binding.appBar.menu
            .findItem(R.id.search)

        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = getString(R.string.order_history_screen_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnSearchChange(newText ?: ""))
                }
                return false
            }
        })
    }

    private fun initFilterCustomer() {
        binding.filterCustomerChip.setOnClickListener {
            binding.filterCustomerChip.apply { isChecked = !isChecked }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnFilterCustomerClick)
            }
        }

        binding.filterCustomerChip.setOnCloseIconClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                if (binding.filterCustomerChip.isChecked) {
                    viewModel.event.emit(UiEvent.OnFilterCustomerClearClick)
                } else {
                    viewModel.event.emit(UiEvent.OnFilterCustomerClick)
                }
            }
        }
    }

    private fun initFilterNoPayment() {
        binding.filterNoPaymentChip.setOnCheckedChangeListener { _, isChecked  ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnFilterNoPaymentChange(isChecked))
            }
        }
    }

    private fun initFilterNoDocuments() {
        binding.filterNoDocumentsChip.setOnCheckedChangeListener { _, isChecked  ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnFilterNoDocumentsChange(isChecked))
            }
        }
    }

    private fun initRefresh() {
        binding.refreshSrl.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnRefresh)
            }
        }
    }

    private fun initOrders() {
        binding.orderRv.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.orderRv.setHasFixedSize(true)
        binding.orderRv.adapter = orderAdapter

        orderAdapter.onItemClickListener = object : OrderHistoryAdapter.OnItemClickListener {
            override fun onItemClick(order: OrderHistory) {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnOrderClick(order))
                }
            }
        }
    }

    private fun initCreate() {
        binding.createFab.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnCreateClick)
            }
        }
    }

    private fun handleState(state: UiState) {
        binding.refreshSrl.isRefreshing = state.loading

        GlideApp
            .with(this)
            .load(state.customerImg)
            .into(binding.customerIv)

        val filter = state.filter

        binding.filterCustomerChip.apply {
            val filterCustomerEnabled = filter.customer != null
            if (isChecked != filterCustomerEnabled) {
                isChecked = filterCustomerEnabled
            }
            if (filter.customer != null) {
                text = filter.customer.name
                closeIcon = ContextCompat.getDrawable(context, R.drawable.ic_close)
            } else {
                text = getString(R.string.order_history_screen_filter_customer)
                closeIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down)
            }
        }

        binding.filterNoPaymentChip.apply {
            if (isChecked != filter.noPayment) {
                isChecked = filter.noPayment
            }
        }

        binding.filterNoDocumentsChip.apply {
            if (isChecked != filter.noDocuments) {
                isChecked = filter.noDocuments
            }
        }

        orderAdapter.items = state.orders
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            is UiAction.NavigateToOrderDetails -> handleActionNavigateToOrderDetails(action)
            is UiAction.NavigateToSettings -> handleActionNavigateToSettings()
            is UiAction.NavigateToCustomers -> handleActionNavigateToCustomers()
            is UiAction.ShowMessage -> handleActionShowMessage(action)
            is UiAction.HideKeyboard -> handleActionHideKeyboard()
        }
    }

    private fun handleActionNavigateToOrderDetails(action: UiAction.NavigateToOrderDetails) {

    }

    private fun handleActionNavigateToSettings() {
        findNavController()
            .navigate(
                OrderHistoryFragmentDirections
                    .actionToSettings()
            )
    }

    private fun handleActionNavigateToCustomers() {

    }

    private fun handleActionShowMessage(action: UiAction.ShowMessage) {
        Snackbar
            .make(binding.root, action.messageId, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun handleActionHideKeyboard() {
        hideKeyboard()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}