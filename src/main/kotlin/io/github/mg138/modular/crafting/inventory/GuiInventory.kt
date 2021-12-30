package io.github.mg138.modular.crafting.inventory

import eu.pb4.sgui.virtual.FakeScreenHandler
import io.github.mg138.modular.crafting.gui.Gui
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.World

abstract class GuiInventory(
    val gui: Gui, val player: ServerPlayerEntity, width: Int, height: Int
) : CraftingInventory(FakeScreenHandler(gui), width, height) {
    protected val world: World = player.world

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
            getStack(i).count--
        }
    }

    fun craft(ingredients: Iterable<Pair<Ingredient, NbtCompound>> = listOf()): ItemStack {
        val list: MutableList<Pair<Ingredient, NbtCompound>> = ingredients.toMutableList()

        for (i in 0 until size()) {
            val itemStack = getStack(i)
            val item = itemStack.item
            if (item is Ingredient) {
                list += item to itemStack.orCreateNbt
            }
        }
        return output().makeItemStack(list)
    }
}