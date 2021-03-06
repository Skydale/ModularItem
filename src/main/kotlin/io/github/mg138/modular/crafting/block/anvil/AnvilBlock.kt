package io.github.mg138.modular.crafting.block.anvil

import io.github.mg138.modular.crafting.block.LeveledBlock
import io.github.mg138.modular.crafting.block.GuiBlock
import io.github.mg138.modular.crafting.block.anvil.impl.BronzeAnvilBlock
import io.github.mg138.modular.crafting.block.anvil.impl.IronAnvilBlock
import io.github.mg138.modular.crafting.inventory.AnvilInventory
import net.minecraft.block.Blocks
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier


abstract class AnvilBlock(
    id: Identifier
) : GuiBlock(id, Blocks.SMITHING_TABLE), LeveledBlock {
    override fun createInventory(block: GuiBlock, player: ServerPlayerEntity) = AnvilInventory(block, player)

    companion object {
        fun register() {
            BronzeAnvilBlock.register()
            IronAnvilBlock.register()
        }
    }
}