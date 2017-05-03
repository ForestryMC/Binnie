package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum Liqueur implements IFluidType, ICocktailLiquid {
	Almond("Almond", 0xe45d2f, 0.3, 0.2),
	Orange("Orange", 0xf98900, 0.4, 0.2),
	Banana("Banana", 0xf8c200, 0.5, 0.2),
	Chocolate("Chocolate", 0xc14b20, 0.3, 0.2),
	Mint("Mint", 0x29c67c, 0.4, 0.2),
	Hazelnut("Hazelnut", 0xed982b, 0.3, 0.2),
	Cinnamon("Cinnamon", 0xe55000, 0.3, 0.2),
	Coffee("Coffee", 0x964319, 0.4, 0.2),
	Melon("Melon", 0xb0c231, 0.4, 0.2),
	Anise("Anise", 0xdae1e9, 0.3, 0.2),
	Peach("Peach", 0xfe9560, 0.4, 0.2),
	Lemon("Lemon", 0xf8e46d, 0.4, 0.2),
	Herbal("Herbal", 0xfed501, 0.3, 0.2),
	Cherry("Cherry", 0xd71901, 0.5, 0.2),
	Blackcurrant("Blackcurrant", 0x6a3d6d, 0.5, 0.2),
	Blackberry("Blackberry", 0x68554d, 0.5, 0.2),
	Raspberry("Raspberry", 0x9b0300, 0.5, 0.2);

	static {
		Liqueur.Almond.addFlavour("cropAlmond");
		Liqueur.Orange.addFlavour("cropOrange");
		Liqueur.Banana.addFlavour("cropBanana");
		Liqueur.Chocolate.addFlavour("cropCocoa");
		Liqueur.Mint.addFlavour("cropMint");
		Liqueur.Hazelnut.addFlavour("cropHazelnut");
		Liqueur.Cinnamon.addFlavour("cropCinnamon");
		Liqueur.Coffee.addFlavour("cropCoffee");
		Liqueur.Melon.addFlavour("cropMelon");
		Liqueur.Anise.addFlavour("cropAnise");
		Liqueur.Peach.addFlavour("cropPeach");
		Liqueur.Lemon.addFlavour("cropLemon");
		Liqueur.Herbal.addFlavour("cropHerbal");
		Liqueur.Cherry.addFlavour("cropCherry");
		Liqueur.Blackcurrant.addFlavour("cropBlackcurrant");
		Liqueur.Blackberry.addFlavour("cropBlackberry");
		Liqueur.Raspberry.addFlavour("cropRaspberry");
	}

	protected String name;
	protected String ident;
	protected IIcon icon;
	protected int colour;
	protected float transparency;
	protected float abv;

	Liqueur(String name, int colour, double transparency, double abv) {
		this(name + " Liqueur", "liqueur" + name, colour, transparency, abv);
	}

	Liqueur(String name, String ident, int colour, double transparency, double abv) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		this.abv = (float) abv;
	}

	private void addFlavour(String oreDict) {
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean canPlaceIn(FluidContainer container) {
		return true;
	}

	@Override
	public boolean showInCreative(FluidContainer container) {
		return container == FluidContainer.Glass;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void registerIcon(IIconRegister register) {
		icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIdentifier() {
		return "binnie." + ident;
	}

	@Override
	public int getColor() {
		return colour;
	}

	@Override
	public FluidStack get(int amount) {
		return Binnie.Liquid.getLiquidStack(getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return (int) (Math.min(1.0, transparency + 0.3) * 255.0);
	}

	@Override
	public String getTooltip(int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + getName();
	}

	@Override
	public int getContainerColor() {
		return getColor();
	}

	@Override
	public float getABV() {
		return abv;
	}
}
