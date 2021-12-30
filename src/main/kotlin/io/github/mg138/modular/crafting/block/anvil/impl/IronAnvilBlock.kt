package io.github.mg138.modular.crafting.block.anvil.impl

import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.block.anvil.AnvilBlock
import io.github.mg138.modular.crafting.gui.anvil.impl.IronAnvilGui
import net.minecraft.server.network.ServerPlayerEntity

object IronAnvilBlock : AnvilBlock(Main.modId - "iron_anvil_block"){
    override fun createGui(player: ServerPlayerEntity) = IronAnvilGui(player)
}