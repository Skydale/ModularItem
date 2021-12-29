package io.github.mg138.modular.item.ingredient.modular

import net.minecraft.util.Identifier

object ModularIngredientManager {
    private val map: MutableMap<Identifier, ModularIngredient> = mutableMapOf()

    operator fun get(id: Identifier) = map[id]

    val ingredients: Iterable<ModularIngredient>
        get() = map.values

    fun add(ingredient: ModularIngredient) {
        map[ingredient.id] = ingredient
    }
}