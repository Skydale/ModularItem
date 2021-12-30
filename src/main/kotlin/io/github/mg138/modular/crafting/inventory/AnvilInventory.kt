package io.github.mg138.modular.crafting.inventory

import io.github.mg138.modular.crafting.forge.ForgeManager
import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.crafting.recipe.AnvilRecipe
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.server.network.ServerPlayerEntity


class AnvilInventory(
    gui: Gui, player: ServerPlayerEntity
) : GuiInventory(gui, player, 9, 6) {
    override fun update() {
        val match = world.recipeManager.getFirstMatch(AnvilRecipe.ANVIL, this, world)

        validRecipe = match.isPresent

        if (match.isPresent) {
            val matchedRecipe = match.get()
            val matchedOutput = matchedRecipe.output.item

            if (matchedOutput !is ModularItem) {
                throw IllegalArgumentException("Output ${matchedRecipe.output.item} in recipe ${matchedRecipe.id} is not a ModularItem!")
            }
            output = matchedOutput

            ForgeManager.show(player, player.mainHandStack, this)
        } else {
            ForgeManager.remove(player)
        }
    }
}