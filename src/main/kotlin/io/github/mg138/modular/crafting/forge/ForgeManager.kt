package io.github.mg138.modular.crafting.forge

import io.github.mg138.modular.crafting.inventory.GuiInventory
import io.github.mg138.modular.item.ingredient.impl.Quality
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

object ForgeManager {
    object Accuracy {
        const val MIN = 0
        const val MAX = 100
        val MARGIN = abs(MAX - MIN)
        val PERFECT = MARGIN / 2
    }

    object Progress {
        const val DONE = 100
    }

    private val DEFAULT_PAIR = Accuracy.PERFECT to 0

    private val map: MutableMap<ServerPlayerEntity, Pair<Int, Int>> = mutableMapOf()

    fun remove(player: ServerPlayerEntity) = map.remove(player)

    operator fun get(player: ServerPlayerEntity) = map.getOrPut(player) { DEFAULT_PAIR }

    fun processResult(player: ServerPlayerEntity, inventory: GuiInventory, result: ActionResult) {
        if (result == ActionResult.SUCCESS) {
            val (accuracy, _) = this[player]

            val quality = abs(Accuracy.MAX - abs((2 * accuracy) - Accuracy.MAX))

            player.inventory.offerOrDrop(inventory.craft(
                listOf(
                    (Quality to Quality.putNbt(NbtCompound(), quality)).also { (ingredient, data) ->
                        player.sendMessage(ingredient.lore(data), false)
                    }
                )
            ))
        }

        if (result == ActionResult.FAIL || result == ActionResult.SUCCESS) {
            inventory.consumeItems()
            remove(player)
            inventory.update()
        }
    }

    fun show(player: ServerPlayerEntity, itemStack: ItemStack?, inventory: GuiInventory) {
        ProgressDisplay.show(player, itemStack, inventory, this[player])
    }

    private fun forge(
        player: ServerPlayerEntity,
        itemStack: ItemStack,
        inventory: GuiInventory,
        modifier: Int,
        undo: Boolean
    ): ActionResult {
        val (accuracy, progress) = map.getOrPut(player) { DEFAULT_PAIR }

        val accuracyD = ((1 / accuracy.toDouble()) * modifier).roundToInt()

        val accuracyNew = accuracy + accuracyD
        val progressNew = if (undo) {
            player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.5F, 0.5F)
            progress - 5
        } else {
            player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.5F, 1.0F)
            progress + 7
        }

        val new = accuracyNew to progressNew

        if (new.second <= 0) {
            return ActionResult.PASS
        }

        ProgressDisplay.show(player, itemStack, inventory, new)

        if (new.first <= Accuracy.MIN || new.first >= Accuracy.MAX) {
            player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 0.5F, 0.5F)
            return ActionResult.FAIL
        }

        map[player] = new

        if (new.second >= Progress.DONE) {
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 0.5F, 1.5F)
            return ActionResult.SUCCESS
        }

        return ActionResult.PASS
    }

    fun left(player: ServerPlayerEntity, itemStack: ItemStack, inventory: GuiInventory) =
        forge(player, itemStack, inventory, -400, false)

    fun leftUndo(player: ServerPlayerEntity, itemStack: ItemStack, inventory: GuiInventory) =
        forge(player, itemStack, inventory, -400, true)

    fun right(player: ServerPlayerEntity, itemStack: ItemStack, inventory: GuiInventory) =
        forge(player, itemStack, inventory, 600, false)

    fun rightUndo(player: ServerPlayerEntity, itemStack: ItemStack, inventory: GuiInventory) =
        forge(player, itemStack, inventory, 600, true)
}