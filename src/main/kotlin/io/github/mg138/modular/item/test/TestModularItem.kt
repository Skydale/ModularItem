package io.github.mg138.modular.item.test

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ModularItem
import io.github.mg138.modular.item.ingredient.test.SpiderEye
import io.github.mg138.modular.item.ingredient.test.ZombieHead
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

class TestModularItem : ModularItem(
    Main.modId - "test_modular_item",
    BookItemSettings(false),
    FabricItemSettings(),
    Items.IRON_SWORD, listOf(ZombieHead.INGREDIENT_ZOMBIE_HEAD, SpiderEye.INGREDIENT_SPIDER_EYE)
) {
    companion object {
        val TEST_MODULAR_ITEM = TestModularItem()

        fun register() {
            TEST_MODULAR_ITEM.register()
        }
    }
}