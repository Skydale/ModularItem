package io.github.mg138.modular.crafting.inventory

import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.block.GuiBlock
import io.github.mg138.modular.crafting.block.anvil.LeveledBlock
import io.github.mg138.modular.crafting.forge.ForgeManager
import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.crafting.recipe.AnvilRecipe
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.block.Blocks
import net.minecraft.block.SweetBerryBushBlock
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.TranslatableText

class AnvilInventory(
    block: GuiBlock, gui: Gui, player: ServerPlayerEntity
) : GuiInventory(block, gui, player, 9, 6) {
    companion object {
        private const val LEVEL_TOO_LOW_KEY = "${Main.modId}.anvil.message.level_too_low"
        private val LEVEL_TOO_LOW_TEXT = TranslatableText(LEVEL_TOO_LOW_KEY)
    }

    override fun update() {
        val match = world.recipeManager.getFirstMatch(AnvilRecipe.ANVIL, this, world)

        validRecipe = match.isPresent

        if (match.isPresent) {
            val matchedRecipe = match.get()

            if (block is LeveledBlock && block.level >= matchedRecipe.level) {
                val matchedOutput = matchedRecipe.output.item

                if (matchedOutput !is ModularItem) {
                    throw IllegalArgumentException("Output ${matchedRecipe.output.item} in recipe ${matchedRecipe.id} is not a ModularItem!")
                }
                output = matchedOutput

                ForgeManager.show(player, player.mainHandStack, this)
                return
            } else {
                player.sendMessage(LEVEL_TOO_LOW_TEXT, false)
                player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.BLOCKS, 0.5F, 0.7F)
            }
        }
        ForgeManager.remove(player)
    }
}