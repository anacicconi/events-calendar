package com.cicconi.events.viewmodel;

import androidx.lifecycle.ViewModel;

public class RecipeDetailsViewModel extends ViewModel {

    //private static final String TAG = RecipeDetailsViewModel.class.getSimpleName();
    //
    //private Recipe mRecipe;
    //
    //private LiveData<List<Ingredient>> ingredients;
    //private LiveData<List<Step>> steps;
    //
    //private RecipeRepository recipeRepository;
    //private LiveData<Boolean> isFavoriteRecipe;
    //
    //RecipeDetailsViewModel(@NonNull Context context, Recipe recipe) {
    //    IngredientRepository ingredientRepository = new IngredientRepository(context);
    //    StepRepository stepRepository = new StepRepository(context);
    //    recipeRepository = new RecipeRepository(context);
    //
    //    this.mRecipe = recipe;
    //
    //    ingredients = ingredientRepository.getIngredientsForARecipe(mRecipe.id);
    //    steps = stepRepository.getStepsForARecipe(mRecipe.id);
    //
    //    isFavoriteRecipe = recipeRepository.getRecipeFavoriteStatus(mRecipe.id);
    //}
    //
    //public LiveData<List<Ingredient>> getIngredients() {
    //    return ingredients;
    //}
    //
    //public LiveData<List<Step>> getSteps() {
    //    return steps;
    //}
    //
    //public LiveData<Boolean> getRecipeFavoriteStatus() {
    //    return isFavoriteRecipe;
    //}
    //
    //public Completable onFavoriteStatusUpdated(Boolean newFavoriteStatus) {
    //    return recipeRepository.updateFavoriteRecipeStatus(mRecipe.id, newFavoriteStatus);
    //}
}
