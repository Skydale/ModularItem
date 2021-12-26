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

class ZombieHead : Ingredient(
    Main.modId - "ingredient_zombie_head",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.ZOMBIE_HEAD,
    StatMap().apply {
        putStat(Preset.DamageTypes.DAMAGE_AQUA, StatRange(100.0, 200.0))
    }
) {
    companion object {
        val INGREDIENT_ZOMBIE_HEAD = ZombieHead()

        fun register() {
            INGREDIENT_ZOMBIE_HEAD.register()
        }
    }
}