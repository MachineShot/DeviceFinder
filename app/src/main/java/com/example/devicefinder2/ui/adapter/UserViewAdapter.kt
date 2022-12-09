package com.example.devicefinder2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.devicefinder2.R
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.databinding.UserCardBinding
import kotlinx.android.synthetic.main.user_card.view.*
import kotlinx.android.synthetic.main.user_details.view.*

class UserViewAdapter(private val listener: UserItemListener) : RecyclerView.Adapter<UserViewAdapter.ViewHolder>() {

    interface UserItemListener {
        fun onClickedUser(userId: Int)
    }

    var mList: ArrayList<User> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_card, parent, false)
        val binding: UserCardBinding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, listener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.bind(item)
        //holder.tvSensor.text = item.sensor
        //holder.tvStrength.text = item.strength.toString()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(itemList: List<User>){
        for (i in itemList.indices) {
            if(!mList.any { it.mac == itemList[i].mac }) {
                mList.add(itemList[i])
            }
        }
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(private val itemBinding: UserCardBinding, private val listener: UserViewAdapter.UserItemListener) : RecyclerView.ViewHolder(
        itemBinding.root
    ), View.OnClickListener {
        /*
        val tvMac: TextView = itemView.findViewById(R.id.mac)
        val tvSensor: TextView = itemView.findViewById(R.id.sensor)
        val tvStrength: TextView = itemView.findViewById(R.id.strength)
         */

        private lateinit var user: User

        init {
            itemBinding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: User) {
            this.user = item
            itemBinding.mac.text = item.mac
        }

        override fun onClick(v: View?) {
            listener.onClickedUser(user.id)
        }
    }
}
