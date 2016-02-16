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

import java.net.MalformedURLException;
import java.net.URL;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;

public class SendVersion implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of(TextColors.GREEN, "-=-=-=- XtraPunish -=-=-=-"));
        src.sendMessage(Text.of(TextColors.GREEN, "Version: ", TextColors.GOLD, XtraPunish.VERSION));
        src.sendMessage(Text.of(TextColors.GREEN, "Author: ", TextColors.GOLD, "12AwesomeMan34"));
        
        try {
            src.sendMessage(Text
                    .builder("Github: ")
                    .color(TextColors.GREEN)
                    .append(Text.builder("https://github.com/12AwsomeMan34/XtraPunish").color(TextColors.GOLD)
                            .onClick(TextActions.openUrl(new URL("https://github.com/12AwsomeMan34/XtraPunish")))
                            .build()).build());
        } catch(MalformedURLException e) {
            XtraPunish.instance.logger.error("Error opening github url!");
            src.sendMessage(Text.of(TextColors.RED, "Error opening github url!"));
            e.printStackTrace();
        }
        return CommandResult.success();
    }
}
