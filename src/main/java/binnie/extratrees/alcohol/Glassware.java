package binnie.extratrees.alcohol;

import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum Glassware {
	BeerMug(16, 10, 14),
	Pint(20, 6, 20),
	Snifter(17, 14, 11),
	Flute(6, 13, 15),
	Cocktail(8, 20, 8),
	Cordial(2, 15, 7),
	Collins(12, 8, 18),
	Highball(8, 10, 14),
	Hurricane(15, 10, 18),
	Margarita(12, 18, 9),
	OldFashioned(8, 13, 8),
	Wine(8, 17, 10),
	Shot(1, 13, 7),
	Sherry(2, 17, 7),
	Coupe(6, 19, 8);

	public IIcon glass;
	public IIcon contents;

	protected float contentBottom;
	protected float contentHeight;

	private int capacity;

	Glassware(int capacity, int contentBottom, int contentHeight) {
		this.capacity = 30 * capacity;
		this.contentBottom = contentBottom / 32.0f;
		this.contentHeight = contentHeight / 32.0f;
	}

	public String getName(String liquid) {
		if (liquid == null) {
			return I18N.localise("extratrees.item.glassware." + name().toLowerCase());
		}
		return I18N.localise("extratrees.item.glassware." + name().toLowerCase() + ".usage", liquid);
	}

	public int getCapacity() {
		return capacity;
	}

	public void registerIcons(IIconRegister par1IconRegister) {
		glass = ExtraTrees.proxy.getIcon(par1IconRegister, "glassware/" + toString().toLowerCase() + ".glass");
		contents = ExtraTrees.proxy.getIcon(par1IconRegister, "glassware/" + toString().toLowerCase() + ".contents");
	}

	public ItemStack get(int i) {
		return ExtraTrees.drink.getStack(this, null);
	}

	public float getContentBottom() {
		return contentBottom;
	}

	public float getContentHeight() {
		return contentHeight;
	}

	public int getVolume() {
		return getCapacity();
	}
}
