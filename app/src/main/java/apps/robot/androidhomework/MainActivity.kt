package apps.robot.androidhomework

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import apps.robot.androidhomework.content.MusicContent
import apps.robot.androidhomework.fragments.MusicListFragment
import apps.robot.androidhomework.fragments.PlayerFragment
import apps.robot.androidhomework.fragments.SettingsFragment


class MainActivity : AppCompatActivity(), MusicListFragment.OnMusicListItemClickListener,
        PlayerFragment.OnPlayerButtonsClickListener, ServiceConnection {
    private lateinit var mMusicService: MusicService
    private var mBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences(SettingsFragment.PREFS_THEME, Context.MODE_PRIVATE)

        val theme = prefs.getString(SettingsFragment.APP_THEME, SettingsFragment.THEME_DEFAULT)
        when (theme) {
            SettingsFragment.THEME_DEFAULT -> {
                setTheme(R.style.AppTheme)
            }
            SettingsFragment.THEME_GREEN -> {
                setTheme(R.style.GreenTheme)
            }
            SettingsFragment.THEME_RED -> {
                setTheme(R.style.RedTheme)
            }
        }

        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MusicListFragment.newInstance(1))
                .addToBackStack(null)
                .commit()
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBound) {
            unbindService(this)
            mBound = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SettingsFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mBound = false
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder: MusicService.MusicBinder = service as MusicService.MusicBinder
        mMusicService = binder.getService()
        mBound = true
    }

    override fun onMusicListItemClicked(item: MusicItem) {
        val fragment = PlayerFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        MusicContent.currentIndex = MusicContent.ITEMS.indexOf(item)
        mMusicService.initMediaPlayer(fragment)
    }

    override fun onPlayOrPauseClicked() {
        mMusicService.playOrPause()
    }

    override fun onNextClicked() {
        mMusicService.next()
    }

    override fun onPrevClicked() {
        mMusicService.prev()
    }
}
