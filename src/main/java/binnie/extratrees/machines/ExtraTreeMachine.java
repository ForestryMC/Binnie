// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import net.minecraft.client.renderer.RenderBlocks;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.resource.ResourceType;
import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.MachineComponent;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;
import binnie.core.BinnieCore;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

public enum ExtraTreeMachine implements IMachineType
{
	Lumbermill(Lumbermill.PackageLumbermill.class),
	Woodworker(Designer.PackageWoodworker.class),
	Panelworker(Designer.PackagePanelworker.class),
	Nursery(Nursery.PackageNursery.class),
	Press(Press.PackagePress.class),
	Brewery(Brewery.PackageBrewery.class),
	Distillery(Distillery.PackageDistillery.class),
	Glassworker(Designer.PackageGlassworker.class),
	Tileworker(Designer.PackageTileworker.class);

	Class<? extends MachinePackage> clss;

	private ExtraTreeMachine(final Class<? extends MachinePackage> clss) {
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

	public static class ComponentExtraTreeGUI extends MachineComponent implements IInteraction.RightClick
	{
		ExtraTreesGUID id;

		public ComponentExtraTreeGUI(final Machine machine, final ExtraTreesGUID id) {
			super(machine);
			this.id = id;
		}

		@Override
		public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
			ExtraTrees.proxy.openGui(this.id, player, x, y, z);
		}
	}

	public abstract static class PackageExtraTreeMachine extends MachinePackage
	{
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
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float partialTick, final RenderBlocks renderer) {
			MachineRendererForestry.renderMachine(this.textureName.getShortPath(), x, y, z, partialTick);
		}
	}
}
