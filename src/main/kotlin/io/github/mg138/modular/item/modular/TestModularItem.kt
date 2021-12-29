package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object TestModularItem : ModularStatedItem(
    Main.modId - "test_modular_item",
    BookItemSettings(true), FabricItemSettings(),
    Items.SAND
)