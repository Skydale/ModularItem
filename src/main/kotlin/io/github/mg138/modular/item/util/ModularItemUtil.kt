package io.github.mg138.modular.item.util

import io.github.mg138.bookshelf.stat.StatMap
import io.github.mg138.modular.item.ModularItem
import io.github.mg138.modular.item.ingredient.Ingredient
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ModularItemUtil {
    private val cacheIngredients: MutableMap<NbtList, List<Ingredient>> = mutableMapOf()
    private val cacheStatMap: MutableMap<List<Ingredient>, StatMap> = mutableMapOf()

    fun getIngredients(modularItemNbt: NbtCompound): List<Ingredient> {
        val ingredientsNbt = modularItemNbt.getList(ModularItem.INGREDIENTS_KEY, NbtType.STRING)

        return cacheIngredients.getOrPut(ingredientsNbt) {
            ingredientsNbt.map { Identifier(it.asString()) }
                .map { Registry.ITEM.get(it) }
                .filterIsInstance<Ingredient>()
        }
    }

    fun getStatMap(modularItemNbt: NbtCompound): StatMap {
        val ingredients = getIngredients(modularItemNbt)

        return cacheStatMap.getOrPut(ingredients) {
            StatMap().apply {
                ingredients.map { it.statMap }
                    .forEach {
                        it.forEach { (type, stat) ->
                            this.addStat(type, stat)
                        }
                    }
            }
        }
    }
}