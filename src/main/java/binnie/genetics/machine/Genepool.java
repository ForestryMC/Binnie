package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.IMachineInformation;
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
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class Genepool {
	public static int slotBee = 0;
	public static int[] slotReserve;
	public static int tankDNA = 0;
	public static int tankEthanol = 1;
	public static int slotEnzyme = 7;

	static {
		slotReserve = new int[]{1, 2, 3, 4, 5, 6};
	}

	public static class PackageGenepool extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageGenepool() {
			super("genepool", GeneticsTexture.Genepool, 0xc134b6, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Genepool);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(7, "enzyme");
			inventory.getSlot(7).setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
			inventory.getSlot(7).forbidExtraction();
			inventory.addSlot(0, "process");
			inventory.getSlot(0).setValidator(new SlotValidator.Individual());
			inventory.getSlot(0).setReadOnly();
			inventory.getSlot(0).forbidExtraction();
			inventory.addSlotArray(Genepool.slotReserve, "input");

			for (InventorySlot slot : inventory.getSlots(Genepool.slotReserve)) {
				slot.setValidator(new SlotValidator.Individual());
				slot.forbidExtraction();
			}

			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(0, "output", 2000);
			tanks.getTankSlot(0).setReadOnly();
			tanks.addTank(1, "input", 1000);
			tanks.getTankSlot(1).forbidExtraction();
			tanks.getTankSlot(1).setValidator(new TankValidator() {
				@Override
				public String getTooltip() {
					return "Ethanol";
				}

				@Override
				public boolean isValid(FluidStack liquid) {
					return liquid.getFluid().getName() == "bioethanol";
				}
			});

			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Genepool.slotReserve, 0, 1);
			new ComponentPowerReceptor(machine, 1600);
			new ComponentGenepoolLogic(machine);
			ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
			chargedSlots.addCharge(7);
			new ComponentGenepoolFX(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentGenepoolLogic extends ComponentProcessSetCost implements IProcess {
		float enzymePerProcess;
		float ethanolPerProcess;
		private float ethanolDrain;

		public ComponentGenepoolLogic(Machine machine) {
			super(machine, 8000, 400);
			enzymePerProcess = 0.25f;
			ethanolPerProcess = 50.0f;
			ethanolDrain = 0.0f;
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(0)) {
				return new ErrorState.NoItem("No Individual", 0);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!getUtil().spaceInTank(0, getDNAAmount(getUtil().getStack(0)))) {
				return new ErrorState.NoSpace("Not enough room in Tank", new int[]{0});
			}
			if (!getUtil().liquidInTank(1, 1)) {
				return new ErrorState.InsufficientLiquid("Not enough Ethanol", 1);
			}
			if (getUtil().getSlotCharge(7) == 0.0f) {
				return new ErrorState.NoItem("Insufficient Enzyme", 7);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			int amount = getDNAAmount(getUtil().getStack(0));
			getUtil().fillTank(0, GeneticLiquid.RawDNA.get(amount));
			getUtil().deleteStack(0);
		}

		private int getDNAAmount(ItemStack stack) {
			ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
			if (root == null) {
				return 10;
			}
			if (root != Binnie.Genetics.getBeeRoot()) {
				return 10;
			}
			if (Binnie.Genetics.getBeeRoot().isDrone(stack)) {
				return 10;
			}
			if (Binnie.Genetics.getBeeRoot().isMated(stack)) {
				return 50;
			}
			return 30;
		}

		@Override
		protected void onTickTask() {
			ethanolDrain += getDNAAmount(getUtil().getStack(0)) * 1.2f * getProgressPerTick() / 100.0f;
			if (ethanolDrain >= 1.0f) {
				getUtil().drainTank(1, 1);
				ethanolDrain--;
			}
			getMachine().getInterface(IChargedSlots.class).alterCharge(7, -enzymePerProcess * getProgressPerTick() / 100.0f);
		}
	}

	public static class ComponentGenepoolFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
		public ComponentGenepoolFX(IMachine machine) {
			super(machine);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
			// ignored
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, int x, int y, int z, Random rand) {
			if (rand.nextFloat() < 1.0f && getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.3 + rand.nextDouble() * 0.4, y + 1, z + 0.3 + rand.nextDouble() * 0.4, 0.0, 0.0, 0.0) {
					double axisX = posX;
					double axisZ = posZ;
					double angle = rand.nextDouble() * 2.0 * 3.1415;

					{
						axisX = 0.0;
						axisZ = 0.0;
						angle = 0.0;
						motionX = 0.0;
						motionZ = 0.0;
						motionY = rand.nextFloat() * 0.01;
						particleMaxAge = 25;
						particleGravity = 0.0f;
						noClip = true;
						setRBGColorF(0.4f + 0.6f * rand.nextFloat(), 0.6f * rand.nextFloat(), 0.6f + 0.4f * rand.nextFloat());
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
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
