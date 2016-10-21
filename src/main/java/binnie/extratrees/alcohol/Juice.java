package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public enum Juice implements IFluidType, ICocktailLiquid {
    Apple("Apple Juice", "juiceApple", 16763442, 0.7, "Apple"),
    Apricot("Apricot Juice", "juiceApricot", 16758046, 0.6, "Apricot"),
    Banana("Banana Juice", "juiceBanana", 15324291, 0.6, "Banana"),
    Cherry("Cherry Juice", "juiceCherry", 13044511, 0.6, "Cherry"),
    Elderberry("Elderberry Juice", "juiceElderberry", 4204073, 0.6, "Elderberry"),
    Lemon("Lemon Juice", "juiceLemon", 14604882, 0.7, "Lemon"),
    Lime("Lime Juice", "juiceLime", 12177007, 0.6, "Lime"),
    Orange("Orange Juice", "juiceOrange", 16359983, 0.8, "Orange"),
    Peach("Peach Juice", "juicePeach", 16434525, 0.7, "Peach"),
    Plum("Plum Juice", "juicePlum", 10559249, 0.7, "Plum"),
    Carrot("Carrot Juice", "juiceCarrot", 16485911, 0.7, "Carrot"),
    Tomato("Tomato Juice", "juiceTomato", 12731438, 0.7, "Tomato"),
    Cranberry("Cranberry Juice", "juiceCranberry", 12920629, 0.7, "Cranberry"),
    Grapefruit("Grapefruit Juice", "juiceGrapefruit", 15897173, 0.6, "Grapefruit"),
    Olive("Olive Oil", "juiceOlive", 16042240, 0.6, "Olive"),
    Pineapple("Pineapple Juice", "juicePineapple", 15189319, 0.6, "Pineapple"),
    Pear("Pear Juice", "juicePear", 14204773, 0.6, "Pear"),
    WhiteGrape("White Grape Juice", "juiceWhiteGrape", 16507769, 0.6, "WhiteGrape"),
    RedGrape("Red Grape Juice", "juiceRedGrape", 9775412, 0.6, "RedGrape");

    String name;
    String ident;
    public String squeezing;
    //IIcon icon;
    int colour;
    float transparency;

    private void addSqueezing(final String oreDict) {
        this.squeezing = oreDict;
    }

    Juice(final String name, final String ident, final int colour, final double transparency, final String squeezing) {
        this.name = name;
        this.ident = ident;
        this.colour = colour;
        this.transparency = (float) transparency;
        this.addSqueezing("crop" + squeezing);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean canPlaceIn(final FluidContainer container) {
        return true;
    }

    @Override
    public boolean showInCreative(final FluidContainer container) {
        return container == FluidContainer.Glass;
    }

//	@Override
//	public IIcon getIcon() {
//		return this.icon;
//	}
//
//	@Override
//	public void registerIcon(final IIconRegister register) {
//		this.icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
//	}


    @Override
    public ResourceLocation getFlowing() {
        return new ResourceLocation("liquids/liquid");
    }

    @Override
    public ResourceLocation getStill() {
        return new ResourceLocation("liquids/liquid");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIdentifier() {
        return "binnie." + this.ident;
    }

    @Override
    public int getColour() {
        return this.colour;
    }

    @Override
    public FluidStack get(final int amount) {
        return Binnie.Liquid.getLiquidStack(this.getIdentifier(), amount);
    }

    @Override
    public int getTransparency() {
        return (int) (Math.min(1.0, this.transparency + 0.3) * 255.0);
    }

    @Override
    public String getTooltip(final int ratio) {
        return ratio + " Part" + ((ratio > 1) ? "s " : " ") + this.getName();
    }

    @Override
    public int getContainerColour() {
        return this.getColour();
    }

    @Override
    public float getABV() {
        return 0.0f;
    }
}
