package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.resource.BinnieSprite;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.minecraft.EnumColor;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.genetics.Genetics;
import binnie.genetics.item.ModuleItems;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;

public class AnalystPageMutations extends ControlAnalystPage {
	public AnalystPageMutations(final IWidget parent, final Area area, final IIndividual ind, final boolean isMaster) {
		super(parent, area);
		this.setColour(3355392);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 18;
		final BreedingSystem system = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		final List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
		final IAlleleSpecies speciesCurrent = ind.getGenome().getPrimary();
		final Collection<IMutation> resultant = system.getResultantMutations(speciesCurrent);
		final Collection<IMutation> further = system.getFurtherMutations(speciesCurrent);
		if (ind instanceof IBee) {
			ItemStack hive = null;
			if (ind.getGenome().getPrimary() == ExtraBeesSpecies.WATER) {
				hive = new ItemStack(ExtraBees.hive, 1, 0);
			}
			if (ind.getGenome().getPrimary() == ExtraBeesSpecies.ROCK) {
				hive = new ItemStack(ExtraBees.hive, 1, 1);
			}
			if (ind.getGenome().getPrimary() == ExtraBeesSpecies.BASALT) {
				hive = new ItemStack(ExtraBees.hive, 1, 2);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Forest.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 1);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Meadows.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 2);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Modest.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 3);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Tropical.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 4);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Ended.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 5);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Wintry.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 6);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Marshy.getAllele()) {
				hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 7);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
				hive = new ItemStack(Blocks.CHEST);
			}
			if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Valiant.getAllele()) {
				new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.mutations.bee.natural")).setColour(this.getColour());
				y += 10;
				new ControlTextCentered(this, y, TextFormatting.ITALIC + Genetics.proxy.localise("gui.analyst.mutations.bee.hive")).setColour(this.getColour());
				y += 22;
			} else if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Monastic.getAllele()) {
				new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.mutations.bee.natural")).setColour(this.getColour());
				y += 10;
				new ControlTextCentered(this, y, TextFormatting.ITALIC + Genetics.proxy.localise("gui.analyst.mutations.bee.villager")).setColour(this.getColour());
				y += 22;
			} else if (hive != null) {
				new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.mutations.bee.natural")).setColour(this.getColour());
				y += 10;
				final ControlItemDisplay display = new ControlItemDisplay(this, (this.width() - 16) / 2, y);
				if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
					display.addTooltip(Genetics.proxy.localise("gui.analyst.mutations.bee.dungeon"));
				} else {
					display.setTooltip();
				}
				display.setItemStack(hive);
				y += 24;
			}
		}
		int ox = (this.width() - 88 - 8) / 2;
		int dx = 0;
		if (!resultant.isEmpty()) {
			if (resultant.size() == 1) {
				ox = (this.width() - 44) / 2;
			}
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.mutations.resultant")).setColour(this.getColour());
			y += 10;
			for (final IMutation mutation : resultant) {
				final float specificChance = this.getSpecificChance(ind, mutation, system);
				if (!isMaster && !this.isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44, 16);
				} else {
					new Control(this, ox + dx, y, 44, 16) {
						@Override
						public void initialise() {
							final IAlleleSpecies species0 = mutation.getAllele0();
							final IAlleleSpecies species2 = mutation.getAllele1();
							final String comb = species0.getName() + " + " + species2.getName();
							this.addTooltip(comb);
							String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% " + Genetics.proxy.localise("gui.analyst.mutations.chance");
							if (specificChance != mutation.getBaseChance()) {
								chance = chance + getMutationColour(specificChance).getCode() + " (" + (int) specificChance + "% " + Genetics.proxy.localise("gui.analyst.mutations.currently") + ")";
							}
							this.addTooltip(chance);
							for (final String s : mutation.getSpecialConditions()) {
								this.addTooltip(s);
							}
						}

						@Override
						@SideOnly(Side.CLIENT)
						public void onRenderBackground(int guiWidth, int guiHeight) {
							RenderUtil.drawItem(Point.ZERO, system.getDefaultMember(mutation.getAllele0().getUID()));
							RenderUtil.drawItem(new Point(28, 0), system.getDefaultMember(mutation.getAllele1().getUID()));
							if (specificChance != mutation.getBaseChance()) {
								RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColour());
								drawSprite(ModuleItems.iconAdd0);
								RenderUtil.setColour(getMutationColour(specificChance).getColour());
								drawSprite(ModuleItems.iconAdd1);
							} else {
								RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColour());
								drawSprite(ModuleItems.iconAdd);
							}
						}
					};
				}
				dx = 52 - dx;
				if (dx == 0 || resultant.size() == 1) {
					y += 18;
				}
			}
			if (dx != 0 && resultant.size() != 1) {
				y += 18;
			}
			y += 10;
		}
		ox = (this.width() - 88 - 8) / 2;
		dx = 0;
		if (!further.isEmpty()) {
			if (further.size() == 1) {
				ox = (this.width() - 44) / 2;
			}
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.mutations.further")).setColour(this.getColour());
			y += 10;
			for (final IMutation mutation : further) {
				final IAllele speciesComb = mutation.getPartner(speciesCurrent);
				final float specificChance2 = this.getSpecificChance(ind, mutation, system);
				if (!isMaster && !this.isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44, 16);
				} else {
					new Control(this, ox + dx, y, 44, 16) {
						@Override
						public void initialise() {
							final IAlleleSpecies species0 = (IAlleleSpecies) speciesComb;
							final IAlleleSpecies species2 = (IAlleleSpecies) mutation.getTemplate()[0];
							this.addTooltip(species2.getName());
							final String comb = speciesCurrent.getName() + " + " + species0.getName();
							this.addTooltip(comb);
							String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% " + Genetics.proxy.localise("gui.analyst.mutations.chance");
							if (specificChance2 != mutation.getBaseChance()) {
								chance = chance + getMutationColour(specificChance2).getCode() + " (" + (int) specificChance2 + "% " + Genetics.proxy.localise("gui.analyst.mutations.currently") + ")";
							}
							this.addTooltip(chance);
							for (final String s : mutation.getSpecialConditions()) {
								this.addTooltip(s);
							}
						}

						@Override
						@SideOnly(Side.CLIENT)
						public void onRenderBackground(int guiWidth, int guiHeight) {
							RenderUtil.drawItem(Point.ZERO, system.getDefaultMember(speciesComb.getUID()));
							RenderUtil.drawItem(new Point(28, 0), system.getDefaultMember(mutation.getTemplate()[0].getUID()));
							RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColour());
							if (specificChance2 != mutation.getBaseChance()) {
								RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColour());
								drawSprite(ModuleItems.iconArrow0);
								RenderUtil.setColour(getMutationColour(specificChance2).getColour());
								drawSprite(ModuleItems.iconArrow1);
							} else {
								RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColour());
								drawSprite(ModuleItems.iconArrow);
							}
						}
					};
				}
				dx = 52 - dx;
				if (dx == 0 || further.size() == 1) {
					y += 18;
				}
			}
			if (dx != 0 && further.size() != 1) {
				y += 18;
			}
		}
		y += 8;
		this.setSize(new Point(this.width(), y));
	}
	
	private void drawSprite(BinnieSprite sprite){
		RenderUtil.drawSprite(new Point(14, 0), sprite.getSprite());
	}

	private boolean isKnown(final BreedingSystem system, final IMutation mutation) {
		return system.getDiscoveredMutations(this.getWindow().getWorld(), this.getWindow().getPlayer().getGameProfile()).contains(mutation);
	}

	private float getSpecificChance(final IIndividual ind, final IMutation mutation, final BreedingSystem system) {
		return system.getChance(mutation, this.getWindow().getPlayer(), mutation.getAllele0(), mutation.getAllele1());
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.mutations.title");
	}

	protected EnumColor getMutationColour(final float percent) {
		if (percent >= 20.0f) {
			return EnumColor.DarkGreen;
		}
		if (percent >= 15.0f) {
			return EnumColor.Green;
		}
		if (percent >= 10.0f) {
			return EnumColor.Yellow;
		}
		if (percent >= 5.0f) {
			return EnumColor.Gold;
		}
		if (percent > 0.0f) {
			return EnumColor.Red;
		}
		return EnumColor.DarkRed;
	}

	static class ControlUnknownMutation extends Control {
		public ControlUnknownMutation(final IWidget parent, final int x, final int y, final int w, final int h) {
			super(parent, x, y, w, h);
			this.addAttribute(Attribute.MouseOver);
			this.addTooltip(Genetics.proxy.localise("gui.analyst.mutations.unknown.tooltip"));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			RenderUtil.drawText(this.getArea(), TextJustification.MiddleCenter, Genetics.proxy.localise("gui.analyst.mutations.unknown"), 11184810);
		}
	}
}
