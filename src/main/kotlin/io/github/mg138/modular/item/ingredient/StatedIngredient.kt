package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.Stats
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.nbt.NbtCompound

interface StatedIngredient : Ingredient {
    fun getStats(nbt: NbtCompound): Stats {
        return ModularItemUtil.getStatMap(nbt)
    }

    val updateStatPriority: Int

    fun updateStats(mutableStats: MutableStats, nbt: NbtCompound) {
        this.getStats(nbt).forEach { (type, stat) ->
            mutableStats.addStat(type, stat)
        }
    }
}