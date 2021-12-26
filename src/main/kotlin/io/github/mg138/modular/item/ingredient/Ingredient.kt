package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.StatMap
import net.minecraft.item.Item
import net.minecraft.util.Identifier

abstract class Ingredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    val statMap: StatMap
) : BookItem(id, bookItemSettings, settings, vanillaItem) {
    override fun register() {
        super.register()
        IngredientManager.ingredients += this
    }
}