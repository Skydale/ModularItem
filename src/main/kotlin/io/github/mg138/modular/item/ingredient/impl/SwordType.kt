package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.StatSingle
import io.github.mg138.bookshelf.stat.type.StatTypes
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StaticStatedIngredient

object SwordType : StaticStatedIngredient(
    Main.modId - "sword_type",
    StatMap().apply {
        putStat(StatTypes.MiscTypes.ATTACK_DELAY, StatSingle(10.0))
    }
)