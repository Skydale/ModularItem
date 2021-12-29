package io.github.mg138.modular.anvil.forge

import eu.pb4.sidebars.api.Sidebar
import io.github.mg138.modular.anvil.gui.AnvilGui
import io.github.mg138.modular.anvil.gui.AnvilInventory
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.boss.BossBar
import net.minecraft.entity.boss.ServerBossBar
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import kotlin.jvm.internal.Ref
import kotlin.math.roundToInt

object ProgressDisplay {
    private operator fun String.times(count: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until count) {
            stringBuilder.append(this)
        }
        return stringBuilder.toString()
    }

    private val map: MutableMap<ServerPlayerEntity, Triple<ServerBossBar, ItemStack?, Ref.IntRef>> = mutableMapOf()

    fun register() {
        ServerTickEvents.END_SERVER_TICK.register {
            val toRemove: MutableList<ServerPlayerEntity> = mutableListOf()

            map.forEach { (player, triple) ->
                val age = triple.third.apply { element++ }

                if (age.element >= 60) {
                    sideBar.removePlayer(player)
                    triple.first.clearPlayers()
                    toRemove += player
                }
            }

            toRemove.forEach {
                map.remove(it)
            }
        }
    }

    private fun color(percentage: Float): BossBar.Color {
        if (percentage > 0.7) return BossBar.Color.GREEN
        if (percentage > 0.4) return BossBar.Color.YELLOW
        return BossBar.Color.RED
    }

    private const val length = 100
    private const val center = length / 2
    private val res = length.toDouble() / ForgeManager.MARGIN
    private val pointer = LiteralText("*").styled { it.withColor(Formatting.RED) }
    private val template = ("-" * length).toMutableList().apply {
        this[center] = '|'
    }.toCharArray().concatToString()

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

    private const val actionBarKey = "${AnvilGui.anvilKey}.message.actionBar"

    private const val sideBarKey = "${AnvilGui.anvilKey}.message.sideBar"
    private val sideBarText = TranslatableText(sideBarKey)

    private const val sideBarTitleKey = "$sideBarKey.title"
    private val sideBarTitleText = TranslatableText(sideBarTitleKey)

    private val sideBar = Sidebar(sideBarTitleText, Sidebar.Priority.HIGH).apply {
        addLines(sideBarText)
        show()
    }

    fun show(player: ServerPlayerEntity, hammerItemStack: ItemStack?, inventory: AnvilInventory, pair: Pair<Int, Int>) {
        val old = map[player]
        val (accuracy, progress) = pair

        val percentage = progress.toFloat() / ForgeManager.DONE
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

        player.sendMessage(
            TranslatableText(
                actionBarKey,
                inventory.output().name
                    .copy()
                    .styled { it.withColor(Formatting.DARK_GREEN) }
            ), true
        )

        sideBar.addPlayer(player)

        map[player] = Triple(bossBar, hammerItemStack, Ref.IntRef().apply { element = 0 })
    }
}