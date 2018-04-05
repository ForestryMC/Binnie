package binnie.botany.farming;

import binnie.botany.EnumHelper;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;

import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
import binnie.core.util.I18N;

public class CircuitGarden extends BinnieCircuit {
	private boolean isManual;
	private final GardenLogic logic;

	public CircuitGarden(EnumMoisture moisture, @Nullable EnumAcidity ph, boolean manual, boolean fertilised, ItemStack recipe, ItemStack icon) {
		super(getName(moisture, ph, manual, fertilised), 4, getLayout(manual), recipe);
		isManual = manual;
		logic = new GardenLogic(moisture, ph, isManual, fertilised, icon, I18N.localise(getUnlocalizedName()));
		String info = I18N.localise("botany.moisture") + ": " + EnumHelper.getLocalisedName(moisture, true) + TextFormatting.GRAY + ", ";
		if (ph != null) {
			info += I18N.localise("botany.ph") + ": " + EnumHelper.getLocalisedName(ph, true) + TextFormatting.GRAY + ", ";
		}
		String fertilizedKey = fertilised ? "for.binnie.circuit.garden.fertilized" : "for.binnie.circuit.garden.unfertilized";
		info += (fertilised ? TextFormatting.GREEN : TextFormatting.RED) + I18N.localise(fertilizedKey) + TextFormatting.GRAY;
		addTooltipString(I18N.localise("for.binnie.circuit.garden.info", info));
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
			name += '.' + ph.getName();
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
		// ignored
	}
}
