package mineward.core.punish.options;

import mineward.core.punish.PunishType;

public enum Offense {

	Chat(PunishType.Mute), General(PunishType.Ban), Hacks(PunishType.Ban), Unban(
			PunishType.Ban), Unmute(PunishType.Mute);

	private PunishType type;

	private Offense(PunishType type) {
		this.type = type;
	}

	public PunishType getPunishType() {
		return type;
	}

}
