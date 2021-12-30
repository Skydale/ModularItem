package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.Stats
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier

abstract class StaticStatedIngredient(
    identifier: Identifier,
    private val stats: Stats
) : StatedIngredient {
    override val id = identifier

    override fun getStats(nbt: NbtCompound) = stats

    override val updateStatPriority = 0

    override fun updateStats(mutableStats: MutableStats, nbt: NbtCompound) {
        this.getStats(nbt).forEach { (type, stat) ->
            mutableStats.addStat(type, stat)
        }
    }
}