package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.modular.item.ingredient.Ingredient
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
    override fun register() {
        super.register()
        ModularItemManager.add(this)
    }

    open fun makeItemStack(ingredients: Iterable<Pair<Ingredient, NbtCompound>>): ItemStack =
        this.defaultStack.apply {
            val nbt = orCreateNbt
            val modularItemNbt = NbtCompound()

            val ingredientsNbt = NbtList()
            ingredients.forEach {
                val compound = NbtCompound()

                compound.put(ID_KEY, NbtString.of(it.first.id.toString()))
                compound.put(DATA_KEY, it.second)

                ingredientsNbt.add(compound)
            }
            modularItemNbt.put(INGREDIENTS_KEY, ingredientsNbt)

            nbt.put(MODULAR_KEY, modularItemNbt)
        }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)

        ModularItemUtil.readIngredients(stack) { ingredient, data, level ->
            ingredient.appendTooltip(level, stack, data, tooltip)
        }
    }

    companion object {
        const val MODULAR_KEY = "mod"
        const val INGREDIENTS_KEY = "ing"
        const val ID_KEY = "id"
        const val DATA_KEY = "data"

        fun register() {
            TestModularItem.register()
            ModularSword.register()
        }
    }
}