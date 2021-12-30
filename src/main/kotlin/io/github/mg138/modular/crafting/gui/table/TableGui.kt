package io.github.mg138.modular.crafting.gui.table

import eu.pb4.sgui.api.elements.GuiElement
import eu.pb4.sgui.api.elements.GuiElementBuilder
import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.item.Items
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText

abstract class TableGui(player: ServerPlayerEntity, inventory: GuiInventory) : Gui(
    inventory, ScreenHandlerType.GENERIC_9X5, player
) {
    abstract val level: Int

    companion object {
        const val OUTPUT = (2 * 9) + 7
    }

    override fun update() {
        if (!inventory.validRecipe()) return

        inventory.craft().let { craft ->
            val element = GuiElementBuilder.from(craft)
                .setCallback { _, _, _, gui ->
                    val screenHandler = player!!.currentScreenHandler

                    if (screenHandler.cursorStack.isEmpty) {
                        screenHandler.cursorStack = craft
                        gui.clearSlot(OUTPUT)
                        inventory.consumeItems()
                        inventory.update()
                    }
                }
            setSlot(OUTPUT, element)
        }
    }

    override fun setting() {
        for (y in 0 until inventory.height) {
            for (x in 0 until inventory.width) {
                val thisIndex = (y * 9) + (x + 1)
                val thatIndex = (y * inventory.width) + x
                this.setSlotRedirect(thisIndex, Slot(inventory, thatIndex, x, y))
            }
        }

        val frame = GuiElementBuilder()
            .setItem(Items.BLACK_STAINED_GLASS_PANE)
            .setLore(listOf())
            .setName(LiteralText.EMPTY)
            .setCallback(GuiElement.EMPTY_CALLBACK)
            .hideFlags()
            .build()

        for (y in 0 until 5) {
            val index = y * 9
            this.setSlot(index, frame)
        }
        for (y in 0 until 5) {
            val index = y * 9 + 6
            this.setSlot(index, frame)
        }
        for (y in 0 until 5) {
            val index = y * 9 + 8
            this.setSlot(index, frame)
        }
        for (y in 0 until 5) {
            if (y == 2) continue
            val index = y * 9 + 7
            this.setSlot(index, frame)
        }
    }
}