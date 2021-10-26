package com.gonzales.mark.n_puzzle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter(context: Context?, textViewResourceId: Int, objects: Array<String?>?) :
    ArrayAdapter<String?>(context!!, textViewResourceId, objects!!) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dropdown: View = inflater.inflate(R.layout.spn_puzzle_dropdown, parent, false)

        val entry = dropdown.findViewById<View>(R.id.tv_spn_puzzle_dropdown) as TextView
        entry.text = getItem(position)

        return entry
    }
}