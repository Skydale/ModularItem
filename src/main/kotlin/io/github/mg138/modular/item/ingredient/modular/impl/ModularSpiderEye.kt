package io.github.mg138.modular.item.ingredient.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.SpiderEye
import io.github.mg138.modular.item.ingredient.modular.ModularIngredient
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ModularSpiderEye : ModularIngredient(
    Main.modId - "modular_spider_eye",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.SPIDER_EYE,
    mutableListOf(SpiderEye)
)