package binnie.core.liquid;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import binnie.Constants;
import binnie.core.util.RecipeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.utils.OreDictUtil;

import binnie.core.ManagerBase;

public class ManagerLiquid extends ManagerBase {
	public static final String WATER = "water";
	//TODO: test if the name is "Creosote Oil" and not "fluid.creosote"
	public static final String CREOSOTE = "fluid.creosote";
	
	Map<String, IFluidType> fluids;

	public ManagerLiquid() {
		this.fluids = new LinkedHashMap<>();
	}

	public Collection<IFluidType> getFluidTypes() {
		return this.fluids.values();
	}

	public void createLiquids(final IFluidType[] liquids) {
		for (final IFluidType liquid : liquids) {
			final BinnieFluid fluid = this.createLiquid(liquid);
			if (fluid == null) {
				throw new RuntimeException("Liquid registered incorrectly - " + liquid.getIdentifier());
			}
		}
	}

	public BinnieFluid createLiquid(final IFluidType fluid) {
		this.fluids.put(fluid.getIdentifier().toLowerCase(), fluid);
		final BinnieFluid bFluid = new BinnieFluid(fluid);
		FluidRegistry.registerFluid(bFluid);
		FluidRegistry.addBucketForFluid(bFluid);
		return bFluid;
	}

	@Nullable
	public FluidStack getFluidStack(final String name, final int amount) {
		return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.CORE_MOD_ID);
		recipeUtil.addShapelessRecipe("glass_container_conversion", FluidContainerType.GLASS.get(1), Items.GLASS_BOTTLE);
		recipeUtil.addShapelessRecipe("glass_bottle_conversion", new ItemStack(Items.GLASS_BOTTLE), FluidContainerType.GLASS.get(1));
		recipeUtil.addRecipe("glass_container", FluidContainerType.GLASS.get(3),
			" b ", "g g", " g ",
			'g', OreDictUtil.BLOCK_GLASS, 'b', OreDictUtil.SLAB_WOOD
		);
	}

	public IFluidType getFluidType(final String liquid) {
		return this.fluids.get(liquid.toLowerCase());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerSprites(TextureStitchEvent event) {
		TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		for (IFluidType fluid : this.fluids.values()) {
			textureMap.registerSprite(fluid.getFlowing());
			textureMap.registerSprite(fluid.getStill());
		}
	}
}
