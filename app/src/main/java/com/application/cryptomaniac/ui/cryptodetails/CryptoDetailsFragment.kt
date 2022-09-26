package com.application.cryptomaniac.ui.cryptodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.application.cryptomaniac.data.model.CryptoDescAndCategories
import com.application.cryptomaniac.databinding.FragmentDetailsCryptoBinding
import com.application.cryptomaniac.ui.MainActivity
import com.application.cryptomaniac.ui.base.BaseFragment
import com.application.cryptomaniac.utils.isOnline
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CryptoDetailsFragment : BaseFragment() {

    private var binding: FragmentDetailsCryptoBinding? = null
    private lateinit var viewModel: CryptoDetailsViewModel

    companion object {
        const val ID = "id"
        fun newInstance(
            id: String?,
        ) = CryptoDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(ID, id)
            }
        }
    }

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ID)?.let { arg ->
            (arg as? String?)?.let { id = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            CryptoDetailsViewModel.factory((activity as? MainActivity)?.getRepository())
        )[CryptoDetailsViewModel::class.java]

        viewModel.id.set(id)
        binding = FragmentDetailsCryptoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        binding?.error?.buttonError?.setOnClickListener {
            binding?.progressLoader?.visibility = View.VISIBLE
            checkError()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressLoader?.isVisible = isLoading
        }

        viewModel.cryptoDescAndCategories.observe(
            viewLifecycleOwner
        ) { bindDescAndCategories(it) }

        binding?.buttonArrowBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        checkError()
    }

    private fun bindDescAndCategories(cryptoDescAndCategories: CryptoDescAndCategories?) {

        binding?.let {
            Glide
                .with(binding!!.cryptoImageDetails)
                .load(cryptoDescAndCategories?.image?.large)
                .apply(RequestOptions.circleCropTransform())
                .into(it.cryptoImageDetails)
        }

        binding?.cryptoNameToolbar?.text = cryptoDescAndCategories?.name
        binding?.cryptoDescription?.text = cryptoDescAndCategories?.description?.get("en")
        binding?.cryptoCategories?.text = cryptoDescAndCategories?.categories?.joinToString()
    }

    private fun checkError() {
        if (isOnline(context)) {
            binding?.error?.root?.visibility = View.GONE
            binding?.nestedScroll?.visibility = View.VISIBLE
            viewModel.getChosenItemDescAndCategories()
        } else {
            binding?.error?.root?.visibility = View.VISIBLE
            binding?.nestedScroll?.visibility = View.GONE
            binding?.toolbar?.visibility = View.VISIBLE
            binding?.progressLoader?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}