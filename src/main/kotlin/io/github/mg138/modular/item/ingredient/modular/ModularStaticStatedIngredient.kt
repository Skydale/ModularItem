package io.github.mg138.modular.item.ingredient.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.data.Stats
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

//abstract class ModularStaticStatedIngredient(
//    id: Identifier,
//    bookItemSettings: BookItemSettings,
//    settings: Settings, vanillaItem: Item,
//    private val statMap: StatMap
//) : ModularStatedIngredient(id, bookItemSettings, settings, vanillaItem) {
//    override fun getStats(itemStack: ItemStack?): Stats = statMap
//
//    override val updateStatPriority = 0
//}