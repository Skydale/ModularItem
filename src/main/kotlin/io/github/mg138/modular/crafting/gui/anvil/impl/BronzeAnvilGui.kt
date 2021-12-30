package io.github.mg138.modular.crafting.gui.anvil.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.gui.anvil.AnvilGui
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class BronzeAnvilGui(player: ServerPlayerEntity) : AnvilGui(player) {
    override val id: Identifier = Main.modId - "bronze_anvil_gui"

    override val level = 0
}