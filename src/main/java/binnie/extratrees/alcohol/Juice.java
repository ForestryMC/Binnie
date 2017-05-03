package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum Juice implements IFluidType, ICocktailLiquid {
	Apple("Apple Juice", "juiceApple", 0xffca32, 0.7, "Apple"),
	Apricot("Apricot Juice", "juiceApricot", 0xffb51e, 0.6, "Apricot"),
	Banana("Banana Juice", "juiceBanana", 0xe9d483, 0.6, "Banana"),
	Cherry("Cherry Juice", "juiceCherry", 0xc70b1f, 0.6, "Cherry"),
	Elderberry("Elderberry Juice", "juiceElderberry", 0x402629, 0.6, "Elderberry"),
	Lemon("Lemon Juice", "juiceLemon", 0xdeda52, 0.7, "Lemon"),
	Lime("Lime Juice", "juiceLime", 0xb9ce6f, 0.6, "Lime"),
	Orange("Orange Juice", "juiceOrange", 0xf9a22f, 0.8, "Orange"),
	Peach("Peach Juice", "juicePeach", 0xfac55d, 0.7, "Peach"),
	Plum("Plum Juice", "juicePlum", 0xa11f11, 0.7, "Plum"),
	Carrot("Carrot Juice", "juiceCarrot", 0xfb8e17, 0.7, "Carrot"),
	Tomato("Tomato Juice", "juiceTomato", 0xc2442e, 0.7, "Tomato"),
	Cranberry("Cranberry Juice", "juiceCranberry", 0xc52735, 0.7, "Cranberry"),
	Grapefruit("Grapefruit Juice", "juiceGrapefruit", 0xf29255, 0.6, "Grapefruit"),
	Olive("Olive Oil", "juiceOlive", 0xf4c900, 0.6, "Olive"),
	Pineapple("Pineapple Juice", "juicePineapple", 0xe7c547, 0.6, "Pineapple"),
	Pear("Pear Juice", "juicePear", 0xd8bf65, 0.6, "Pear"),
	WhiteGrape("White Grape Juice", "juiceWhiteGrape", 0xfbe379, 0.6, "WhiteGrape"),
	RedGrape("Red Grape Juice", "juiceRedGrape", 0x952934, 0.6, "RedGrape");

	public String squeezing;

	protected String name;
	protected String ident;
	protected IIcon icon;
	protected int colour;
	protected float transparency;

	Juice(String name, String ident, int colour, double transparency, String squeezing) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		addSqueezing("crop" + squeezing);
	}

	private void addSqueezing(String oreDict) {
		squeezing = oreDict;
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
		return 0.0f;
	}
}
