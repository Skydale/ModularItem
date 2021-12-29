package io.github.mg138.modular.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import io.github.mg138.modular.item.ModularItem
import io.github.mg138.modular.command.suggestion.IngredientSuggestion
import io.github.mg138.modular.command.suggestion.ModularItemTypeSuggestion
import io.github.mg138.modular.item.ingredient.ModularIngredient
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object MakeModularItemCmd {
    private fun listIngredients(context: CommandContext<ServerCommandSource>): Int {
        val source = context.source
        source.sendFeedback(LiteralText("Ingredients:"), false)

        Registry.ITEM.forEach {
            if (it is ModularIngredient) {
                source.sendFeedback(
                    LiteralText(it.id.toString())
                        .append(" (Name: ")
                        .append(it.name)
                        .append(") "),
                    false
                )
            }
        }

        return Command.SINGLE_SUCCESS
    }

    private fun makeModularItem(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source.player
        val modularItem = Registry.ITEM.get(IdentifierArgumentType.getIdentifier(context, "type"))
        if (modularItem !is ModularItem) {
            return 0
        }
        val ingredients = StringArgumentType.getString(context, "ingredients").split(" ")
            .map { Identifier(it) }
            .map { Registry.ITEM.get(it) }
            .filterIsInstance<ModularIngredient>()

        player.giveItemStack(modularItem.makeItemStack(ingredients))

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