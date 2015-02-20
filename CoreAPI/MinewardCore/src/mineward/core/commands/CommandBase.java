package mineward.core.commands;

import mineward.core.common.Rank;

public abstract class CommandBase implements NCommand {

	private String[] alias;
	private Rank rank;

	public CommandBase(String[] alias, Rank rank) {
		this.alias = alias;
		this.rank = rank;
	}

	public String[] getAliases() {
		return alias;
	}

	public Rank getRank() {
		return rank;
	}

}
