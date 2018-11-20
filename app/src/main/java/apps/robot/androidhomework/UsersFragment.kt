package apps.robot.androidhomework

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.widget.ProgressBar
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : UsersListClickListener, Fragment() {

    private var mUsersList: List<User> = getData()
    private lateinit var mUserAdapter: UserAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_users_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val dialog = context?.let {
            AlertDialog.Builder(it)
                    .setView(LayoutInflater.from(context).inflate(R.layout.dialog_progress, null))
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.cancel()
                    }
                    .create()

        }
        dialog?.show()
        val pb_dialog: ProgressBar? = dialog?.findViewById(R.id.pb_dialog)
        when (item?.itemId) {
            R.id.menu_sort_alpha -> {
                Observable
                        .just(mUsersList)
                        .flatMapIterable { list -> list }
                        .take(12)
                        .doOnNext { it ->
                            it.name = it.name + it.name.length.toString()
                            if (pb_dialog != null) {
                                pb_dialog.progress = pb_dialog.progress + 1
                            }
                        }
                        .toSortedList { p1, p2 -> p1.name.compareTo(p2.name) }
                        .subscribe { list ->
                            mUsersList = list
                            dialog?.dismiss()
                        }
            }
            R.id.menu_sort_digit -> {
                Observable
                        .just(mUsersList)
                        .flatMapIterable { list -> list }
                        .take(12)
                        .doOnNext { it ->
                            it.name = it.name + it.name.length
                            if (pb_dialog != null) {
                                pb_dialog.progress = pb_dialog.progress + 1
                            }
                        }
                        .toSortedList { p1, p2 -> p1.id.compareTo(p2.id) }
                        .subscribe { list ->
                            mUsersList = list
                            dialog?.dismiss()
                        }
            }
            else -> {
                dialog?.dismiss()
                return false
            }
        }
        mUserAdapter.submitList(mUsersList)
        return true
    }

    override fun onClick(pos: Int) {

    }

    private fun init() {
        mUserAdapter = UserAdapter(this)
        mUserAdapter.submitList(mUsersList)
        rv_users.layoutManager = GridLayoutManager(context, 1)
        rv_users.adapter = mUserAdapter
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}
