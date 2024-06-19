package com.example.growthtrack.ui.diagnose.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Viewpager(activity: AppCompatActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DangerFragmen()
            1 -> fragment = FoodFragmen()
            2 -> fragment = Prinsip()
        }
        return fragment as Fragment
    }
}