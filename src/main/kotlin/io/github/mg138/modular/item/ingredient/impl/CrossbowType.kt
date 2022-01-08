package io.github.mg138.modular.item.ingredient.impl

import io.github.mg138.bookshelf.item.type.SimpleBow
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.stat.stat.StatSingle
import io.github.mg138.bookshelf.stat.type.StatTypes
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StaticStatedIngredient
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

object CrossbowType : StaticStatedIngredient(
    Main.modId - "crossbow_type",
    StatMap().apply {
        putStat(StatTypes.MiscTypes.ATTACK_DELAY, StatSingle(27.0))
    }
)