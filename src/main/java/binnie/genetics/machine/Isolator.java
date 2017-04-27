package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.IMachineInformation;
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
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSequence;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class Isolator {
	public static int slotEnzyme = 0;
	public static int slotSequencerVial = 1;
	public static int[] slotReserve = new int[]{2, 3, 4};
	public static int[] slotFinished = new int[]{7, 8, 9, 10, 11, 12};
	public static int slotTarget = 5;
	public static int slotResult = 6;
	public static int tankEthanol = 0;

	public static class PackageIsolator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageIsolator() {
			super("isolator", GeneticsTexture.Isolator, 16740111, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Isolator);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(0, "enzyme");
			inventory.getSlot(0).setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
			inventory.getSlot(0).forbidExtraction();
			inventory.addSlot(1, "sequencervial");
			inventory.getSlot(1).setValidator(new SlotValidator.Item(GeneticsItems.EmptySequencer.get(1), ModuleMachine.IconSequencer));
			inventory.getSlot(1).forbidExtraction();
			inventory.addSlotArray(Isolator.slotReserve, "input");
			for (InventorySlot slot : inventory.getSlots(Isolator.slotReserve)) {
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
			for (InventorySlot slot : inventory.getSlots(Isolator.slotFinished)) {
				slot.setReadOnly();
				slot.forbidInsertion();
			}

			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(0, "input", 1000);
			tanks.getTankSlot(0).setValidator(new TankValidator() {
				@Override
				public String getTooltip() {
					return "Ethanol";
				}

				@Override
				public boolean isValid(FluidStack liquid) {
					return liquid.getFluid().getName() == "bioethanol";
				}
			});

			ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
			chargedSlots.addCharge(0);
			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
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
	}

	public static class ComponentIsolatorLogic extends ComponentProcessSetCost implements IProcess {
		protected float enzymePerProcess;
		protected float ethanolPerProcess;

		public ComponentIsolatorLogic(Machine machine) {
			super(machine, 192000, 4800);
			enzymePerProcess = 0.5f;
			ethanolPerProcess = 10.0f;
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(5)) {
				return new ErrorState.NoItem("No individual to isolate", 5);
			}
			if (!getUtil().isSlotEmpty(6)) {
				return new ErrorState.NoSpace("No room for DNA sequences", Isolator.slotFinished);
			}
			if (getUtil().isSlotEmpty(1)) {
				return new ErrorState.NoItem("No empty sequencer vials", 1);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!getUtil().liquidInTank(0, (int) ethanolPerProcess)) {
				return new ErrorState.InsufficientLiquid("Insufficient ethanol", 0);
			}
			if (getUtil().getSlotCharge(0) == 0.0f) {
				return new ErrorState.NoItem("No enzyme", 0);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			Random rand = getMachine().getWorld().rand;
			ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(getUtil().getStack(5));
			if (root == null) {
				return;
			}

			IIndividual individual = root.getMember(getUtil().getStack(5));
			if (individual == null) {
				return;
			}

			IAllele allele;
			IChromosomeType chromosome;
			Gene gene = null;
			if (getMachine().getWorld().rand.nextFloat() < 2.0f) {
				while (gene == null) {
					try {
						chromosome = root.getKaryotype()[rand.nextInt(root.getKaryotype().length)];
						allele = rand.nextBoolean() ?
							individual.getGenome().getActiveAllele(chromosome) :
							individual.getGenome().getInactiveAllele(chromosome);
						gene = Gene.create(allele, chromosome, root);
					} catch (Exception e) {
						// ignored
					}
				}
			}

			ItemStack serum = ItemSequence.create(gene);
			getUtil().setStack(6, serum);
			getUtil().decreaseStack(1, 1);
			if (rand.nextFloat() < 0.05f) {
				getUtil().decreaseStack(5, 1);
			}
			getUtil().drainTank(0, (int) ethanolPerProcess);
		}

		@Override
		protected void onTickTask() {
			getMachine().getInterface(IChargedSlots.class).alterCharge(0, -enzymePerProcess * getProgressPerTick() / 100.0f);
		}
	}

	public static class ComponentIsolaterFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
		public ComponentIsolaterFX(IMachine machine) {
			super(machine);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
			if (!getUtil().getProcess().isInProgress()) {
				return;
			}
			BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.4 + 0.2 * rand.nextDouble(), y + 1.6, z + 0.4 + rand.nextDouble() * 0.2, 0.0, 0.0, 0.0) {
				double axisX = 0.0;
				double axisZ = 0.0;
				double angle = 0.0;

				{
					motionX = 0.0;
					motionZ = 0.0;
					motionY = -0.012;
					particleMaxAge = 100;
					particleGravity = 0.0f;
					noClip = true;
					setRBGColorF(0.8f, 0.4f, 0.0f);
				}

				@Override
				public void onUpdate() {
					super.onUpdate();
					angle += 0.06;
					setAlphaF((float) Math.sin(3.14 * particleAge / particleMaxAge));
				}

				@Override
				public int getFXLayer() {
					return 0;
				}
			});
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, int x, int y, int z, Random rand) {
			int tick = (int) (world.getTotalWorldTime() % 6L);
			if ((tick == 0 || tick == 5) && getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0) {
					double axisX = posX;
					double axisZ = posZ;
					double angle = 0.7 + (int) (worldObj.getTotalWorldTime() % 2L) * 3.1415;

					{
						axisX = 0.0;
						axisZ = 0.0;
						angle = 0.0;
						motionX = 0.0;
						motionZ = 0.0;
						motionY = 0.012;
						particleMaxAge = 100;
						particleGravity = 0.0f;
						noClip = true;
						setRBGColorF(0.8f, 0.0f, 1.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						angle += 0.06;
						setPosition(axisX + 0.26 * Math.sin(angle), posY, axisZ + 0.26 * Math.cos(angle));
						setAlphaF((float) Math.cos(1.57 * particleAge / particleMaxAge));
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
