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

import binnie.core.Binnie;
import binnie.core.Mods;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.genetics.ForestryAllele;
import binnie.core.gui.Attribute;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.EnumColor;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.integration.extrabees.ExtraBeesIntegration;
import binnie.core.util.I18N;
import binnie.genetics.api.analyst.AnalystConstants;

public class AnalystPageMutations extends Control implements ITitledWidget {
	public AnalystPageMutations(IWidget parent, IArea area, IIndividual ind, boolean isMaster) {
		super(parent, area);
		setColor(3355392);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 18;
		IBreedingSystem system = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
		IAlleleSpecies speciesCurrent = ind.getGenome().getPrimary();
		Collection<IMutation> resultant = system.getResultantMutations(speciesCurrent);
		Collection<IMutation> further = system.getFurtherMutations(speciesCurrent);
		if (ind instanceof IBee) {
			ItemStack hive = getHive(speciesCurrent);
			if (speciesCurrent == ForestryAllele.BeeSpecies.Valiant.getAllele()) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.natural")).setColor(getColor());
				y += 10;
				new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.hive")).setColor(getColor());
				y += 22;
			} else if (speciesCurrent == ForestryAllele.BeeSpecies.Monastic.getAllele()) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.natural")).setColor(getColor());
				y += 10;
				new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.villager")).setColor(getColor());
				y += 22;
			} else if (!hive.isEmpty()) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.natural")).setColor(getColor());
				y += 10;
				ControlItemDisplay display = new ControlItemDisplay(this, (getWidth() - 16) / 2, y);
				if (speciesCurrent == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
					display.addTooltip(I18N.localise(AnalystConstants.MUTATIONS_KEY + ".bee.dungeon"));
				} else {
					display.setTooltip();
				}
				display.setItemStack(hive);
				y += 24;
			}
		}
		int ox = (getWidth() - 88 - 8) / 2;
		int dx = 0;
		if (!resultant.isEmpty()) {
			if (resultant.size() == 1) {
				ox = (getWidth() - 44) / 2;
			}
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".resultant")).setColor(getColor());
			y += 10;
			for (IMutation mutation : resultant) {
				float specificChance = getSpecificChance(ind, mutation, system);
				if (!isMaster && !isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44, 16);
				} else {
					new ControlResultantMutation(this, ox + dx, y, mutation, specificChance, system, mutation.getAllele0(), mutation.getAllele1());
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
		ox = (getWidth() - 88 - 8) / 2;
		dx = 0;
		if (!further.isEmpty()) {
			if (further.size() == 1) {
				ox = (getWidth() - 44) / 2;
			}
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".further")).setColor(getColor());
			y += 10;
			for (IMutation mutation : further) {
				IAllele speciesComb = mutation.getPartner(speciesCurrent);
				float specificChance = getSpecificChance(ind, mutation, system);
				if (!isMaster && !isKnown(system, mutation)) {
					new ControlUnknownMutation(this, ox + dx, y, 44, 16);
				} else {
					new ControlFurtherMutation(this, ox + dx, y, mutation, specificChance, system, speciesCurrent, (IAlleleSpecies) speciesComb, (IAlleleSpecies) mutation.getTemplate()[0]);
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
		setSize(new Point(getWidth(), y));
	}

	private boolean isKnown(IBreedingSystem system, IMutation mutation) {
		return system.getDiscoveredMutations(getWindow().getWorld(), getWindow().getPlayer().getGameProfile()).contains(mutation);
	}

	private float getSpecificChance(IIndividual ind, IMutation mutation, IBreedingSystem system) {
		return system.getChance(mutation, getWindow().getPlayer(), mutation.getAllele0(), mutation.getAllele1());
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.MUTATIONS_KEY + ".title");
	}

	protected EnumColor getMutationColour(float percent) {
		if (percent >= 20.0f) {
			return EnumColor.DARK_GREEN;
		}
		if (percent >= 15.0f) {
			return EnumColor.GREEN;
		}
		if (percent >= 10.0f) {
			return EnumColor.YELLOW;
		}
		if (percent >= 5.0f) {
			return EnumColor.GOLD;
		}
		if (percent > 0.0f) {
			return EnumColor.RED;
		}
		return EnumColor.DARK_RED;
	}

	protected ItemStack getHive(IAlleleSpecies speciesCurrent) {
		if (ExtraBeesIntegration.isLoaded()) {
			ItemStack hive = ExtraBeesIntegration.getHive(speciesCurrent);
			if (!hive.isEmpty()) {
				return hive;
			}
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Forest.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 1);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Meadows.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 2);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Modest.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 3);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Tropical.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 4);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Ended.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 5);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Wintry.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 6);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Marshy.getAllele()) {
			return new ItemStack(Mods.Forestry.block("beehives"), 1, 7);
		}
		if (speciesCurrent == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
			return new ItemStack(Blocks.CHEST);
		}
		return ItemStack.EMPTY;
	}

	static class ControlUnknownMutation extends Control {
		public ControlUnknownMutation(IWidget parent, int x, int y, int w, int h) {
			super(parent, x, y, w, h);
			addAttribute(Attribute.MOUSE_OVER);
			addTooltip(I18N.localise(AnalystConstants.MUTATIONS_KEY + ".unknown.tooltip"));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			RenderUtil.drawText(getArea(), TextJustification.MIDDLE_CENTER, I18N.localise(AnalystConstants.MUTATIONS_KEY + ".unknown"), 11184810);
		}
	}
}
