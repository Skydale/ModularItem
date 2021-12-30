package io.github.mg138.modular.crafting.forge

import eu.pb4.sidebars.api.Sidebar
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.boss.BossBar
import net.minecraft.entity.boss.ServerBossBar
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import java.util.*
import kotlin.jvm.internal.Ref
import kotlin.math.roundToInt

object ProgressDisplay {
    private val map: MutableMap<UUID, Triple<ServerBossBar, ItemStack?, Ref.IntRef>> = mutableMapOf()

    object BossBarDisplay {
        private const val length = 100
        private const val center = length / 2

        private val res = length.toDouble() / ForgeManager.Accuracy.MARGIN

        private val pointer = LiteralText("*").styled { it.withColor(Formatting.RED) }

        private val template = MutableList(length) { '-' }
            .apply { this[center] = '|' }
            .toCharArray()
            .concatToString()

        private fun color(percentage: Float): BossBar.Color {
            if (percentage > 0.7) return BossBar.Color.GREEN
            if (percentage > 0.4) return BossBar.Color.YELLOW
            return BossBar.Color.RED
        }

        private fun progressString(accuracy: Int): Text {
            val start = LiteralText("[").styled { it.withColor(Formatting.DARK_GREEN) }

            val pos = (accuracy * res).roundToInt()

            val left = try {
                LiteralText(template.substring(0, pos)).styled { it.withColor(Formatting.WHITE) }
            } catch (e: Exception) {
                LiteralText(template.substring(0, center)).styled { it.withColor(Formatting.WHITE) }
            }

            val right = try {
                LiteralText(template.substring(pos + 1, template.length)).styled { it.withColor(Formatting.WHITE) }
            } catch (e: Exception) {
                LiteralText(template.substring(center + 1, length)).styled { it.withColor(Formatting.WHITE) }
            }

            val end = LiteralText("]").styled { it.withColor(Formatting.DARK_GREEN) }

            return start.append(left).append(pointer).append(right).append(end)
        }

        fun show(player: ServerPlayerEntity, accuracy: Int, progress: Int): ServerBossBar {
            val old = map[player.uuid]

            val percentage = progress.toFloat() / ForgeManager.Progress.DONE
            val name = progressString(accuracy)

            val bossBar: ServerBossBar
            if (old == null) {
                bossBar = ServerBossBar(name, color(percentage), BossBar.Style.PROGRESS).apply {
                    addPlayer(player)
                }
            } else {
                bossBar = old.first
                bossBar.color = color(percentage)
                bossBar.name = name
            }
            bossBar.percent = percentage

            return bossBar
        }
    }

    object SideBarDisplay {
        private const val sideBarKey = "$anvilKey.message.sideBar"
        private val sideBarText = TranslatableText(sideBarKey)

        private const val sideBarTitleKey = "$sideBarKey.title"
        private val sideBarTitleText = TranslatableText(sideBarTitleKey)

        private val sideBar = Sidebar(sideBarTitleText, Sidebar.Priority.HIGH).apply {
            addLines(sideBarText)
            show()
        }

        fun addPlayer(player: ServerPlayerEntity) {
            sideBar.addPlayer(player)
        }
        
        fun removePlayer(player: ServerPlayerEntity) {
            sideBar.removePlayer(player)
        }
    }

    private const val anvilKey = "modular_item.anvil"
    private const val actionBarKey = "$anvilKey.message.actionBar"

    private fun tick(server: MinecraftServer) {
        val toRemove: MutableList<UUID> = mutableListOf()

        map.forEach { (uuid, triple) ->
            val age = triple.third.apply { element++ }

            if (age.element >= 60) {
                server.playerManager.getPlayer(uuid)?.let {
                    SideBarDisplay.removePlayer(it)
                }
                triple.first.clearPlayers()
                toRemove += uuid
            }
        }

        toRemove.forEach {
            map.remove(it)
        }
    }

    fun register() {
        ServerTickEvents.END_SERVER_TICK.register(this::tick)
    }

    fun show(player: ServerPlayerEntity, itemStack: ItemStack?, inventory: GuiInventory, pair: Pair<Int, Int>) {
        val (accuracy, progress) = pair

        val bossBar = BossBarDisplay.show(player, accuracy, progress)

        SideBarDisplay.addPlayer(player)

        player.sendMessage(
            TranslatableText(
                actionBarKey,
                inventory.output().name
                    .copy()
                    .styled { it.withColor(Formatting.DARK_GREEN) }
            ), true
        )

        map[player.uuid] = Triple(bossBar, itemStack, Ref.IntRef().apply { element = 0 })
    }
}