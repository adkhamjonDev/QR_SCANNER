package uz.adkhamjon.qr_scanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import uz.adkhamjon.qr_scanner.databinding.HistoryItemBinding
import uz.adkhamjon.qr_scanner.databinding.RvItemBinding
import uz.adkhamjon.qr_scanner.models.DbModel
import uz.adkhamjon.qr_scanner.models.MenuModel

class HistoryRvAdapter(var list: ArrayList<DbModel>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<HistoryRvAdapter.MyViewHolder>(), Filterable {
    val list1=ArrayList<DbModel>(list)
    inner class MyViewHolder(var historyItemBinding: HistoryItemBinding): RecyclerView.ViewHolder(
        historyItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dbModel = list[position]
        holder.historyItemBinding.date.text=dbModel.date
        holder.historyItemBinding.msg.text=dbModel.data
        holder.historyItemBinding.share.setOnClickListener {
            onItemClickListener.onItemShare(dbModel.data)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemMenu(dbModel)
        }


    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onItemMenu(dbModel: DbModel)
        fun onItemShare(str:String)
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val newList=ArrayList<DbModel>()
            if(constraint==null|| constraint.isEmpty()){
                newList.addAll(list1)
            }else{
                val filterPattern=constraint.toString().toString().toLowerCase().trim()
                for (i in 0 until list1.size){
                    if(list1[i].data?.toLowerCase()!!.contains(filterPattern)){
                        newList.add(list1[i])
                    }
                }
            }
            val results=FilterResults()
            results.values=newList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list.clear()
            list.addAll(results.values as ArrayList<DbModel>)
            notifyDataSetChanged()
        }
    }
}