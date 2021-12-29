package io.github.mg138.modular.item.ingredient

import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.Stats
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.world.World

interface StatedIngredient : Ingredient {
    fun getStats(itemStack: ItemStack?): Stats

    val updateStatPriority: Int

    fun updateStats(mutableStats: MutableStats, item: Item, itemStack: ItemStack) {
        this.getStats(itemStack).forEach { (type, stat) ->
            mutableStats.addStat(type, stat)
        }
    }
}