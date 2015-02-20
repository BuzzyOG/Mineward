package mineward.core.staff;

import java.util.HashMap;
import java.util.UUID;

import mineward.core.common.Rank;

public class StaffChatModule {

	public enum ChatType {
		Global(Rank.Player, false), Staff(Rank.Helper, false);
		private Rank rank;
		private boolean channelspecific;

		private ChatType(Rank rank, boolean channelspecific) {
			this.rank = rank;
			this.channelspecific = channelspecific;
		}

		public Rank getRank() {
			return rank;
		}

		public boolean isChannelSpecific() {
			return this.channelspecific;
		}
	}

	public static HashMap<UUID, ChatType> channels = new HashMap<UUID, ChatType>();

}
