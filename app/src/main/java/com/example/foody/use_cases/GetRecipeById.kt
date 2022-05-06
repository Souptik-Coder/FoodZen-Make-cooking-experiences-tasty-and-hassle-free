package com.example.foody.use_cases

import com.example.foody.R
import com.example.foody.data.repositories.Repository
import com.example.foody.models.Recipe
import com.example.foody.util.NetworkResults
import java.io.IOException

class GetRecipeById(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): NetworkResults<List<Recipe>> {
        return try {
            val response = repository.remote.getRecipesById(id)
            if (response.isSuccessful) {
                NetworkResults.Success(listOf(response.body()!!))
            } else {
                NetworkResults.Error(R.string.unknown_error)
            }
        } catch (e: IOException) {
            NetworkResults.Error(R.string.internet_error)
        } catch (e: Exception) {
            NetworkResults.Error(R.string.unknown_error)
        }

    }
}