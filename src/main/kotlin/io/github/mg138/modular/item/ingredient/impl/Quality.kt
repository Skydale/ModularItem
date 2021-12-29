package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StatedIngredient
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.text.TranslatableText
import net.minecraft.world.World

object Quality : StatedIngredient {
    override val id = Main.modId - "quality"

    const val qualityKey = "quality"

    private fun getQuality(itemStack: ItemStack?) =
        itemStack?.orCreateNbt?.getInt(qualityKey) ?: 0

    private fun color(accuracy: Int): TextColor {
        if (accuracy == 100) {
            return TextColor.fromRgb(0x5bffec)
        }
        if (accuracy >= 90) {
            return TextColor.fromRgb(0x5bff85)
        }
        if (accuracy >= 60) {
            return TextColor.fromRgb(0xfdff59)
        }
        if (accuracy >= 30) {
            return TextColor.fromRgb(0xff8c59)
        }
        return TextColor.fromRgb(0xff5963)
    }

    override fun getStats(itemStack: ItemStack?) = StatMap.EMPTY

    override val updateStatPriority = Int.MAX_VALUE

    override fun updateStats(mutableStats: MutableStats, item: Item, itemStack: ItemStack) {
        val quality = getQuality(itemStack)
        val m = quality / 100.0

        mutableStats.forEach { (type, stat) ->
            mutableStats.putStat(type, stat * m)
        }
    }

    override fun lore(itemStack: ItemStack?): Text {
        val quality = getQuality(itemStack)

        return TranslatableText(loreKey, LiteralText(quality.toString()).styled { it.withColor(color(quality)) })
    }
}