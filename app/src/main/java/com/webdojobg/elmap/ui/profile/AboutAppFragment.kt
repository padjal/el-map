package com.webdojobg.elmap.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.webdojobg.elmap.databinding.FragmentAboutBinding

/**
 * A fragment for showing the about section of the application.
 *
 */
class AboutAppFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}