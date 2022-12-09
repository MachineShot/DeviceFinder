package com.example.devicefinder2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.devicefinder2.R
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.databinding.StrengthCardBinding
import com.example.devicefinder2.databinding.UserCardBinding

class StrengthViewAdapter(private val listener: StrengthViewAdapter.StrengthItemListener) : RecyclerView.Adapter<StrengthViewAdapter.ViewHolder>() {

    interface StrengthItemListener {
        fun onClickedStrength(strengthId: Int)
    }

    var mList: List<Strength> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.strength_card, parent, false)
        val binding: StrengthCardBinding = StrengthCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, listener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.bind(item)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(itemList: List<Strength>){
        mList = itemList!!
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(private val itemBinding: StrengthCardBinding, private val listener: StrengthViewAdapter.StrengthItemListener) : RecyclerView.ViewHolder(
        itemBinding.root
    ), View.OnClickListener {
        private lateinit var strength: Strength

        init {
            itemBinding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Strength) {
            this.strength = item
            itemBinding.id.text = "ID: " + item.id.toString()
            itemBinding.strength.text = "Strength: " + item.strength.toString()
            itemBinding.calculation.text = "Calculation: " + item.calculation.toString()
            itemBinding.sensor.text = "Sensor: " + item.sensor
        }

        override fun onClick(v: View?) {
            listener.onClickedStrength(strength.id)
        }
    }
}
