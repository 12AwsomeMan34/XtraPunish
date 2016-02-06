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
			src.sendMessage(Text.builder("Github: ").color(TextColors.GREEN)
				.append(Text.builder("https://github.com/12AwsomeMan34/XtraPunish").color(TextColors.GOLD)
				.onClick(TextActions.openUrl(new URL("https://github.com/12AwsomeMan34/XtraPunish"))).build()).build());
		} catch(MalformedURLException e) {
			XtraPunish.instance.logger.error("Error opening github url!");
			src.sendMessage(Text.of(TextColors.RED, "Error opening github url!"));
			e.printStackTrace();
		}
		return CommandResult.success();
	}
}
