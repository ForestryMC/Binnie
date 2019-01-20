package binnie.extratrees.machines;

import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.core.Constants;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IInteraction;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.util.ModuleManager;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.brewery.BreweryMachine;
import binnie.extratrees.machines.designer.DesignerType;
import binnie.extratrees.machines.designer.PackageDesigner;
import binnie.extratrees.machines.distillery.DistilleryMachine;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import binnie.extratrees.modules.features.ExtraTreesMachines;

public enum ExtraTreeMachine implements IMachineType {
	LUMBERMILL(LumbermillMachine::new),
	WOODWORKER(() -> {
		if (ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY)) {
			return new PackageDesigner(DesignerType.Woodworker);
		}
		return null;
	}),
	PANELWORKER(() -> {
		if (ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY)) {
			return new PackageDesigner(DesignerType.Panelworker);
		}
		return null;
	}),
	NURSERY(() -> {
		// TODO: implement
		// binnie.extratrees.machines.nursery.Nursery.PackageNursery::new
		return null;
	}),
	PRESS(FruitPressMachine::new),
	BREWERY(BreweryMachine::new),
	DISTILLERY(DistilleryMachine::new),
	GLASSWORKER(() -> {
		if (ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY)) {
			return new PackageDesigner(DesignerType.GlassWorker);
		}
		return null;
	});

	private final Supplier<MachinePackage> supplier;

	ExtraTreeMachine(Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return this.supplier;
	}

	public ItemStack get(int i) {
		return ExtraTreesMachines.MACHINE.stack(i, ordinal());
	}

	public static class ComponentExtraTreeGUI extends MachineComponent implements IInteraction.RightClick {
		private final ExtraTreesGUID id;

		public ComponentExtraTreeGUI(Machine machine, ExtraTreesGUID id) {
			super(machine);
			this.id = id;
		}

		@Override
		public void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			if (!player.isSneaking()) {
				ExtraTrees.proxy.openGui(this.id, player, pos);
			}
		}
	}

	public abstract static class PackageExtraTreeMachine extends MachinePackage {

		protected PackageExtraTreeMachine(String uid) {
			super(uid);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

	}
}
