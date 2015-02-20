package mineward.core.commands;

import java.util.ArrayList;
import java.util.List;

import mineward.core.chat.ShoutCMD;
import mineward.core.chat.SilenceCMD;
import mineward.core.commands.list.CommandListCMD;
import mineward.core.holo.HoloCMD;
import mineward.core.me.MeCMD;
import mineward.core.message.MessageCMD;
import mineward.core.message.ReplyCMD;
import mineward.core.options.command.OptionsCMD;
import mineward.core.punish.command.PunishCMD;
import mineward.core.rank.RankCMD;
import mineward.core.report.ReportCMD;
import mineward.core.staff.StaffCMD;
import mineward.core.staff.StaffListCMD;
import mineward.core.stats.StatsCMD;
import mineward.core.whitelist.WhitelistCMD;

public class CommandFactory {

	private static List<CommandBase> commands = new ArrayList<CommandBase>();

	public static void addCommand(CommandBase cmd) {
		commands.add(cmd);
	}

	private static void addCommands() {
		addCommand(new RankCMD());
		addCommand(new ShoutCMD());
		addCommand(new SilenceCMD());
		addCommand(new StaffCMD());
		addCommand(new PunishCMD());
		addCommand(new StatsCMD());
		addCommand(new HoloCMD());
		addCommand(new StaffListCMD());
		addCommand(new ReportCMD());
		addCommand(new CommandListCMD());
		addCommand(new MessageCMD());
		addCommand(new ReplyCMD());
		addCommand(new WhitelistCMD());
		addCommand(new OptionsCMD());
		addCommand(new MeCMD());
	}

	public static void createCommands() {
		addCommands();
	}

	public static List<CommandBase> getCommands() {
		return commands;
	}

}
