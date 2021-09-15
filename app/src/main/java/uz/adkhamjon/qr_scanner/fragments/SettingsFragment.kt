package uz.adkhamjon.qr_scanner.fragments

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.databinding.FragmentSettingsBinding
import uz.adkhamjon.qr_scanner.sharedPreferences.SoundSharedPreference
import uz.adkhamjon.qr_scanner.sharedPreferences.VibrateSharedPreference


class SettingsFragment : Fragment() {
    private lateinit var binding:FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSettingsBinding.inflate(inflater, container, false)
        binding.volume.isChecked=SoundSharedPreference.getInstance(requireContext()).haslang
        binding.vibration.isChecked=VibrateSharedPreference.getInstance(requireContext()).haslang
        binding.volume.setOnCheckedChangeListener { p0, p1 ->
            if(p1){
                SoundSharedPreference.getInstance(requireContext()).setHasLang(true)
            }
            else if(!p1){
                SoundSharedPreference.getInstance(requireContext()).setHasLang(false)
            }
        }
        binding.vibration.setOnCheckedChangeListener { p0, p1 ->
            if(p1){
                VibrateSharedPreference.getInstance(requireContext()).setHasLang(true)
            }
            else if(!p1){
                VibrateSharedPreference.getInstance(requireContext()).setHasLang(false)
            }
        }
        binding.feedback.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Feedback")
            dialog.setMessage("Email: Qr Scanner")
            dialog.setPositiveButton("Send",
                DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data =
                        Uri.parse("mailto:") // only email apps should handle this

                    intent.putExtra(Intent.EXTRA_EMAIL, "adkhamjon.rakhimov.dev@gamil.com")
                    intent.putExtra(Intent.EXTRA_SUBJECT, "QR_SCANNER")
                    startActivity(intent)


                })
            dialog.setNegativeButton("Back",
                DialogInterface.OnClickListener { dialog, id ->

                })
            val alertDialog = dialog.create()
            alertDialog.show()
        }
        binding.rate.setOnClickListener {
            val uri = Uri.parse("market://details?id=" + requireActivity().packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=uz.adkhamjon.qr_scanner")
                    )
                )
            }
        }
        binding.share.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setText("http://play.google.com/store/apps/details?id=uz.adkhamjon.qr_scanner")
                .startChooser()
        }
        return binding.root
    }

}