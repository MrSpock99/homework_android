package apps.robot.androidhomework.content

import apps.robot.androidhomework.MusicItem
import apps.robot.androidhomework.R
import java.util.*

object MusicContent {
    val ITEMS: ArrayList<MusicItem> = ArrayList()
    var currentIndex = 0

    init {
        ITEMS.add(MusicItem(1, "Imagine Dragons", "Bad Liar", R.raw.bad_liar))
        ITEMS.add(MusicItem(2, "Moby", "Bring Sally Up", R.raw.brin_sally_up))
        ITEMS.add(MusicItem(3, "Imagine Dragons", "Machine", R.raw.machine))
        ITEMS.add(MusicItem(4, "TOP", "Chlorine", R.raw.top))
        ITEMS.add(MusicItem(5, "ELj", "Vrum-vrum", R.raw.vrum_vrum_elj))
    }
}
