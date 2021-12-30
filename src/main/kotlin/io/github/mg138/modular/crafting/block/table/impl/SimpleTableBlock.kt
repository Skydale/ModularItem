package io.github.mg138.modular.crafting.block.table.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.block.table.TableBlock
import io.github.mg138.modular.crafting.gui.table.impl.SimpleTableGui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.server.network.ServerPlayerEntity


object SimpleTableBlock : TableBlock(Main.modId - "simple_table") {
    override fun createGui(player: ServerPlayerEntity, inventory: GuiInventory) = SimpleTableGui(player, inventory)
}