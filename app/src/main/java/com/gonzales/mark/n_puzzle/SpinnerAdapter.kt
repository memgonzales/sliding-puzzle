package com.gonzales.mark.n_puzzle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Adapter for the spinner that handles the selection of the puzzle image.
 *
 * @param context Current context. This value cannot be <code>null</code>.
 * @param textViewResourceId Resource ID for a layout file containing a layout to use when instantiating views.
 * @param objects Objects to represent in the <code>ListView</code>. This value cannot be <code>null</code>.
 */
class SpinnerAdapter(context: Context?, textViewResourceId: Int, objects: Array<String?>?) :
    ArrayAdapter<String?>(context!!, textViewResourceId, objects!!) {
    /**
     * Gets a <code>View</code> that displays in the drop down popup the data at the specified position
     * in the data set.
     *
     * @param position Index of the item whose view we want.
     * @param convertView This value may be <code>null</code>.
     * @param parent This value cannot be <code>null</code>.
     */
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dropdown: View = inflater.inflate(R.layout.spn_puzzle_dropdown, parent, false)

        val entry = dropdown.findViewById<View>(R.id.tv_spn_puzzle_dropdown) as TextView
        entry.text = getItem(position)

        return entry
    }
}