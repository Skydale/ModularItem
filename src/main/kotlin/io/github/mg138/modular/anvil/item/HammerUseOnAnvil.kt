package io.github.mg138.modular.anvil.item

import io.github.mg138.modular.anvil.block.AnvilBlock
import io.github.mg138.modular.anvil.forge.ForgeManager
import io.github.mg138.modular.anvil.gui.AnvilInventory
import io.github.mg138.modular.item.ingredient.impl.Quality
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.math.abs
import kotlin.math.roundToInt

@Suppress("UNUSED")
object HammerUseOnAnvil {
    private fun processResult(player: ServerPlayerEntity, inventory: AnvilInventory, result: ActionResult) {
        if (result == ActionResult.SUCCESS) {
            val (accuracy, _) = ForgeManager[player]

            val quality = abs(ForgeManager.MAX - abs((2 * accuracy) - ForgeManager.MAX))

            player.inventory.offerOrDrop(inventory.output().makeItemStack(listOf(Quality)).apply {
                orCreateNbt.putInt(Quality.qualityKey, quality)
                player.sendMessage(Quality.lore(this), false)
            })
        }

        if (result == ActionResult.FAIL || result == ActionResult.SUCCESS) {
            inventory.consumeItems()
            ForgeManager.remove(player)
            inventory.update()
        }
    }

    private fun leftClickAnvil(
        player: PlayerEntity,
        world: World,
        hand: Hand,
        pos: BlockPos,
        direction: Direction
    ): ActionResult {
        if (player !is ServerPlayerEntity) return ActionResult.PASS
        val block = world.getBlockState(pos).block

        if (block !is AnvilBlock) return ActionResult.PASS
        val inventory = block.inventory(player) ?: return ActionResult.PASS
        if (!inventory.validRecipe()) return ActionResult.PASS

        val hammerItemStack = player.mainHandStack
        if (hammerItemStack.item !is HammerItem) return ActionResult.PASS

        val result = if (player.isSneaking) {
            ForgeManager.leftUndo(player, hammerItemStack, inventory)
        } else {
            ForgeManager.left(player, hammerItemStack, inventory)
        }
        processResult(player, inventory, result)

        return ActionResult.CONSUME
    }

    private fun rightClickAnvil(
        player: PlayerEntity,
        world: World,
        hand: Hand,
        hitResult: BlockHitResult
    ): ActionResult {
        if (player !is ServerPlayerEntity) return ActionResult.PASS
        val block = world.getBlockState(hitResult.blockPos).block

        if (block !is AnvilBlock) return ActionResult.PASS
        val inventory = block.inventory(player) ?: return ActionResult.PASS
        if (!inventory.validRecipe()) return ActionResult.PASS

        val hammerItemStack = player.mainHandStack
        if (hammerItemStack.item !is HammerItem) return ActionResult.PASS

        val result = if (player.isSneaking) {
            ForgeManager.rightUndo(player, hammerItemStack, inventory)
        } else {
            ForgeManager.right(player, hammerItemStack, inventory)
        }
        processResult(player, inventory, result)

        return ActionResult.CONSUME
    }

    fun register() {
        AttackBlockCallback.EVENT.register(this::leftClickAnvil)

        UseBlockCallback.EVENT.register(this::rightClickAnvil)
    }
}