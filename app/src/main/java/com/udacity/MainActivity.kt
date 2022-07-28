package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var downloadManager: DownloadManager? = null

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        custom_button.setOnClickListener {
            val id: Int = radioGroup.checkedRadioButtonId
            if (id == -1) {
                Toast.makeText(
                    applicationContext, getString(R.string.selectFile),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val radioButton = radioGroup.findViewById(id) as RadioButton
                when (radioButton.text) {
                    getString(R.string.loadGlide) -> download(URL_GLIDE)
                    getString(R.string.loadUdacity) -> download(URL_UDACITY)
                    getString(R.string.loadRetrofit) -> download(URL_RETROFIT)
                }
            }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadID) {
                val cursor: Cursor? =
                    downloadManager?.query(DownloadManager.Query().setFilterById(downloadID))
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val status =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        when (status) {
                            DownloadManager.STATUS_SUCCESSFUL -> {
                                Log.e("MainActivity Cursor Status: ", "SUCCESSFUL")
                            }
                            DownloadManager.STATUS_FAILED -> {

                            }
                        }

                    }
                }
            }
        }
    }

    private fun download(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
        downloadID =
            downloadManager?.enqueue(request) ?: 0// enqueue puts the download request in the queue.
    }

    companion object {
        private const val URL_UDACITY =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val URL_GLIDE = "https://github.com/bumptech/glide"
        private const val URL_RETROFIT = "https://github.com/square/retrofit"

        private const val CHANNEL_ID = "channelId"
    }

}
