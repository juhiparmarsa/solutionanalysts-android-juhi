package com.test.imaginato.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.test.imaginato.MainActivity
import com.test.imaginato.R
import com.test.imaginato.database.remote.Resource
import com.test.imaginato.databinding.FragmentLoginBinding
import com.test.imaginato.ui.signin.viewmodel.SignInViewModel
import com.test.imaginato.utils.ProgressUtils
import com.test.imaginato.utils.showSnackBar
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private val viewModel:SignInViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val signInBinding: FragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(LayoutInflater.from(container?.context), R.layout.fragment_login, container, false)
        signInBinding.viewModel = viewModel
        signInBinding.lifecycleOwner = this
        // Inflate the layout for this fragmentA
        initializeComponent(signInBinding.getRoot())
        return signInBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponent(view)
    }

    private fun initializeComponent(root: View) {
        viewModel.showSnackBar.observe(viewLifecycleOwner, {
            root.showSnackBar(getString(it))
        })
        viewModel.loginLiveData.observe(this, Observer { resources ->
            resources?.apply {
                when (this) {
                    is Resource.Success -> {
                        ProgressUtils.closeProgressDialog()
                        viewModel.clearInputValue()
                        val bundle = bundleOf("username" to edtUserName.text.toString())
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
                    }
                    is Resource.Error -> {
                        ProgressUtils.closeProgressDialog()
                        error.errorMessage?.let { root.showSnackBar(it) }
                    }
                    is Resource.Loading -> {
                        ProgressUtils.showProgressDialog(activity)
                    }
                }
            }
        })
    }
}