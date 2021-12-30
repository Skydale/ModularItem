package io.github.mg138.modular.crafting.gui.table.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.gui.table.TableGui
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class SimpleTableGui(player: ServerPlayerEntity) : TableGui(player) {
    override val id: Identifier = Main.modId - "simple_table_gui"

    override val level = 0
}