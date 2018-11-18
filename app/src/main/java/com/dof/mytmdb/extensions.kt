package com.dof.mytmdb

import android.support.design.widget.CoordinatorLayout
import android.view.View

/**
 * from https://github.com/Semper-Viventem/Material-backdrop
 */

fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
    if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")

    (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
            ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
}