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
        viewModel.getChosenItemDescAndCategories()
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressLoader?.isVisible = isLoading
        }

        viewModel.cryptoDescAndCategories.observe(
            viewLifecycleOwner
        ) { bindDescAndCategories(it) }

        binding?.buttonArrowBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    fun bindDescAndCategories(cryptoDescAndCategories: CryptoDescAndCategories?) {

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}