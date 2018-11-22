package com.dof.mytmdb.ui.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.ColorUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.dof.mytmdb.Const
import com.dof.mytmdb.R
import com.dof.mytmdb.databinding.ActivityMovieBinding
import com.dof.mytmdb.dp2px
import com.dof.mytmdb.module.GlideApp
import com.dof.mytmdb.viewmodel.MovieViewModel
import it.sephiroth.android.library.uigestures.UIGestureRecognizer
import it.sephiroth.android.library.uigestures.UIGestureRecognizerDelegate
import it.sephiroth.android.library.uigestures.UIPanGestureRecognizer
import kotlinx.android.synthetic.main.activity_movie.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE



class MovieActivity : AppCompatActivity(), UIGestureRecognizerDelegate.Callback,
        UIGestureRecognizer.OnActionListener {

    private var id : Int = 0
    private lateinit var viewModel : MovieViewModel
    private lateinit var binding : ActivityMovieBinding
    private lateinit var delegate: UIGestureRecognizerDelegate
    private lateinit var pan : UIPanGestureRecognizer

    private var movieTitleY : Int = 0
    private var toolbarNotificationHeight : Int = 0

    companion object {
        private val THRESHOLD_TOOLBAR = 200F
        private val TAG = "MovieActivity"
        private val ARG_ID = "arg_id"

        fun newActivity(a: Activity, id: Int) {
            val intent = Intent(a, MovieActivity::class.java)
            intent.putExtra(ARG_ID, id)
            a.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        delegate = UIGestureRecognizerDelegate(null)
        delegate.callback = this

        pan = UIPanGestureRecognizer(this)
        pan.actionListener = this


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.setStatusBarColor(Color.argb(0, 0, 0, 0));


        delegate.addGestureRecognizer(pan)

        id = intent.getIntExtra(ARG_ID, 0)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getMovieDetail(id).observe(this, Observer {
            it.let {
                binding.data = it
                GlideApp.with(this)
                        .load(Const.URL_PHOTO_ORIGINAL+it?.backdrop_path)
                        .into(backDrop)

                GlideApp.with(this)
                        .load(Const.URL_PHOTO+it?.poster_path)
                        .into(cover)

                toolbar_title.text = it?.original_title ?: getString(R.string.app_name)
                Log.d(TAG, " NAME : ${Const.URL_PHOTO+it?.backdrop_path}")
            }
        })

        movie_title.viewTreeObserver.addOnGlobalLayoutListener {
            val location = intArrayOf(0, 0)
            movie_title.getLocationOnScreen(location)
            movieTitleY = location[1]

            toolbarNotificationHeight = toolbar.height + resources.getDimension(R.dimen.statusBarHeight).toInt()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener { view, i1, i2, i3, i4 ->

                if (i2 >= movieTitleY - toolbarNotificationHeight && i2 <= movieTitleY + THRESHOLD_TOOLBAR) {
                    val tmp = i2 - (movieTitleY - toolbarNotificationHeight)

                    if ((THRESHOLD_TOOLBAR - tmp) >= 0) {
                        val titleAlpha = ((THRESHOLD_TOOLBAR - tmp) * 1F / THRESHOLD_TOOLBAR)
                        val toolbarTitleAlpha = (tmp * 1F / THRESHOLD_TOOLBAR)
                        val toolbarAlpha = ((tmp * 255) / THRESHOLD_TOOLBAR).toInt()
                        val color = ColorUtils.setAlphaComponent(resources.getColor(R.color.background), toolbarAlpha)

                        movie_title.alpha = titleAlpha
                        toolbar_title.alpha = toolbarTitleAlpha

//                        Log.d(TAG, "${tmp} | ${titleAlpha}")

                        window.statusBarColor = color
                        toolbar.setBackgroundColor(color)
                    }
                } else {
                    if (i2 <= movieTitleY - toolbarNotificationHeight) {
                        if (movie_title.alpha != 1F || toolbar_title.alpha != 0F) {
                            movie_title.alpha = 1F
                            toolbar_title.alpha = 0F
                            val color = ColorUtils.setAlphaComponent(resources.getColor(R.color.background), 0)
                            toolbar.setBackgroundColor(color)
                            window.statusBarColor = color
                        }
                    } else {
                        if (movie_title.alpha != 0F || toolbar_title.alpha != 1F) {
                            val color = ColorUtils.setAlphaComponent(resources.getColor(R.color.background), 255)

                            movie_title.alpha = 0F
                            toolbar_title.alpha = 1F
                            toolbar.setBackgroundColor(color)
                            window.statusBarColor = color
                        }
                    }
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        delegate.onTouchEvent(binding.root, ev!!)
        return super.dispatchTouchEvent(ev)
    }

    private var beginYPos : Float = 0F
    private var currentYPos : Float = 0F
    private var defaultHeight : Int = 0

    override fun onGestureRecognized(recognizer: UIGestureRecognizer) {
//        Log.d(TAG, "onGestureRecognized(${recognizer.currentLocationX} / ${recognizer.currentLocationY}). state: " + recognizer.state);
//        Log.d(TAG, "${pan.scrollX} / ${pan.scrollY} ")
//        Log.d(TAG, "${pan.relativeScrollX} / ${pan.relativeScrollY} ")
//        Log.d(TAG, "${pan.translationX} / ${pan.translationY} ")

//        Log.d(TAG, "${movie_title.y} / ${recognizer.currentLocationY}")

        if (scrollView.scrollY == 0) {
            if (beginYPos == 0F)
                beginYPos = recognizer.currentLocationY
            if (defaultHeight == 0)
                defaultHeight = backDrop.height

            currentYPos = recognizer.currentLocationY - beginYPos

//            if (currentYPos >= 10F) {
//                var tmp = currentYPos / 10
//                Log.d(TAG, " test ")
//                backDrop.layoutParams.height = defaultHeight + tmp.toInt()
//            }

            backDrop.scaleY += 0.01F
            backDrop.scaleX += 0.01F
            backDrop.layoutParams.height = defaultHeight + currentYPos.toInt()

        }

        if (recognizer.state == UIGestureRecognizer.State.Ended) {
            beginYPos = 0F
            backDrop.scaleY = 1F
            backDrop.scaleX = 1F
        }
    }


    override fun shouldBegin(recognizer: UIGestureRecognizer): Boolean {
        Log.d(TAG, "shouldBegin ")
        return true
    }

    override fun shouldReceiveTouch(recognizer: UIGestureRecognizer): Boolean {
//        Log.d(TAG, "shouldReceiveTouch ")
        return true;
    }

    override fun shouldRecognizeSimultaneouslyWithGestureRecognizer(recognizer: UIGestureRecognizer, other: UIGestureRecognizer): Boolean {
        Log.d(TAG, "shouldRecognizeSimultaneouslyWithGestureRecognizer ")
        return true;
    }
}