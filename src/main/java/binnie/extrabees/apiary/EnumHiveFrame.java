// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary;

import binnie.extrabees.ExtraBees;
import binnie.core.genetics.EnumBeeBooleanModifier;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;

import net.minecraft.init.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Items;

import binnie.core.Mods;

import net.minecraft.item.ItemStack;

import binnie.core.genetics.EnumBeeModifier;
import binnie.core.genetics.BeeModifierLogic;

import net.minecraft.item.Item;

import forestry.api.apiculture.IHiveFrame;

public enum EnumHiveFrame implements IHiveFrame, IBeeModifier
{
	Cocoa,
	Cage,
	Soul,
	Clay,
	Debug;

	Item item;
	int maxDamage;
	BeeModifierLogic logic;

	public static void init() {
		EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.25f);
		EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.Production, 1.5f, 5.0f);
		EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Territory, 0.5f, 0.1f);
		EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.5f);
		EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Production, 0.75f, 0.5f);
		EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Mutation, 1.5f, 5.0f);
		EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.5f);
		EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Production, 0.25f, 0.1f);
		EnumHiveFrame.Soul.setMaxDamage(80);
		EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Lifespan, 1.5f, 5.0f);
		EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Mutation, 0.5f, 0.2f);
		EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Production, 0.75f, 0.2f);
		EnumHiveFrame.Debug.logic.setModifier(EnumBeeModifier.Lifespan, 1.0E-4f, 1.0E-4f);
		GameRegistry.addRecipe(new ItemStack(EnumHiveFrame.Cocoa.item), new Object[] { " c ", "cFc", " c ", 'F', Mods.Forestry.stack("frameImpregnated"), 'c', new ItemStack(Items.dye, 1, 3) });
		GameRegistry.addShapelessRecipe(new ItemStack(EnumHiveFrame.Cage.item), new Object[] { Mods.Forestry.stack("frameImpregnated"), Blocks.iron_bars });
		GameRegistry.addShapelessRecipe(new ItemStack(EnumHiveFrame.Soul.item), new Object[] { Mods.Forestry.stack("frameImpregnated"), Blocks.soul_sand });
		GameRegistry.addRecipe(new ItemStack(EnumHiveFrame.Clay.item), new Object[] { " c ", "cFc", " c ", 'F', Mods.Forestry.stack("frameImpregnated"), 'c', Items.clay_ball });
	}

	public int getIconIndex() {
		return 55 + this.ordinal();
	}

	public void setMaxDamage(final int damage) {
		this.maxDamage = damage;
	}

	private EnumHiveFrame() {
		this.maxDamage = 240;
		this.logic = new BeeModifierLogic();
	}

	@Override
	public ItemStack frameUsed(final IBeeHousing house, final ItemStack frame, final IBee queen, final int wear) {
		frame.setItemDamage(frame.getItemDamage() + wear);
		if (frame.getItemDamage() >= frame.getMaxDamage()) {
			return null;
		}
		return frame;
	}

	@Override
	public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Territory, currentModifier);
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Mutation, currentModifier);
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Lifespan, currentModifier);
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Production, currentModifier);
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Flowering, currentModifier);
	}

	@Override
	public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.GeneticDecay, currentModifier);
	}

	@Override
	public boolean isSealed() {
		return this.logic.getModifier(EnumBeeBooleanModifier.Sealed);
	}

	@Override
	public boolean isSelfLighted() {
		return this.logic.getModifier(EnumBeeBooleanModifier.SelfLighted);
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.logic.getModifier(EnumBeeBooleanModifier.SunlightStimulated);
	}

	@Override
	public boolean isHellish() {
		return this.logic.getModifier(EnumBeeBooleanModifier.Hellish);
	}

	public String getName() {
		return ExtraBees.proxy.localise("item.frame." + this.toString().toLowerCase());
	}

	@Override
	public IBeeModifier getBeeModifier() {
		return this;
	}
}
