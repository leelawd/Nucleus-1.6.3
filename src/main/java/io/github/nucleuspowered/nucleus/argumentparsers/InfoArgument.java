/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers;

import com.google.common.base.Preconditions;
import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.TextFileController;
import io.github.nucleuspowered.nucleus.modules.info.services.InfoHandler;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class InfoArgument extends CommandElement {

    private final InfoHandler handler;

    public InfoArgument(@Nullable Text key, InfoHandler handler) {
        super(key);
        Preconditions.checkNotNull(handler);
        this.handler = handler;
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String a = args.next();
        Optional<TextFileController> list = this.handler.getSection(a);
        if (list.isPresent()) {
            return new Result(this.handler.getInfoSections().stream().filter(a::equalsIgnoreCase).findFirst().get(), list.get());
        }

        throw args.createError(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("args.info.noinfo", a));
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        try {
            String p = args.peek();
            return this.handler.getInfoSections().stream().filter(x -> x.toLowerCase().startsWith(p.toLowerCase())).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>(this.handler.getInfoSections());
        }
    }

    public static class Result {
        public final String name;
        public final TextFileController text;

        public Result(String name, TextFileController text) {
            this.name = name;
            this.text = text;
        }
    }
}
