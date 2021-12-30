package io.github.mg138.modular.item.modular

import io.github.mg138.bookshelf.item.BookItemSettings
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import io.github.mg138.modular.item.ingredient.StatedIngredient
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items

object ModularSword : ModularStatedItem(
    Main.modId - "modular_sword",
    BookItemSettings(true), FabricItemSettings(),
    Items.IRON_SWORD
), StatedIngredient {
    override val updateStatPriority = 1

    override fun register() {
        super<ModularStatedItem>.register()
        super<StatedIngredient>.register()
    }

}