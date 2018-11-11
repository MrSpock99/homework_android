package apps.robot.androidhomework.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.robot.androidhomework.R
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        rootView.btn_change_theme.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle(getString(R.string.dialog_theme_title))

            val themes = resources.getStringArray(R.array.app_themes)
            val prefs = activity?.getSharedPreferences(PREFS_THEME, Context.MODE_PRIVATE)
            builder.setItems(themes) { _, which ->
                when (which) {
                    0 -> {
                        prefs?.edit()
                                ?.putString(APP_THEME, THEME_RED)
                                ?.apply()
                    }
                    1 -> {
                        prefs?.edit()
                                ?.putString(APP_THEME, THEME_GREEN)
                                ?.apply()
                    }
                    2 -> {
                        prefs?.edit()
                                ?.putString(APP_THEME, THEME_DEFAULT)
                                ?.apply()
                    }
                }
            }

            val dialog = builder.create()
            dialog.setOnDismissListener {
                activity?.recreate()
            }
            dialog.show()
        }
        return rootView
    }


    companion object {
        const val PREFS_THEME = "PREFS_THEME"

        const val APP_THEME = "APP_THEME"
        const val THEME_GREEN = "THEME_GREEN"
        const val THEME_RED = "THEME_RED"
        const val THEME_DEFAULT = "THEME_DEFAULT"
        fun newInstance() = SettingsFragment()
    }
}
