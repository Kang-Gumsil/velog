package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(ActivityMainBinding.inflate(layoutInflater))
    }

    private fun initView(binding: ActivityMainBinding) = with(binding) {
        setContentView(root)
        bottomNav.setOnItemSelectedListener(this@MainActivity)
        bottomNav.selectedItemId = R.id.menu_home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_home -> {
                showFragment(HomeFragment.TAG)
                true
            }
            R.id.menu_gallery -> {
                showFragment(GalleryFragment.TAG)
                true
            }
            R.id.menu_my_page -> {
                showFragment(MyPageFragment.TAG)
                true
            }
            else -> false
        }

    private fun showFragment(tag: String) {
        // 기존 프래그먼트 숨기기
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }

        // 선택한 프래그먼트 보여주기
        supportFragmentManager.findFragmentByTag(tag)?.let {
            supportFragmentManager.beginTransaction().show(it).commit()
        } ?: createFragmentByTag(tag).let {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, it, tag).commit()
        }
    }

    private fun createFragmentByTag(tag: String): Fragment = when (tag) {
        HomeFragment.TAG -> HomeFragment.newInstance()
        GalleryFragment.TAG -> GalleryFragment.newInstance()
        MyPageFragment.TAG -> MyPageFragment.newInstance()
        else -> throw Exception("유효하지 않은 TAG 입니다.")
    }
}