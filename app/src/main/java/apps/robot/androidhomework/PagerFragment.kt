package apps.robot.androidhomework

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.robot.androidhomework.pager_tabs.Tab
import apps.robot.androidhomework.pager_tabs.Tab1
import apps.robot.androidhomework.pager_tabs.Tab2
import apps.robot.androidhomework.pager_tabs.Tab3
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pagerAdapter = PagerAdapter(activity!!.supportFragmentManager)

        pagerAdapter.addFragment(Tab1.newInstance("Tab1"))
        pagerAdapter.addFragment(Tab2.newInstance("Tab2"))
        pagerAdapter.addFragment(Tab3.newInstance("Tab3"))

        pager.adapter = pagerAdapter

        tablayout.setupWithViewPager(pager)
    }

    companion object {
        fun newInstance() = PagerFragment()
    }

    private class PagerAdapter(fm: android.support.v4.app.FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val mFragmentList: MutableList<Tab> = ArrayList()
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return mFragmentList[position] as Fragment
                }
                1 -> {
                    return mFragmentList[position] as Fragment
                }
                2 -> {
                    return mFragmentList[position] as Fragment
                }
            }
            return mFragmentList[position] as Fragment
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentList[position].getFragName()
        }

        fun addFragment(frag: Tab) {
            mFragmentList.add(frag)
        }
    }
}
