package io.github.mg138.modular.item

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.BookStatedItem
import io.github.mg138.modular.item.ingredient.ModularIngredient
import io.github.mg138.modular.item.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
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
        ModularItemManager.items += this
    }

    fun makeItemStack(ingredients: Iterable<ModularIngredient>): ItemStack =
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

    fun getIngredients(itemStack: ItemStack) = ModularItemUtil.getIngredients(itemStack)

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)
        tooltip.addAll(getIngredients(stack).map { it.lore(stack) })
    }
}