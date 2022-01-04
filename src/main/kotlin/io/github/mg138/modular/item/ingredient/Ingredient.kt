package io.github.mg138.modular.item.ingredient

import io.github.mg138.modular.item.ingredient.impl.IronBlade
import io.github.mg138.modular.item.ingredient.impl.Quality
import io.github.mg138.modular.item.ingredient.impl.SpiderEye
import io.github.mg138.modular.item.ingredient.impl.ZombieHead
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

interface Ingredient {

    val id: Identifier

    val loreKey
        get() = "modular_item.ingredient.${id.namespace}.${id.path}"

    fun lore(data: NbtCompound): Text = TranslatableText(loreKey)

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

        fun register() {
            SpiderEye.register()
            ZombieHead.register()
            Quality.register()
            IronBlade.register()
        }
    }
}