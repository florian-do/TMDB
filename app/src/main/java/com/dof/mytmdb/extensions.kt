package com.dof.mytmdb

import android.content.res.Resources
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.util.DisplayMetrics
import android.view.ViewTreeObserver
import android.widget.TextView


/**
 * from https://github.com/Semper-Viventem/Material-backdrop
 */

fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
    if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")

    (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
            ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
}

fun dp2px(dp : Int) : Int {
    val metrics = Resources.getSystem().getDisplayMetrics()
    val px = dp * (metrics.densityDpi / 160f)
    return Math.round(px)
}

inline fun <T: View> T.afterMeasured(crossinline onFinished: T.() -> Unit)  {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                onFinished()
            }
        }
    })
}