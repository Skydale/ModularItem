package io.github.mg138.modular.item

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.BookStatedItem
import io.github.mg138.modular.item.ingredient.Ingredient
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.util.Identifier

abstract class ModularItem(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : BookStatedItem(id, bookItemSettings, settings, vanillaItem) {
    companion object {
        const val MODULAR_ITEM_KEY = "modular"
        const val INGREDIENTS_KEY = "ingredients"
    }

    override fun register() {
        super.register()
        ModularItemManager.items += this
    }

    fun makeItemStack(ingredients: Iterable<Ingredient>): ItemStack =
        this.defaultStack.apply {
            val nbt = orCreateNbt
            val modularItemNbt = NbtCompound()

            val ingredientsNbt = NbtList()
            ingredients.forEach {
                ingredientsNbt.add(NbtString.of(it.id.toString()))
            }
            modularItemNbt.put(INGREDIENTS_KEY, ingredientsNbt)

            nbt.put(MODULAR_ITEM_KEY, modularItemNbt)
        }
}