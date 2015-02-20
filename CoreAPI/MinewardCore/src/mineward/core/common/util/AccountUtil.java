package mineward.core.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mineward.core.account.Account;
import mineward.core.common.Rank;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;

import org.bukkit.entity.Player;

public class AccountUtil {

	private static List<Account> online = new ArrayList<Account>();

	public static void setOnline(Player p) {
		online.add(getAccountFromStats(p));
	}

	public static void setOffline(UUID uuid) {
		online.remove(getAccountFromUUID(uuid));
	}

	public static Account getAccountFromUUID(UUID uuid) {
		for (Account account : online) {
			if (account.getUUID().equals(uuid))
				return account;
		}
		return null;
	}

	public static Account getAccountFromPlayer(Player p) {
		return AccountUtil.getAccountFromUUID(p.getUniqueId());
	}

	public static Account getAccountFromStats(Player p) {
		return new Account(p.getUniqueId(),
				RankUtil.getSQLRank(p.getUniqueId()), EcoUtil.getSQLCoins(p
						.getUniqueId()), TMUtil.getSQLTime(p.getUniqueId()),
				XPUtil.getSQLXP(p.getUniqueId()),
				Punish.getActiveMutePunishment(p.getUniqueId(),
						Punish.getPunishments(p.getUniqueId())), null);
	}

	public static Account updateAccount(Player p, Rank rank) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), rank, old.getCoins(),
				old.getTimeOnline(), old.getXP(), old.getMute(),
				old.getIgnores());
		updateAccount(old, current);
		return current;
	}

	public static Account updateAccount(Player p, Rank rank, int coins) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), rank, coins,
				old.getTimeOnline(), old.getXP(), old.getMute(),
				old.getIgnores());
		updateAccount(old, current);
		return current;
	}

	public static Account updateAccount(Player p, int coins) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), old.getRank(), coins,
				old.getTimeOnline(), old.getXP(), old.getMute(),
				old.getIgnores());
		updateAccount(old, current);
		return current;
	}

	public static Account updateAccount(Player p, Punishment punish) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), old.getRank(),
				old.getCoins(), old.getTimeOnline(), old.getXP(), punish,
				old.getIgnores());
		updateAccount(old, current);
		return current;
	}

	public static Account updateAccount(Player p, String[] ignores) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), old.getRank(),
				old.getCoins(), old.getTimeOnline(), old.getXP(),
				old.getMute(), ignores);
		updateAccount(old, current);
		return current;
	}

	public static void removePunishment(Player p) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), old.getRank(),
				old.getCoins(), old.getTimeOnline(), old.getXP(), null,
				old.getIgnores());
		updateAccount(old, current);
	}

	public static void updateAccount(Account oldAccount, Account account) {
		online.remove(oldAccount);
		online.add(account);
	}

	public static Account updateAccount(Player p, long time, boolean what) {
		Account old = getAccountFromPlayer(p);
		Account current = new Account(p.getUniqueId(), old.getRank(),
				old.getCoins(), time, old.getXP(), old.getMute(),
				old.getIgnores());
		if (what) {
			current = new Account(p.getUniqueId(), old.getRank(),
					old.getCoins(), old.getTimeOnline(), time, old.getMute(),
					old.getIgnores());
		}
		updateAccount(old, current);
		return current;
	}

}
