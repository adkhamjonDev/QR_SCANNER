package uz.adkhamjon.qr_scanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.adkhamjon.qr_scanner.databinding.RvItemBinding
import uz.adkhamjon.qr_scanner.models.MenuModel

class MenuRvAdapter( var list: List<MenuModel>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<MenuRvAdapter.MyViewHolder>(){
    inner class MyViewHolder(var rvItemBinding: RvItemBinding): RecyclerView.ViewHolder(
        rvItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val menuModel = list[position]
        holder.rvItemBinding.txt.text=menuModel.label
        menuModel.image?.let { holder.rvItemBinding.image.setImageResource(it) }
        holder.itemView.setOnClickListener {
            menuModel.label?.let { it1 -> onItemClickListener.onItemMenu(it1) }
        }


    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onItemMenu(str:String)
    }
}