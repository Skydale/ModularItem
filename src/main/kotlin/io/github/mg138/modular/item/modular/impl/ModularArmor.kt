package io.github.mg138.modular.item.modular.impl

import io.github.mg138.bookshelf.item.Armor
import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.modular.item.modular.ModularStatedItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier

abstract class ModularArmor(
    id: Identifier,
    bookItemSettings: BookItemSettings,
    settings: Settings, vanillaItem: Item
) : ModularStatedItem(id, bookItemSettings, settings, vanillaItem), Armor {
}