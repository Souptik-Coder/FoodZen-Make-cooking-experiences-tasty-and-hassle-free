package com.example.foody.ui.screens.recipes.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.foody.R
import com.example.foody.data.repositories.DataStoreRepository
import com.example.foody.databinding.RecipesBottomSheetBinding
import com.example.foody.ui.screens.recipes.RecipesViewModel
import com.example.foody.util.Constants.DEFAULT_CUISINE
import com.example.foody.util.Constants.DEFAULT_DIET
import com.example.foody.util.Constants.DEFAULT_INTOLERANCE
import com.example.foody.util.Constants.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {

    private val recipesViewModel by activityViewModels<RecipesViewModel>()
    private val TAG = "RecipesBottomSheet"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipes_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipesBottomSheetBinding.bind(view)

//        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val NO_FILTER_TEXT = getString(R.string.no_filter)
        var recipeFilterParameters = DataStoreRepository.RecipeFilterParameters(
            DEFAULT_MEAL_TYPE, 0,
            DEFAULT_DIET, 0,
            DEFAULT_CUISINE, 0,
            DEFAULT_INTOLERANCE, 0
        )

        recipesViewModel.topRecipeFilterParameters
            .observe(viewLifecycleOwner) { value ->
                recipeFilterParameters = value.copy()
                Log.e("Sheet",recipeFilterParameters.selectedMealType)
                updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
                updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
                updateChip(value.selectedCuisineTypeId, binding.cuisineChipGroup)
                updateChip(value.selectedIntoleranceTypeId, binding.intolerancesChipGroup)
            }

        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip != null) {
                recipeFilterParameters.selectedMealType =
                    chip.text.toString().lowercase().takeIf { !it.equals(NO_FILTER_TEXT, true) }
                        .orEmpty()
                recipeFilterParameters.selectedMealTypeId = checkedId
                scrollChipToVisibleArea(chip)
            }
        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip != null) {
                recipeFilterParameters.selectedDietType =
                    chip.text.toString().lowercase().takeIf { !it.equals(NO_FILTER_TEXT, true) }
                        .orEmpty()
                recipeFilterParameters.selectedDietTypeId = checkedId
                scrollChipToVisibleArea(chip)
            }
        }

        binding.cuisineChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip != null) {
                recipeFilterParameters.selectedCuisineType =
                    chip.text.toString().lowercase().takeIf { !it.equals(NO_FILTER_TEXT, true) }
                        .orEmpty()
                recipeFilterParameters.selectedCuisineTypeId = checkedId
                scrollChipToVisibleArea(chip)
            }
        }

        binding.intolerancesChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip != null) {
                recipeFilterParameters.selectedIntoleranceType =
                    chip.text.toString().lowercase().takeIf { !it.equals(NO_FILTER_TEXT, true) }
                        .orEmpty()
                recipeFilterParameters.selectedIntoleranceTypeId = checkedId
                scrollChipToVisibleArea(chip)
            }
        }

        binding.applyButton.setOnClickListener {
            recipesViewModel.setNewTopRecipeFilterParameter(recipeFilterParameters)
            dismiss()
        }
    }

    private fun scrollChipToVisibleArea(chip: Chip) {
        chip.parent.requestChildFocus(chip, chip)
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0)
            chipGroup.findViewById<Chip>(chipId)?.isChecked = true
    }

}