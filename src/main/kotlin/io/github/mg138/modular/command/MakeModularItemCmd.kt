package io.github.mg138.modular.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import io.github.mg138.modular.command.suggestion.IngredientSuggestion
import io.github.mg138.modular.command.suggestion.ModularItemTypeSuggestion
import io.github.mg138.modular.item.ingredient.IngredientManager
import io.github.mg138.modular.item.modular.ModularItemManager
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier

object MakeModularItemCmd {
    private fun listIngredients(context: CommandContext<ServerCommandSource>): Int {
        val source = context.source
        source.sendFeedback(LiteralText("Ingredients:"), false)

        IngredientManager.ingredients.forEach {
            source.sendFeedback(
                LiteralText(it.id.toString()), false
            )
        }

        return Command.SINGLE_SUCCESS
    }

    private fun makeModularItem(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source.player

        val modularItemArg = IdentifierArgumentType.getIdentifier(context, "type")
        val modularItem = ModularItemManager[modularItemArg] ?: return 0

        val ingredientArgs = StringArgumentType.getString(context, "ingredients").split(" ")
        val ingredients = ingredientArgs
            .map { Identifier(it) }
            .mapNotNull { IngredientManager[it] }

        player.giveItemStack(modularItem.makeItemStack(ingredients.map { it to NbtCompound() }))

        return Command.SINGLE_SUCCESS
    }

    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("modular")
                    .then(
                        literal("ingredients")
                            .executes(this::listIngredients)
                    )

                    .then(
                        literal("make")
                            .requires(CommandUtil.admin())
                            .then(
                                argument("type", IdentifierArgumentType.identifier())
                                    .suggests(ModularItemTypeSuggestion)
                                    .then(
                                        argument("ingredients", StringArgumentType.greedyString())
                                            .suggests(IngredientSuggestion)
                                            .executes(this::makeModularItem)
                                    )
                            )
                    )
            )
        }
    }
}