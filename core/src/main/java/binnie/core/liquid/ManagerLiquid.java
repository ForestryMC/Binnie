package binnie.core.liquid;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.utils.OreDictUtil;

import binnie.core.Constants;
import binnie.core.ManagerBase;
import binnie.core.util.Log;
import binnie.core.util.RecipeUtil;

public class ManagerLiquid extends ManagerBase {
	public static final String WATER = "water";
	//TODO: test if the name is "Creosote Oil" and not "fluid.creosote"
	public static final String CREOSOTE = "fluid.creosote";

	private final Map<String, FluidType> fluids;

	public ManagerLiquid() {
		this.fluids = new LinkedHashMap<>();
	}

	public void createLiquids(IFluidDefinition[] liquids) {
		for (final IFluidDefinition liquid : liquids) {
			final FluidType type = liquid.getType();
			final BinnieFluid fluid = this.createLiquid(type);
		}
	}

	private BinnieFluid createLiquid(final FluidType fluid) {
		this.fluids.put(fluid.getIdentifier().toLowerCase(), fluid);
		final BinnieFluid bFluid = new BinnieFluid(fluid);
		if ((!FluidRegistry.registerFluid(bFluid)) || (FluidRegistry.addBucketForFluid(bFluid))) {
			Log.error("Liquid registered incorrectly - {} ", fluid.getIdentifier());
		}
		return bFluid;
	}

	@Nullable
	public FluidStack getFluidStack(final String name, final int amount) {
		return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
	}

	@Nullable
	public FluidStack getFluidStack(String name) {
		return getFluidStack(name, Fluid.BUCKET_VOLUME);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		final RecipeUtil recipeUtil = new RecipeUtil(Constants.CORE_MOD_ID);
		recipeUtil.addShapelessRecipe("glass_container_conversion", FluidContainerType.GLASS.get(1), Items.GLASS_BOTTLE);
		recipeUtil.addShapelessRecipe("glass_bottle_conversion", new ItemStack(Items.GLASS_BOTTLE), FluidContainerType.GLASS.get(1));
		recipeUtil.addRecipe("glass_container", FluidContainerType.GLASS.get(3),
			" b ", "g g", " g ",
			'g', OreDictUtil.BLOCK_GLASS, 'b', OreDictUtil.SLAB_WOOD
		);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerSprites(TextureStitchEvent event) {
		final TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		for (final FluidType definition : fluids.values()) {
			textureMap.registerSprite(definition.getFlowing());
			textureMap.registerSprite(definition.getStill());
		}
	}

	public Map<String, FluidType> getFluids() {
		return fluids;
	}
}
