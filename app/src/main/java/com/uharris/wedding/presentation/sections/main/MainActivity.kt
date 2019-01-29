package com.uharris.wedding.presentation.sections.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.uharris.wedding.R
import com.uharris.wedding.presentation.sections.gift.GiftFragment
import com.uharris.wedding.presentation.sections.home.HomeFragment
import com.uharris.wedding.presentation.sections.photos.PhotosFragment
import com.uharris.wedding.presentation.sections.sites.SitesFragment
import com.uharris.wedding.presentation.sections.wishes.WishesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
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
        setSupportActionBar(toolbar)

        setUpViewPager()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private lateinit var viewPagerAdapter: MainFragmentAdapter

    private fun setUpViewPager() {
        viewPagerAdapter = MainFragmentAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(HomeFragment())
        viewPagerAdapter.addFragment(GiftFragment())
        viewPagerAdapter.addFragment(PhotosFragment())
        viewPagerAdapter.addFragment(SitesFragment())
        viewPagerAdapter.addFragment(WishesFragment())

        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(this)
    }
}
