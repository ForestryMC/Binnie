package binnie.extratrees.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.Constants;
import binnie.botany.modules.BotanyModuleUIDs;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IInteraction;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.brewery.BreweryMachine;
import binnie.extratrees.machines.designer.Designer;
import binnie.extratrees.machines.distillery.DistilleryMachine;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import binnie.extratrees.modules.ExtraTreesModuleUIDs;
import binnie.modules.ModuleManager;

public enum ExtraTreeMachine implements IMachineType {
	Lumbermill(LumbermillMachine.class),
	Woodworker(Designer.PackageWoodworker.class),
	Panelworker(Designer.PackagePanelworker.class),
	Nursery(binnie.extratrees.machines.nursery.Nursery.PackageNursery.class),
	Press(FruitPressMachine.class),
	BREWERY(BreweryMachine.class),
	Distillery(DistilleryMachine.class),
	Glassworker(Designer.PackageGlassworker.class),
	Tileworker(Designer.PackageTileworker.class);

	Class<? extends MachinePackage> clss;

	ExtraTreeMachine(final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	@Override
	public boolean isActive() {
		if (this == ExtraTreeMachine.Tileworker) {
			return ModuleManager.isEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.CERAMIC);
		}
		if(this == Glassworker || this == Woodworker || this == Panelworker){
			return ModuleManager.isEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY);
		}
		return this != ExtraTreeMachine.Nursery;
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
			ExtraTrees.proxy.openGui(this.id, p1, pos);
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
