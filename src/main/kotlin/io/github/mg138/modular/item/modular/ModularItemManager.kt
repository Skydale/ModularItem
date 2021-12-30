package io.github.mg138.modular.item.modular

import net.minecraft.util.Identifier

object ModularItemManager {
    private val map: MutableMap<Identifier, ModularItem> = mutableMapOf()

    operator fun get(id: Identifier) = map[id]

    val items: Iterable<ModularItem>
        get() = map.values

    fun add(item: ModularItem) {
        map[item.id] = item
    }
}