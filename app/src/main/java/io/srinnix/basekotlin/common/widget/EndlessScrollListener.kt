package io.srinnix.basekotlin.common.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Tuha on 10/5/2017.
 */
abstract class EndlessScrollListener(private val mLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    companion object {
        const val SCROLL_STATE_IDLE_INSERT = 3
    }

    private var loading = true
    private val visibleThreshold = 1
    private var firstVisibleItem: Int = 0
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        when {
            dy < 0 -> {
                return
            }
            dy == 0 -> {
                if (dx == 0 && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    onStateChanged(SCROLL_STATE_IDLE_INSERT)
                }
            }
            else -> {
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                totalItemCount = mLayoutManager.itemCount
                if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold && totalItemCount > visibleThreshold) {
                    onLoadMore()
                    loading = true
                }
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        onStateChanged(newState)
    }

    fun setLoaded() {
        loading = false
    }

    fun enableLoadMore(enable: Boolean) {
        loading = !enable
    }
    abstract fun onLoadMore()

    abstract fun onStateChanged(newState: Int)
}