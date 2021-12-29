package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.ModularStatedIngredient
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.text.LiteralText
import net.minecraft.text.TextColor
import net.minecraft.text.TranslatableText

object Quality : ModularStatedIngredient(
    Main.modId - "quality_dummy_item",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.AIR
) {
    const val qualityKey = "quality"

    override val updateStatPriority = 100000000

    private fun getQuality(itemStack: ItemStack) =
        itemStack.orCreateNbt.getInt(qualityKey)

    override fun updateStat(statMap: StatMap, item: Item, itemStack: ItemStack) {
        val quality = getQuality(itemStack)
        val m = quality / 100.0

        statMap.forEach { (type, stat) ->
            statMap[type] = stat * m
        }
    }

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

    override fun lore(itemStack: ItemStack): TranslatableText {
        val quality = getQuality(itemStack)

        return TranslatableText(loreKey, LiteralText(quality.toString()).styled { it.withColor(color(quality)) })
    }
}