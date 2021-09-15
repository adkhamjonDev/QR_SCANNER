package uz.adkhamjon.qr_scanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=FragmentInfoBinding.inflate(inflater, container, false)


        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.share.setOnClickListener {

        }
        return binding.root
    }


}