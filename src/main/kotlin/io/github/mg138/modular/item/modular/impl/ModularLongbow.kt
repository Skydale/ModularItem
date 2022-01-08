package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleBow
import io.github.mg138.bookshelf.projectile.ArrowProjectile
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.LongbowType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

object ModularLongbow : ModularStatedItem(
    Main.modId - "modular_longbow",
    BookItemSettings(false), FabricItemSettings(),
    Items.BOW,
    listOf(LongbowType)
), SimpleBow {
    override val speed = 1.0

    private fun getRotationVector(pitch: Float, yaw: Float): Vec3d {
        val f = pitch * 0.017453292f
        val g = -yaw * 0.017453292f
        val h = MathHelper.cos(g)
        val i = MathHelper.sin(g)
        val j = MathHelper.cos(f)
        val k = MathHelper.sin(f)

        return Vec3d((i * j).toDouble(), (-k).toDouble(), (h * j).toDouble())
    }

    val rawDelta = listOf(-5.0F, 0.0F, 5.0F)

    override fun spawnProjectile(player: ServerPlayerEntity, itemStack: ItemStack) {
        val world = player.world

        for (d in rawDelta) {
            val entity = ArrowProjectile(player, world).also { projectile ->
                projectile.itemStack = itemStack
                projectile.velocity = getRotationVector(player.pitch, player.yaw + d).multiply(speed)
            }
            world.spawnEntity(entity)
            player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 0.7F)
        }
    }
}