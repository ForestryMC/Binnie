package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.EnumColor;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.genetics.BreedingSystem;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.apiculture.genetics.BeeDefinition;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collection;
import java.util.List;

public class AnalystPageMutations extends ControlAnalystPage {
	public AnalystPageMutations(IWidget parent, IArea area, IIndividual ind, boolean isMaster) {
		super(parent, area);
		setColor(3355392);
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Mutations").setColor(getColor());
		y += 18;
		BreedingSystem system = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
		List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
		IAlleleSpecies speciesCurrent = ind.getGenome().getPrimary();
		Collection<IMutation> resultant = system.getResultantMutations(speciesCurrent);
		Collection<IMutation> further = system.getFurtherMutations(speciesCurrent);
		if (ind instanceof IBee) {
			ItemStack hive = null;
			if (ind.getGenome().getPrimary() == ExtraBeeDefinition.WATER) {
				hive = new ItemStack(ExtraBees.hive, 1, 0);
			}
			if (ind.getGenome().getPrimary() == ExtraBeeDefinition.ROCK) {
				hive = new ItemStack(ExtraBees.hive, 1, 1);
			}
			if (ind.getGenome().getPrimary() == ExtraBeeDefinition.BASALT) {
				hive = new ItemStack(ExtraBees.hive, 1, 2);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.FOREST.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 1);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.MEADOWS.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 2);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.MODEST.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 3);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.TROPICAL.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 4);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.ENDED.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 5);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.WINTRY.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 6);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.MARSHY.getGenome().getPrimary()) {
				hive = new ItemStack(Mods.forestry.block("beehives"), 1, 7);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.STEADFAST.getGenome().getPrimary()) {
				hive = new ItemStack(Blocks.chest);
			}
			if (ind.getGenome().getPrimary() == BeeDefinition.VALIANT.getGenome().getPrimary()) {
				new ControlTextCentered(this, y, "Natural Habitat").setColor(getColor());
				y += 10;
				new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Found in any Hive").setColor(getColor());
				y += 22;
			} else if (ind.getGenome().getPrimary() == BeeDefinition.MONASTIC.getGenome().getPrimary()) {
				new ControlTextCentered(this, y, "Natural Habitat").setColor(getColor());
				y += 10;
				new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Bought from Villagers").setColor(getColor());
				y += 22;
			} else if (hive != null) {
				new ControlTextCentered(this, y, "Natural Habitat").setColor(getColor());
				y += 10;
				ControlItemDisplay display = new ControlItemDisplay(this, (w() - 16.0f) / 2.0f, y);
				if (ind.getGenome().getPrimary() == BeeDefinition.STEADFAST.getGenome().getPrimary()) {
					display.addTooltip("Dungeon Chests");
				} else {
					display.setTooltip();
				}
				display.setItemStack(hive);
				y += 24;
			}
		}
		float ox = (w() - 88.0f - 8.0f) / 2.0f;
		float dx = 0.0f;
		if (!resultant.isEmpty()) {
			if (resultant.size() == 1) {
				ox = (w() - 44.0f) / 2.0f;
			}
			new ControlTextCentered(this, y, "Resultant Mutations").setColor(getColor());
			y += 10;
			for (IMutation mutation : resultant) {
				float specificChance = getSpecificChance(ind, mutation, system);
				if (!isMaster && !isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44.0f, 16.0f);
				} else {
					new Control(this, ox + dx, y, 44.0f, 16.0f) {
						@Override
						public void initialise() {
							IAlleleSpecies species0 = mutation.getAllele0();
							IAlleleSpecies species2 = mutation.getAllele1();
							String comb = species0.getName() + " + " + species2.getName();
							addTooltip(comb);
							String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% Chance";
							if (specificChance != mutation.getBaseChance()) {
								chance = chance + getMutationColour(specificChance).getCode() + " (" + (int) specificChance + "% currently)";
							}
							addTooltip(chance);
							for (String s : mutation.getSpecialConditions()) {
								addTooltip(s);
							}
						}

						@Override
						public void onRenderBackground() {
							CraftGUI.Render.item(new IPoint(0.0f, 0.0f), system.getDefaultMember(mutation.getAllele0().getUID()));
							CraftGUI.Render.item(new IPoint(28.0f, 0.0f), system.getDefaultMember(mutation.getAllele1().getUID()));
							if (specificChance != mutation.getBaseChance()) {
								CraftGUI.Render.colour(getMutationColour(mutation.getBaseChance()).getColour());
								CraftGUI.Render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconAdd0.getIcon());
								CraftGUI.Render.colour(getMutationColour(specificChance).getColour());
								CraftGUI.Render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconAdd1.getIcon());
							} else {
								CraftGUI.Render.colour(getMutationColour(mutation.getBaseChance()).getColour());
								CraftGUI.Render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconAdd.getIcon());
							}
						}
					};
				}
				dx = 52.0f - dx;
				if (dx == 0.0f || resultant.size() == 1) {
					y += 18;
				}
			}
			if (dx != 0.0f && resultant.size() != 1) {
				y += 18;
			}
			y += 10;
		}
		ox = (w() - 88.0f - 8.0f) / 2.0f;
		dx = 0.0f;
		if (!further.isEmpty()) {
			if (further.size() == 1) {
				ox = (w() - 44.0f) / 2.0f;
			}
			new ControlTextCentered(this, y, "Further Mutations").setColor(getColor());
			y += 10;
			for (IMutation mutation : further) {
				IAllele speciesComb = mutation.getPartner(speciesCurrent);
				float specificChance2 = getSpecificChance(ind, mutation, system);
				if (!isMaster && !isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44.0f, 16.0f);
				} else {
					new Control(this, ox + dx, y, 44.0f, 16.0f) {
						@Override
						public void initialise() {
							IAlleleSpecies species0 = (IAlleleSpecies) speciesComb;
							IAlleleSpecies species2 = (IAlleleSpecies) mutation.getTemplate()[0];
							addTooltip(species2.getName());
							String comb = speciesCurrent.getName() + " + " + species0.getName();
							addTooltip(comb);
							String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% Chance";
							if (specificChance2 != mutation.getBaseChance()) {
								chance = chance + getMutationColour(specificChance2).getCode() + " (" + (int) specificChance2 + "% currently)";
							}
							addTooltip(chance);
							for (String s : mutation.getSpecialConditions()) {
								addTooltip(s);
							}
						}

						@Override
						public void onRenderBackground() {
							CraftGUI.Render.item(new IPoint(0.0f, 0.0f), system.getDefaultMember(speciesComb.getUID()));
							CraftGUI.Render.item(new IPoint(28.0f, 0.0f), system.getDefaultMember(mutation.getTemplate()[0].getUID()));
							CraftGUI.Render.colour(getMutationColour(mutation.getBaseChance()).getColour());
							if (specificChance2 != mutation.getBaseChance()) {
								CraftGUI.Render.colour(getMutationColour(mutation.getBaseChance()).getColour());
								CraftGUI.Render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconArrow0.getIcon());
								CraftGUI.Render.colour(getMutationColour(specificChance2).getColour());
								CraftGUI.Render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconArrow1.getIcon());
							} else {
								CraftGUI.Render.colour(getMutationColour(mutation.getBaseChance()).getColour());
								CraftGUI.Render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconArrow.getIcon());
							}
						}
					};
				}
				dx = 52.0f - dx;
				if (dx == 0.0f || further.size() == 1) {
					y += 18;
				}
			}
			if (dx != 0.0f && further.size() != 1) {
				y += 18;
			}
		}
		y += 8;
		setSize(new IPoint(w(), y));
	}

	private boolean isKnown(BreedingSystem system, IMutation mutation) {
		return system.getDiscoveredMutations(getWindow().getWorld(), getWindow().getPlayer().getGameProfile()).contains(mutation);
	}

	private float getSpecificChance(IIndividual ind, IMutation mutation, BreedingSystem system) {
		return system.getChance(mutation, getWindow().getPlayer(), mutation.getAllele0(), mutation.getAllele1());
	}

	@Override
	public String getTitle() {
		return "Mutations";
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
		public ControlUnknownMutation(IWidget parent, float x, float y, float w, float h) {
			super(parent, x, y, w, h);
			addAttribute(WidgetAttribute.MOUSE_OVER);
			addTooltip("Unknown Mutation");
		}

		@Override
		public void onRenderBackground() {
			CraftGUI.Render.text(getArea(), TextJustification.MiddleCenter, "UNKNOWN", 11184810);
		}
	}
}
