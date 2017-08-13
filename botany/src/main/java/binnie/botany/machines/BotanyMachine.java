package binnie.botany.machines;

import java.util.function.Supplier;

import binnie.botany.Botany;
import binnie.botany.machines.designer.Tileworker;
import binnie.botany.modules.ModuleMachine;
import binnie.core.Constants;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IInteraction;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.modules.ModuleManager;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.designer.PackageDesigner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum BotanyMachine implements IMachineType {
	Tileworker(() -> {
		if (ModuleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.CERAMIC)) {
			return new PackageDesigner(new Tileworker());
		}
		return null;
	});

	Supplier<MachinePackage> supplier;

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

	public static class ComponentExtraTreeGUI extends MachineComponent implements IInteraction.RightClick {
		ExtraTreesGUID id;

		public ComponentExtraTreeGUI(final Machine machine, final ExtraTreesGUID id) {
			super(machine);
			this.id = id;
		}

		@Override
		public void onRightClick(World p0, EntityPlayer p1, BlockPos pos) {
			Botany.proxy.openGui(this.id, p1, pos);
		}
	}

	public abstract static class PackageExtraTreeMachine extends MachinePackage {

		protected PackageExtraTreeMachine(final String uid, final boolean powered) {
			super(uid, powered);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}
}
