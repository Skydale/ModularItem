package io.github.mg138.modular.anvil.forge

import io.github.mg138.modular.anvil.gui.AnvilInventory
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import kotlin.math.abs
import kotlin.math.roundToInt

object ForgeManager {
    const val MIN = 0
    const val MAX = 100
    val MARGIN = abs(MAX - MIN)
    val PERFECT = MARGIN / 2

    val DEFAULT = Pair(PERFECT, 0)

    const val DONE = 100

    private val map: MutableMap<ServerPlayerEntity, Pair<Int, Int>> = mutableMapOf()

    fun remove(player: ServerPlayerEntity) = map.remove(player)

    operator fun get(player: ServerPlayerEntity) = map.getOrPut(player) { DEFAULT }

    private fun forge(
        player: ServerPlayerEntity,
        hammerItemStack: ItemStack,
        inventory: AnvilInventory,
        modifier: Int,
        undo: Boolean
    ): ActionResult {
        val (accuracy, progress) = map.getOrPut(player) { DEFAULT }

        val accuracyD = ((1 / accuracy.toDouble()) * modifier).roundToInt()

        val accuracyNew = accuracy + accuracyD
        val progressNew = if (undo) {
            player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.5F, 0.5F)
            progress - 5
        } else {
            player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.5F, 1.0F)
            progress + 7
        }

        val new = Pair(accuracyNew, progressNew)

        if (new.second <= 0) {
            return ActionResult.PASS
        }

        ProgressDisplay.show(player, hammerItemStack, inventory, new)

        if (new.first <= MIN || new.first >= MAX) {
            player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 0.5F, 0.5F)
            return ActionResult.FAIL
        }

        map[player] = new

        if (new.second >= DONE) {
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 0.5F, 1.5F)
            return ActionResult.SUCCESS
        }

        return ActionResult.PASS
    }

    fun left(player: ServerPlayerEntity, hammerItemStack: ItemStack, inventory: AnvilInventory) =
        forge(player, hammerItemStack, inventory, -400, false)

    fun leftUndo(player: ServerPlayerEntity, hammerItemStack: ItemStack, inventory: AnvilInventory) =
        forge(player, hammerItemStack, inventory, -400, true)

    fun right(player: ServerPlayerEntity, hammerItemStack: ItemStack, inventory: AnvilInventory) =
        forge(player, hammerItemStack, inventory, 600, false)

    fun rightUndo(player: ServerPlayerEntity, hammerItemStack: ItemStack, inventory: AnvilInventory) =
        forge(player, hammerItemStack, inventory, 600, true)
}