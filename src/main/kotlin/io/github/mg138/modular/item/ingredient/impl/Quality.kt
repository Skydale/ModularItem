package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.Stat
import io.github.mg138.bookshelf.utils.StatUtil
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StatedIngredient
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.text.TranslatableText

object Quality : StatedIngredient {
    override val id = Main.modId - "quality"

    private const val qualityKey = "quality"

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

    override fun getStats(nbt: NbtCompound) = StatMap.EMPTY

    override val updateStatPriority = Int.MAX_VALUE

    private fun getQuality(nbt: NbtCompound) =
        nbt.getInt(qualityKey)

    override fun updateStats(mutableStats: MutableStats, nbt: NbtCompound) {
        val quality = getQuality(nbt)
        val m = quality / 100.0

        mutableStats.forEach { (type, stat) ->
            mutableStats.putStat(type, stat.modifier(m))
        }
    }

    override fun lore(data: NbtCompound): Text {
        val quality = getQuality(data)

        return TranslatableText(loreKey, LiteralText(quality.toString()).styled { it.withColor(color(quality)) })
    }

    fun putNbt(data: NbtCompound, quality: Int): NbtCompound {
        data.putInt(qualityKey, quality)
        return data
    }
}