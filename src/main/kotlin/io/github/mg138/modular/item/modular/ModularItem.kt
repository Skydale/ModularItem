package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.SimpleBookItem
import io.github.mg138.modular.item.ingredient.Hide
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.modular.impl.armor.*
import io.github.mg138.modular.item.modular.impl.weapon.*
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.text.LiteralText
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.world.World

abstract class ModularItem(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : SimpleBookItem(id, bookItemSettings, settings, vanillaItem) {
    companion object {
        fun ingredientList(list: Iterable<Pair<Ingredient, NbtCompound>>): Text {
            val name: MutableText = LiteralText.EMPTY.copy()
            val ingredients: MutableSet<Ingredient> = mutableSetOf()

            list.forEach { (ingredient, data) ->
                if (ingredient !is Hide) {
                    if (ingredient is Item) {
                        if (ingredient !in ingredients) {
                            name.append(ingredient.lore(data))
                            ingredients += ingredient
                        }
                    }
                }
            }

            return name
        }

        const val MODULAR_KEY = "mod"
        const val INGREDIENTS_KEY = "ing"
        const val ID_KEY = "id"
        const val DATA_KEY = "data"

        fun register() {
            ModularHelmet.register()
            ModularChestplate.register()
            ModularLeggings.register()
            ModularBoots.register()
            ModularNecklace.register()
            ModularBracelet.register()
            ModularRing.register()
            ModularSword.register()
            ModularDagger.register()
            ModularSpear.register()
            ModularBow.register()
            ModularCrossbow.register()
            ModularLongbow.register()
            ModularWand.register()
            ModularLongWand.register()
            ModularTrident.register()
        }
    }

    open fun getDefaultIngredients(): List<Ingredient> = listOf()

    override fun register() {
        super.register()
        ModularItemManager.add(this)
    }

    open fun getName(list: Iterable<Pair<Ingredient, NbtCompound>>): Text {
        return TranslatableText(this.translationKey, ingredientList(list))
    }

    override fun getName(stack: ItemStack): Text {
        val list: MutableList<Pair<Ingredient, NbtCompound>> = mutableListOf()

        ModularItemUtil.readIngredientsShallow(stack) { ingredient, data, _ ->
            list += ingredient to data
        }
        return getName(list)
    }

    open fun makeItemStack(ingredients: Iterable<Pair<Ingredient, NbtCompound>>): ItemStack {
        return this.defaultStack.apply {
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
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)

        ModularItemUtil.readIngredientsShallow(stack) { ingredient, data, level ->
            ingredient.appendTooltip(level, stack, data, tooltip)
        }
    }
}