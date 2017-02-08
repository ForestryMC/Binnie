package binnie.core.liquid;

import binnie.core.ManagerBase;
import forestry.core.items.EnumContainerType;
import forestry.core.render.TextureManager;
import forestry.core.utils.OreDictUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ManagerLiquid extends ManagerBase {
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
		GameRegistry.addShapelessRecipe(FluidContainerType.GLASS.get(1), Items.GLASS_BOTTLE);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.GLASS_BOTTLE), FluidContainerType.GLASS.get(1));
		GameRegistry.addRecipe(new ShapedOreRecipe(FluidContainerType.GLASS.get(3),
				" b ", "g g", " g ",
			'g', OreDictUtil.BLOCK_GLASS, 'b', OreDictUtil.SLAB_WOOD));
	}

	public IFluidType getFluidType(final String liquid) {
		return this.fluids.get(liquid.toLowerCase());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerSprites(TextureStitchEvent event) {
		for (IFluidType fluid : this.fluids.values()) {
			TextureManager.registerSprite(fluid.getFlowing());
			TextureManager.registerSprite(fluid.getStill());
		}
	}
}
