package binnie.core;

public enum ModId {
	CORE(Constants.CORE_MOD_ID),
	BOTANY(Constants.BOTANY_MOD_ID),
	DESIGN(Constants.DESIGN_MOD_ID),
	EXTRA_BEES(Constants.EXTRA_BEES_MOD_ID),
	EXTRA_TREES(Constants.EXTRA_TREES_MOD_ID),
	GENETICS(Constants.GENETICS_MOD_ID);

	private final String modId;

	ModId(String modId) {
		this.modId = modId;
	}

	public String getDomain() {
		return modId;
	}
}
