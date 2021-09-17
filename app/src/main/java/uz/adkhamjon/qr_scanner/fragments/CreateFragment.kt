package uz.adkhamjon.qr_scanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.adapters.MenuRvAdapter
import uz.adkhamjon.qr_scanner.databinding.FragmentCreateBinding
import uz.adkhamjon.qr_scanner.models.MenuModel

class CreateFragment : Fragment() {
    private lateinit var binding:FragmentCreateBinding
    private lateinit var list:ArrayList<MenuModel>
    private lateinit var menuRvAdapter: MenuRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentCreateBinding.inflate(inflater, container, false)
        loadData()
        menuRvAdapter= MenuRvAdapter(list,object :MenuRvAdapter.OnItemClickListener{
            override fun onItemMenu(str: String) {
                Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
            }

        })
        binding.recView.adapter=menuRvAdapter
        return binding.root
    }

    private fun loadData() {
        list= ArrayList()
        list.add(MenuModel(R.drawable.ic_mail,"Email"))
        list.add(MenuModel(R.drawable.ic_chat,"Message"))
        list.add(MenuModel(R.drawable.ic_pin,"Location"))
        list.add(MenuModel(R.drawable.ic_calendar,"Event"))
        list.add(MenuModel(R.drawable.ic_avatar,"Contact"))
        list.add(MenuModel(R.drawable.ic_call,"Telephone"))
        list.add(MenuModel(R.drawable.ic_file,"Text"))
        list.add(MenuModel(R.drawable.ic_wifi_sign,"Wifi"))
        list.add(MenuModel(R.drawable.ic_globe,"Url"))
    }
}