package com.dof.mytmdb.view

import android.content.Context
import android.support.annotation.IdRes
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.dof.mytmdb.R

class BackdropBehavior : CoordinatorLayout.Behavior<View> {

    enum class DropState {
        OPEN,
        CLOSE
    }

    companion object {
        private val DEFAULT_STATE = DropState.CLOSE
        private const val DURATION_TIME = 300L
        private const val NO_DURATION = 0L
    }

    private val TAG = "BackdropBehavior"

    private var toolbar : Toolbar ?= null
    private var backContainer : View ?= null
    private var child : View ?= null

    private var backContainerId : Int ?= null
    private var toolbarId : Int ?= null

    private var state : DropState = DEFAULT_STATE

    constructor() : super()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View, dependency: View): Boolean {
        if (toolbarId == null || backContainerId == null) return false

        return when (dependency.id) {
            toolbarId -> true
            backContainerId -> true
            else -> false
        }
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        this.child = child
        when (dependency.id) {
            toolbarId -> toolbar = dependency as Toolbar
            backContainerId -> backContainer = dependency
        }

        if (toolbar != null && backContainer != null) {
            initViews(parent, child, toolbar!!, backContainer!!)
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

    fun close() {
        if (state == DropState.CLOSE)
            return ;

        state = DropState.CLOSE
        onDrawDrop(child!!, backContainer!!, toolbar!!)
    }

    private fun initViews(parent: CoordinatorLayout, child: View, toolbar: Toolbar, backContainer: View) {
        backContainer.y = toolbar.y + toolbar.height
        child.layoutParams.height = parent.height - toolbar.height

        onDrawDrop(child, backContainer, toolbar, false)
        with (toolbar) {
            setNavigationOnClickListener {
                state = when (state) {
                    DropState.CLOSE -> DropState.OPEN
                    DropState.OPEN -> DropState.CLOSE
                }

                onDrawDrop(child, backContainer, toolbar)
            }
        }
    }

    private fun onDrawDrop(child: View, backContainer: View, toolbar: Toolbar, drawAnimation: Boolean = true) {
        when (state) {
            DropState.OPEN -> {
                drawOpenedState(child, backContainer, drawAnimation)
                toolbar.setNavigationIcon(R.drawable.ic_close)
            }
            DropState.CLOSE -> {
                drawCloseState(child, backContainer, drawAnimation)
                toolbar.setNavigationIcon(R.drawable.ic_menu)
            }
        }
    }

    private fun drawOpenedState(child: View, backContainer: View, drawAnimation : Boolean = true) {
        val position = child.y + backContainer.height
        val duration = if (drawAnimation) DURATION_TIME else NO_DURATION

        child.animate().y(position).setDuration(duration).start()
    }

    private fun drawCloseState(child: View, backContainer: View, drawAnimation : Boolean = true) {
        val position = backContainer.y
        val duration = if (drawAnimation) DURATION_TIME else NO_DURATION

        child.animate().y(position).setDuration(duration).start()
    }

    fun attachBackContainer(@IdRes  backContainerId : Int) {
        this.backContainerId = backContainerId
    }

    fun attachToolbar(@IdRes toolbarId : Int) {
        this.toolbarId = toolbarId
    }
}