package com.appsnado.momoxassignment.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsnado.momoxassignment.R
import com.appsnado.momoxassignment.adapters.ImageListAdapter
import com.appsnado.momoxassignment.adapters.ImageLoadstateAdapter
import com.appsnado.momoxassignment.databinding.FragmentImageListBinding
import com.appsnado.momoxassignment.model.SearchResponse
import com.appsnado.momoxassignment.utils.Status
import com.appsnado.momoxassignment.viewModel.ImageListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageListFragment @Inject constructor(
    private val adapter: ImageListAdapter
) : Fragment(R.layout.fragment_image_list) {

//    private lateinit var viewModel: ImageListViewModel

    private val viewModel : ImageListViewModel by viewModels()
    private var fragmentBinding : FragmentImageListBinding? = null
    var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // viewModel = ViewModelProvider(requireActivity())[ImageListViewModel::class.java]

        val binding = FragmentImageListBinding.bind(view)
        fragmentBinding = binding

        setHasOptionsMenu(true)
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect{
                val state = it.refresh
               binding.progressBar.isVisible = state is LoadState.Loading
               binding.rlImages.isVisible = state is LoadState.NotLoading
               binding.textViewError.isVisible = state is LoadState.Error
               binding.buttonRetry.isVisible = state is LoadState.Error

                if(state is LoadState.NotLoading && state.endOfPaginationReached && adapter.itemCount<1){
                    binding.rlImages.isVisible = true
                    binding.textViewError.isVisible = true
                    binding.textViewError.text = "No more result found"
                }else{
                    binding.textViewError.isVisible = false
                }
            }
        }

        binding.apply {
            rlImages.setHasFixedSize(true)
            rlImages.adapter = adapter.withLoadStateFooter(
                ImageLoadstateAdapter{
                    adapter.retry()
                }
            )
            rlImages.layoutManager = LinearLayoutManager(requireContext())
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.setOnItemClickListener {
            val direction = ImageListFragmentDirections.actionImageListFragmentToDetailFragment22(it)
            findNavController().navigate(direction)

        }


       viewModel.image.observe(viewLifecycleOwner) {
           adapter.submitData(viewLifecycleOwner.lifecycle, it)
       }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    job?.cancel()
                    job = lifecycleScope.launch {
                        delay(1000)
                        query?.let {
                            viewModel.searchImage(it)
                        }
                    }
                    fragmentBinding?.rlImages?.scrollToPosition(0)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }



    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }


}