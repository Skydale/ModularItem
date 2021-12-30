package io.github.mg138.modular

import eu.pb4.polymer.api.resourcepack.PolymerRPUtils
import io.github.mg138.modular.command.MakeModularItemCmd
import io.github.mg138.modular.crafting.block.anvil.AnvilBlock
import io.github.mg138.modular.crafting.block.table.TableBlock
import io.github.mg138.modular.crafting.event.HammerUseOnAnvil
import io.github.mg138.modular.crafting.forge.ProgressDisplay
import io.github.mg138.modular.crafting.item.HammerItem
import io.github.mg138.modular.crafting.recipe.AnvilRecipe
import io.github.mg138.modular.crafting.recipe.TableRecipe
import io.github.mg138.modular.item.ingredient.Ingredient
import io.github.mg138.modular.item.ingredient.modular.ModularIngredient
import io.github.mg138.modular.item.modular.ModularItem
import net.fabricmc.api.DedicatedServerModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object Main : DedicatedServerModInitializer {
    const val modId = "modular_item"
    private val logger: Logger = LogManager.getLogger(modId)

    override fun onInitializeServer() {
        PolymerRPUtils.addAssetSource(modId)

        Ingredient.register()
        ModularIngredient.register()

        ProgressDisplay.register()

        HammerItem.register()

        AnvilRecipe.register()
        AnvilBlock.register()
        TableRecipe.register()
        TableBlock.register()

        HammerUseOnAnvil.register()

        MakeModularItemCmd.register()

        ModularItem.register()
        logger.info("Registered Modular Item.")
    }
}