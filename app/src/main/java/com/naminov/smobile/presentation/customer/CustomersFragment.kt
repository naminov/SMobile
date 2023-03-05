package com.naminov.smobile.presentation.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.naminov.smobile.app.App
import com.naminov.smobile.databinding.CustomersFragmentBinding
import com.naminov.smobile.presentation.adapter.CustomersAdapter
import com.naminov.smobile.presentation.adapter.LoadingAdapter
import com.naminov.smobile.presentation.extension.setNavigationOnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class CustomersFragment: BottomSheetDialogFragment() {

    companion object {
        const val REQUEST_KEY = "CustomersFragmentRequestKey"
        const val ARGUMENT_CUSTOMER = "Customer"
    }

    private var _binding: CustomersFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: CustomersViewModelFactory
    private val viewModel: CustomersViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var singleClickController: SingleClickController

    private val customersAdapter: CustomersAdapter by lazy {
        CustomersAdapter(singleClickController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomersFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()

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

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.event.emit(UiEvent.OnInitialization)
        }
    }

    private fun initDialog() {
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.apply {
            isFitToContents = false
            skipCollapsed = true
            expandedOffset = 64
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initViews() {
        initAppBar()

        initSearch()

        initCustomers()

        binding.refreshSrl.isEnabled = false
    }

    private fun initAppBar() {
        binding.appBar.setNavigationOnSingleClickListener(singleClickController) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnExitClick)
            }
        }
    }

    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun initCustomers() {
        binding.customerRv.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = customersAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter(),
                footer = LoadingAdapter()
            )
        }

        customersAdapter.addLoadStateListener { state: CombinedLoadStates ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                if (state.refresh is LoadState.NotLoading) {
                    viewModel.event.emit(UiEvent.OnLoadCustomers())
                } else if (state.refresh is LoadState.Error) {
                    val error = (state.refresh as LoadState.Error).error
                    viewModel.event.emit(UiEvent.OnLoadCustomers(error))
                }
            }
        }

        customersAdapter.onItemClickListener =
            CustomersAdapter.OnItemClickListener { customer ->
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.event.emit(UiEvent.OnCustomerClick(customer))
                }
            }

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var isFocusCleared = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        if (isFocusCleared) return
                        binding.searchView.clearFocus()
                        isFocusCleared = true
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        isFocusCleared = false
                    }
                }
            }
        }

        binding.customerRv.addOnScrollListener(onScrollListener)
    }

    private fun handleState(state: UiState) {
        binding.run {
            shimmerSfl.apply {
                isVisible = !state.initialized
                if (!state.initialized) {
                    startShimmer()
                } else {
                    stopShimmer()
                }
            }

            refreshSrl.apply {
                isVisible = state.initialized
                isRefreshing = state.initialized && state.loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            state.customers.collectLatest { customersAdapter.submitData(it) }
        }
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            is UiAction.Exit -> handleActionExit()
            is UiAction.ShowMessage -> handleActionShowMessage(action)
            is UiAction.SetResult -> handleActionSetResult(action)
        }
    }

    private fun handleActionExit() {
        findNavController()
            .navigateUp()
    }

    private fun handleActionShowMessage(action: UiAction.ShowMessage) {
        Snackbar
            .make(binding.root, action.messageId, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun handleActionSetResult(action: UiAction.SetResult) {
        setFragmentResult(
            REQUEST_KEY,
            bundleOf(ARGUMENT_CUSTOMER to action.customer)
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}