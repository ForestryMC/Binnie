package binnie.core.liquid;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.util.I18N;

import javax.annotation.Nullable;
import java.awt.*;

public class FluidType {
	private ResourceLocation textureFlowing;
	private ResourceLocation textureStill;
	private int color;
	private int containerColor;
	private int transparency;
	private String unlocalizedName;
	private final String identifier;
	private ContainerShowHandler showHandler = (t)->true;
	private ContainerPlaceHandler placeHandler = (t)->true;

	public FluidType(String identifier, String unlocalizedName, int color) {
		this.identifier = "binnie." + identifier;
		this.unlocalizedName = unlocalizedName.toLowerCase();
		this.color = color;
		this.containerColor = color;
		ResourceLocation texture = new ResourceLocation(Constants.CORE_MOD_ID, "blocks/liquids/blank");
		this.textureFlowing = texture;
		this.textureStill = texture;
	}

	public Block makeBlock() {
		return new BlockBinnieFluid(this);
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
		this.transparency = (int)(Math.min(1.0, transparency + 0.3) * 255.0);
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

	public interface ContainerShowHandler{
		boolean showInCreative(FluidContainerType type);
	}

	public interface ContainerPlaceHandler{
		boolean canPlaceIn(FluidContainerType type);
	}

	public FluidType getDefinition() {
		return this;
	}
}
