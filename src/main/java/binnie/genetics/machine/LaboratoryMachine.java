// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import java.util.List;
import binnie.extratrees.ExtraTrees;
import binnie.extrabees.ExtraBees;
import binnie.botany.Botany;
import net.minecraft.item.Item;
import java.util.ArrayList;
import buildcraft.api.tools.IToolWrench;
import binnie.core.BinnieCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.MachineComponent;
import net.minecraft.client.renderer.RenderBlocks;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.genetics.core.GeneticsTexture;
import net.minecraft.block.Block;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

public enum LaboratoryMachine implements IMachineType
{
	LabMachine(PackageLabMachine.class),
	Analyser(Analyser.PackageAnalyser.class),
	Incubator(Incubator.PackageIncubator.class),
	Genepool(Genepool.PackageGenepool.class),
	Acclimatiser(Acclimatiser.PackageAcclimatiser.class);

	Class<? extends MachinePackage> clss;

	private LaboratoryMachine(final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public ItemStack get(final int i) {
		return new ItemStack(Genetics.packageLabMachine.getBlock(), i, this.ordinal());
	}

	public static class PackageLabMachine extends GeneticMachine.PackageGeneticBase
	{
		public PackageLabMachine() {
			super("labMachine", GeneticsTexture.LabMachine, 16777215, false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentGUIHolder(machine);
		}

		@Override
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
			MachineRendererLab.instance.renderMachine(machine, this.colour, this.renderTexture, x, y, z, var8);
		}
	}

	public static class ComponentGUIHolder extends MachineComponent implements INetwork.TilePacketSync, IInteraction.RightClick
	{
		private ItemStack stack;

		public ComponentGUIHolder(final IMachine machine) {
			super(machine);
		}

		public ItemStack getStack() {
			return this.stack;
		}

		@Override
		public void readFromNBT(final NBTTagCompound nbttagcompound) {
			super.readFromNBT(nbttagcompound);
			if (nbttagcompound == null) {
				return;
			}
			this.stack = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("Item"));
		}

		@Override
		public void writeToNBT(final NBTTagCompound nbttagcompound) {
			super.writeToNBT(nbttagcompound);
			final NBTTagCompound nbt = new NBTTagCompound();
			if (this.stack != null) {
				this.stack.writeToNBT(nbt);
			}
			nbttagcompound.setTag("Item", nbt);
		}

		@Override
		public void syncToNBT(final NBTTagCompound nbt) {
			this.writeToNBT(nbt);
		}

		@Override
		public void syncFromNBT(final NBTTagCompound nbt) {
			this.readFromNBT(nbt);
		}

		@Override
		public void onDestruction() {
			super.onDestruction();
			if (this.stack != null) {
				final float f = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f3 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				if (this.stack.stackSize == 0) {
					this.stack.stackSize = 1;
				}
				final EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), this.getMachine().getTileEntity().xCoord + f, this.getMachine().getTileEntity().yCoord + f2, this.getMachine().getTileEntity().zCoord + f3, this.stack.copy());
				final float accel = 0.05f;
				entityitem.motionX = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				entityitem.motionY = (float) this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2f;
				entityitem.motionZ = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				this.getMachine().getWorld().spawnEntityInWorld(entityitem);
			}
		}

		@Override
		public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
			if (BinnieCore.proxy.isSimulating(world) && player.getHeldItem() != null) {
				if (this.stack != null && player.getHeldItem().getItem() instanceof IToolWrench) {
					final float f = 0.7f;
					final double d0 = world.rand.nextFloat() * f + (1.0f - f) * 0.5;
					final double d2 = world.rand.nextFloat() * f + (1.0f - f) * 0.5;
					final double d3 = world.rand.nextFloat() * f + (1.0f - f) * 0.5;
					final EntityItem entityitem = new EntityItem(world, x + d0, y + d2, z + d3, this.stack);
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
					this.stack = null;
					((IToolWrench) player.getHeldItem().getItem()).wrenchUsed(player, x, y, z);
					this.getUtil().refreshBlock();
					return;
				}
				final List<Item> validSelections = new ArrayList<Item>();
				if (BinnieCore.isBotanyActive()) {
					validSelections.add(Botany.database);
				}
				if (BinnieCore.isExtraBeesActive()) {
					validSelections.add(ExtraBees.dictionary);
				}
				if (BinnieCore.isExtraTreesActive()) {
					validSelections.add(ExtraTrees.itemDictionary);
				}
				if (BinnieCore.isLepidopteryActive()) {
					validSelections.add(ExtraTrees.itemDictionaryLepi);
				}
				validSelections.add(Genetics.database);
				validSelections.add(Genetics.analyst);
				validSelections.add(Genetics.registry);
				validSelections.add(Genetics.masterRegistry);
				validSelections.add(BinnieCore.genesis);
				if (this.stack == null && validSelections.contains(player.getHeldItem().getItem())) {
					this.stack = player.getHeldItem().copy();
					final ItemStack heldItem = player.getHeldItem();
					--heldItem.stackSize;
					world.markBlockForUpdate(x, y, z);
					return;
				}
				if (this.stack != null && player.getHeldItem().getItem() instanceof IToolWrench) {
					this.stack.getItem().onItemRightClick(this.stack, world, player);
				}
			}
			if (this.stack != null) {
				this.stack.getItem().onItemRightClick(this.stack, world, player);
			}
		}
	}
}
