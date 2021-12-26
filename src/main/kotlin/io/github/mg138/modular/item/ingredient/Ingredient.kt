package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.StatMap
import io.github.mg138.bookshelf.stat.Stated
import io.github.mg138.bookshelf.stat.stat.Stat
import io.github.mg138.bookshelf.stat.type.StatType
import net.minecraft.item.Item
import net.minecraft.util.Identifier

abstract class Ingredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    private val statMap: StatMap
) : BookItem(id, bookItemSettings, settings, vanillaItem), Stated {
    override fun getStatResult(type: StatType) = statMap.getStatResult(type)
    override fun getStat(type: StatType) = statMap.getStat(type)
    override fun stats() = statMap.stats()
    override fun types() = statMap.types()
    override fun pairs() = statMap.pairs()
    override fun iterator() = statMap.iterator()
}