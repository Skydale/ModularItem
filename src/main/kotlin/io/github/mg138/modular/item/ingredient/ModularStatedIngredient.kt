package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.StatedItem
import io.github.mg138.bookshelf.stat.data.StatMap
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class ModularStatedIngredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : ModularIngredient(id, bookItemSettings, settings, vanillaItem) {
    abstract val updateStatPriority: Int

    abstract fun updateStat(statMap: StatMap, item: Item, itemStack: ItemStack)

    override fun register() {
        super.register()
        ModularIngredientManager.ingredients += this
    }
}