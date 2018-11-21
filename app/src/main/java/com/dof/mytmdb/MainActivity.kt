package com.dof.mytmdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.MotionEventCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.dof.mytmdb.ui.main.DiscoverFragment
import com.dof.mytmdb.view.BackdropBehavior
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var backdropBehavior : BackdropBehavior

    companion object {
        private const val SP_LAST_ITEM_USED = "sp_last_item_used"
        private const val DEBUG_TAG = "Gestures"
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frontContainer, DiscoverFragment.newInstance())
                    .commitNow()
        }

        backdropBehavior = frontContainer.findBehavior()
        with(backdropBehavior) {
            attachBackContainer(R.id.backContainer)
            attachToolbar(R.id.toolbar)
        }

        with(toolbar) {
            setTitle(R.string.app_name)
        }

        navigationView.setNavigationItemSelectedListener {
            backdropBehavior.close()
            true
        }
        
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        outState?.putInt(SP_LAST_ITEM_USED, navigationView.get)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)

        val action: Int = MotionEventCompat.getActionMasked(event)
        when (action) {
            MotionEvent.ACTION_UP -> {
                if (isScrolling) {
                    backdropBehavior.replaceView(currentYPos)
                    isScrolling = false
                    beginYPos = 0F
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }

    override fun onDown(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(
            event1: MotionEvent,
            event2: MotionEvent,
            velocityX: Float,
            velocityY: Float
    ): Boolean {
//        Log.d(DEBUG_TAG, "onFling: x: ${event1.x} y: ${event1.y} x: ${event2.x} y: ${event2.y}")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
//        Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    private var beginYPos : Float = 0F
    private var currentYPos : Float = 0F
    private var isScrolling : Boolean = false

    override fun onScroll(event1: MotionEvent, event2: MotionEvent,
            distanceX: Float, distanceY: Float): Boolean {

        Log.d(TAG, "${event1.y} => ${backdropBehavior.endY1} && ${event1.y} <= ${backdropBehavior.endY2}")

        if (event1.y >= backdropBehavior.startY1
                && event1.y <= backdropBehavior.startY2
                && backdropBehavior.state == BackdropBehavior.DropState.CLOSE) {
            isScrolling = true

            if (event2.y >= backdropBehavior.minHeight
                    && event2.y <= backdropBehavior.maxHeight
                    && beginYPos == 0F) {
                beginYPos = event2.y
            }

            currentYPos = backdropBehavior.minHeight + (event2.y - beginYPos)
            if (currentYPos >= backdropBehavior.minHeight && currentYPos <= backdropBehavior.maxHeight) {
                frontContainer.y = currentYPos
            }
        } else if (event1.y >= backdropBehavior.endY1
                && event1.y <= backdropBehavior.endY2
                && backdropBehavior.state == BackdropBehavior.DropState.OPEN) {
            isScrolling = true

            if (event2.y >= backdropBehavior.minHeight
                    && event2.y <= backdropBehavior.maxHeight
                    && beginYPos == 0F) {
                beginYPos = event2.y
            }

            currentYPos = backdropBehavior.maxHeight - (beginYPos - event2.y)
            if (currentYPos >= backdropBehavior.minHeight && currentYPos <= backdropBehavior.maxHeight) {
                frontContainer.y = currentYPos
            }
        }
        return true
    }

    override fun onShowPress(event: MotionEvent) {
//        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }
}