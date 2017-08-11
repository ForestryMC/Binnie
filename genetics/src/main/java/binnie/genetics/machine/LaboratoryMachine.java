package binnie.genetics.machine;

import javax.annotation.Nonnull;

import java.util.function.Supplier;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.core.machines.IMachine;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.network.INetwork;
import binnie.genetics.Genetics;
import binnie.genetics.machine.acclimatiser.PackageAcclimatiser;
import binnie.genetics.machine.analyser.PackageAnalyser;
import binnie.genetics.machine.genepool.PackageGenepool;
import binnie.genetics.machine.incubator.PackageIncubator;

public enum LaboratoryMachine implements IMachineType {
	LabMachine(PackageLabMachine::new),
	Analyser(PackageAnalyser::new),
	Incubator(PackageIncubator::new),
	Genepool(PackageGenepool::new),
	Acclimatiser(PackageAcclimatiser::new);

	private final Supplier<MachinePackage> supplier;

	LaboratoryMachine(final Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public ItemStack get(final int amount) {
		Genetics.machine();
		return new ItemStack(ModuleMachine.packageLabMachine.getBlock(), amount, this.ordinal());
	}

	public static class PackageLabMachine extends GeneticMachine.PackageGeneticBase {
		public PackageLabMachine() {
			super("lab_machine", 16777215, false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentGUIHolder(machine);
		}
	}

	public static class ComponentGUIHolder extends MachineComponent implements INetwork.TilePacketSync, IInteraction.RightClick {
		@Nonnull
		private ItemStack stack = ItemStack.EMPTY;

		public ComponentGUIHolder(final IMachine machine) {
			super(machine);
		}

		public ItemStack getStack() {
			return this.stack;
		}

		@Override
		public void readFromNBT(final NBTTagCompound nbttagcompound) {
			super.readFromNBT(nbttagcompound);
			this.stack = new ItemStack(nbttagcompound.getCompoundTag("Item"));
		}

		@Override
		public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound1) {
			NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound1);
			final NBTTagCompound nbt = new NBTTagCompound();
			if (!this.stack.isEmpty()) {
				this.stack.writeToNBT(nbt);
			}
			nbttagcompound.setTag("Item", nbt);
			return nbttagcompound;
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
			if (!this.stack.isEmpty()) {
				final float f = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f3 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				if (this.stack.getCount() == 0) {
					this.stack.setCount(1);
				}
				final EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), this.getMachine().getTileEntity().getPos().getX() + f, this.getMachine().getTileEntity().getPos().getY() + f2, this.getMachine().getTileEntity().getPos().getZ() + f3, this.stack.copy());
				final float accel = 0.05f;
				entityitem.motionX = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				entityitem.motionY = (float) this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2f;
				entityitem.motionZ = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				this.getMachine().getWorld().spawnEntity(entityitem);
			}
		}

		@Override
		public void onRightClick(World p0, EntityPlayer p1, BlockPos pos) {

		}
		/*@Override
		public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
			if (BinnieCore.proxy.isSimulating(world) && player.getHeldItem() != null) {
				if (!this.stack.isEmpty() && player.getHeldItem().getItem() instanceof IToolWrench) {
					final float f = 0.7f;
					final double d0 = world.rand.nextFloat() * f + (1.0f - f) * 0.5;
					final double d2 = world.rand.nextFloat() * f + (1.0f - f) * 0.5;
					final double d3 = world.rand.nextFloat() * f + (1.0f - f) * 0.5;
					final EntityItem entityitem = new EntityItem(world, x + d0, y + d2, z + d3, this.stack);
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntity(entityitem);
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
				if (this.stack.isEmpty() && validSelections.contains(player.getHeldItem().getItem())) {
					this.stack = player.getHeldItem().copy();
					final ItemStack heldItem = player.getHeldItem();
					heldItem.shrink(1);
					world.markBlockForUpdate(x, y, z);
					return;
				}
				if (!this.stack.isEmpty() && player.getHeldItem().getItem() instanceof IToolWrench) {
					this.stack.getItem().onItemRightClick(this.stack, world, player);
				}
			}
			if (!this.stack.isEmpty()) {
				this.stack.getItem().onItemRightClick(this.stack, world, player);
			}
		}*/
	}
}
