package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.modular.item.ModularItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

abstract class ModularIngredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : ModularItem(id, bookItemSettings, settings, vanillaItem) {
    open val loreKey = "modular_item.ingredient.${id.namespace}.${id.path}"
    open fun lore(itemStack: ItemStack) = TranslatableText(loreKey)

    override fun register() {
        super.register()
        ModularIngredientManager.ingredients += this
    }
}