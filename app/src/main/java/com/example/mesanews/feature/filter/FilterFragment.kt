package com.example.mesanews.feature.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.example.mesanews.R
import com.example.mesanews.feature.home.HomeContract

class FilterFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filter_fragment, container, false)
    }
}