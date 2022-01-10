package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleMeleeWeapon
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.DaggerType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object ModularDagger : ModularStatedItem(
    Main.modId - "modular_dagger",
    BookItemSettings(false), FabricItemSettings(),
    Items.IRON_SWORD,
    listOf(DaggerType)
), SimpleMeleeWeapon {
    override val range = 2.5

    override fun onLeftClick(player: ServerPlayerEntity, itemStack: ItemStack): Boolean {
        if (!super.onLeftClick(player, itemStack)) return false
        player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0F, 1.4F)
        return true
    }
}