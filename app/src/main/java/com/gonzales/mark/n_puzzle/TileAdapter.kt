package com.gonzales.mark.n_puzzle

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageButton

class TileAdapter(
    private val tileImages: ArrayList<ImageButton>,
    private val tileWidth: Int,
    private val tileHeight: Int
) : BaseAdapter() {
    override fun getCount(): Int {
        return tileImages.size
    }

    override fun getItem(position: Int): Any {
        return tileImages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tileView: ImageButton = if (convertView == null) {
            tileImages[position]
        } else {
            convertView as ImageButton
        }

        tileView.layoutParams = AbsListView.LayoutParams(tileWidth, tileHeight)

        return tileView
    }
}