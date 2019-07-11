package com.example.espl.sampleappkotlin.giphysearch

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.R.attr.spacing



public class GridSpacingItemDecoration(mSpanCount: Int, mSpacing: Int, mIncludeEdge: Boolean) : RecyclerView.ItemDecoration() {
    private var spanCount: Int = mSpanCount
    private var spacing: Int = mSpacing
    private var includeEdge: Boolean = mIncludeEdge

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}