package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.StatRange
import io.github.mg138.bookshelf.stat.type.StatTypes
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StaticStatedIngredient

object ZombieHead : StaticStatedIngredient(
    Main.skydale - "zombie_head",
    StatMap().apply {
        putStat(StatTypes.DamageTypes.DamageTerra, StatRange(100.0, 1000.0))
    }
)