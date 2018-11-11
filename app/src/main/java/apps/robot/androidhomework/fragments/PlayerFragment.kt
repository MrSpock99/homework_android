package apps.robot.androidhomework.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.robot.androidhomework.MusicItem
import apps.robot.androidhomework.MusicService
import apps.robot.androidhomework.R
import apps.robot.androidhomework.content.MusicContent
import kotlinx.android.synthetic.main.fragment_player.view.*

class PlayerFragment : Fragment(), MusicService.OnTrackCompletedListener {
    private var listener: OnPlayerButtonsClickListener? = null
    private lateinit var receiver: BroadcastReceiver

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.extras[PLAYER_ACTION]
                Log.d("MyLog", action as String?)

                when (action) {
                    ACTION_PLAY_OR_PAUSE -> {
                        setNameAndAuthor(view)
                        listener?.onPlayOrPauseClicked()
                    }
                    ACTION_NEXT -> {
                        setNameAndAuthor(view)
                        listener?.onNextClicked()
                    }
                    ACTION_PREV -> {
                        setNameAndAuthor(view)
                        listener?.onPrevClicked()
                    }
                }
            }
        }

        context?.let {
            val intentFilter = IntentFilter(PLAYER_ACTION)
            intentFilter.priority = 100
            it.registerReceiver(receiver, intentFilter)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_player, container, false)

        setNameAndAuthor(rootView)

        rootView.btn_play.setOnClickListener {
            setNameAndAuthor(rootView)
            listener?.onPlayOrPauseClicked()
        }
        rootView.btn_next.setOnClickListener {
            listener?.onNextClicked()
            setNameAndAuthor(rootView)
        }
        rootView.btn_prev.setOnClickListener {
            listener?.onPrevClicked()
            setNameAndAuthor(rootView)
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPlayerButtonsClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnPlayerButtonsClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        context?.let {
            it.unregisterReceiver(receiver)
        }
    }

    private fun setNameAndAuthor(view: View?) {
        view?.tv_song_author?.text = MusicContent.ITEMS[MusicContent.currentIndex].author
        view?.tv_song_name?.text = MusicContent.ITEMS[MusicContent.currentIndex].name
    }

    override fun onCompleted(musicItem: MusicItem) {
        setNameAndAuthor(view)
    }

    interface OnPlayerButtonsClickListener {
        fun onPlayOrPauseClicked()
        fun onNextClicked()
        fun onPrevClicked()
    }

    companion object {
        const val PLAYER_ACTION = "PLAYER_ACTION"
        const val ACTION_PLAY_OR_PAUSE = "PLAY_OR_PAUSE"
        const val ACTION_NEXT = "NEXT"
        const val ACTION_PREV = "PREV"
        fun newInstance() = PlayerFragment()
    }
}
