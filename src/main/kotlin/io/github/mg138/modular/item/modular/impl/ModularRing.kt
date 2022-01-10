package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.player.data.ArmorType
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ModularRing : ModularArmor(
    Main.modId - "modular_ring",
    BookItemSettings(false), FabricItemSettings(),
    Items.GOLD_NUGGET
), ArmorType.Ring