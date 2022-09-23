package com.application.cryptomaniac.ui.fragments

import androidx.fragment.app.Fragment
import com.application.cryptomaniac.R

abstract class BaseFragment : Fragment() {

    fun replaceFragment(fragment: BaseFragment, fragmentTag: String?) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.activity_main, fragment, fragmentTag)
            ?.addToBackStack(fragmentTag)
            ?.commit()
    }
}