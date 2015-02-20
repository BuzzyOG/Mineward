package mineward.core.punish.gui;

import java.util.ArrayList;
import java.util.List;

import mineward.core.punish.options.Offense;
import mineward.core.punish.options.Severity;

import org.bukkit.inventory.Inventory;

public class PunishPageBuilder {

	private List<PunishButton> buttons = new ArrayList<PunishButton>();

	public PunishPageBuilder(Inventory inv, List<RecordItem> record) {
		String[][] chat = {
				new String[] { "Spamming in chat (Indirect)",
						"Using excessive caps", "Just being annoying",
						"First time offense" },
				new String[] { "Spamming constantly in chat",
						"Generally being just r00d", "Swearing",
						"Second or third time offense" },
				new String[] { "Being mildly discriminatory",
						"Being very rood",
						"Accusing others for political reasons",
						"Third or fourth time chat offense" },
				new String[] { "Being discriminatory", "Cyberbullying",
						"Light advertising",
						"Fourth or Fifth time chat offense" },
				new String[] { "Harrassment", "Major discrimination",
						"Lots of past offenses" },
				new String[] { "Advertising", "Spamvertising" },
				new String[] { "Use if multiple past offenses" } };
		String[][] general = {
				new String[] { "Trolling", "Breaking game rules" },
				new String[] { "[Hub] map exploits", "Team Trolling" },
				new String[] { "[Game] map exploits",
						"Multiple past troll offenses" },
				new String[] { "[Hub] glitch exploits",
						"Building offensive structures" },
				new String[] { "Team Griefing in survival",
						"Multiple past Hub exploits" },
				new String[] { "[Game] Glitch exploits",
						"Use if lots of past offenses" }, new String[] { "Use if multiple past offenses" } };
		String[][] hacks = {
				new String[] { "Unapproved mod [Minor]",
						"First time minor offense" },
				new String[] { "Unapproved mod (Minimap)",
						"Second time minor offense" },
				new String[] { "Fly hacking in hub", "First time offense",
						"Use if Helper" },
				new String[] { "Unapproved mod [Major] (Xray)",
						"Second time offense" },
				new String[] { "Hacking in hub", "Non-Movement hacks in game",
						"1st Non-Mov Hack offense", "2nd general offense" },
				new String[] { "Killaura hacks", "Aimbot hacks (No bowbot)",
						"PVP Hacks", "Movement Hacks", "3rd Minor offense",
						"1st MAJOR offense" }, new String[] { "Use if multiple past offenses" } };
		int c = 9;
		int g = 18;
		int h = 27;
		for (Offense offense : Offense.values()) {
			int i = 0;
			if ((offense.equals(Offense.Unban))
					|| (offense.equals(Offense.Unmute))) {
				// Nothing
			} else {
				for (Severity sev : Severity.values()) {
					String[] examples = { "Broken." };
					int slot = 0;
					try {
						if (offense.equals(Offense.Chat)) {
							c++;
							examples = chat[i];
							slot = c;
						} else if (offense.equals(Offense.General)) {
							g++;
							examples = general[i];
							slot = g;
						} else {
							h++;
							examples = hacks[i];
							slot = h;
						}
					} catch (Exception e) {
						examples = new String[] { "Examples have not been created yet." };
					}
					buttons.add(new PunishButton(inv, examples, sev
							.getDyeColor(), slot, offense, sev, record));
					i++;
				}
			}
		}
	}

	public List<PunishButton> getButtons() {
		return buttons;
	}

}
