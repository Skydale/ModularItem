package io.github.mg138.modular.item.modular.util

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.IngredientManager
import io.github.mg138.modular.item.ingredient.StatedIngredient
import io.github.mg138.modular.item.modular.ModularItem
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier

object ModularItemUtil {
    private val cacheStatMap: MutableMap<Identifier, MutableMap<NbtCompound, StatMap>> = mutableMapOf()

    private fun readIngredient(nbt: NbtCompound, level: Int = 0, callback: (Ingredient, NbtCompound, Int) -> Unit) {
        if (!nbt.contains(ModularItem.ID_KEY)) return
        if (!nbt.contains(ModularItem.DATA_KEY)) return

        val ingredient = IngredientManager[Identifier(nbt.getString(ModularItem.ID_KEY))] ?: return
        val data = nbt.getCompound(ModularItem.DATA_KEY)

        callback(ingredient, data, level)
    }

    private fun readIngredients(data: NbtCompound, level: Int = 0, callback: (Ingredient, NbtCompound, Int) -> Unit) {
        if (!data.contains(ModularItem.MODULAR_KEY)) return
        val modularItemNbt = data.getCompound(ModularItem.MODULAR_KEY)

        if (!modularItemNbt.contains(ModularItem.INGREDIENTS_KEY)) return
        val ingredientsNbt = modularItemNbt.getList(ModularItem.INGREDIENTS_KEY, NbtType.COMPOUND)

        ingredientsNbt.filterIsInstance<NbtCompound>().forEach {
            readIngredient(it, level) { ingredient, data, level ->
                callback(ingredient, data, level)
                readIngredients(data, level + 1, callback)
            }
        }
    }

    fun readIngredientsShallow(data: NbtCompound, level: Int = 0, callback: (Ingredient, NbtCompound, Int) -> Unit) {
        if (!data.contains(ModularItem.MODULAR_KEY)) return
        val modularItemNbt = data.getCompound(ModularItem.MODULAR_KEY)

        if (!modularItemNbt.contains(ModularItem.INGREDIENTS_KEY)) return
        val ingredientsNbt = modularItemNbt.getList(ModularItem.INGREDIENTS_KEY, NbtType.COMPOUND)

        ingredientsNbt.filterIsInstance<NbtCompound>().forEach {
            readIngredient(it, level) { ingredient, data, level ->
                callback(ingredient, data, level)
            }
        }
    }

    fun readIngredientsShallow(itemStack: ItemStack, level: Int = 0, callback: (Ingredient, NbtCompound, Int) -> Unit) {
        readIngredientsShallow(itemStack.orCreateNbt, level, callback)
    }

    fun readIngredients(itemStack: ItemStack, level: Int = 0, callback: (Ingredient, NbtCompound, Int) -> Unit) {
        readIngredients(itemStack.orCreateNbt, level, callback)
    }

    fun getStatMap(data: NbtCompound, item: ModularItem): StatMap {
        val map = cacheStatMap.getOrPut(item.id) { mutableMapOf() }

        return map.getOrPut(data) {
            StatMap().apply {
                val list: MutableList<Pair<StatedIngredient, NbtCompound>> = mutableListOf()

                readIngredientsShallow(data) { ingredient, data, _ ->
                    if (ingredient is StatedIngredient) {
                        list.add(ingredient to data)
                    }
                }

                item.getDefaultIngredients().forEach {
                    if (it is StatedIngredient) {
                        list.add(it to NbtCompound())
                    }
                }

                list.sortedBy { it.first.updateStatPriority }
                    .forEach { (ingredient, data) ->
                        ingredient.updateStats(this, data)
                    }
            }
        }
    }

    fun getStatMap(itemStack: ItemStack?): StatMap {
        if (itemStack == null) return StatMap()
        val item = itemStack.item
        if (item !is ModularItem) return StatMap()

        return getStatMap(itemStack.orCreateNbt, item)
    }
}