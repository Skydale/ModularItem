package io.github.mg138.modular

import eu.pb4.polymer.api.resourcepack.PolymerRPUtils
import io.github.mg138.modular.anvil.AnvilRecipe
import io.github.mg138.modular.anvil.block.AnvilBlock
import io.github.mg138.modular.anvil.forge.ProgressDisplay
import io.github.mg138.modular.anvil.item.HammerUseOnAnvil
import io.github.mg138.modular.anvil.item.HammerItem
import io.github.mg138.modular.command.MakeModularItemCmd
import io.github.mg138.modular.item.TestModularItem
import io.github.mg138.modular.item.ingredient.impl.Quality
import io.github.mg138.modular.item.ingredient.impl.SpiderEye
import io.github.mg138.modular.item.ingredient.impl.ZombieHead
import net.fabricmc.api.DedicatedServerModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object Main : DedicatedServerModInitializer {
    const val modId = "modular_item"
    val logger: Logger = LogManager.getLogger(modId)

    override fun onInitializeServer() {
        PolymerRPUtils.addAssetSource(modId)

        TestModularItem.register()

        ProgressDisplay.register()
        HammerItem.register()
        HammerUseOnAnvil.register()

        Quality.register()

        SpiderEye.register()
        ZombieHead.register()

        AnvilRecipe.register()

        AnvilBlock.register()

        MakeModularItemCmd.register()
        logger.info("Registered Modular Item.")
    }
}