package io.github.mg138.modular.anvil.gui

import eu.pb4.sgui.virtual.FakeScreenHandler
import io.github.mg138.modular.anvil.AnvilRecipe
import io.github.mg138.modular.anvil.forge.ForgeManager
import io.github.mg138.modular.anvil.forge.ProgressDisplay
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.modular.ModularItem
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity


class AnvilInventory(val gui: AnvilGui, private val player: ServerPlayerEntity) :
    CraftingInventory(FakeScreenHandler(gui), width, height) {

    companion object {
        const val width = 9
        const val height = 6
    }

    private var validRecipe = false

    fun consumeItems() {
        for (i in 0 until size()) {
            getStack(i).count--
        }
    }

    fun validRecipe() = validRecipe

    private var output: ModularItem? = null

    fun output() = output!!

    fun craft(ingredients: Iterable<Ingredient> = listOf()): ItemStack {
        val list: MutableList<Ingredient> = ingredients.toMutableList()
        for (i in 0 until size()) {
            val item = getStack(i).item

            if (item is Ingredient) {
                list.add(item)
            }
        }

        return output().makeItemStack(list)
    }

    fun update() {
        val world = player.world

        val match = world.recipeManager.getFirstMatch(AnvilRecipe.ANVIL, this, world)

        validRecipe = match.isPresent

        if (match.isPresent) {
            val matchedRecipe = match.get()
            val matchedOutput = matchedRecipe.output.item

            if (matchedOutput !is ModularItem) {
                throw IllegalArgumentException("Output ${matchedRecipe.output.item} in recipe ${matchedRecipe.id} is not a ModularItem!")
            }
            output = matchedOutput

            ProgressDisplay.show(player, player.mainHandStack, this, ForgeManager.DEFAULT)
        } else {
            ForgeManager.remove(player)
        }
    }

    override fun setStack(slot: Int, stack: ItemStack?) {
        super.setStack(slot, stack)

        update()
    }
}