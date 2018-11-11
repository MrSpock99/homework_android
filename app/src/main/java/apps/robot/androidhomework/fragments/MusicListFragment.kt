package apps.robot.androidhomework.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.robot.androidhomework.MusicItem
import apps.robot.androidhomework.MyMusicRecyclerViewAdapter
import apps.robot.androidhomework.R
import apps.robot.androidhomework.content.MusicContent

class MusicListFragment : Fragment() {

    private var columnCount = 1

    private var listenerMusic: OnMusicListItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyMusicRecyclerViewAdapter(MusicContent.ITEMS, listenerMusic)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMusicListItemClickListener) {
            listenerMusic = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnMusicListItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerMusic = null
    }

    interface OnMusicListItemClickListener {
        fun onMusicListItemClicked(item: MusicItem)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                MusicListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
