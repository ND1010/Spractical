package com.dhruv.apps.historyapp.ui.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dhruv.apps.historyapp.App
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.ui.LoginActivity
import kotlinx.android.synthetic.main.activity_user.*
class UserActivity : AppCompatActivity() {

    private fun logout() {
        App.spUser.edit().clear().apply()
        startActivity(Intent(this@UserActivity,LoginActivity::class.java))
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val arList = ArrayList<Fragment>()

        arList.add(PlaceholderFragment())
        arList.add(SelectedUserFragment())

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager,arList)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        logout.setOnClickListener { logout() }
    }


}