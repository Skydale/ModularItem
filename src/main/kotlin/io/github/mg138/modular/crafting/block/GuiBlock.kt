package io.github.mg138.modular.crafting.block

import eu.pb4.polymer.api.block.PolymerBlock
import eu.pb4.polymer.api.item.PolymerBlockItem
import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import java.util.*


abstract class GuiBlock(
    val id: Identifier,
    private val vanillaBlock: Block
) : Block(FabricBlockSettings.copy(vanillaBlock)), PolymerBlock {
    private val map: MutableMap<UUID, GuiInventory> = mutableMapOf()

    abstract fun createGui(player: ServerPlayerEntity, inventory: GuiInventory): Gui
    abstract fun createInventory(block: GuiBlock, player: ServerPlayerEntity): GuiInventory

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient() && player is ServerPlayerEntity) {
            val uuid = player.uuid
            val inventory = map.computeIfAbsent(uuid) {
                createInventory(this, player)
            }

            val gui = createGui(player, inventory)
            gui.setting()
            gui.open()
            return ActionResult.CONSUME
        }
        return ActionResult.PASS
    }

    override fun getPolymerBlock(state: BlockState?) = vanillaBlock

    fun inventory(player: ServerPlayerEntity) = map[player.uuid]

    fun validRecipe(player: ServerPlayerEntity): Boolean {
        return inventory(player)?.validRecipe() ?: false
    }

    fun register() {
        Registry.register(Registry.BLOCK, id, this)
        Registry.register(Registry.ITEM, id, PolymerBlockItem(this, FabricItemSettings(), vanillaBlock.asItem()))
    }
}