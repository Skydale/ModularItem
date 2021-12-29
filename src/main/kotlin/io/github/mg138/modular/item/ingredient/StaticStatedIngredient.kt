package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.Stats
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class StaticStatedIngredient(
    identifier: Identifier,
    private val stats: Stats
) : StatedIngredient {
    override val id = identifier

    override fun getStats(itemStack: ItemStack?) = stats

    override val updateStatPriority = 0

    override fun updateStats(mutableStats: MutableStats, item: Item, itemStack: ItemStack) {
        this.getStats(itemStack).forEach { (type, stat) ->
            mutableStats.addStat(type, stat)
        }
    }
}