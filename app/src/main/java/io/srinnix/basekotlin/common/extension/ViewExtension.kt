package io.srinnix.basekotlin.common.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.view.TouchDelegate
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.srinnix.basekotlin.common.utils.DimensionUtils
import io.srinnix.basekotlin.common.widget.EndlessScrollListener


/**
 * Created by Tuha on 8/8/2017.
 */

object ViewUtils {
    fun getBitmapFromXml(context: Context, drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(context, drawableId) ?: return null
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}

fun RecyclerView.setupLoadEndless(onLoadMore: (() -> Unit)? = null, onStateChanged: ((newState: Int) -> Unit)? = null): EndlessScrollListener {
    val layoutManger = layoutManager as LinearLayoutManager
    val onScrollListener = object : EndlessScrollListener(layoutManger) {

        override fun onLoadMore() {
            onLoadMore?.invoke()
        }

        override fun onStateChanged(newState: Int) {
            onStateChanged?.invoke(newState)
        }
    }

    addOnScrollListener(onScrollListener)

    return onScrollListener
}

/**
 * The method to expand click area of view
 * unit: dp
 */
fun View.expandClickArea(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    if (left == 0 && top == 0 && right == 0 && bottom == 0) {
        return
    }

    (this.parent as? View)?.post {
        val rect = Rect()
        this.getHitRect(rect)
        rect.left -= DimensionUtils.dpToPx(left.toFloat()).toInt()
        rect.top -= DimensionUtils.dpToPx(top.toFloat()).toInt()
        rect.right += DimensionUtils.dpToPx(right.toFloat()).toInt()
        rect.bottom += DimensionUtils.dpToPx(bottom.toFloat()).toInt()

        val touchDelegate = TouchDelegate(rect, this)
        if (View::class.java.isInstance(this.parent)) {
            (this.parent as View).touchDelegate = touchDelegate
        }
    }
}

fun View.showKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}