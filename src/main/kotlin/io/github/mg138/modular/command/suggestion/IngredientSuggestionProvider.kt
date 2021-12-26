package io.github.mg138.modular.command.suggestion

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.github.mg138.modular.item.ingredient.IngredientManager
import net.minecraft.server.command.ServerCommandSource
import java.util.concurrent.CompletableFuture

object IngredientSuggestionProvider : SuggestionProvider<ServerCommandSource> {
    override fun getSuggestions(
        context: CommandContext<ServerCommandSource>?,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        IngredientManager.ingredients
            .forEach { builder.suggest(it.id.toString()) }
        return builder.buildFuture()
    }
}