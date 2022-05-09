package com.webdojobg.elmap.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.webdojobg.elmap.R
import com.webdojobg.elmap.databinding.FragmentProfileBinding

/**
 * A fragment used for showing the profile page of each user.
 *
 */
class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = _binding.root

        root.findViewById<LinearLayout>(R.id.profile_about).setOnClickListener {
            //view?.findNavController()?.navigate(R.id.navigation_about)
            view?.findNavController()?.navigate(R.id.action_navigation_profile_to_navigation_about)
        }

        return root
    }
}