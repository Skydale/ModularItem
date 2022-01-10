package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleWand
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.WandType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object ModularWand : ModularStatedItem(
    Main.modId - "modular_wand",
    BookItemSettings(false), FabricItemSettings(),
    Items.STICK,
    listOf(WandType)
), SimpleWand {
    override val speed = 1.0

    override fun spawnProjectile(player: ServerPlayerEntity, itemStack: ItemStack) {
        super.spawnProjectile(player, itemStack)

        player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F)
    }
}