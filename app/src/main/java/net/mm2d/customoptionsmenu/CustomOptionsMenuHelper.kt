/*
 * Copyright (c) 2018 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.customoptionsmenu

import android.app.Activity
import android.view.*
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import androidx.annotation.Dimension
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import java.lang.ref.WeakReference

/**
 * @author [大前良介 (OHMAE Ryosuke)](mailto:ryo@mm2d.net)
 */
class CustomOptionsMenuHelper(activity: Activity, toolbarId: Int) {
    private val activityReference = WeakReference(activity)
    private val toolbar = activity.findViewById<Toolbar>(toolbarId)
    private val adapter = ArrayAdapter<MenuItem>(activity, android.R.layout.simple_list_item_1)
    private val margin = MARGIN * activity.resources.displayMetrics.density
    private val popup = ListPopupWindow(activity).also {
        it.setPromptView(activity.layoutInflater.inflate(R.layout.layout_credit, toolbar, false))
        it.promptPosition = ListPopupWindow.POSITION_PROMPT_BELOW
        it.width = Math.round(WIDTH * activity.resources.displayMetrics.density)
        it.setDropDownGravity(Gravity.END)
        it.inputMethodMode = PopupWindow.INPUT_METHOD_NOT_NEEDED
        it.setAdapter(adapter)
        it.setOnItemClickListener { _, _, position, _ ->
            activity.onOptionsItemSelected(adapter.getItem(position))
            it.dismiss()
        }
    }
    private var invalidateBySelect = false

    private fun findActionMenuView(view: View): View {
        if (view is ViewGroup) {
            view.forEach {
                if (it is ActionMenuView) {
                    return it
                }
            }
        }
        return view
    }

    fun onPrepareOptionsMenu(menu: Menu, overflowGroupId: Int): Boolean {
        menu.setGroupVisible(overflowGroupId, false)
        adapter.clear()
        menu.forEach {
            if (it.groupId == overflowGroupId) {
                adapter.add(it)
            }
        }
        if (invalidateBySelect) {
            show()
            invalidateBySelect = false
        }
        return true
    }

    private fun show() {
        val anchorView = findActionMenuView(toolbar)
        popup.anchorView = anchorView
        popup.verticalOffset = Math.round(-anchorView.height + margin)
        popup.horizontalOffset = Math.round(-margin)
        popup.show()
    }

    fun onSelectOverflowMenu() {
        invalidateBySelect = true
        activityReference.get()?.invalidateOptionsMenu()
    }

    companion object {
        @Dimension(unit = Dimension.DP)
        private const val MARGIN = 5
        @Dimension(unit = Dimension.DP)
        private const val WIDTH = 200
    }
}
