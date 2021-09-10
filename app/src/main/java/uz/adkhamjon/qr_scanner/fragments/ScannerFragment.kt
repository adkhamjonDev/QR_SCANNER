package uz.adkhamjon.qr_scanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.databinding.FragmentScannerBinding

class ScannerFragment : Fragment(R.layout.fragment_scanner) {
    private val binding by viewBinding(FragmentScannerBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {




        }
    }
}