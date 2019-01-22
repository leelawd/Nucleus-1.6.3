/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.inventory.commands;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.Util;
import io.github.nucleuspowered.nucleus.internal.annotations.command.NoModifiers;
import io.github.nucleuspowered.nucleus.internal.annotations.command.Permissions;
import io.github.nucleuspowered.nucleus.internal.annotations.command.RegisterCommand;
import io.github.nucleuspowered.nucleus.internal.command.AbstractCommand;
import io.github.nucleuspowered.nucleus.internal.command.NucleusParameters;
import io.github.nucleuspowered.nucleus.internal.command.ReturnMessageException;
import io.github.nucleuspowered.nucleus.internal.docgen.annotations.EssentialsEquivalent;
import io.github.nucleuspowered.nucleus.modules.inventory.events.ClearInventoryEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.util.annotation.NonnullByDefault;

@RegisterCommand({"clear", "clearinv", "clearinventory", "ci", "clearinvent"})
@NoModifiers
@NonnullByDefault
@Permissions(supportsOthers = true)
@EssentialsEquivalent({"clearinventory", "ci", "clean", "clearinvent"})
public class ClearInventoryCommand extends AbstractCommand<CommandSource> {

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
            GenericArguments.flags().flag("a", "-all").buildWith(
                GenericArguments.optional(
                        GenericArguments.requiringPermission(
                                NucleusParameters.ONE_USER,
                                this.permissions.getPermissionWithSuffix("others")
                        ))
            )
        };
    }

    @Override protected CommandResult executeCommand(CommandSource source, CommandContext args) throws Exception {
        User user = this.getUserFromArgs(User.class, source, NucleusParameters.Keys.USER, args);
        boolean all = args.hasAny("a");
        if (user.getPlayer().isPresent()) {
            Player target = user.getPlayer().get();
            if (Sponge.getEventManager().post(new ClearInventoryEvent.Pre(Sponge.getCauseStackManager().getCurrentCause(), target, all))) {
                source.sendMessage(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("command.clearinventory.cancelled", target.getName()));
                return CommandResult.empty();
            }
            if (all) {
                target.getInventory().clear();
            } else {
                Util.getStandardInventory(target).clear();
            }
            Sponge.getEventManager().post(new ClearInventoryEvent.Post(Sponge.getCauseStackManager().getCurrentCause(), target, all));
            source.sendMessage(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("command.clearinventory.success", target.getName()));
            return CommandResult.success();
        } else {
            try {
                if (Sponge.getEventManager().post(new ClearInventoryEvent.Pre(Sponge.getCauseStackManager().getCurrentCause(), user, all))) {
                    source.sendMessage(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("command.clearinventory.cancelled", user.getName()));
                    return CommandResult.empty();
                }
                if (all) {
                    user.getInventory().clear();
                } else {
                    Util.getStandardInventory(user).clear();
                }
                Sponge.getEventManager().post(new ClearInventoryEvent.Post(Sponge.getCauseStackManager().getCurrentCause(), user, all));
                source.sendMessage(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("command.clearinventory.success", user.getName()));
                return CommandResult.success();
            } catch (UnsupportedOperationException e) {
                throw ReturnMessageException.fromKey("command.clearinventory.offlinenotsupported");
            }
        }
    }
}
