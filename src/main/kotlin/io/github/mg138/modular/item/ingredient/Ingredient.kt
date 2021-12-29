package io.github.mg138.modular.item.ingredient

import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.world.World

interface Ingredient {
    val id: Identifier

    val loreKey
        get() = "modular_item.ingredient.${id.namespace}.${id.path}"

    fun lore(itemStack: ItemStack?): Text = TranslatableText(loreKey)

    fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(lore(stack))
    }

    fun register() {
        IngredientManager.add(this)
    }
}