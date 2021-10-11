package com.skl.foodrecipeapp.presentation.recipelist.searchrecipe

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.skl.foodrecipeapp.R
import com.skl.foodrecipeapp.databinding.FragmentSearchRecipeBinding
import com.skl.foodrecipeapp.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchRecipeFragment : BaseFragment<FragmentSearchRecipeBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    override fun getFragmentBinding(): FragmentSearchRecipeBinding = FragmentSearchRecipeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_or_filter_recipes_menu, menu)

        val searchItem = menu.findItem(R.id.search_menu_item)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_menu_item -> { /*nothing*/ }
            R.id.filter_menu_item -> {
                Toast.makeText(requireContext(), "clicked on the filter menu", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setQuery(query: String){
        viewModel.setQuery(query)
    }
}
