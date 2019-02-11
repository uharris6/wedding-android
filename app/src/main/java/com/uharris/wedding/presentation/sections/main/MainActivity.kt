package com.uharris.wedding.presentation.sections.main

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.uharris.wedding.R
import com.uharris.wedding.presentation.sections.gift.GiftFragment
import com.uharris.wedding.presentation.sections.home.HomeFragment
import com.uharris.wedding.presentation.sections.photos.PhotosFragment
import com.uharris.wedding.presentation.sections.sites.SitesFragment
import com.uharris.wedding.presentation.sections.wishes.WishesFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    private var prevMenuItem: MenuItem? = null

    override fun onPageSelected(position: Int) {
        if (prevMenuItem != null) {
            prevMenuItem?.isChecked = false
        }
        else
        {
            navigation.menu.getItem(0).isChecked = false
        }

        navigation.menu.getItem(position).isChecked = true
        prevMenuItem = navigation.menu.getItem(position)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sites -> {
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera -> {
                viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_gift -> {
                viewPager.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_wishes -> {
                viewPager.currentItem = 4
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)
        toolbar.changeToolbarFont()
        setUpViewPager()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun Toolbar.changeToolbarFont(){
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/ArcherPro-Bold.otf")
                break
            }
        }
    }

    private lateinit var viewPagerAdapter: MainFragmentAdapter

    private fun setUpViewPager() {
        viewPagerAdapter = MainFragmentAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(HomeFragment())
        viewPagerAdapter.addFragment(SitesFragment())
        viewPagerAdapter.addFragment(PhotosFragment())
        viewPagerAdapter.addFragment(GiftFragment())
        viewPagerAdapter.addFragment(WishesFragment())

        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        fun startActivity(a: Activity) {
            a.startActivity(Intent(a, MainActivity::class.java))
        }
    }
}
