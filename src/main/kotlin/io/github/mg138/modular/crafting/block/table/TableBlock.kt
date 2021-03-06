package io.github.mg138.modular.crafting.block.table

import io.github.mg138.modular.crafting.block.GuiBlock
import io.github.mg138.modular.crafting.block.table.impl.SimpleTableBlock
import io.github.mg138.modular.crafting.inventory.TableInventory
import net.minecraft.block.Blocks
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

abstract class TableBlock(
    id: Identifier
) : GuiBlock(id, Blocks.CRAFTING_TABLE) {
    override fun createInventory(block: GuiBlock, player: ServerPlayerEntity) = TableInventory(block, player)

    companion object {
        fun register() {
            SimpleTableBlock.register()
        }
    }
}