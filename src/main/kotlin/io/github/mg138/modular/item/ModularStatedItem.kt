package io.github.mg138.modular.item

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.StatedItem
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.modular.item.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World

abstract class ModularStatedItem(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : ModularItem(id, bookItemSettings, settings, vanillaItem), StatedItem {
    override fun getStatMap(itemStack: ItemStack?): StatMap {
        val nbt = itemStack?.orCreateNbt ?: return StatMap()

        val modularItemNbt = nbt.getCompound(MODULAR_ITEM_KEY)

        return ModularItemUtil.getStatMap(this, itemStack, modularItemNbt)
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.addAll(getStatMap(stack).lores())
        tooltip.add(LiteralText.EMPTY)
        super.appendTooltip(stack, world, tooltip, context)
    }
}