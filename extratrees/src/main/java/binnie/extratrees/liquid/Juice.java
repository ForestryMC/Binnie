package binnie.extratrees.liquid;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.CocktailLiquid;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;

@Mod.EventBusSubscriber(modid = Constants.EXTRA_TREES_MOD_ID)
public enum Juice implements IFluidDefinition, ICocktailIngredientProvider {
	Apple("juice.apple", 16763442, 0.7, "Apple", "Apple"),
	Apricot("juice.apricot", 16758046, 0.6, "Apricot", "Apricot"),
	Banana("juice.banana", 15324291, 0.6, "Banana", "Banana"),
	Cherry("juice.cherry", 13044511, 0.6, "Cherry", "Cherry"),
	Elderberry("juice.elderberry", 4204073, 0.6, "Elderberry", "Elderberry"),
	Lemon("juice.lemon", 14604882, 0.7, "Lemon", "Lemon"),
	Lime("juice.lime", 12177007, 0.6, "Lime", "Lime"),
	Orange("juice.orange", 16359983, 0.8, "Orange", "Orange"),
	Peach("juice.peach", 16434525, 0.7, "Peach", "Peach"),
	Plum("juice.plum", 10559249, 0.7, "Plum", "Plum"),
	Carrot("juice.carrot", 16485911, 0.7, "Carrot", "Carrot"),
	Tomato("juice.tomato", 12731438, 0.7, "Tomato", "Tomato"),
	Cranberry("juice.cranberry", 12920629, 0.7, "Cranberry", "Cranberry"),
	Grapefruit("juice.grapefruit", 15897173, 0.6, "Grapefruit", "Grapefruit"),
	Olive("juice.olive", 16042240, 0.6, "Olive", "Olive"),
	Pineapple("juice.pineapple", 15189319, 0.6, "Pineapple", "Pineapple"),
	Pear("juice.pear", 14204773, 0.6, "Pear", "Pear"),
	WhiteGrape("juice.white.grape", 16507769, 0.6, "WhiteGrape", "Grape"),
	RedGrape("juice.red.grape", 9775412, 0.6, "RedGrape", "Grape");

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	private final String oreDict;
	private String squeezing;
	@Nullable
	private CocktailLiquid cocktailLiquid;

	Juice(String identifier, int color, double transparency, String squeezing, String oreDict) {
		this.addSqueezing("crop" + squeezing);
		this.identifier = identifier;
		this.constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "Juice", this.name()), color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler(type -> type == FluidContainerType.GLASS);
		this.oreDict = "food" + oreDict + "juice";
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(Juice.class);
	}

	private void addSqueezing(String oreDict) {
		this.squeezing = oreDict;
	}

	@Override
	public String toString() {
		return fluid.toString();
	}

	@Override
	public ICocktailIngredient getIngredient() {
		if (cocktailLiquid != null) {
			return cocktailLiquid;
		} else {
			throw new IllegalStateException("Called feature getter method before content creation was called in the pre init.");
		}
	}

	public String getSqueezing() {
		return squeezing;
	}

	public String getOreDict() {
		return oreDict;
	}

	/* Feature */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public IFeatureConstructor<FluidType> getConstructor() {
		return constructor;
	}

	@Override
	public boolean hasFluid() {
		return fluid != null;
	}

	@Nullable
	@Override
	public FluidType getFluid() {
		return fluid;
	}

	@Override
	public void setFluid(FluidType fluid) {
		this.fluid = fluid;
		cocktailLiquid = new CocktailLiquid(this.fluid, 0.0F);
	}

	@Override
	public FeatureType getType() {
		return FeatureType.FLUID;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return ExtraTreesModuleUIDs.ALCOHOL;
	}
}
