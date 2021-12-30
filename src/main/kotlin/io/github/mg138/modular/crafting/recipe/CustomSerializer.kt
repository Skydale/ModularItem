package io.github.mg138.modular.crafting.recipe

import com.google.common.collect.Maps
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList

object CustomSerializer {
    private fun readSymbols(jsonObject: JsonObject): MutableMap<Char, Ingredient> {
        val map: MutableMap<Char, Ingredient> = Maps.newHashMap()
        val iter = jsonObject.entrySet().iterator()

        while (iter.hasNext()) {
            val (key, value) = iter.next()
            if (key.length != 1) {
                throw JsonSyntaxException("Invalid key entry: '$key' is an invalid symbol (must be 1 character only).")
            }
            val char = key[0]
            if (char == ' ') {
                throw JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.")
            }
            map[char] = Ingredient.fromJson(value)
        }

        map[' '] = Ingredient.EMPTY
        return map
    }

    private fun getPattern(json: JsonArray): List<String> {
        val strings: MutableList<String> = mutableListOf()

        for (i in 0 until json.size()) {
            val string = JsonHelper.asString(json[i], "pattern[$i]")

            if (i > 0 && string.length != strings[0].length) {
                throw JsonSyntaxException("Invalid pattern: each row must be the same width")
            }

            strings.add(string)
        }

        return strings
    }

    private fun createPatternMatrix(
        pattern: List<String>,
        symbols: Map<Char, Ingredient>,
        width: Int,
        height: Int
    ): DefaultedList<Ingredient> {
        val defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY)
        val set: MutableSet<Char> = symbols.keys.toMutableSet()
        set.remove(' ')

        for (i in pattern.indices) {
            for (j in 0 until pattern[i].length) {
                val char = pattern[i][j]
                val ingredient = symbols[char] ?: throw JsonSyntaxException("Pattern references symbol '$char' but it's not defined in the key")
                defaultedList[j + width * i] = ingredient
                set.remove(char)
            }
        }

        if (set.isNotEmpty()) throw IllegalArgumentException("Elements $set are assigned but not used!")

        return defaultedList
    }

    fun read(identifier: Identifier, jsonObject: JsonObject): ShapedRecipe {
        val string = JsonHelper.getString(jsonObject, "group", "")
        val map = readSymbols(JsonHelper.getObject(jsonObject, "key"))
        val strings = getPattern(JsonHelper.getArray(jsonObject, "pattern"))
        val i = strings[0].length
        val j = strings.size
        val defaultedList = createPatternMatrix(strings, map, i, j)
        val itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"))
        return ShapedRecipe(identifier, string, i, j, defaultedList, itemStack)
    }
}