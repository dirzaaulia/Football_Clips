package com.dirzaaulia.footballclips.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

fun formatDateTime(value: String?): String {
    return if (value.isNullOrBlank()) {
        "Unknown Date"
    } else {
        val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = dateParser.parse(value)
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        dateFormatter.format(date!!)
    }
}

fun openLink(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(link)
    ContextCompat.startActivity(context, intent, null)
}