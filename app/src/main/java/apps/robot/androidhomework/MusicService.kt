package apps.robot.androidhomework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import apps.robot.androidhomework.content.MusicContent
import apps.robot.androidhomework.fragments.PlayerFragment
import apps.robot.androidhomework.fragments.PlayerFragment.Companion.ACTION_NEXT
import apps.robot.androidhomework.fragments.PlayerFragment.Companion.ACTION_PLAY_OR_PAUSE
import apps.robot.androidhomework.fragments.PlayerFragment.Companion.ACTION_PREV
import apps.robot.androidhomework.fragments.PlayerFragment.Companion.PLAYER_ACTION

private const val CHANNEL_ID = "NOTIFICATION_PLAYER"
private const val NOTIFICATION_ID = 101

class MusicService : Service() {
    private val mBinder = MusicBinder()
    private var player: MediaPlayer = MediaPlayer()
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var listener: OnTrackCompletedListener? = null

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        player.release()
    }

    fun initMediaPlayer(fragment: PlayerFragment) {
        listener = fragment
        var musicItem = MusicContent.ITEMS[MusicContent.currentIndex]
        player = MediaPlayer.create(this, musicItem.musicId)
        player.setOnCompletionListener {
            MusicContent.currentIndex++
            if (MusicContent.currentIndex >= MusicContent.ITEMS.size - 1) {
                MusicContent.currentIndex = 0
            }
            musicItem = MusicContent.ITEMS[MusicContent.currentIndex]
            player = MediaPlayer.create(this, musicItem.musicId)
            playOrPause()
            updateNotification(musicItem)
            (listener as PlayerFragment).onCompleted(musicItem)
        }
        showNotification(musicItem)
    }

    fun playOrPause() {
        if (!player.isPlaying) {
            player.start()
        } else {
            player.pause()
        }
    }

    fun next() {
        MusicContent.currentIndex++
        if (MusicContent.currentIndex >= MusicContent.ITEMS.size - 1) {
            MusicContent.currentIndex = 0
        }
        player.release()
        player = MediaPlayer.create(this, MusicContent.ITEMS[MusicContent.currentIndex].musicId)
        player.start()

        updateNotification(MusicContent.ITEMS[MusicContent.currentIndex])
    }

    fun prev() {
        MusicContent.currentIndex--
        if (MusicContent.currentIndex < 0) {
            MusicContent.currentIndex = MusicContent.ITEMS.size - 1
        }
        player.release()
        player = MediaPlayer.create(this, MusicContent.ITEMS[MusicContent.currentIndex].musicId)
        player.start()

        updateNotification(MusicContent.ITEMS[MusicContent.currentIndex])
    }

    private fun updateNotification(musicItem: MusicItem) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val layout = RemoteViews(packageName, R.layout.notification_layout)

        layout.setTextViewText(R.id.tv_notificaton_name, musicItem.name)
        layout.setTextViewText(R.id.tv_notification_author, musicItem.author)

        val playOrPauseIntent = Intent(PlayerFragment.PLAYER_ACTION)
        playOrPauseIntent.putExtra(PLAYER_ACTION, ACTION_PLAY_OR_PAUSE)

        val nextIntent = Intent(PlayerFragment.PLAYER_ACTION)
        nextIntent.putExtra(PLAYER_ACTION, ACTION_NEXT)

        val prevIntent = Intent(PlayerFragment.PLAYER_ACTION)
        prevIntent.putExtra(PLAYER_ACTION, ACTION_PREV)

        val pendingPlayOrPauseIntent = PendingIntent.getBroadcast(this, 0, playOrPauseIntent, 0)
        val pendingNextIntent = PendingIntent.getBroadcast(this, 1, nextIntent, 0)
        val pendingPrevIntent = PendingIntent.getBroadcast(this, 2, prevIntent, 0)

        layout.setOnClickPendingIntent(R.id.btn_notification_play, pendingPlayOrPauseIntent)
        layout.setOnClickPendingIntent(R.id.btn_notification_next, pendingNextIntent)
        layout.setOnClickPendingIntent(R.id.btn_notification_prev, pendingPrevIntent)

        notificationBuilder.setCustomContentView(layout)

        //notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        startForeground(NOTIFICATION_ID, notificationBuilder.build())

    }

    private fun showNotification(musicItem: MusicItem) {
        createNotificationChannel()

        val layout = RemoteViews(packageName, R.layout.notification_layout)

        layout.setTextViewText(R.id.tv_notificaton_name, musicItem.name)
        layout.setTextViewText(R.id.tv_notification_author, musicItem.author)

        val playOrPauseIntent = Intent(PlayerFragment.PLAYER_ACTION)
        playOrPauseIntent.putExtra(PLAYER_ACTION, ACTION_PLAY_OR_PAUSE)

        val nextIntent = Intent(PlayerFragment.PLAYER_ACTION)
        nextIntent.putExtra(PLAYER_ACTION, ACTION_NEXT)

        val prevIntent = Intent(PlayerFragment.PLAYER_ACTION)
        prevIntent.putExtra(PLAYER_ACTION, ACTION_PREV)

        val pendingPlayOrPauseIntent = PendingIntent.getBroadcast(this, 0, playOrPauseIntent, 0)
        val pendingNextIntent = PendingIntent.getBroadcast(this, 1, nextIntent, 0)
        val pendingPrevIntent = PendingIntent.getBroadcast(this, 2, prevIntent, 0)

        layout.setOnClickPendingIntent(R.id.btn_notification_play, pendingPlayOrPauseIntent)
        layout.setOnClickPendingIntent(R.id.btn_notification_next, pendingNextIntent)
        layout.setOnClickPendingIntent(R.id.btn_notification_prev, pendingPrevIntent)

        val pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(layout)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID,
                    getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = getString(R.string.channel_description)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    interface OnTrackCompletedListener {
        fun onCompleted(musicItem: MusicItem)
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }
}
