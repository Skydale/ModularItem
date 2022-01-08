package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.StatedItem
import io.github.mg138.bookshelf.stat.event.StatEvent
import io.github.mg138.bookshelf.utils.StatUtil.filterAndSort
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.StatedIngredient
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World

abstract class ModularStatedItem(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    private val ingredients: List<Ingredient> = listOf()
) : ModularItem(id, bookItemSettings, settings, vanillaItem), StatedItem {
    override fun getStatMap(itemStack: ItemStack?) = ModularItemUtil.getStatMap(itemStack)

    override fun getDefaultIngredients() = ingredients

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val lores = getStatMap(stack).lores()
        if (lores.isNotEmpty()) {
            tooltip.addAll(lores)
            tooltip.add(LiteralText.EMPTY)
        }

        super.appendTooltip(stack, world, tooltip, context)
    }

    override fun getDefaultStack(): ItemStack {
        return super.getDefaultStack().also { itemStack ->
            getStatMap(itemStack)
                .filterAndSort<StatEvent.OnItemPostProcessCallback> { it.postProcessPriority }
                .forEach { it.first.onPostProcess(StatEvent.OnItemPostProcessCallback.ItemPostProcessEvent(it.second, this, itemStack))}
        }
    }
}