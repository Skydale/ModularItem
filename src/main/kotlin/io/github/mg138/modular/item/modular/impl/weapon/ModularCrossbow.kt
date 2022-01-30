package io.github.mg138.modular.item.modular.impl.weapon

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.item.type.SimpleBow
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.impl.CrossbowType
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import java.util.*

object ModularCrossbow : ModularStatedItem(
    Main.skydale - "modular_crossbow",
    BookItemSettings(false), FabricItemSettings(),
    Items.CROSSBOW,
    listOf(CrossbowType)
), SimpleBow {
    private val map: MutableMap<UUID, Triple<ItemStack, Int, Int>> = mutableMapOf()

    override val speed = 2.0

    override fun spawnProjectile(player: ServerPlayerEntity, itemStack: ItemStack) {
        super.spawnProjectile(player, itemStack)

        player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.5F)
    }

    override fun onRightClick(player: ServerPlayerEntity, itemStack: ItemStack): Boolean {
        if (!super.onRightClick(player, itemStack)) return false
        map[player.uuid] = Triple(itemStack, player.server.ticks, 2)

        return true
    }

    override fun register() {
        super.register()
        ServerTickEvents.START_SERVER_TICK.register {
            map.forEach { (uuid, triple) ->
                val start = triple.second
                if ((it.ticks - start) % 2 == 0) {
                    val itemStack = triple.first

                    it.playerManager.getPlayer(uuid)?.let { player -> spawnProjectile(player, itemStack) }

                    map[uuid] = Triple(itemStack, start, triple.third - 1)
                }
            }
            map.filter { it.value.third <= 0 }.forEach { (uuid, _) ->
                map.remove(uuid)
            }
        }
    }
}