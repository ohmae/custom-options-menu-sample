/*
 * Copyright (c) 2018 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.customoptionsmenu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_menu.*

/**
 * @author [大前良介 (OHMAE Ryosuke)](mailto:ryo@mm2d.net)
 */
class CustomMenuActivity : AppCompatActivity() {
    private lateinit var optionsMenu: CustomOptionsMenuHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_menu)
        setSupportActionBar(toolbar)
        optionsMenu = CustomOptionsMenuHelper(this, R.id.toolbar, R.id.action_overflow)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.custom, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return optionsMenu.onPrepareOptionsMenu(menu, R.id.overflow)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_overflow -> optionsMenu.onSelectOverflowMenu()
            else -> Toast.makeText(this, item.title, Toast.LENGTH_LONG).show()
        }
        return true
    }
}
