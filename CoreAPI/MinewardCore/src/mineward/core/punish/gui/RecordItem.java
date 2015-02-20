package mineward.core.punish.gui;

import java.sql.Timestamp;
import java.util.UUID;

import mineward.core.punish.PunishType;
import mineward.core.punish.options.Offense;
import mineward.core.punish.options.Severity;

public class RecordItem {

	private UUID who;
	private String reason;
	private long time;
	private long expirydate;
	private Timestamp date;
	private Severity sev;
	private PunishType type;
	private String punisher;
	private Offense offense;
	private long millis;

	public RecordItem(UUID who, String reason, long time, long expires,
			Timestamp date, Severity sev, PunishType type, String punisher,
			Offense offense, long millis) {
		this.who = who;
		this.reason = reason;
		this.time = time;
		this.date = date;
		this.sev = sev;
		this.type = type;
		this.punisher = punisher;
		this.offense = offense;
		this.expirydate = expires;
		this.millis = millis;
	}

	public UUID getPunished() {
		return who;
	}

	public String getReason() {
		return reason;
	}

	public long getTime() {
		return time;
	}

	public Timestamp getDate() {
		return date;
	}

	public Severity getSeverity() {
		return sev;
	}

	public PunishType getPunishType() {
		return type;
	}

	public String getPunisher() {
		return punisher;
	}

	public Offense getOffense() {
		return offense;
	}

	public long getExpiryTime() {
		return expirydate;
	}

	public long getCurrentTimeMillis() {
		return millis;
	}

}
