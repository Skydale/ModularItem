package io.github.mg138.modular.item.modular.impl.armor

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.player.data.ArmorType
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ModularBoots : ModularArmor(
    Main.skydale - "modular_boots",
    BookItemSettings(false), FabricItemSettings(),
    Items.IRON_BOOTS
), ArmorType.Feet