package com.thernat.foursquareapp.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.R
import kotlinx.android.synthetic.main.list_item_venue.view.*

/**
 * Created by m.rafalski on 01/06/2019.
 */
class VenueAdapter(context: Context, private var venues: ArrayList<Venue>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder: ViewHolder
        val rowView: View?
        if(convertView == null){
            rowView = inflater.inflate(R.layout.list_item_venue,parent,false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }
        val venue = venues[position]

        with(viewHolder){
            tvName.text = venue.name
            tvAddress.text = venue.location.address.orEmpty()
            tvDistance.text = "${venue.location.distance} m"
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return venues[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return venues.size
    }

    fun replaceData(venues: List<Venue>) {
        this.venues = ArrayList(venues)
        notifyDataSetChanged()
    }


    private class ViewHolder(view: View) {
        val tvName = view.tvName
        val tvAddress = view.tvAddress
        val tvDistance = view.tvDistance
    }
}