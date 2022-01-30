package io.github.mg138.modular.crafting.gui.anvil.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.gui.anvil.AnvilGui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class BronzeAnvilGui(player: ServerPlayerEntity, inventory: GuiInventory) : AnvilGui(player, inventory) {
    override val id: Identifier = Main.skydale - "bronze_anvil_gui"

    override val level = 0
}