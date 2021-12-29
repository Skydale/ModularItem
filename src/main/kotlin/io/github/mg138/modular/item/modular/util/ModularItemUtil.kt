package io.github.mg138.modular.item.modular.util

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.IngredientManager
import io.github.mg138.modular.item.ingredient.StatedIngredient
import io.github.mg138.modular.item.modular.ModularItem
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier

object ModularItemUtil {
    private val cacheIngredients: MutableMap<NbtList, List<Ingredient>> = mutableMapOf()
    private val cacheStatMap: MutableMap<ItemStack, StatMap> = mutableMapOf()

    fun getModularItemNbt(itemStack: ItemStack?) =
        itemStack?.orCreateNbt?.getCompound(ModularItem.MODULAR_ITEM_KEY)

    fun getIngredientNbt(modularItemNbt: NbtCompound) =
        modularItemNbt.getList(ModularItem.INGREDIENTS_KEY, NbtType.STRING)

    fun getIngredients(modularItemNbt: NbtCompound): List<Ingredient> {
        val ingredientsNbt = getIngredientNbt(modularItemNbt)

        return cacheIngredients.getOrPut(ingredientsNbt) {
            ingredientsNbt
                .map { Identifier(it.asString()) }
                .mapNotNull { IngredientManager[it] }
        }
    }

    fun getIngredients(itemStack: ItemStack?): List<Ingredient> {
        val modularItemNbt = getModularItemNbt(itemStack) ?: return emptyList()

        return getIngredients(modularItemNbt)
    }

    fun getStatMap(item: Item, itemStack: ItemStack, modularItemNbt: NbtCompound): StatMap {
        return cacheStatMap.getOrPut(itemStack) {
            val ingredients = getIngredients(modularItemNbt)

            StatMap().apply {
                ingredients
                    .filterIsInstance<StatedIngredient>()
                    .sortedBy { it.updateStatPriority }
                    .forEach {
                        it.updateStats(this, item, itemStack)
                    }
            }
        }
    }

    fun getStatMap(item: Item, itemStack: ItemStack?): StatMap {
        if (itemStack == null) return StatMap()
        val modularItemNbt = getModularItemNbt(itemStack) ?: return StatMap()

        return getStatMap(item, itemStack, modularItemNbt)
    }
}