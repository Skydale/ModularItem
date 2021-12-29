package io.github.mg138.modular.item.ingredient.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.ZombieHead
import io.github.mg138.modular.item.ingredient.modular.ModularIngredient
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ModularZombieHead : ModularIngredient(
    Main.modId - "modular_zombie_head",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.ZOMBIE_HEAD,
    mutableListOf(ZombieHead)
)