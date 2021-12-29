package io.github.mg138.modular.command

import net.minecraft.server.command.ServerCommandSource
import java.util.function.Predicate

object CommandUtil {
    fun admin() = Predicate<ServerCommandSource> { it.hasPermissionLevel(2) }
}