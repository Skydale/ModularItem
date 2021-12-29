package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.StatRange
import io.github.mg138.bookshelf.stat.type.Preset
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StaticStatedIngredient

object SpiderEye : StaticStatedIngredient(
    Main.modId - "spider_eye",
    StatMap().apply {
        putStat(Preset.DamageTypes.DAMAGE_AQUA, StatRange(100.0, 1000.0))
    }
)