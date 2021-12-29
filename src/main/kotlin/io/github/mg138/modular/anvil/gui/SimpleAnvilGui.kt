package io.github.mg138.modular.anvil.gui

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class SimpleAnvilGui(player: ServerPlayerEntity) : AnvilGui(player) {
    override val id: Identifier = Main.modId - "simple_anvil_gui"
}