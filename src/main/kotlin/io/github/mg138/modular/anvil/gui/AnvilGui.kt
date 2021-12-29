package io.github.mg138.modular.anvil.gui

import eu.pb4.sgui.api.gui.SimpleGui
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

abstract class AnvilGui(player: ServerPlayerEntity) :
    SimpleGui(ScreenHandlerType.GENERIC_9X6, player, false) {
    abstract val id: Identifier

    companion object {
        const val anvilKey = "modular_item.anvil"
    }

    open val titleKey: String
        get() = "$anvilKey.${id.namespace}.${id.path}.title"

    open val title
        get() = TranslatableText(titleKey)

    override fun getTitle(): Text {
        return title
    }

    fun slotRedirectTo(inventory: AnvilInventory) {
        for (y in 0 until AnvilInventory.height) {
            for (x in 0 until AnvilInventory.width) {
                val index = (y * AnvilInventory.width) + x
                this.setSlotRedirect(index, Slot(inventory, index, x, y))
            }
        }
    }
}