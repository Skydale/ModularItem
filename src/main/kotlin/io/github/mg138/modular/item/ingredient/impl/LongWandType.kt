package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.StatSingle
import io.github.mg138.bookshelf.stat.type.StatTypes
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StaticStatedIngredient

object LongWandType : StaticStatedIngredient(
    Main.skydale - "long_wand_type",
    StatMap().apply {
        putStat(StatTypes.MiscTypes.AttackDelay, StatSingle(17.0))
    }
)