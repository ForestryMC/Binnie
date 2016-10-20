// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.carpentry;

import binnie.extratrees.ExtraTrees;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.api.IDesignMaterial;
import java.util.HashMap;
import net.minecraft.util.IIcon;
import java.util.Map;
import binnie.extratrees.api.IDesignSystem;

public enum DesignSystem implements IDesignSystem
{
	Wood,
	Glass;

	Map<Integer, IIcon> primary;
	Map<Integer, IIcon> secondary;

	private DesignSystem() {
		this.primary = new HashMap<Integer, IIcon>();
		this.secondary = new HashMap<Integer, IIcon>();
		DesignerManager.instance.registerDesignSystem(this);
	}

	@Override
	public IDesignMaterial getDefaultMaterial() {
		switch (this) {
		case Glass: {
			return GlassType.get(0);
		}
		case Wood: {
			return PlankType.ExtraTreePlanks.Fir;
		}
		default: {
			return null;
		}
		}
	}

	@Override
	public IDesignMaterial getDefaultMaterial2() {
		switch (this) {
		case Glass: {
			return GlassType.get(1);
		}
		case Wood: {
			return PlankType.ExtraTreePlanks.Whitebeam;
		}
		default: {
			return null;
		}
		}
	}

	@Override
	public IDesignMaterial getMaterial(final int id) {
		switch (this) {
		case Glass: {
			return GlassType.get(id);
		}
		case Wood: {
			return CarpentryManager.carpentryInterface.getWoodMaterial(id);
		}
		default: {
			return null;
		}
		}
	}

	@Override
	public int getMaterialIndex(final IDesignMaterial id) {
		switch (this) {
		case Glass: {
			return GlassType.getIndex(id);
		}
		case Wood: {
			return CarpentryManager.carpentryInterface.getCarpentryWoodIndex(id);
		}
		default: {
			return 0;
		}
		}
	}

	public String getTexturePath() {
		switch (this) {
		case Glass: {
			return "glass";
		}
		case Wood: {
			return "patterns";
		}
		default: {
			return "";
		}
		}
	}

	@Override
	public IDesignMaterial getMaterial(final ItemStack stack) {
		switch (this) {
		case Glass: {
			return GlassType.get(stack);
		}
		case Wood: {
			return CarpentryManager.carpentryInterface.getWoodMaterial(stack);
		}
		default: {
			return null;
		}
		}
	}

	@Override
	public ItemStack getAdhesive() {
		switch (this) {
		case Glass: {
			return ExtraTreeItems.GlassFitting.get(1);
		}
		case Wood: {
			return ExtraTreeItems.WoodWax.get(1);
		}
		default: {
			return null;
		}
		}
	}

	@Override
	public IIcon getPrimaryIcon(final IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.primary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	public IIcon getSecondaryIcon(final IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.secondary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	public void registerIcons(final IIconRegister register) {
		for (final EnumPattern pattern : EnumPattern.values()) {
			this.primary.put(pattern.ordinal(), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0"));
			this.secondary.put(pattern.ordinal(), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1"));
		}
	}

	public AbstractMod getMod() {
		return ExtraTrees.instance;
	}
}
