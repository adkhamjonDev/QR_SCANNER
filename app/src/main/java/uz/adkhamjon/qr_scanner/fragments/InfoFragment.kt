package uz.adkhamjon.qr_scanner.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
    private var str:String?=null
    private var date:String?=null
    private var image:ByteArray?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentInfoBinding.inflate(inflater, container, false)
        if(arguments!=null){
            str=arguments?.getString("data")as String
            date=arguments?.getString("date")as String
            image=arguments?.getByteArray("image")as ByteArray
        }
        binding.msg.text=str
        binding.txt.text=str
        binding.date.text=date.toString()
        val i=image
        val bitmap = image?.let { BitmapFactory.decodeByteArray(i, 0, it.size) }
        binding.image.setImageBitmap(bitmap)
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                str
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        binding.openInBrowser.setOnClickListener {
            val intent= Intent("android.intent.action.VIEW")
            intent.data = Uri.parse(str.toString())
            startActivity(intent)
        }
        return binding.root
    }


}