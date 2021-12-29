package io.github.mg138.modular.item.ingredient.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.MutableStats
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.data.Stats
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World

//abstract class ModularStatedIngredient(
//    id: Identifier,
//    bookItemSettings: BookItemSettings,
//    settings: Settings, vanillaItem: Item
//) : ModularIngredient(id, bookItemSettings, settings, vanillaItem) {
//    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
//        tooltip.addAll(getStats(stack).lores())
//        tooltip.add(LiteralText.EMPTY)
//        super.appendTooltip(stack, world, tooltip, context)
//    }
//}