package io.github.mg138.modular.crafting.event

import io.github.mg138.modular.crafting.block.anvil.AnvilBlock
import io.github.mg138.modular.crafting.forge.ForgeManager
import io.github.mg138.modular.crafting.item.HammerItem
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

@Suppress("UNUSED")
object HammerUseOnAnvil {
    private fun leftClickAnvil(
        player: PlayerEntity,
        world: World,
        hand: Hand,
        pos: BlockPos,
        direction: Direction
    ): ActionResult {
        if (player !is ServerPlayerEntity) return ActionResult.PASS

        val block = world.getBlockState(pos).block as? AnvilBlock ?: return ActionResult.PASS

        val inventory = block.inventory(player) ?: return ActionResult.PASS
        if (!inventory.validRecipe()) return ActionResult.PASS

        val itemStack = player.mainHandStack
        if (itemStack.item != HammerItem) return ActionResult.PASS

        val result = if (!player.isSneaking) {
            ForgeManager.left(player, itemStack, inventory)
        } else {
            ForgeManager.leftUndo(player, itemStack, inventory)
        }
        ForgeManager.processResult(player, inventory, result)

        return ActionResult.CONSUME
    }

    private fun rightClickAnvil(player: PlayerEntity, world: World, hand: Hand, hitResult: BlockHitResult): ActionResult {
        if (player !is ServerPlayerEntity) return ActionResult.PASS
        val block = world.getBlockState(hitResult.blockPos).block as? AnvilBlock ?: return ActionResult.PASS

        val inventory = block.inventory(player) ?: return ActionResult.PASS
        if (!inventory.validRecipe()) return ActionResult.PASS

        val itemStack = player.mainHandStack
        if (itemStack.item != HammerItem) return ActionResult.PASS

        val result = if (!player.isSneaking) {
            ForgeManager.right(player, itemStack, inventory)
        } else {
            ForgeManager.rightUndo(player, itemStack, inventory)
        }
        ForgeManager.processResult(player, inventory, result)

        return ActionResult.CONSUME
    }

    fun register() {
        AttackBlockCallback.EVENT.register(this::leftClickAnvil)
        UseBlockCallback.EVENT.register(this::rightClickAnvil)
    }
}