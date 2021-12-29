package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.StatMap
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class ModularStaticStatedIngredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    private val statMap: StatMap
) : ModularStatedIngredient(id, bookItemSettings, settings, vanillaItem) {
    override val updateStatPriority = 0

    override fun updateStat(statMap: StatMap, item: Item, itemStack: ItemStack) {
        this.statMap.forEach { (type, stat) ->
            statMap.addStat(type, stat)
        }
    }

    override fun register() {
        super.register()
        ModularIngredientManager.ingredients += this
    }
}