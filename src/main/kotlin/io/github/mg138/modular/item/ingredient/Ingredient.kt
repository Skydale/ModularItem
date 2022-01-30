package io.github.mg138.modular.item.ingredient

import io.github.mg138.modular.item.ingredient.impl.*
import io.github.mg138.modular.item.modular.util.ModularItemUtil
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.*
import net.minecraft.util.Identifier

interface Ingredient {
    val id: Identifier

    val color: TextColor
        get() = TextColor.fromRgb(0x7bc558)

    val key
        get() = "${id.namespace}.${id.path}"

    val nameKey
        get() = "item.$key"

    fun name(nbt: NbtCompound): Text {
        val text = LiteralText.EMPTY.copy()

        ingredientSet(nbt).forEach { (ingredient, data) ->
            text.append(ingredient.name(data))
        }

        return TranslatableText(nameKey, text).styled { it.withColor(color) }
    }

    val loreKey
        get() = "modular_item.ingredient.$key"

    fun lore(nbt: NbtCompound): Text {
        val text = LiteralText.EMPTY.copy()

        ingredientSet(nbt).forEach { (ingredient, data) ->
            text.append(ingredient.lore(data))
        }

        return TranslatableText(loreKey, text).styled { it.withColor(color) }
    }

    fun appendTooltip(
        level: Int,
        itemStack: ItemStack,
        data: NbtCompound,
        tooltip: MutableList<Text>,
    ) {
        val padding = LiteralText("".padEnd(level * 2, ' '))

        tooltip.add(padding.append(lore(data)))
    }

    fun register() {
        IngredientManager.add(this)
    }

    companion object {
        fun ingredientSet(nbt: NbtCompound): MutableSet<Pair<Ingredient, NbtCompound>> {
            val ingredients: MutableSet<Pair<Ingredient, NbtCompound>> = mutableSetOf()

            ModularItemUtil.readIngredientsShallow(nbt) { ingredient, data, _ ->
                if (ingredient !is Hide) {
                    ingredients += ingredient to data
                }
            }

            return ingredients
        }

        fun register() {
            SpiderEye.register()
            ZombieHead.register()
            Quality.register()
            SwordType.register()
            SpearType.register()
            DaggerType.register()
            BowType.register()
            CrossbowType.register()
            LongbowType.register()
            WandType.register()
            LongWandType.register()
            TridentType.register()
        }
    }
}