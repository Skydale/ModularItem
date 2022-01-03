package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StatedIngredient
import io.github.mg138.modular.item.modular.ModularStatedItem
import io.github.mg138.player.data.ArmorType
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ModularBracelet : ModularArmor(
    Main.modId - "modular_bracelet",
    BookItemSettings(false), FabricItemSettings(),
    Items.MUSIC_DISC_CAT
), ArmorType.Bracelet