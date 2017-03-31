package com.example.ryota.kotlinapp.views.AlarmList

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.TextView
import com.example.ryota.kotlinapp.model.alarm.Alarm
import org.jetbrains.anko.*

/**
 * Created by ryota on 2017/03/30.
 */

class AlarmListItem(context: Context) : FrameLayout(context), AnkoComponent<Context> {

    private var label: TextView? = null

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            lparams(height = 100)
            label = textView()
        }.apply { this@AlarmListItem.addView(this) }
    }

    fun update(alarm: Alarm, onClickAlarm: (Alarm) -> Unit) {
        label?.text = "alarmTitle: " + alarm.title
        label?.onClick {onClickAlarm(alarm)}
    }
}

class AlarmListAdaptor(var context: Context, var alarms: List<Alarm> = emptyList(), var onClickAlarm: (Alarm) -> Unit) : BaseAdapter() {
    fun updateAlarms(updatedAlarms: List<Alarm>) {
        alarms = updatedAlarms
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return alarms.count()
    }

    override fun getItemId(n: Int): Long {
        return n.toLong()
    }

    override fun getItem(n: Int): Alarm {
        return alarms[n]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? =
            ((convertView as? AlarmListItem) ?: AlarmListItem(context).apply {
                createView(context.UI {})
            }).apply {
                update(getItem(position), onClickAlarm)
            }
}