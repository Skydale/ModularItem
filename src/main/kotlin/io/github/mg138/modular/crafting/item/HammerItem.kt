package io.github.mg138.modular.crafting.item

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.SimpleBookItem
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.server.network.ServerPlayerEntity

object HammerItem : SimpleBookItem(
    Main.skydale - "test_hammer",
    BookItemSettings(false), FabricItemSettings(),
    Items.IRON_SHOVEL
) {
    override fun getPolymerItemStack(itemStack: ItemStack?, player: ServerPlayerEntity?): ItemStack {
        return super.getPolymerItemStack(itemStack, player).apply {
            orCreateNbt.put("CanDestroy", NbtList().apply {
                add(NbtString.of("minecraft:smithing_table"))
            })
        }
    }
}