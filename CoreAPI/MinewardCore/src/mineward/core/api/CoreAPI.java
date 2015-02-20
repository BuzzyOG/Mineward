package mineward.core.api;

import mineward.core.account.UpdateAccount;
import mineward.core.common.Rank;
import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.EcoUtil;
import mineward.core.common.util.RankUtil;
import mineward.core.common.util.XPUtil;

import org.bukkit.OfflinePlayer;

public class CoreAPI {
	@Deprecated
	public static int setCoins(OfflinePlayer p, int amount, boolean editTotal) {
		EcoUtil.setCoins(p.getUniqueId(), amount, editTotal);
		return amount;
	}

	public static int getCoins(OfflinePlayer p) {
		return EcoUtil.getCoins(p);
	}

	@Deprecated
	public static int addCoins(OfflinePlayer p, int amount, boolean editTotal) {
		int a = EcoUtil.addCoins(p.getUniqueId(), amount, editTotal);
		return a;
	}

	@Deprecated
	public static int removeCoins(OfflinePlayer p, int amount) {
		int a = EcoUtil.removeCoins(p.getUniqueId(), amount, false);
		return a;
	}

	/**
	 * 
	 * @param p
	 *            Target Player
	 * @param amount
	 *            Amount to be deducted
	 * @return The balance
	 * 
	 *         Please use this method to remove coins from the player.
	 * 
	 */
	public static int buyItem(OfflinePlayer p, int amount) {
		return removeCoins(p, amount);
	}

	/**
	 * 
	 * @param p
	 *            Target Player
	 * @param amount
	 *            Amount to be added
	 * @return The player's balance
	 * 
	 *         Please use this method to add coins to player.
	 */
	public static int earnCoins(OfflinePlayer p, int amount) {
		return addCoins(p, amount, true);
	}

	/**
	 * 
	 * @param p
	 *            Target Player
	 * @return Player's rank
	 * 
	 *         To compare ranks use Rank.hasPermission(Rank param);
	 * 
	 */
	public static Rank getRank(OfflinePlayer p) {
		return RankUtil.getRank(p);
	}

	/**
	 * 
	 * @param p
	 *            Target Player
	 * @param rank
	 *            Target Rank
	 * @return Their new rank
	 * 
	 *         Sets a player's rank
	 * 
	 */

	public static Rank setRank(OfflinePlayer p, Rank rank) {
		new UpdateAccount(p.getUniqueId(), rank);
		if (p.isOnline()) {
			AccountUtil.updateAccount(p.getPlayer(), rank);
		}
		return rank;
	}

	public static int getLevel(OfflinePlayer p) {
		return (int) XPUtil.getLevel(p);
	}

	public static long getXP(OfflinePlayer p) {
		return XPUtil.getXP(p);
	}

	public static long getXPToNextLevel(OfflinePlayer p) {
		return XPUtil.getXPToNextLevel(p);
	}

}
