package mineward.core.options;

public enum OptionType {

	Me("me"), Chat("chat"), PrivateMessage("pm");

	private String column;

	private OptionType(String column) {
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

}
