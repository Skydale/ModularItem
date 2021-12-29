package io.github.mg138.modular.item.ingredient.tag

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.Ingredient
import net.fabricmc.fabric.api.tag.TagFactory
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

object IngredientTag {
    val TOOL_HEAD: Tag<Item> = TagFactory.ITEM.create(Main.modId - "tool_head")
}