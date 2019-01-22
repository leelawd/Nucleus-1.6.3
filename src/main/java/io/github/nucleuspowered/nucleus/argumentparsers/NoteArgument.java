/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers;

import io.github.nucleuspowered.nucleus.NucleusPlugin;
import io.github.nucleuspowered.nucleus.modules.note.data.NoteData;
import io.github.nucleuspowered.nucleus.modules.note.services.NoteHandler;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

public class NoteArgument extends CommandElement {

    private final NoteHandler handler;

    public NoteArgument(@Nullable Text key, NoteHandler handler) {
        super(key);
        this.handler = handler;
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        Optional<String> optPlayer = args.nextIfPresent();
        if (!optPlayer.isPresent()) {
            throw args.createError(NucleusPlugin.getNucleus().getMessageProvider().getTextMessageWithFormat("args.note.nouserarg"));
        }
        String player = optPlayer.get();

        Optional<User> optUser = Sponge.getServiceManager().provideUnchecked(UserStorageService.class).get(player);
        if (!optUser.isPresent()) {
            throw args.createError(NucleusPlugin.getNucleus().getMessageProvider().getTextMessageWithFormat("args.note.nouser", player));
        }
        User user = optUser.get();

        Optional<String> optIndex = args.nextIfPresent();
        if (!optIndex.isPresent()) {
            throw args.createError(NucleusPlugin.getNucleus().getMessageProvider().getTextMessageWithFormat("args.note.noindex", user.getName()));
        }

        List<NoteData> noteData = this.handler.getNotesInternal(user);
        int index;
        try {
            index = Integer.parseInt(optIndex.get()) - 1;
            if (index >= noteData.size() || index < 0) {
                throw args.createError(NucleusPlugin.getNucleus().getMessageProvider().getTextMessageWithFormat("args.note.nonotedata", optIndex.get(), user.getName()));
            }
        } catch (NumberFormatException ex) {
            throw args.createError(NucleusPlugin.getNucleus().getMessageProvider().getTextMessageWithFormat("args.note.indexnotnumber"));
        }

        return new Result(user, noteData.get(index));

    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return Collections.emptyList();
    }

    @Override
    public Text getUsage(CommandSource src) {
        return Text.of("<user> <ID>");
    }

    public static class Result {
        public final User user;
        public final NoteData noteData;

        public Result(User user, NoteData noteData) {
            this.user = user;
            this.noteData = noteData;
        }
    }
}
