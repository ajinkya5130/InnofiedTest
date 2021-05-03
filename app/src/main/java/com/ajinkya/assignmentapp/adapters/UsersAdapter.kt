package com.ajinkya.assignmentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajinkya.assignmentapp.databinding.ItemLoadingBinding
import com.ajinkya.assignmentapp.databinding.SingleUserInfoLayoutBinding
import com.ajinkya.assignmentapp.models.UsersInfoModel

class UsersAdapter(var listOfUsers: ArrayList<UsersInfoModel.Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    // private var listOfUsers:ArrayList<UsersInfoModel.Data> = arrayListOf()


    class MyViewHolder(val binding: SingleUserInfoLayoutBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ITEM) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: SingleUserInfoLayoutBinding = SingleUserInfoLayoutBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return MyViewHolder(binding)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: ItemLoadingBinding = ItemLoadingBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return MyItemLoadingViewHolder(binding)
        }


    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is MyViewHolder) {
            viewHolder.binding.model = listOfUsers[position]
        } else if (viewHolder is MyItemLoadingViewHolder) {
            showLoadingView(
                viewHolder,
                position
            )
        }

    }


    override fun getItemCount(): Int {
        return if (listOfUsers.size == 0) 0 else listOfUsers.size
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (listOfUsers.get(position) == UsersInfoModel.Data()) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(list: ArrayList<UsersInfoModel.Data>) {
        listOfUsers = list
        notifyDataSetChanged()

    }

    private fun showLoadingView(
        viewHolder: MyItemLoadingViewHolder,
        position: Int
    ) {
        //ProgressBar would be displayed
    }


    class MyItemLoadingViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}