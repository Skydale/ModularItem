package io.github.mg138.modular.item.modular.impl.weapon

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleBow
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.BowType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object ModularBow : ModularStatedItem(
    Main.skydale - "modular_bow",
    BookItemSettings(false), FabricItemSettings(),
    Items.BOW,
    listOf(BowType)
), SimpleBow {
    override val speed = 1.0

    override fun spawnProjectile(player: ServerPlayerEntity, itemStack: ItemStack) {
        super.spawnProjectile(player, itemStack)

        player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F)
    }
}