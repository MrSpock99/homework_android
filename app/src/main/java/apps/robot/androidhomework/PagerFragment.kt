package apps.robot.androidhomework

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val pagerAdapter = PagerAdapter(childFragmentManager)

        val fragNames: MutableList<String> = ArrayList()
        fragNames.add("Tab1")
        fragNames.add("Tab2")
        fragNames.add("Tab3")

        pagerAdapter.addFragment(Tab1.newInstance(fragNames[0]))
        pagerAdapter.addFragment(Tab2.newInstance(fragNames[1]))
        pagerAdapter.addFragment(Tab3.newInstance(fragNames[2]))

        pagerAdapter.setFragNames(fragNames)

        pager.adapter = pagerAdapter

        tablayout.setupWithViewPager(pager)
    }

    private class PagerAdapter(fm: android.support.v4.app.FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private var mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return mFragmentList[position]
                }
                1 -> {
                    return mFragmentList[position]
                }
                2 -> {
                    return mFragmentList[position]
                }
            }
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

        fun addFragment(frag: Fragment) {
            mFragmentList.add(frag)
        }

        fun setFragNames(list: MutableList<String>) {
            mFragmentTitleList = list
        }
    }

    companion object {
        fun newInstance() = PagerFragment()
    }
}

