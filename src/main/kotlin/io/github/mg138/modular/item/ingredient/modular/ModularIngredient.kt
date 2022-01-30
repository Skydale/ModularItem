package io.github.mg138.modular.item.ingredient.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.Stats
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.StatedIngredient
import io.github.mg138.modular.item.modular.ModularItem
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World

abstract class ModularIngredient(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item,
    private val ingredients: List<Ingredient>
) : ModularItem(id, bookItemSettings, settings, vanillaItem), StatedIngredient {
    companion object {
        fun register() {
        }
    }

    override fun getName(list: Iterable<Pair<Ingredient, NbtCompound>>): Text {
        return super.getName(list).copy().styled { it.withColor(color) }
    }

    override fun getStats(nbt: NbtCompound): Stats = ModularItemUtil.getStatMap(nbt, this)

    override val updateStatPriority = 0

    override fun makeItemStack(ingredients: Iterable<Pair<Ingredient, NbtCompound>>): ItemStack {
        val list = this.ingredients.map { it to NbtCompound() }.toMutableList()
        list.addAll(ingredients)

        return super.makeItemStack(list)
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val lores = this.getStats(stack.orCreateNbt).lores().toList()
        if (lores.isNotEmpty()) {
            tooltip.addAll(lores)
            tooltip.add(LiteralText.EMPTY)
        }

        super<ModularItem>.appendTooltip(stack, world, tooltip, context)
    }

    override fun register() {
        super<ModularItem>.register()
        super<StatedIngredient>.register()

        ModularIngredientManager.add(this)
    }
}