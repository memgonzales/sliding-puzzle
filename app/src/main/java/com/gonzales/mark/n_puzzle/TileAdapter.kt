package com.gonzales.mark.n_puzzle

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageButton

/**
 * Adapter for <code>GridViewGesture</code> that handles the tiles in the puzzle grid.
 *
 * @param tileImages Images displayed on the tiles in the puzzle grid.
 * @param tileWidth Width of each tile in the puzzle grid.
 * @param tileHeight Height of each tile in the puzzle grid.
 */
class TileAdapter(
    private val tileImages: ArrayList<ImageButton>,
    private val tileWidth: Int,
    private val tileHeight: Int
) : BaseAdapter() {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    override fun getCount(): Int {
        return tileImages.size
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's data set.
     * @return Data at the specified position.
     */
    override fun getItem(position: Int): Any {
        return tileImages[position]
    }

    /**
     * Get the row ID associated with the specified position in the list.
     *
     * @param position Position of the item within the adapter's data set whose row id we want.
     * @return ID of the item at the specified position.
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated,
     * the parent <code>View (GridView, ListView...)</code> will apply default layout parameters
     * unless you use <code>LayoutInflater.inflate(int, android.view.ViewGroup, boolean)</code>
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position Position of the item within the adapter's data set of the item whose view we want.
     * @param convertView Old view to reuse, if possible. Note: You should check that this view is non-null
     * and of an appropriate type before using. If it is not possible to convert this view to display the
     * correct data, this method can create a new view. Heterogeneous lists can specify their number of view
     * types, so that this View is always of the right type.
     * @param parent Parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
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