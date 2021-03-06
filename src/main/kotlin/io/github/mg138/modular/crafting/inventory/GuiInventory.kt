package io.github.mg138.modular.crafting.inventory

import io.github.mg138.modular.crafting.block.GuiBlock
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

abstract class GuiInventory(
    val block: GuiBlock, val server: MinecraftServer, private val uuid: UUID, width: Int, height: Int
) : CraftingInventory(object : ScreenHandler(null, -1) {
    override fun canUse(player: PlayerEntity?): Boolean {
        return false
    }
}, width, height) {
    constructor(block: GuiBlock, player: ServerPlayerEntity, width: Int, height: Int) : this(
        block, player.server, player.uuid, width, height
    )

    val player
        get() = server.playerManager.getPlayer(uuid)!!

    protected var validRecipe = false
    fun validRecipe() = validRecipe

    protected var output: ModularItem? = null
    fun output() = output!!

    abstract fun update()

    override fun setStack(slot: Int, stack: ItemStack?) {
        super.setStack(slot, stack)

        update()
    }

    fun consumeItems() {
        for (i in 0 until size()) {
            getStack(i).decrement(1)
        }
    }

    fun getIngredients(): Iterable<Pair<Ingredient, NbtCompound>> {
        val list: MutableList<Pair<Ingredient, NbtCompound>> = mutableListOf()

        for (i in 0 until size()) {
            val itemStack = getStack(i)
            val item = itemStack.item
            if (item is Ingredient) {
                list += item to itemStack.orCreateNbt
            }
        }
        return list
    }

    fun craft(ingredients: Iterable<Pair<Ingredient, NbtCompound>> = listOf()): ItemStack {
        val list = this.getIngredients().toMutableList()
        list.addAll(ingredients)

        return output().makeItemStack(list)
    }
}