package com.naminov.smobile.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.naminov.smobile.R
import com.naminov.smobile.app.App
import com.naminov.smobile.databinding.LoginFragmentBinding
import com.naminov.smobile.presentation.extension.hideKeyboard
import javax.inject.Inject

class LoginFragment: Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

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

        initUserName()

        initPassword()

        initLogin()
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
    }

    private fun initUserName() {
        binding.userNameEt.editText?.doOnTextChanged { text, _, _, _ ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                val event = UiEvent.OnUserNameChange(text.toString())
                viewModel.event.emit(event)
            }
        }
    }

    private fun initPassword() {
        binding.passwordEt.editText?.doOnTextChanged { text, _, _, _ ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                val event = UiEvent.OnPasswordChange(text.toString())
                viewModel.event.emit(event)
            }
        }
    }

    private fun initLogin() {
        binding.loginBtn.setOnClickListener {
            hideKeyboard()
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnLoginClick)
            }
        }
    }

    private fun handleState(state: UiState) {
        binding.run {
            userNameEt.apply {
                isEnabled = !state.loading
                editText?.apply {
                    if (text.toString() != state.userName) {
                        setText(state.userName)
                    }
                }
            }
            passwordEt.apply {
                isEnabled = !state.loading
                editText?.apply {
                    if (text.toString() != state.password) {
                        setText(state.password)
                    }
                }
            }
            loginBtn.isEnabled = !state.loading && state.loginAvailable
        }
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            UiAction.NavigateToOrderHistory -> handleActionNavigateToOrderHistory()
            UiAction.NavigateToSettings -> handleActionNavigateToSettings()
            is UiAction.ShowMessage -> handleActionShowMessage(action)
        }
    }

    private fun handleActionNavigateToOrderHistory() {
        findNavController()
            .navigate(
                LoginFragmentDirections
                    .actionToOrderHistory()
            )
    }

    private fun handleActionNavigateToSettings() {
        findNavController()
            .navigate(
                LoginFragmentDirections
                    .actionToSettings()
            )
    }

    private fun handleActionShowMessage(action: UiAction.ShowMessage) {
        Snackbar
            .make(binding.root, action.messageId, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}