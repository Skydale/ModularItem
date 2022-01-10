package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleMeleeWeapon
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.SwordType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object ModularSword : ModularStatedItem(
    Main.modId - "modular_sword",
    BookItemSettings(false), FabricItemSettings(),
    Items.IRON_SWORD,
    listOf(SwordType)
), SimpleMeleeWeapon {
    override val range = 4.0


    override fun onLeftClick(player: ServerPlayerEntity, itemStack: ItemStack): Boolean {
        if (!super.onLeftClick(player, itemStack)) return false
        player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0F, 1.0F)
        return true
    }
}