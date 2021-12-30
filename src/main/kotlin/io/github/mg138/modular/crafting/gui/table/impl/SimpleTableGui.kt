package io.github.mg138.modular.crafting.gui.table.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.gui.table.TableGui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class SimpleTableGui(player: ServerPlayerEntity, guiInventory: GuiInventory) : TableGui(player, guiInventory) {
    override val id: Identifier = Main.modId - "simple_table_gui"

    override val level = 0
}