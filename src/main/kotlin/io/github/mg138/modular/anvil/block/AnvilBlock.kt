package io.github.mg138.modular.anvil.block

import eu.pb4.polymer.api.block.PolymerBlock
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.anvil.gui.AnvilInventory
import io.github.mg138.modular.anvil.gui.SimpleAnvilGui
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World


object AnvilBlock : Block(FabricBlockSettings.copy(Blocks.SMITHING_TABLE)), PolymerBlock {
    private val map: MutableMap<ServerPlayerEntity, AnvilInventory> = mutableMapOf()

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient() && player is ServerPlayerEntity) {
            val inventory = map.computeIfAbsent(player) {
                val gui = SimpleAnvilGui(player)

                AnvilInventory(gui, player).also {
                    gui.slotRedirectTo(it)
                }
            }
            inventory.gui.open()
            return ActionResult.CONSUME
        }
        return ActionResult.PASS
    }

    override fun getPolymerBlock(state: BlockState?): Block = Blocks.SMITHING_TABLE

    fun inventory(player: ServerPlayerEntity) = map[player]

    fun validRecipe(player: ServerPlayerEntity): Boolean {
        return inventory(player)?.validRecipe() ?: false
    }

    val ID = Main.modId - "anvil_block"

    fun register() {
        Registry.register(Registry.BLOCK, ID, this)
    }
}