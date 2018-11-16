package com.dof.mytmdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dof.mytmdb.ui.main.DiscoverFragment
import com.dof.mytmdb.view.BackdropBehavior
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frontContainer, DiscoverFragment.newInstance())
                    .commitNow()
        }

        val backdropBehavior : BackdropBehavior = frontContainer.findBehavior()

        with(backdropBehavior) {
            attachBackContainer(R.id.backContainer)
            attachToolbar(R.id.toolbar)
        }

        with(toolbar) {
            setTitle("test")
        }

        navigationView.setNavigationItemSelectedListener {
            backdropBehavior.close()
            true
        }
    }
}