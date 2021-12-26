package io.github.mg138.modular.item

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.Stated
import io.github.mg138.bookshelf.stat.type.StatType
import io.github.mg138.bookshelf.utils.StatUtil.sumOf
import io.github.mg138.modular.item.ingredient.Ingredient
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtString
import net.minecraft.util.Identifier
import kotlin.collections.sumOf

abstract class ModularItem(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    val ingredients: List<Ingredient>
) : BookItem(id, bookItemSettings, settings, vanillaItem), Stated {
    override fun getStatResult(type: StatType) = ingredients.sumOf { it.getStatResult(type) }
    override fun getStat(type: StatType) = ingredients.sumOf { it.getStat(type) }
    override fun types() = mutableSetOf<StatType>().apply { ingredients.flatMapTo(this) { it.types() } }
    override fun stats() = ingredients.flatMap { it.stats() }
    override fun pairs() = ingredients.flatMap { it.pairs() }
    override fun iterator() = pairs().iterator()
}