package binnie.botany.machines;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

import forestry.api.core.ForestryAPI;

import binnie.botany.machines.designer.PackageDesigner;
import binnie.botany.machines.designer.Tileworker;
import binnie.botany.modules.ModuleMachine;
import binnie.core.Constants;
import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.modules.BotanyModuleUIDs;

public enum BotanyMachine implements IMachineType {
	Tileworker(() -> {
		if (ForestryAPI.moduleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.CERAMIC)) {
			return new PackageDesigner(new Tileworker());
		}
		return null;
	});

	private final Supplier<MachinePackage> supplier;

	BotanyMachine(final Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return this.supplier;
	}

	public ItemStack get(final int i) {
		return new ItemStack(ModuleMachine.blockMachine, i, this.ordinal());
	}

}
