package io.github.mg138.modular.item.util

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.modular.item.ModularItem
import io.github.mg138.modular.item.ingredient.ModularIngredient
import io.github.mg138.modular.item.ingredient.ModularStatedIngredient
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ModularItemUtil {
    private val cacheIngredients: MutableMap<NbtList, List<ModularIngredient>> = mutableMapOf()
    private val cacheStatMap: MutableMap<ItemStack, StatMap> = mutableMapOf()

    fun getModularItemNbt(itemStack: ItemStack?) =
        itemStack?.orCreateNbt?.getCompound(ModularItem.MODULAR_ITEM_KEY)

    fun getIngredientNbt(modularItemNbt: NbtCompound) =
        modularItemNbt.getList(ModularItem.INGREDIENTS_KEY, NbtType.STRING)

    fun getIngredients(modularItemNbt: NbtCompound): List<ModularIngredient> {
        val ingredientsNbt = getIngredientNbt(modularItemNbt)

        return cacheIngredients.getOrPut(ingredientsNbt) {
            ingredientsNbt.map { Identifier(it.asString()) }
                .map { Registry.ITEM.get(it) }
                .filterIsInstance<ModularIngredient>()
        }
    }

    fun getIngredients(itemStack: ItemStack?): List<ModularIngredient> {
        val modularItemNbt = getModularItemNbt(itemStack) ?: return emptyList()

        return getIngredients(modularItemNbt)
    }

    fun getStatMap(item: Item, itemStack: ItemStack, modularItemNbt: NbtCompound): StatMap {
        return cacheStatMap.getOrPut(itemStack) {
            val ingredients = getIngredients(modularItemNbt)

            StatMap().apply {
                ingredients
                    .filterIsInstance<ModularStatedIngredient>()
                    .sortedBy { it.updateStatPriority }
                    .forEach {
                        it.updateStat(this, item, itemStack)
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