package apps.robot.androidhomework

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UsersListClickListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val ft = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_users -> {
                ft.replace(R.id.container_main, UsersFragment.newInstance())
            }
            R.id.navigation_empty -> {
                ft.replace(R.id.container_main, EmptyFragment.newInstance())
            }
            R.id.navigation_pager -> {
                ft.replace(R.id.container_main, PagerFragment.newInstance())
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }
        ft.addToBackStack(null)
        ft.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_main, UsersFragment.newInstance())
            .commit()

        setSupportActionBar(findViewById(R.id.my_toolbar))

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onClick(pos: Int) {

    }
}
