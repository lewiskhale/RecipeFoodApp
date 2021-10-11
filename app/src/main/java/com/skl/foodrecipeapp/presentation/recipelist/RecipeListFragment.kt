package com.skl.foodrecipeapp.presentation.recipelist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.chip.Chip
import com.skl.foodrecipeapp.R
import com.skl.foodrecipeapp.common.adapter.RecipeListAdapter
import com.skl.foodrecipeapp.common.domain.model.Recipe
import com.skl.foodrecipeapp.databinding.FragmentRecipeListBinding
import com.skl.foodrecipeapp.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : BaseFragment<FragmentRecipeListBinding>(), RecipeListAdapter.OnItemClick {

    override fun getFragmentBinding(): FragmentRecipeListBinding = FragmentRecipeListBinding.inflate(layoutInflater)

    private val viewModel: RecipeViewModel by viewModels()
    private val mAdapter: RecipeListAdapter = RecipeListAdapter(this)

    private var diet: MutableList<String> = arrayListOf()
    private var intolerance: MutableList<String> = arrayListOf()

    var isLoading: Boolean = false
    var isScrolling: Boolean = false
    var isLastPage: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val compat = requireActivity() as AppCompatActivity
        compat.supportActionBar?.show()

        setUpChips()
        setUpRecyclerview()
        setHasOptionsMenu(true)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recipes.collectLatest {
                mAdapter.submitList(it)
            }
        }
    }

    private fun setUpRecyclerview() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun setUpChips() {
        Log.d(TAG, "setUpChips: inside")
        binding.apply {
            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                val chip: Chip? = group?.findViewById(checkedId)
                if(chip != null){
                    viewModel.addCuisine(chip.text.toString())
                }else{
                    viewModel.clearCuisine()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.recipelist_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_menu_item -> {
                //go to the search recipes fragment
                Toast.makeText(requireContext(), "clicked on the search menu", Toast.LENGTH_SHORT).show()
            }
            R.id.filter_menu_item -> {
                setupDialog()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(recipe: Recipe) {
        val actions = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(recipe)
        findNavController().navigate(actions)
    }

    private fun setupDialog() {
        //TODO fix this mess
        diet = viewModel.diet.value
        intolerance = viewModel.intolerance.value

        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.custom_filter_dialog)

        val gluten_free = dialog.findViewById<CheckBox>(R.id.gluten_free)
        val vegetarian = dialog.findViewById<CheckBox>(R.id.vegetarian)
        val vegan = dialog.findViewById<CheckBox>(R.id.vegan)
        val paleo = dialog.findViewById<CheckBox>(R.id.paleo)
        val lacto_vegetarian = dialog.findViewById<CheckBox>(R.id.lacto_vegetarian)
        val ovo_vegetarian = dialog.findViewById<CheckBox>(R.id.ovo_vegetarian)
        val ketogenic = dialog.findViewById<CheckBox>(R.id.ketogenic)

        val eggs = dialog.findViewById<CheckBox>(R.id.egg)
        val dairy = dialog.findViewById<CheckBox>(R.id.dairy)
        val grain = dialog.findViewById<CheckBox>(R.id.grain)
        val peanut = dialog.findViewById<CheckBox>(R.id.peanut)
        val sesame = dialog.findViewById<CheckBox>(R.id.sesame)
        val shellfish = dialog.findViewById<CheckBox>(R.id.shellfish)
        val seafood = dialog.findViewById<CheckBox>(R.id.seafood)
        val soy = dialog.findViewById<CheckBox>(R.id.soy)
        val wheat = dialog.findViewById<CheckBox>(R.id.wheat)
        val tree_nut = dialog.findViewById<CheckBox>(R.id.tree_nut)

        //getting checked
        if(diet.contains("gluten")){ gluten_free.isChecked = true }
        if(diet.contains("vegetarian")){ vegetarian.isChecked = true }
        if(diet.contains("vegan")){ vegan.isChecked = true }
        if(diet.contains("paleo")){ paleo.isChecked = true }
        if(diet.contains("lacto-vegetarian")){ lacto_vegetarian.isChecked = true }
        if(diet.contains("ovo-vegetarian")){ ovo_vegetarian.isChecked = true }
        if(diet.contains("ketogenic")){ ketogenic.isChecked = true }

        if(intolerance.contains("eggs")){ eggs.isChecked = true }
        if(intolerance.contains("dairy")){ dairy.isChecked = true }
        if(intolerance.contains("grain")){ grain.isChecked = true }
        if(intolerance.contains("peanut")){ peanut.isChecked = true }
        if(intolerance.contains("sesame")){ sesame.isChecked = true }
        if(intolerance.contains("seafood")){ seafood.isChecked = true }
        if(intolerance.contains("shellfish")){ shellfish.isChecked = true }
        if(intolerance.contains("soy")){ soy.isChecked = true }
        if(intolerance.contains("wheat")){ wheat.isChecked = true }
        if(intolerance.contains("treenut")){ tree_nut.isChecked = true }

        //cancelling dialog
        dialog.findViewById<Button>(R.id.cancel_dialog_button).setOnClickListener {
            dialog.dismiss()
        }
        //confirming dialog
        dialog.findViewById<Button>(R.id.confirm_dialog_button).setOnClickListener {

            diet = arrayListOf()
            intolerance = arrayListOf()

            if(gluten_free.isChecked){ diet.add("gluten") }
            if(vegetarian.isChecked){ diet.add("vegetarian") }
            if(vegan.isChecked){ diet.add("vegan") }
            if(paleo.isChecked){ diet.add("paleo") }
            if(lacto_vegetarian.isChecked){ diet.add("lacto-vegetarian") }
            if(ovo_vegetarian.isChecked){ diet.add("ovo-vegetarian") }
            if(ketogenic.isChecked){ diet.add("ketogenic") }

            if(eggs.isChecked){ intolerance.add("eggs") }
            if(dairy.isChecked){ intolerance.add("dairy") }
            if(grain.isChecked){ intolerance.add("grain") }
            if(peanut.isChecked){ intolerance.add("peanut") }
            if(sesame.isChecked){ intolerance.add("sesame") }
            if(seafood.isChecked){ intolerance.add("seafood") }
            if(shellfish.isChecked){ intolerance.add("shellfish") }
            if(soy.isChecked){ intolerance.add("soy") }
            if(wheat.isChecked){ intolerance.add("wheat") }
            if(tree_nut.isChecked){ intolerance.add("treenut") }

            if(diet.isEmpty() || intolerance.isEmpty()){
                viewModel.clearDietAndIntolerance()
            }else{
                viewModel.addDietAndIntolerance(diet, intolerance)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

}

//    override fun onClick(position: Int) {
//        val recipe = binding.recyclerView[position]
//        Log.d(TAG, "onClick: clicked on item: $recipe position: $position")
//    }

//    val mScrollListener = object: RecyclerView.OnScrollListener(){
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                isScrolling = true
//            }
//        }
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//
//            val visibleItemCount = layoutManager.childCount
//            val totalItemCount = layoutManager.itemCount
//            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
//
//            if(isLoading && !isLastPage && (visibleItemCount + firstVisibleItemPos + 1 >= totalItemCount) && isScrolling){
//
//                isScrolling = false
//            }
//        }
//    }