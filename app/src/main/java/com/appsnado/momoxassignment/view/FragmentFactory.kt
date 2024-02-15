package com.appsnado.momoxassignment.view

import com.appsnado.momoxassignment.adapters.ImageListAdapter
import javax.inject.Inject
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


class FragmentFactory @Inject constructor(
    private val adapter: ImageListAdapter

) : FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImageListFragment::class.java.name -> ImageListFragment(adapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}