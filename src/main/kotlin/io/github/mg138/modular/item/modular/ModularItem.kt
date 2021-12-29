package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.modular.ModularIngredient
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World

abstract class ModularItem(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : BookItem(id, bookItemSettings, settings, vanillaItem) {
    companion object {
        const val MODULAR_ITEM_KEY = "modular"
        const val INGREDIENTS_KEY = "ingredients"
    }

    override fun register() {
        super.register()
        ModularItemManager.add(this)
    }

    open fun makeItemStack(ingredients: Iterable<Ingredient>): ItemStack =
        this.defaultStack.apply {
            val nbt = orCreateNbt
            val modularItemNbt = NbtCompound()

            val ingredientsNbt = NbtList()
            ingredients.forEach {
                ingredientsNbt.add(NbtString.of(it.id.toString()))
            }
            modularItemNbt.put(INGREDIENTS_KEY, ingredientsNbt)

            nbt.put(MODULAR_ITEM_KEY, modularItemNbt)
        }

    open fun getIngredients(itemStack: ItemStack) = ModularItemUtil.getIngredients(itemStack)

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)
        this.getIngredients(stack).forEach { it.appendTooltip(stack, world, tooltip, context) }
    }
}