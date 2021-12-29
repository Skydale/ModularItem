package io.github.mg138.modular.anvil.item

import io.github.mg138.bookshelf.item.BookItem
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object HammerItem : BookItem(
    Main.modId - "test_hammer",
    BookItemSettings(false), FabricItemSettings(),
    Items.IRON_SHOVEL
)