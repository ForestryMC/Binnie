package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.genetics.Gene;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSequence;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;
import java.util.Random;

public class Isolator {
	public static final int slotEnzyme = 0;
	public static final int slotSequencerVial = 1;
	public static final int[] slotReserve = new int[]{2, 3, 4};
	public static final int slotTarget = 5;
	public static final int slotResult = 6;
	public static final int[] slotFinished = new int[]{7, 8, 9, 10, 11, 12};
	public static final int tankEthanol = 0;

	public static class PackageIsolator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageIsolator() {
			super("isolator", GeneticsTexture.Isolator, 16740111, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Isolator);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(0, "enzyme");
			inventory.getSlot(0).setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
			inventory.getSlot(0).forbidExtraction();
			inventory.addSlot(1, "sequencervial");
			inventory.getSlot(1).setValidator(new SlotValidator.Item(GeneticsItems.EmptySequencer.get(1), ModuleMachine.IconSequencer));
			inventory.getSlot(1).forbidExtraction();
			inventory.addSlotArray(Isolator.slotReserve, "input");
			for (final InventorySlot slot : inventory.getSlots(Isolator.slotReserve)) {
				slot.setValidator(new SlotValidator.Individual());
				slot.forbidExtraction();
			}
			inventory.addSlot(5, "process");
			inventory.getSlot(5).setValidator(new SlotValidator.Individual());
			inventory.getSlot(5).setReadOnly();
			inventory.getSlot(5).forbidInteraction();
			inventory.addSlot(6, "output");
			inventory.getSlot(6).setReadOnly();
			inventory.getSlot(6).forbidInteraction();
			inventory.addSlotArray(Isolator.slotFinished, "output");
			for (final InventorySlot slot : inventory.getSlots(Isolator.slotFinished)) {
				slot.setReadOnly();
				slot.forbidInsertion();
			}
			final ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(0, "input", 1000);
			tanks.getTankSlot(0).setValidator(new TankValidator() {
				@Override
				public String getTooltip() {
					return "Ethanol";
				}

				@Override
				public boolean isValid(final FluidStack stack) {
					return Objects.equals(stack.getFluid().getName(), "bioethanol");
				}
			});
			final ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
			chargedSlots.addCharge(0);
			final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Isolator.slotReserve, 5, 1);
			transfer.addStorage(6, Isolator.slotFinished);
			new ComponentPowerReceptor(machine, 20000);
			new ComponentIsolatorLogic(machine);
			new ComponentIsolaterFX(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class ComponentIsolatorLogic extends ComponentProcessSetCost implements IProcess {
		float enzymePerProcess;
		float ethanolPerProcess;

		public ComponentIsolatorLogic(final Machine machine) {
			super(machine, 192000, 4800);
			this.enzymePerProcess = 0.5f;
			this.ethanolPerProcess = 10.0f;
		}

		@Override
		public ErrorState canWork() {
			if (this.getUtil().isSlotEmpty(5)) {
				return new ErrorState.NoItem("No individual to isolate", 5);
			}
			if (!this.getUtil().isSlotEmpty(6)) {
				return new ErrorState.NoSpace("No room for DNA sequences", Isolator.slotFinished);
			}
			if (this.getUtil().isSlotEmpty(1)) {
				return new ErrorState.NoItem("No empty sequencer vials", 1);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!this.getUtil().liquidInTank(0, (int) this.ethanolPerProcess)) {
				return new ErrorState.InsufficientLiquid("Insufficient ethanol", 0);
			}
			if (this.getUtil().getSlotCharge(0) == 0.0f) {
				return new ErrorState.NoItem("No enzyme", 0);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			final Random rand = this.getMachine().getWorld().rand;
			final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(this.getUtil().getStack(5));
			if (root == null) {
				return;
			}
			final IIndividual individual = root.getMember(this.getUtil().getStack(5));
			if (individual == null) {
				return;
			}
			IAllele allele = null;
			IChromosomeType chromosome = null;
			Gene gene = null;
			if (this.getMachine().getWorld().rand.nextFloat() < 2.0f) {
				while (gene == null) {
					try {
						chromosome = root.getKaryotype()[rand.nextInt(root.getKaryotype().length)];
						allele = (rand.nextBoolean() ? individual.getGenome().getActiveAllele(chromosome) : individual.getGenome().getInactiveAllele(chromosome));
						gene = Gene.create(allele, chromosome, root);
					} catch (Exception e) {
					}
				}
			}
			final ItemStack serum = ItemSequence.create(gene);
			this.getUtil().setStack(6, serum);
			this.getUtil().decreaseStack(1, 1);
			if (rand.nextFloat() < 0.05f) {
				this.getUtil().decreaseStack(5, 1);
			}
			this.getUtil().drainTank(0, (int) this.ethanolPerProcess);
		}

		@Override
		protected void onTickTask() {
			this.getMachine().getInterface(IChargedSlots.class).alterCharge(0, -this.enzymePerProcess * this.getProgressPerTick() / 100.0f);
		}
	}

	public static class ComponentIsolaterFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
		public ComponentIsolaterFX(final IMachine machine) {
			super(machine);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, BlockPos pos, Random rand) {
			if (!this.getUtil().getProcess().isInProgress()) {
				return;
			}
			BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new Particle(world, pos.getX() + 0.4 + 0.2 * rand.nextDouble(), pos.getY() + 1.6, pos.getZ() + 0.4 + rand.nextDouble() * 0.2, 0.0, 0.0, 0.0) {
				double axisX = 0.0;
				double axisZ = 0.0;
				double angle = 0.0;

				{
					this.motionX = 0.0;
					this.motionZ = 0.0;
					this.motionY = -0.012;
					this.particleMaxAge = 100;
					this.particleGravity = 0.0f;
					this.field_190017_n = true;
					this.setRBGColorF(0.8f, 0.4f, 0.0f);
				}

				@Override
				public void onUpdate() {
					super.onUpdate();
					this.angle += 0.06;
					this.setAlphaF((float) Math.sin(3.14 * this.particleAge / this.particleMaxAge));
				}

				@Override
				public int getFXLayer() {
					return 0;
				}
			});
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, BlockPos pos, Random rand) {
			final int tick = (int) (world.getTotalWorldTime() % 6L);
			if ((tick == 0 || tick == 5) && this.getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new Particle(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0) {
					double axisX = this.posX;
					double axisZ = this.posZ;
					double angle = 0.7 + (int) (this.worldObj.getTotalWorldTime() % 2L) * 3.1415;

					{
						this.axisX = 0.0;
						this.axisZ = 0.0;
						this.angle = 0.0;
						this.motionX = 0.0;
						this.motionZ = 0.0;
						this.motionY = 0.012;
						this.particleMaxAge = 100;
						this.particleGravity = 0.0f;
						this.field_190017_n = true;
						this.setRBGColorF(0.8f, 0.0f, 1.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						this.angle += 0.06;
						this.setPosition(this.axisX + 0.26 * Math.sin(this.angle), this.posY, this.axisZ + 0.26 * Math.cos(this.angle));
						this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
					}

					@Override
					public int getFXLayer() {
						return 0;
					}
				});
			}
		}
	}
}
