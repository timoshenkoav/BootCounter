package com.aura.bootcounter.pres

import android.content.Context
import com.aura.bootcounter.R
import com.aura.bootcounter.data.BootEntry
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotifTitlePresenter @Inject constructor(
    @ApplicationContext
    private val ctx: Context,
) {
    fun noEntries() = ctx.resources.getString(R.string.notification_no_entries)
    fun singleEntry(ts: Long) =
        ctx.resources.getString(R.string.notification_single_entry, ts.toString())

    fun multiEntry(diff: Long) =
        ctx.resources.getString(R.string.notification_multi_entry, diff.toString())

    fun entriesList(entries: List<BootEntry>): String {
        return entries.mapIndexed  { idx,entry->
            "$idx - ${entry.ts}"
        }.joinToString("\n")
    }
}