package binnie.genetics.gui.analyst;

import java.util.Collection;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.EnumColor;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.integration.extrabees.ExtraBeesIntegration;
import binnie.core.resource.BinnieSprite;
import binnie.core.util.I18N;
import binnie.genetics.item.ModuleItems;

public class AnalystPageMutations extends ControlAnalystPage {
	public AnalystPageMutations(IWidget parent, Area area, IIndividual ind, boolean isMaster) {
		super(parent, area);
		setColor(3355392);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 18;
		BreedingSystem system = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
		IAlleleSpecies speciesCurrent = ind.getGenome().getPrimary();
		Collection<IMutation> resultant = system.getResultantMutations(speciesCurrent);
		Collection<IMutation> further = system.getFurtherMutations(speciesCurrent);
		if (ind instanceof IBee) {
			ItemStack hive = null;
			if (ExtraBeesIntegration.isLoaded()) {
				if (ind.getGenome().getPrimary() == ExtraBeesIntegration.water) {
					hive = new ItemStack(ExtraBeesIntegration.hive, 1, 0);
				}
				if (ind.getGenome().getPrimary() == ExtraBeesIntegration.rock) {
					hive = new ItemStack(ExtraBeesIntegration.hive, 1, 1);
				}
				if (ind.getGenome().getPrimary() == ExtraBeesIntegration.basalt) {
					hive = new ItemStack(ExtraBeesIntegration.hive, 1, 2);
				}
				if (ind.getGenome().getPrimary() == ExtraBeesIntegration.marble) {
					hive = new ItemStack(ExtraBeesIntegration.hive, 1, 3);
				}
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
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.natural")).setColor(getColor());
				y += 10;
				new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.hive")).setColor(getColor());
				y += 22;
			} else if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Monastic.getAllele()) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.natural")).setColor(getColor());
				y += 10;
				new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.villager")).setColor(getColor());
				y += 22;
			} else if (hive != null) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.natural")).setColor(getColor());
				y += 10;
				ControlItemDisplay display = new ControlItemDisplay(this, (width() - 16) / 2, y);
				if (ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
					display.addTooltip(I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.dungeon"));
				} else {
					display.setTooltip();
				}
				display.setItemStack(hive);
				y += 24;
			}
		}
		int ox = (width() - 88 - 8) / 2;
		int dx = 0;
		if (!resultant.isEmpty()) {
			if (resultant.size() == 1) {
				ox = (width() - 44) / 2;
			}
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".resultant")).setColor(getColor());
			y += 10;
			for (IMutation mutation : resultant) {
				float specificChance = getSpecificChance(ind, mutation, system);
				if (!isMaster && !isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44, 16);
				} else {
					new Control(this, ox + dx, y, 44, 16) {
						@Override
						public void initialise() {
							IAlleleSpecies species0 = mutation.getAllele0();
							IAlleleSpecies species2 = mutation.getAllele1();
							String comb = species0.getName() + " + " + species2.getName();
							addTooltip(comb);
							String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% " + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".chance");
							if (specificChance != mutation.getBaseChance()) {
								chance = chance + getMutationColour(specificChance).getCode() + " (" + (int) specificChance + "% " + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".currently") + ")";
							}
							addTooltip(chance);
							for (String s : mutation.getSpecialConditions()) {
								addTooltip(s);
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
		ox = (width() - 88 - 8) / 2;
		dx = 0;
		if (!further.isEmpty()) {
			if (further.size() == 1) {
				ox = (width() - 44) / 2;
			}
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".further")).setColor(getColor());
			y += 10;
			for (IMutation mutation : further) {
				IAllele speciesComb = mutation.getPartner(speciesCurrent);
				float specificChance2 = getSpecificChance(ind, mutation, system);
				if (!isMaster && !isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44, 16);
				} else {
					new Control(this, ox + dx, y, 44, 16) {
						@Override
						public void initialise() {
							IAlleleSpecies species0 = (IAlleleSpecies) speciesComb;
							IAlleleSpecies species2 = (IAlleleSpecies) mutation.getTemplate()[0];
							addTooltip(species2.getAlleleName());
							String comb = speciesCurrent.getAlleleName() + " + " + species0.getAlleleName();
							addTooltip(comb);
							String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% " + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".chance");
							if (specificChance2 != mutation.getBaseChance()) {
								chance = chance + getMutationColour(specificChance2).getCode() + " (" + (int) specificChance2 + "% " + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".currently") + ")";
							}
							addTooltip(chance);
							for (String s : mutation.getSpecialConditions()) {
								addTooltip(s);
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
		setSize(new Point(width(), y));
	}

	@SideOnly(Side.CLIENT)
	private void drawSprite(BinnieSprite sprite) {
		RenderUtil.drawSprite(new Point(14, 0), sprite.getSprite());
	}

	private boolean isKnown(BreedingSystem system, IMutation mutation) {
		return system.getDiscoveredMutations(getWindow().getWorld(), getWindow().getPlayer().getGameProfile()).contains(mutation);
	}

	private float getSpecificChance(IIndividual ind, IMutation mutation, BreedingSystem system) {
		return system.getChance(mutation, getWindow().getPlayer(), mutation.getAllele0(), mutation.getAllele1());
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.MUTATIONS_KEY + ".title");
	}

	protected EnumColor getMutationColour(float percent) {
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
		public ControlUnknownMutation(IWidget parent, int x, int y, int w, int h) {
			super(parent, x, y, w, h);
			addAttribute(Attribute.MouseOver);
			addTooltip(I18N.localise(AnalystConstants.MUTATIONS_KEY + ".unknown.tooltip"));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			RenderUtil.drawText(getArea(), TextJustification.MIDDLE_CENTER, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".unknown"), 11184810);
		}
	}
}
