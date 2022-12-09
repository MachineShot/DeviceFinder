package com.example.devicefinder2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.devicefinder2.R
import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength

class CalculationViewAdapter() : RecyclerView.Adapter<CalculationViewAdapter.ViewHolder>() {

    var mList: List<Calculation> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calculation_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.tvX.text = item.x.toString()
        holder.tvY.text = item.y.toString()
        holder.tvLength.text = item.length.toString()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(itemList: List<Calculation>){
        mList = itemList!!
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvX: TextView = itemView.findViewById(R.id.x)
        val tvY: TextView = itemView.findViewById(R.id.y)
        val tvLength: TextView = itemView.findViewById(R.id.length)
    }
}
