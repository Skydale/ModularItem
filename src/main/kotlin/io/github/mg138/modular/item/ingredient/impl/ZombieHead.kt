package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.StatRange
import io.github.mg138.bookshelf.stat.type.Preset
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.ModularStaticStatedIngredient
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ZombieHead : ModularStaticStatedIngredient(
    Main.modId - "ingredient_zombie_head",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.ZOMBIE_HEAD,
    StatMap().apply {
        putStat(Preset.DamageTypes.DAMAGE_TERRA, StatRange(100.0, 1000.0))
    }
)