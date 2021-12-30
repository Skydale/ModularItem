package io.github.mg138.modular.crafting.gui.anvil

import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity

abstract class AnvilGui(player: ServerPlayerEntity, inventory: GuiInventory) : Gui(inventory, ScreenHandlerType.GENERIC_9X6, player) {
    abstract val level: Int

    override fun setting() {
        for (y in 0 until inventory.height) {
            for (x in 0 until inventory.width) {
                val index = (y * inventory.width) + x
                this.setSlotRedirect(index, Slot(inventory, index, x, y))
            }
        }
    }

    override fun update() {
    }
}