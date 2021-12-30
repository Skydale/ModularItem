package io.github.mg138.modular.crafting.gui

import eu.pb4.sgui.api.gui.SimpleGui
import io.github.mg138.modular.Main
import io.github.mg138.modular.crafting.inventory.GuiInventory
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

abstract class Gui(
    type: ScreenHandlerType<*>,
    player: ServerPlayerEntity
) : SimpleGui(type, player, false) {
    abstract val id: Identifier

    open val titleKey: String
        get() = "${Main.modId}.gui.${id.namespace}.${id.path}.title"

    open val title
        get() = TranslatableText(titleKey)

    override fun getTitle(): Text {
        return title
    }

    open fun setFrame() {
    }

    abstract fun slotRedirectTo(inventory: GuiInventory)
}