package mineward.core.account;

import java.util.UUID;

import mineward.core.common.Rank;
import mineward.core.punish.Punishment;

public class Account {

	private Rank rank;
	private UUID uuid;
	private int coins;
	private long timeonline;
	private long xp;
	private Punishment mute;
	private String[] ignores;

	public Account(UUID uuid, Rank rank, int coins, long timeonline, long xp,
			Punishment mute, String[] ignores) {
		this.rank = rank;
		this.uuid = uuid;
		this.coins = coins;
		this.timeonline = timeonline;
		this.xp = xp;
		this.mute = mute;
		this.ignores = ignores;
	}

	public Punishment getMute() {
		return mute;
	}

	public Rank getRank() {
		return rank;
	}

	public UUID getUUID() {
		return uuid;
	}

	public int getCoins() {
		return coins;
	}

	public long getTimeOnline() {
		return timeonline;
	}

	public long getXP() {
		return xp;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setXP(long xp) {
		this.xp = xp;
	}

	public String[] getIgnores() {
		return ignores;
	}

	public void setIgnores(String[] ignores) {
		this.ignores = ignores;
	}

}
