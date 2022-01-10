package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleWand
import io.github.mg138.bookshelf.projectile.TridentProjectile
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.TridentType
import io.github.mg138.modular.item.modular.ModularStatedItem
import io.github.mg138.modular.item.modular.impl.ModularLongbow.getRotationVector
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object ModularTrident : ModularStatedItem(
    Main.modId - "modular_trident",
    BookItemSettings(false), FabricItemSettings(),
    Items.STICK,
    listOf(TridentType)
), SimpleWand {
    override val speed = 1.2

    private val yawDelta = listOf(-30.0F, 0.0F, 30.0F)

    override fun spawnProjectile(player: ServerPlayerEntity, itemStack: ItemStack) {
        val world = player.world

        for (d in yawDelta) {
            val entity = TridentProjectile(player, world).also { projectile ->
                projectile.itemStack = itemStack
                projectile.velocity = getRotationVector(player.pitch, player.yaw + d).multiply(ModularLongbow.speed)
            }
            world.spawnEntity(entity)
            player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 0.7F)
        }
    }
}