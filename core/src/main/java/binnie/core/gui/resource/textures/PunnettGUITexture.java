package binnie.core.gui.resource.textures;

public enum PunnettGUITexture {
	CHROMOSOME("chromosome"),
	CHROMOSOME_OVERLAY("chromosome_overlay");
	private final String name;

	PunnettGUITexture(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
