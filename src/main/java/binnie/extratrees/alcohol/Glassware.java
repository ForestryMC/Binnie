package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;


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

    private int capacity;
    float contentBottom;
    float contentHeight;
//	public IIcon glass;
//	public IIcon contents;

    public String getName(final String liquid) {
        if (liquid == null) {
            return ExtraTrees.proxy.localise("item.glassware." + this.name().toLowerCase());
        }
        return Binnie.Language.localise(ExtraTrees.instance, "item.glassware." + this.name().toLowerCase() + ".usage", liquid);
    }

    public int getCapacity() {
        return this.capacity;
    }

    Glassware(final int capacity, final int contentBottom, final int contentHeight) {
        this.capacity = 30 * capacity;
        this.contentBottom = contentBottom / 32.0f;
        this.contentHeight = contentHeight / 32.0f;
    }

//	public void registerIcons(final IIconRegister par1IconRegister) {
//		this.glass = ExtraTrees.proxy.getIcon(par1IconRegister, "glassware/" + this.toString().toLowerCase() + ".glass");
//		this.contents = ExtraTrees.proxy.getIcon(par1IconRegister, "glassware/" + this.toString().toLowerCase() + ".contents");
//	}

    public ItemStack get(final int i) {
        return ExtraTrees.drink.getStack(this, null);
    }

    public float getContentBottom() {
        return this.contentBottom;
    }

    public float getContentHeight() {
        return this.contentHeight;
    }

    public int getVolume() {
        return this.getCapacity();
    }
}
