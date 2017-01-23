package binnie.extratrees.machines;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IInteraction;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.brewery.BreweryMachine;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum ExtraTreeMachine implements IMachineType {
	Lumbermill(LumbermillMachine.class),
	Woodworker(Designer.PackageWoodworker.class),
	Panelworker(Designer.PackagePanelworker.class),
	Nursery(Nursery.PackageNursery.class),
	Press(FruitPressMachine.class),
	Brewery(BreweryMachine.class),
	Distillery(Distillery.PackageDistillery.class),
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
			return BinnieCore.isBotanyActive();
		}
		return this != ExtraTreeMachine.Nursery;
	}

	public ItemStack get(final int i) {
		return new ItemStack(ExtraTrees.blockMachine, i, this.ordinal());
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
		BinnieResource textureName;

		protected PackageExtraTreeMachine(final String uid, final String textureName, final boolean powered) {
			super(uid, powered);
			this.textureName = Binnie.Resource.getFile(ExtraTrees.instance, ResourceType.Tile, textureName);
		}

		protected PackageExtraTreeMachine(final String uid, final BinnieResource textureName, final boolean powered) {
			super(uid, powered);
			this.textureName = textureName;
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
		
		@Override
		public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
			MachineRendererForestry.renderMachine(this.textureName.getShortPath(), x, y, z, partialTicks);
		}
	}
}
