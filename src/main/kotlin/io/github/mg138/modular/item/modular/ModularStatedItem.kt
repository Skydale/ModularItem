package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.StatedItem
import io.github.mg138.modular.item.modular.util.ModularItemUtil
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
    override fun getStatMap(itemStack: ItemStack?) = ModularItemUtil.getStatMap(itemStack)

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.addAll(getStatMap(stack).lores())
        tooltip.add(LiteralText.EMPTY)
        super.appendTooltip(stack, world, tooltip, context)
    }
}