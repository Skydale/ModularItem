package io.github.mg138.modular

import eu.pb4.polymer.api.resourcepack.PolymerRPUtils
import io.github.mg138.modular.item.ingredient.test.SpiderEye
import io.github.mg138.modular.item.ingredient.test.ZombieHead
import io.github.mg138.modular.item.test.TestModularItem
import net.fabricmc.api.DedicatedServerModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object Main : DedicatedServerModInitializer {
    const val modId = "modular_item"
    val logger: Logger = LogManager.getLogger(modId)

    override fun onInitializeServer() {
        PolymerRPUtils.addAssetSource(modId)
        SpiderEye.register()
        ZombieHead.register()
        TestModularItem.register()
        logger.info("Registered Modular Item.")
    }
}