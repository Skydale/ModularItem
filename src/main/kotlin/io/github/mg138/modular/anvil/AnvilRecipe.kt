package io.github.mg138.modular.anvil

import com.google.gson.JsonObject
import eu.pb4.polymer.api.item.PolymerRecipe
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class AnvilRecipe(
    recipe: ShapedRecipe
) : ShapedRecipe(recipe.id, recipe.group, recipe.width, recipe.height, recipe.ingredients, recipe.output),
    PolymerRecipe {
    override fun getType() = ANVIL

    companion object {
        val ID = Main.modId - "anvil"

        val ANVIL = object : RecipeType<AnvilRecipe> {
            override fun toString(): String {
                return ID.toString()
            }
        }
        private val SERIALIZER = object : ShapedRecipe.Serializer() {
            override fun read(identifier: Identifier, jsonObject: JsonObject): AnvilRecipe {
                return AnvilRecipe(super.read(identifier, jsonObject))
            }

            override fun read(identifier: Identifier, packetByteBuf: PacketByteBuf): ShapedRecipe {
                return AnvilRecipe(super.read(identifier, packetByteBuf))
            }
        }

        fun register() {
            Registry.register(
                Registry.RECIPE_SERIALIZER,
                ID,
                SERIALIZER
            )
            Registry.register(
                Registry.RECIPE_TYPE,
                ID,
                ANVIL
            )
        }
    }
}