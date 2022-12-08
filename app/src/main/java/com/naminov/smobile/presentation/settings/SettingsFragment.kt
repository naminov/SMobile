package com.naminov.smobile.presentation.settings

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
import com.naminov.smobile.app.App
import com.naminov.smobile.databinding.SettingsFragmentBinding
import javax.inject.Inject

class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SettingsViewModelFactory
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { handleState(it) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.action.collect { handleAction(it) }
        }

        binding.appBar.setNavigationOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnCancelClick)
            }
        }

        binding.saveBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnSaveClick)
            }
        }

        binding.connectionUrlEt.editText?.doOnTextChanged { text, _, _, _ ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                val event = UiEvent.OnConnectionUrlChange(text.toString())
                viewModel.event.emit(event)
            }
        }

        if (savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.emit(UiEvent.OnLoad)
            }
        }
    }

    private fun handleState(state: UiState) {
        binding.connectionUrlEt.isEnabled = !state.loading
        binding.saveBtn.isEnabled = !state.loading

        binding.connectionUrlEt.editText?.apply {
            if (text.toString() == state.connectionUrl) return
            setText(state.connectionUrl)
        }
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            UiAction.Close -> handleActionClose()
            is UiAction.ShowMessage -> handleActionShowMessage(action)
        }
    }

    private fun handleActionClose() {
        findNavController()
            .navigateUp()
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