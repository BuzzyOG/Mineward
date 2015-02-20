package mineward.core.options.command;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.options.gui.OptionsGUI;

import org.bukkit.entity.Player;

public class OptionsCMD extends CommandBase {

	public OptionsCMD() {
		super(new String[] { "options", "op" }, Rank.Player);
	}

	public void execute(Player p, String[] args) {
		OptionsGUI.gui(p);
	}

}
