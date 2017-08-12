package binnie.core.liquid;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.util.I18N;

public class FluidDefinition {
	public ResourceLocation textureFlowing;
	public ResourceLocation textureStill;
	public int color;
	public int containerColor;
	public int transparency;
	public String unlocalizedName;
	public String identifier;
	public ContainerShowHandler showHandler = (t)->true;
	public ContainerPlaceHandler placeHandler = (t)->true;

	public FluidDefinition(String identifier, String unlocalizedName, int color) {
		this.identifier = "binnie." + identifier;
		this.unlocalizedName = unlocalizedName;
		this.color = color;
		this.containerColor = color;
		setTextures(new ResourceLocation(Constants.CORE_MOD_ID, "blocks/liquids/blank"));
	}

	/* TEXTURES */
	public FluidDefinition setTextures(ResourceLocation texture) {
		this.textureFlowing = texture;
		this.textureStill = texture;
		return this;
	}

	public FluidDefinition setTextureFlowing(ResourceLocation textureFlowing) {
		this.textureFlowing = textureFlowing;
		return this;
	}

	public ResourceLocation getFlowing() {
		return textureFlowing;
	}

	public FluidDefinition setTextureStill(ResourceLocation textureStill) {
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

	public FluidDefinition setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
		return this;
	}

	public FluidDefinition setIdentifier(String identifier) {
		this.identifier = identifier;
		return this;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

	public FluidStack get(int amount) {
		return Binnie.LIQUID.getFluidStack(this.getIdentifier(), amount);
	}

	public FluidStack get() {
		return get(Fluid.BUCKET_VOLUME);
	}

	/* COLORS */
	public FluidDefinition setColor(int color) {
		this.color = color;
		return this;
	}

	public int getColor() {
		return color;
	}

	public FluidDefinition setContainerColor(int containerColor) {
		this.containerColor = containerColor;
		return this;
	}

	public int getContainerColor() {
		return containerColor;
	}

	public FluidDefinition setTransparency(double transparency) {
		this.transparency = (int)(Math.min(1.0, transparency + 0.3) * 255.0);
		return this;
	}

	public FluidDefinition setTransparency(int transparency) {
		this.transparency = transparency;
		return this;
	}

	public int getTransparency() {
		return transparency;
	}

	/* HANDLERS*/
	public FluidDefinition setPlaceHandler(ContainerPlaceHandler placeHandler) {
		this.placeHandler = placeHandler;
		return this;
	}

	public FluidDefinition setShowHandler(ContainerShowHandler showHandler) {
		this.showHandler = showHandler;
		return this;
	}

	public boolean canPlaceIn(FluidContainerType type) {
		return placeHandler.canPlaceIn(type);
	}

	public boolean showInCreative(FluidContainerType type) {
		return showHandler.showInCreative(type);
	}

	public static interface ContainerShowHandler{
		boolean showInCreative(FluidContainerType type);
	}

	public static interface ContainerPlaceHandler{
		boolean canPlaceIn(FluidContainerType type);
	}

	public FluidDefinition getDefinition() {
		return this;
	}
}
