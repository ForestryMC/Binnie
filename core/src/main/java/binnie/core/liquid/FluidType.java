package binnie.core.liquid;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.features.Feature;
import binnie.core.features.IFeatureRegistry;
import binnie.core.proxy.BinnieProxy;
import binnie.core.util.I18N;

public class FluidType extends Feature {
	private ResourceLocation textureFlowing;
	private ResourceLocation textureStill;
	private int color;
	private int containerColor;
	private int transparency;
	private String unlocalizedName;
	private ContainerShowHandler showHandler = t -> true;
	private ContainerPlaceHandler placeHandler = t -> true;
	private boolean flammable = false;
	private int flammability = 0;

	public FluidType(IFeatureRegistry registry, String identifier, String unlocalizedName, int color) {
		super(registry, "binnie." + identifier.toLowerCase(Locale.ENGLISH));
		this.unlocalizedName = unlocalizedName.toLowerCase();
		this.color = color;
		this.containerColor = color;
		ResourceLocation texture = new ResourceLocation(Constants.CORE_MOD_ID, "blocks/liquids/blank");
		this.textureFlowing = texture;
		this.textureStill = texture;
	}

	@Override
	public void create() {
		Binnie.LIQUID.addLiquid(this);
		final BinnieFluid bFluid = new BinnieFluid(this);
		FluidRegistry.registerFluid(bFluid);
		FluidRegistry.addBucketForFluid(bFluid);
		createBlock(bFluid);
	}

	private static void createBlock(BinnieFluid binnieFluid) {
		FluidType fluidType = binnieFluid.getType();
		String name = fluidType.getIdentifier();

		Block fluidBlock = fluidType.makeBlock();
		fluidBlock.setUnlocalizedName(fluidType.getUnlocalizedName());
		fluidBlock.setRegistryName(name);
		BinnieProxy proxy = BinnieCore.getBinnieProxy();
		proxy.registerBlock(fluidBlock);

		ItemBlock itemBlock = new ItemBlock(fluidBlock);
		itemBlock.setRegistryName(name);
		proxy.registerItem(itemBlock);

		BinnieCore.getBinnieProxy().registerFluidStateMapper(fluidBlock, fluidType);
	}

	public FluidType setFlammable(boolean flammable) {
		this.flammable = flammable;
		return this;
	}

	public FluidType setFlammability(int flammability) {
		this.flammability = flammability;
		return this;
	}

	public Block makeBlock() {
		return new BlockBinnieFluid(this, flammability, flammable);
	}

	public final Color getParticleColor() {
		return new Color(color);
	}

	@Nullable
	public final Fluid getFluid() {
		return FluidRegistry.getFluid(identifier);
	}

	/* TEXTURES */
	public FluidType setTextures(ResourceLocation texture) {
		this.textureFlowing = texture;
		this.textureStill = texture;
		return this;
	}

	public FluidType setTextureFlowing(ResourceLocation textureFlowing) {
		this.textureFlowing = textureFlowing;
		return this;
	}

	public ResourceLocation getFlowing() {
		return textureFlowing;
	}

	public FluidType setTextureStill(ResourceLocation textureStill) {
		this.textureStill = textureStill;
		return this;
	}

	public ResourceLocation getStill() {
		return textureStill;
	}

	/* NAMES */
	public String getDisplayName() {
		return I18N.localise(unlocalizedName);
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	public FluidType setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
		return this;
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

	public FluidStack stack(int amount) {
		Fluid fluid = getFluid();
		if (fluid == null) {
			throw new NullPointerException("This feature has no fluid to create a stack for.");
		}
		return new FluidStack(fluid, amount);
	}

	public FluidStack stack() {
		return stack(Fluid.BUCKET_VOLUME);
	}

	/* COLORS */
	public FluidType setColor(int color) {
		this.color = color;
		return this;
	}

	public int getColor() {
		return color;
	}

	public FluidType setContainerColor(int containerColor) {
		this.containerColor = containerColor;
		return this;
	}

	public int getContainerColor() {
		return containerColor;
	}

	public FluidType setTransparency(double transparency) {
		this.transparency = (int) (Math.min(1.0, transparency + 0.3) * 255.0);
		return this;
	}

	public FluidType setTransparency(int transparency) {
		this.transparency = transparency;
		return this;
	}

	public int getTransparency() {
		return transparency;
	}

	/* HANDLERS*/
	public FluidType setPlaceHandler(ContainerPlaceHandler placeHandler) {
		this.placeHandler = placeHandler;
		return this;
	}

	public FluidType setShowHandler(ContainerShowHandler showHandler) {
		this.showHandler = showHandler;
		return this;
	}

	public boolean canPlaceIn(FluidContainerType type) {
		return placeHandler.canPlaceIn(type);
	}

	public boolean showInCreative(FluidContainerType type) {
		return showHandler.showInCreative(type);
	}

	public interface ContainerShowHandler {
		boolean showInCreative(FluidContainerType type);
	}

	public interface ContainerPlaceHandler {
		boolean canPlaceIn(FluidContainerType type);
	}

	public FluidType getDefinition() {
		return this;
	}
}
