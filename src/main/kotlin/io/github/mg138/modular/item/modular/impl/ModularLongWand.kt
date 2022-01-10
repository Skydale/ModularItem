package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleWand
import io.github.mg138.bookshelf.projectile.LongWandProjectile
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.LongWandType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object ModularLongWand : ModularStatedItem(
    Main.modId - "modular_long_wand",
    BookItemSettings(false), FabricItemSettings(),
    Items.STICK,
    listOf(LongWandType)
), SimpleWand {
    override val speed = 1.7

    override fun spawnProjectile(player: ServerPlayerEntity, itemStack: ItemStack) {
        val world = player.world
        val entity = LongWandProjectile(player, world).also { projectile ->
            projectile.itemStack = itemStack
            projectile.velocity = player.getRotationVec(1.0F).multiply(speed)
        }
        world.spawnEntity(entity)

        player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 0.5F)
    }
}