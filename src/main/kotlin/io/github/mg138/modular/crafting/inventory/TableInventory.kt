package io.github.mg138.modular.crafting.inventory

import io.github.mg138.modular.crafting.block.GuiBlock
import io.github.mg138.modular.crafting.recipe.TableRecipe
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.server.network.ServerPlayerEntity

class TableInventory(
    block: GuiBlock, player: ServerPlayerEntity
) : GuiInventory(block, player, 5, 5) {
    override fun update() {
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
        }
    }
}