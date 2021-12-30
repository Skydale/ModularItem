package io.github.mg138.modular.crafting.recipe

import com.google.gson.JsonObject
import eu.pb4.polymer.api.item.PolymerRecipe
import io.github.mg138.bookshelf.utils.minus
import io.github.mg138.modular.Main
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry

class AnvilRecipe(
    recipe: ShapedRecipe,
    val level: Int
) : ShapedRecipe(recipe.id, recipe.group, recipe.width, recipe.height, recipe.ingredients, recipe.output),
    PolymerRecipe {
    override fun getType() = ANVIL

    override fun getPolymerRecipe(input: Recipe<*>?): Recipe<*> = PolymerRecipe.createSmithingRecipe(input)

    companion object {
        val ID = Main.modId - "anvil"

        private val SERIALIZER = object : RecipeSerializer<AnvilRecipe> {
            override fun read(identifier: Identifier, jsonObject: JsonObject): AnvilRecipe {
                val shaped = CustomSerializer.read(identifier, jsonObject)
                val level = JsonHelper.getInt(jsonObject, "level", 0)

                return AnvilRecipe(shaped, level)
            }

            override fun read(identifier: Identifier, packetByteBuf: PacketByteBuf): AnvilRecipe {
                val shaped = Serializer.SHAPED.read(identifier, packetByteBuf)
                val level = packetByteBuf.readVarInt()

                return AnvilRecipe(shaped, level)
            }

            override fun write(packetByteBuf: PacketByteBuf, anvilRecipe: AnvilRecipe) {
                Serializer.SHAPED.write(packetByteBuf, anvilRecipe)
                packetByteBuf.writeInt(anvilRecipe.level)
            }
        }

        val ANVIL = object : RecipeType<AnvilRecipe> {
            override fun toString(): String {
                return ID.toString()
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