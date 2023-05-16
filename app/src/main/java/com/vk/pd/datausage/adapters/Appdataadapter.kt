package com.vk.pd.datausage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.vk.pd.datausage.R
import com.vk.pd.datausage.models.AppDataUsageModel


class Appdataadapter(private val dataSet:  MutableList<AppDataUsageModel>) :
    RecyclerView.Adapter<Appdataadapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appname: AppCompatTextView
        val senddata: AppCompatTextView
        val receiveddata: AppCompatTextView
        val appimsgeicon: AppCompatImageView
        init {
            // Define click listener for the ViewHolder's View
            appname = view.findViewById(R.id.appCompatTextView2)
            senddata = view.findViewById(R.id.appCompatTextView4)
            receiveddata = view.findViewById(R.id.appCompatTextView5)
            appimsgeicon= view.findViewById(R.id.appimsgeicon)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listdatalayout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.appname.text = dataSet[position].appName
        viewHolder.senddata.text = (dataSet[position].sentMobile/1080*1080).toString()
        viewHolder.receiveddata.text = (dataSet[position].receivedMobile/1080*1080).toString()
       viewHolder.appimsgeicon.setImageDrawable(dataSet[position].appIcon)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
