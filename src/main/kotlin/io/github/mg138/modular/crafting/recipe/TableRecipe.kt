package io.github.mg138.modular.crafting.recipe

import com.google.gson.JsonObject
import eu.pb4.polymer.api.item.PolymerRecipe
import eu.pb4.polymer.api.item.PolymerRecipe.createSmithingRecipe
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

class TableRecipe(
    recipe: ShapedRecipe,
    val level: Int
) : ShapedRecipe(recipe.id, recipe.group, recipe.width, recipe.height, recipe.ingredients, recipe.output),
    PolymerRecipe {
    override fun getType() = TABLE

    override fun getPolymerRecipe(input: Recipe<*>?): Recipe<*> = createSmithingRecipe(input)

    companion object {
        val ID = Main.modId - "table"

        private val SERIALIZER = object : RecipeSerializer<TableRecipe> {
            override fun read(identifier: Identifier, jsonObject: JsonObject): TableRecipe {
                val shaped = CustomSerializer.read(identifier, jsonObject)
                val level = JsonHelper.getInt(jsonObject, "level", 0)

                return TableRecipe(shaped, level)
            }

            override fun read(identifier: Identifier, packetByteBuf: PacketByteBuf): TableRecipe {
                val shaped = Serializer.SHAPED.read(identifier, packetByteBuf)
                val level = packetByteBuf.readVarInt()

                return TableRecipe(shaped, level)
            }

            override fun write(packetByteBuf: PacketByteBuf, anvilRecipe: TableRecipe) {
                Serializer.SHAPED.write(packetByteBuf, anvilRecipe)
                packetByteBuf.writeInt(anvilRecipe.level)
            }
        }

        val TABLE = object : RecipeType<TableRecipe> {
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
                TABLE
            )
        }
    }
}