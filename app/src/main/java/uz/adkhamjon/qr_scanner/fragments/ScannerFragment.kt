package uz.adkhamjon.qr_scanner.fragments

import android.Manifest
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.databinding.FragmentScannerBinding

class ScannerFragment : Fragment(),View.OnClickListener {
    private lateinit var binding:FragmentScannerBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentScannerBinding.inflate(inflater,container,false)
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        binding.apply {
            codeScanner.decodeCallback = DecodeCallback {data->
                requireActivity().runOnUiThread {


                }
                scannerView.setOnClickListener {
                    codeScanner.startPreview()
                }

            }
        }
        setOnClick()
        return binding.root
    }
    private fun setOnClick(){
        binding.back.setOnClickListener(this)
        binding.front.setOnClickListener(this)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.back -> {
                codeScanner.camera = CodeScanner.CAMERA_FRONT
                binding.back.visibility = View.GONE
                binding.front.visibility = View.VISIBLE
            }
            R.id.front -> {
                codeScanner.camera = CodeScanner.CAMERA_BACK
                binding.back.visibility = View.VISIBLE
                binding.front.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        codeScanner.startPreview()
    }

    override fun onDestroy() {
        super.onDestroy()
        codeScanner.stopPreview()
    }
}