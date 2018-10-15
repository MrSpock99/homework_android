package apps.robot.androidhomework

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.*
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
        mUsersList = when (item?.itemId) {
            R.id.menu_sort_alpha -> {
                mUsersList.sortedWith(compareBy { it.name })
            }
            R.id.menu_sort_digit -> {
                mUsersList.sortedWith(compareBy { it.id })
            }
            else -> {
                return false
            }
        }
        Log.d("MyLog", mUsersList.toString())
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
