package io.github.mg138.modular.crafting.block.anvil.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.block.anvil.AnvilBlock
import io.github.mg138.modular.crafting.gui.anvil.impl.BronzeAnvilGui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.server.network.ServerPlayerEntity

object BronzeAnvilBlock : AnvilBlock(
    Main.modId - "bronze_anvil_block"
){
    override fun createGui(player: ServerPlayerEntity, inventory: GuiInventory) = BronzeAnvilGui(player, inventory)

    override val level = 0
}