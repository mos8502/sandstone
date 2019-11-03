package hu.nemi.sandstone.features

import androidx.fragment.app.Fragment

interface Feature {
    fun createFragment(className: String): Fragment?
}