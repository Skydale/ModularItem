package io.github.mg138.modular.item.ingredient

import net.minecraft.util.Identifier

object IngredientManager {
    private val map: MutableMap<Identifier, Ingredient> = mutableMapOf()

    val ingredients: Iterable<Ingredient>
        get() = map.values

    operator fun get(id: Identifier) = map[id]

    fun add(ingredient: Ingredient) {
        map[ingredient.id] = ingredient
    }
}