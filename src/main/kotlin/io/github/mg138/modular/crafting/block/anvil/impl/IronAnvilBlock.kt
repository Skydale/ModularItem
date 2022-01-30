package io.github.mg138.modular.crafting.block.anvil.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.block.anvil.AnvilBlock
import io.github.mg138.modular.crafting.gui.anvil.impl.IronAnvilGui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.server.network.ServerPlayerEntity

object IronAnvilBlock : AnvilBlock(
    Main.skydale - "iron_anvil_block"
){
    override fun createGui(player: ServerPlayerEntity, inventory: GuiInventory) = IronAnvilGui(player, inventory)

    override val level = 1
}