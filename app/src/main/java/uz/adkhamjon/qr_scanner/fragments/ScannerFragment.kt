package uz.adkhamjon.qr_scanner.fragments

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.databinding.FragmentScannerBinding
import uz.adkhamjon.qr_scanner.db.AppDataBase
import uz.adkhamjon.qr_scanner.models.DbModel
import uz.adkhamjon.qr_scanner.sharedPreferences.SoundSharedPreference
import uz.adkhamjon.qr_scanner.sharedPreferences.VibrateSharedPreference
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import com.karumi.dexter.listener.single.PermissionListener as PermissionListener1

class ScannerFragment : Fragment(),View.OnClickListener {
    private lateinit var binding: FragmentScannerBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var byteArray: ByteArray
    private lateinit var appDataBase: AppDataBase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.decodeCallback = DecodeCallback { data ->
            requireActivity().runOnUiThread {
                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.short_msg)
                if (SoundSharedPreference.getInstance(requireContext()).haslang) {
                    mediaPlayer.start()
                }
                val date = Date()
                val simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyy ")
                val currentDate = simpleDateFormat.format(date)
                val multiFormatWriter=MultiFormatWriter()
                try {
                    val image=multiFormatWriter.encode(data.toString(),BarcodeFormat.QR_CODE,350,350)
                    val barcodeEncoder=BarcodeEncoder()
                    val bitmap=barcodeEncoder.createBitmap(image)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    byteArray = byteArrayOutputStream.toByteArray()
                }catch (e:Exception){
                    e.printStackTrace()
                }
                appDataBase.scannerDao().addScanner(DbModel(data = data.toString(),date = currentDate,image = byteArray))
                val bundle = bundleOf("data" to data.toString(), "date" to currentDate,"image" to byteArray)
                val v = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(VibrateSharedPreference.getInstance(requireContext()).haslang) {
                    v.vibrate(300)
                }
                findNavController().navigate(R.id.infoFragment, bundle)
            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        setOnClick()
        return binding.root
    }

    private fun setOnClick() {
        binding.back.setOnClickListener(this)
        binding.front.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
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
        Dexter.withContext(context)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(object : PermissionListener1 {
                        @RequiresApi(Build.VERSION_CODES.R)
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            codeScanner.startPreview()
                           }
                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
                            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                        }
                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                        ) {
                            token?.continuePermissionRequest()
                        }
                    }).check()

    }

    override fun onDestroy() {
        super.onDestroy()
        codeScanner.stopPreview()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}