package binnie.botany.farm;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
import binnie.core.util.I18N;

public class CircuitGarden extends BinnieCircuit {
	private boolean isManual;
	private GardenLogic logic;

	public CircuitGarden(EnumMoisture moisture, @Nullable EnumAcidity ph, boolean manual, boolean fertilised, ItemStack recipe, ItemStack icon) {
		super(getName(moisture, ph, manual, fertilised), 4, getLayout(manual), recipe);
		this.isManual = manual;
		this.logic = new GardenLogic(moisture, ph, this.isManual, fertilised, icon, I18N.localise(this.getUnlocalizedName()));
		StringBuilder info = new StringBuilder("Flowers (")
			.append(TextFormatting.GRAY.toString())
			.append(I18N.localise("botany.moisture"))
			.append(": ")
			.append(moisture.getLocalisedName(true));

		if (ph != null) {
			info.append(TextFormatting.GRAY.toString())
				.append(", ")
				.append(I18N.localise("botany.ph"))
				.append(": ")
				.append(ph.getLocalisedName(true));
		}
		String fertilizedKey = fertilised ? "for.binnie.circuit.garden.fertilized" : "for.binnie.circuit.garden.unfertilized";
		info.append(TextFormatting.RESET.toString())
			.append(" ")
			.append(I18N.localise(fertilizedKey))
			.append(")");
		this.addTooltipString(info.toString());
	}

	private static ICircuitLayout getLayout(boolean manual) {
		String layoutUid = manual ? "forestry.farms.manual" : "forestry.farms.managed";
		ICircuitLayout layout = ChipsetManager.circuitRegistry.getLayout(layoutUid);
		Preconditions.checkState(layout != null, "Couldn't find Forestry circuit layout %s.", layoutUid);
		return layout;
	}

	private static String getName(EnumMoisture moisture, @Nullable EnumAcidity ph, boolean manual, boolean fertilised) {
		String name = "garden." + moisture.getName();
		if (ph != null) {
			name += "." + ph.getName();
		}
		if (manual) {
			name += ".manual";
		}
		if (fertilised) {
			name += ".fert";
		}
		return name;
	}

	public CircuitGarden setManual() {
		isManual = true;
		return this;
	}

	@Override
	public boolean isCircuitable(Object tile) {
		return tile instanceof IFarmHousing;
	}

	@Nullable
	private IFarmHousing getCircuitable(Object tile) {
		if (!isCircuitable(tile)) {
			return null;
		}
		return (IFarmHousing) tile;
	}

	@Override
	public void onInsertion(int slot, Object tile) {
		IFarmHousing housing = getCircuitable(tile);
		if (housing == null) {
			return;
		}

		logic.setManual(isManual);

		housing.setFarmLogic(FarmDirection.values()[slot], logic);
	}

	@Override
	public void onLoad(int slot, Object tile) {
		onInsertion(slot, tile);
	}

	@Override
	public void onRemoval(int slot, Object tile) {
		IFarmHousing farmHousing = getCircuitable(tile);
		if (farmHousing == null) {
			return;
		}

		farmHousing.resetFarmLogic(FarmDirection.values()[slot]);
	}

	@Override
	public void onTick(int slot, Object tile) {
	}
}
