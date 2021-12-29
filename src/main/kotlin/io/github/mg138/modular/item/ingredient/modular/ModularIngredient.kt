package io.github.mg138.modular.item.ingredient.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.data.Stats
import io.github.mg138.modular.item.modular.ModularItem
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.StatedIngredient
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.world.World

abstract class ModularIngredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    val ingredients: List<Ingredient>
) : ModularItem(id, bookItemSettings, settings, vanillaItem), StatedIngredient {
    override fun getStats(itemStack: ItemStack?) = ModularItemUtil.getStatMap(this, itemStack)

    override val updateStatPriority = 0

    override fun makeItemStack(ingredients: Iterable<Ingredient>): ItemStack {
        val list = this.ingredients.toMutableList()
        list.addAll(ingredients)

        return super.makeItemStack(list)
    }

    override fun register() {
        super<ModularItem>.register()

        ModularIngredientManager.add(this)
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.addAll(getStats(stack).lores())
        tooltip.add(LiteralText.EMPTY)
        super<ModularItem>.appendTooltip(stack, world, tooltip, context)
    }
}