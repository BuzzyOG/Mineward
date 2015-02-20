package mineward.core.punish;

import java.sql.Timestamp;
import java.util.UUID;

import mineward.core.punish.gui.RecordItem;
import mineward.core.punish.options.Offense;
import mineward.core.punish.options.Severity;

public class Punishment {

	private UUID who;
	private long time;
	private long expires;
	private String message;
	private String punisher;
	private Severity sev;
	private long currentTimeMillis;
	private PunishType type;
	private Offense offense;

	public Punishment(UUID who, long time, long expires, String message,
			String punisher, Severity sev, long currentTimeMillis,
			PunishType type, Offense offense) {
		this.who = who;
		this.time = time;
		this.expires = expires;
		this.message = message;
		this.punisher = punisher;
		this.sev = sev;
		this.currentTimeMillis = currentTimeMillis;
		this.type = type;
		this.offense = offense;
	}

	public UUID getPunished() {
		return who;
	}

	public long getLength() {
		return time;
	}

	public long getExpiryTime() {
		return expires;
	}

	public String getReason() {
		return message;
	}

	public String getPunisher() {
		return punisher;
	}

	public Severity getSeverity() {
		return sev;
	}

	public long getCurrentTimeMillis() {
		return currentTimeMillis;
	}

	public PunishType getType() {
		return type;
	}

	public Offense getOffense() {
		return offense;
	}

	public RecordItem toRecordItem() {
		return new RecordItem(getPunished(), getReason(), getLength(),
				getExpiryTime(), new Timestamp(getCurrentTimeMillis()),
				getSeverity(), getType(), getPunisher(), getOffense(),
				getCurrentTimeMillis());
	}

}
