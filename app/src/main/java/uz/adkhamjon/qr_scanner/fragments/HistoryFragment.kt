package uz.adkhamjon.qr_scanner.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.adkhamjon.qr_scanner.R
import uz.adkhamjon.qr_scanner.adapters.HistoryRvAdapter
import uz.adkhamjon.qr_scanner.databinding.FragmentHistoryBinding
import uz.adkhamjon.qr_scanner.db.AppDataBase
import uz.adkhamjon.qr_scanner.models.DbModel



class HistoryFragment : Fragment() {

    private lateinit var binding:FragmentHistoryBinding
    private lateinit var appDataBase: AppDataBase
    private lateinit var historyRvAdapter: HistoryRvAdapter
    private lateinit var list:ArrayList<DbModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHistoryBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        list= ArrayList(appDataBase.scannerDao().getAllScanners())
        historyRvAdapter= HistoryRvAdapter(list,object :HistoryRvAdapter.OnItemClickListener{
                override fun onItemMenu(dbModel: DbModel) {
                    val bundle = bundleOf("data" to dbModel.data, "date" to dbModel.date,"image" to dbModel.image)
                    findNavController().navigate(uz.adkhamjon.qr_scanner.R.id.infoFragment, bundle)
                }
                override fun onItemShare(str: String) {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        str
                    )
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                }
            })
        binding.recView.adapter=historyRvAdapter
        binding.searchView.queryHint = "Enter text for search..."
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                historyRvAdapter.filter.filter(newText)
                return false
            }
        })
        return binding.root
    }
}
