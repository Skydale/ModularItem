package io.github.mg138.modular.item

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.stat.StatMap
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.util.ModularItemUtil
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound

class TestModularItem : ModularItem(
    Main.modId - "test_modular_item",
    BookItemSettings(true), FabricItemSettings(),
    Items.SAND
) {
    companion object {
        val TEST_MODULAR_ITEM = TestModularItem()

        fun register() {
            TEST_MODULAR_ITEM.register()
        }
    }

    override fun getStatMap(itemStack: ItemStack?): StatMap {
        val nbt = itemStack?.orCreateNbt ?: return StatMap()

        val modularItemNbt = nbt.getCompound(MODULAR_ITEM_KEY)

        return ModularItemUtil.getStatMap(modularItemNbt)
    }
}