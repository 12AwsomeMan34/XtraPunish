/*
The MIT License (MIT)

Copyright (c) 2016 12AwesomeMan34 / 12AwsomeMan34

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;
import com.awesomeman.xtrapunish.api.punish.Punishment;

public class HelpCommand implements Punishment {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        PaginationService service = Sponge.getServiceManager().provide(PaginationService.class).get();
        service.builder()
            .title(Text.of(TextColors.GOLD, "XtraPunish"))
            .contents(XtraPunish.instance.helpList)
            .paddingString("-=")
            .sendTo(src);
        return CommandResult.success();
    }

    @Override
    public String permission() {
        return "xtrapunish.help";
    }

    @Override
    public Text description() {
        return Text.of("The help command for XtraPunish!");
    }

    @Override
    public Text helpDescription() {
        return Text.of(TextColors.GREEN, "/punish help - ", TextColors.GOLD, "Display's this help list!");
    }

    @Override
    public Optional<CommandElement> arguments() {
        return Optional.empty();
    }

    @Override
    public String command() {
        return "help";
    }
}
