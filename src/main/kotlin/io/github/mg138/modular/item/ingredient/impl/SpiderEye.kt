package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.StatMap
import io.github.mg138.bookshelf.stat.stat.StatRange
import io.github.mg138.bookshelf.stat.type.Preset
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.Ingredient
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

class SpiderEye : Ingredient(
    Main.modId - "ingredient_spider_eye",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.SPIDER_EYE,
    StatMap().apply {
        putStat(Preset.DamageTypes.DAMAGE_TERRA, StatRange(100.0, 200.0))
    }
) {
    companion object {
        val INGREDIENT_SPIDER_EYE = SpiderEye()

        fun register() {
            INGREDIENT_SPIDER_EYE.register()
        }
    }
}