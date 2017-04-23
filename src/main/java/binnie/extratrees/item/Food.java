// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.item;

import java.util.Collection;
import net.minecraftforge.oredict.OreDictionary;
import binnie.core.Mods;
import binnie.Binnie;
import forestry.api.recipes.RecipeManagers;
import binnie.extratrees.alcohol.Juice;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.IIcon;
import binnie.core.item.IItemMisc;

public enum Food implements IItemMisc
{
	Crabapple(2),
	Orange(4),
	Kumquat(2),
	Lime(2),
	WildCherry(2),
	SourCherry(2),
	BlackCherry(2),
	Blackthorn(3),
	CherryPlum(3),
	Almond(1),
	Apricot(4),
	Grapefruit(4),
	Peach(4),
	Satsuma(3),
	BuddhaHand(3),
	Citron(3),
	FingerLime(3),
	KeyLime(2),
	Manderin(3),
	Nectarine(3),
	Pomelo(3),
	Tangerine(3),
	Pear(4),
	SandPear(2),
	Hazelnut(2),
	Butternut(1),
	Beechnut(0),
	Pecan(0),
	Banana(4),
	RedBanana(4),
	Plantain(2),
	BrazilNut(0),
	Fig(2),
	Acorn(0),
	Elderberry(1),
	Olive(1),
	GingkoNut(1),
	Coffee(0),
	OsangeOrange(1),
	Clove(0),
	Papayimar(8),
	Blackcurrant(2),
	Redcurrant(2),
	Blackberry(2),
	Raspberry(2),
	Blueberry(2),
	Cranberry(2),
	Juniper(0),
	Gooseberry(2),
	GoldenRaspberry(2),
	Coconut(2),
	Cashew(0),
	Avacado(2),
	Nutmeg(0),
	Allspice(0),
	Chilli(2),
	StarAnise(0),
	Mango(4),
	Starfruit(2),
	Candlenut(0);

	IIcon icon;
	int hunger;
	private List<String> ores;

	private Food() {
		this(0);
	}

	private Food(final int hunger) {
		this.ores = new ArrayList<String>();
		this.hunger = hunger;
	}

	public boolean isEdible() {
		return this.hunger > 0;
	}

	public int getHealth() {
		return this.hunger;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public String getName(final ItemStack itemStack) {
		return ExtraTrees.proxy.localise("item.food." + this.name().toLowerCase());
	}

	@Override
	public ItemStack get(final int count) {
		return new ItemStack(ExtraTrees.itemFood, count, this.ordinal());
	}

	@Override
	public IIcon getIcon(final ItemStack itemStack) {
		return this.icon;
	}

	@Override
	public void registerIcons(final IIconRegister register) {
		this.icon = ExtraTrees.proxy.getIcon(register, "food/" + this.toString());
	}

	@Override
	public void addInformation(final List data) {
	}

	public void addJuice(final Juice juice, final int time, final int amount, final int mulch) {
		RecipeManagers.squeezerManager.addRecipe(time, new ItemStack[] { this.get(1) }, Binnie.Liquid.getLiquidStack("juice", amount), Mods.Forestry.stack("mulch"), mulch);
	}

	public void addJuice(final int time, final int amount, final int mulch) {
		RecipeManagers.squeezerManager.addRecipe(time, new ItemStack[] { this.get(1) }, Binnie.Liquid.getLiquidStack("juice", amount), Mods.Forestry.stack("mulch"), mulch);
	}

	public void addOil(final int time, final int amount, final int mulch) {
		RecipeManagers.squeezerManager.addRecipe(time, new ItemStack[] { this.get(1) }, Binnie.Liquid.getLiquidStack("seedoil", amount), Mods.Forestry.stack("mulch"), mulch);
	}

	public static void registerOreDictionary() {
		Food.Crabapple.ore("Apple").ore("Crabapple");
		Food.Orange.ore("Orange");
		Food.Kumquat.ore("Kumquat");
		Food.Lime.ore("Lime");
		Food.WildCherry.ore("Cherry").ore("WildCherry");
		Food.SourCherry.ore("Cherry").ore("SourCherry");
		Food.BlackCherry.ore("Cherry").ore("BlackCherry");
		Food.Blackthorn.ore("Blackthorn");
		Food.CherryPlum.ore("Plum").ore("CherryPlum");
		Food.Almond.ore("Almond");
		Food.Apricot.ore("Apricot");
		Food.Grapefruit.ore("Grapefruit");
		Food.Peach.ore("Peach");
		Food.Satsuma.ore("Satsuma").ore("Orange");
		Food.BuddhaHand.ore("BuddhaHand").ore("Citron");
		Food.Citron.ore("Citron");
		Food.FingerLime.ore("Lime").ore("FingerLime");
		Food.KeyLime.ore("KeyLime").ore("Lime");
		Food.Manderin.ore("Orange").ore("Manderin");
		Food.Nectarine.ore("Peach").ore("Nectarine");
		Food.Pomelo.ore("Pomelo");
		Food.Tangerine.ore("Tangerine").ore("Orange");
		Food.Pear.ore("Pear");
		Food.SandPear.ore("SandPear");
		Food.Hazelnut.ore("Hazelnut");
		Food.Butternut.ore("Butternut").ore("Walnut");
		Food.Beechnut.ore("Beechnut");
		Food.Pecan.ore("Pecan");
		Food.Banana.ore("Banana");
		Food.RedBanana.ore("RedBanana").ore("Banana");
		Food.Plantain.ore("Plantain");
		Food.BrazilNut.ore("BrazilNut");
		Food.Fig.ore("Fig");
		Food.Acorn.ore("Acorn");
		Food.Elderberry.ore("Elderberry");
		Food.Olive.ore("Olive");
		Food.GingkoNut.ore("GingkoNut");
		Food.Coffee.ore("Coffee");
		Food.OsangeOrange.ore("OsangeOrange");
		Food.Clove.ore("Clove");
	}

	private Food ore(final String string) {
		OreDictionary.registerOre("crop" + string, this.get(1));
		this.ores.add("crop" + string);
		return this;
	}

	public Collection<String> getOres() {
		return this.ores;
	}
}
