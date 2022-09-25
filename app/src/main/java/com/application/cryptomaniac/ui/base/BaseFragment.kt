package com.application.cryptomaniac.ui.base

import androidx.fragment.app.Fragment
import com.application.cryptomaniac.R

abstract class BaseFragment : Fragment() {

    fun replaceFragment(fragment: BaseFragment, fragmentTag: String? = null) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.activity_main, fragment, fragmentTag)
            ?.addToBackStack(fragmentTag)
            ?.commit()
    }
}