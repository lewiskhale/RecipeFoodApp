package com.skl.foodrecipeapp.presentation.recipedetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.skl.foodrecipeapp.common.domain.model.Recipe
import com.skl.foodrecipeapp.common.utils.OrderedListSpan
import com.skl.foodrecipeapp.databinding.FragmentRecipeDetailBinding
import com.skl.foodrecipeapp.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment<FragmentRecipeDetailBinding>() {

    private val args: RecipeDetailFragmentArgs by navArgs()
    lateinit var recipe: Recipe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = args.recipe
        val compat = requireActivity() as AppCompatActivity
        compat.supportActionBar?.hide()
        setupViews()
    }

    private fun setupViews() {
        binding.apply {

            Glide.with(binding.root)
                .load(recipe.image)
                .into(collapsingToolbarImage)

            collapsingToolbar.title = recipe.title
            readyInText.text = "READY IN: "+recipe.readyInMinutes.toString() + " minutes"
            summary.text = decodeHTML(recipe.summary)
            ingredients.text = parseIngredients(recipe.detailedIngredients)
            equipment.text = parseEquipment(recipe.detailedInstructions)
            steps.text = parseSteps(recipe.detailedInstructions)
        }
    }

    private fun parseSteps(detailedInstructions: List<Recipe.Instruction>): CharSequence? {
        val builder = SpannableStringBuilder()
        detailedInstructions.forEachIndexed { index, instruction ->
            builder.append(
                instruction.instruction + "\n\n",
                OrderedListSpan(70, "${instruction.number}."),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return builder
    }

    private fun parseEquipment(detailedInstructions: List<Recipe.Instruction>): CharSequence {
        val builder = SpannableStringBuilder()
        val itemCheck: ArrayList<String> = arrayListOf()
        detailedInstructions.forEach { instruction ->
            instruction.equipment.forEach { equipment ->
                if(!itemCheck.contains(equipment.name)){
                    itemCheck.add(equipment.name)
                    builder.append(
                        equipment.name+ "\n\n",
                        BulletSpan(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        return builder
    }

    private fun parseIngredients(detailedIngredients: List<Recipe.Ingredient>): CharSequence {
        val builder = SpannableStringBuilder()
        detailedIngredients.forEach { ingredient ->
            builder.append(
                "${ingredient.name}: ${ingredient.measures.metric_amount} ${ingredient.measures.metric_unitLong}\n\n",
                BulletSpan(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return builder
    }

    private fun decodeHTML(summary: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            Html.fromHtml(summary).toString()
        }
    }

    override fun getFragmentBinding(): FragmentRecipeDetailBinding = FragmentRecipeDetailBinding.inflate(layoutInflater)
}