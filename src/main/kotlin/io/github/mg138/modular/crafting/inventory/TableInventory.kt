package io.github.mg138.modular.crafting.inventory

import eu.pb4.sgui.api.elements.GuiElementBuilder
import io.github.mg138.modular.crafting.block.GuiBlock
import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.crafting.gui.table.TableGui
import io.github.mg138.modular.crafting.recipe.TableRecipe
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.server.network.ServerPlayerEntity

class TableInventory(
    block: GuiBlock, gui: Gui, player: ServerPlayerEntity
) : GuiInventory(block, gui, player.server.playerManager, player.uuid, 5, 5) {
    override fun update() {
        player()?.let { player ->
            val world = player.world
            val match = world.recipeManager.getFirstMatch(TableRecipe.TABLE, this, world)

            validRecipe = match.isPresent

            if (match.isPresent) {
                val matchedRecipe = match.get()
                val matchedOutput = matchedRecipe.output.item

                if (matchedOutput !is ModularItem) {
                    throw IllegalArgumentException("Output ${matchedRecipe.output.item} in recipe ${matchedRecipe.id} is not a ModularItem!")
                }
                output = matchedOutput

                val craft = this.craft()
                val element = GuiElementBuilder.from(craft)
                    .setCallback { _, _, _ ->
                        val screenHandler = player.currentScreenHandler
                        if (screenHandler.cursorStack.isEmpty) {
                            screenHandler.cursorStack = craft
                            gui.clearSlot(TableGui.OUTPUT)
                            consumeItems()
                            update()
                        }
                    }

                gui.setSlot(TableGui.OUTPUT, element)
            } else {
                gui.clearSlot(TableGui.OUTPUT)
            }
        }
    }
}