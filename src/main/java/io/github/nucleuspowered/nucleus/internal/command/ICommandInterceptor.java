/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.command;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

public interface ICommandInterceptor {

    void onPreCommand(Class<? extends AbstractCommand<?>> commandClass, CommandSource source, CommandContext context);

    void onPostCommand(Class<? extends AbstractCommand<?>> commandClass, CommandSource source, CommandContext context, CommandResult result);
}
