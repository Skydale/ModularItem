package io.github.mg138.modular.item

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.data.StatMap
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.util.ModularItemUtil
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object TestModularItem : ModularStatedItem(
    Main.modId - "test_modular_item",
    BookItemSettings(true), FabricItemSettings(),
    Items.SAND
)