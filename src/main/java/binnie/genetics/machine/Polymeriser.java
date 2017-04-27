package binnie.genetics.machine;

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
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.Validator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class Polymeriser {
	public static int tankBacteria = 0;
	public static int tankDNA = 1;
	public static int slotSerum = 0;
	public static int slotGold = 1;
	public static int[] slotSerumReserve = new int[]{2, 3, 4, 5};
	public static int[] slotSerumFinished = new int[]{6, 7, 8, 9};

	public static class PackagePolymeriser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackagePolymeriser() {
			super("polymeriser", GeneticsTexture.Polymeriser, 58819, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Replicator);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(1, "catalyst");
			inventory.getSlot(1).setValidator(new SlotValidator.Item(new ItemStack(Items.gold_nugget, 1), ModuleMachine.IconNugget));
			inventory.getSlot(1).forbidExtraction();

			inventory.addSlot(0, "process");
			inventory.getSlot(0).setValidator(new SlotValidatorUnfilledSerum());
			inventory.getSlot(0).forbidInteraction();
			inventory.getSlot(0).setReadOnly();

			for (InventorySlot slot : inventory.addSlotArray(Polymeriser.slotSerumReserve, "input")) {
				slot.setValidator(new SlotValidatorUnfilledSerum());
				slot.forbidExtraction();
			}

			for (InventorySlot slot : inventory.addSlotArray(Polymeriser.slotSerumFinished, "output")) {
				slot.setReadOnly();
			}

			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Polymeriser.slotSerumReserve, 0, 1);
			transfer.addStorage(0, Polymeriser.slotSerumFinished, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(ItemStack stack) {
					return !stack.isItemDamaged();
				}
			});

			ComponentTankContainer tank = new ComponentTankContainer(machine);
			tank.addTank(0, "input", 1000);
			tank.getTankSlot(0).setValidator(new Validator<FluidStack>() {
				@Override
				public boolean isValid(FluidStack itemStack) {
					return GeneticLiquid.BacteriaPoly.get(1).isFluidEqual(itemStack);
				}

				@Override
				public String getTooltip() {
					return "Polymerising Bacteria";
				}
			});

			tank.addTank(1, "input", 1000);
			tank.getTankSlot(1).setValidator(new Validator<FluidStack>() {
				@Override
				public boolean isValid(FluidStack itemStack) {
					return GeneticLiquid.RawDNA.get(1).isFluidEqual(itemStack);
				}

				@Override
				public String getTooltip() {
					return "Raw DNA";
				}
			});

			new ComponentChargedSlots(machine).addCharge(1);
			new ComponentPowerReceptor(machine, 8000);
			new ComponentPolymeriserLogic(machine);
			new ComponentPolymeriserFX(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentPolymeriserLogic extends ComponentProcessSetCost implements IProcess {
		private static float chargePerProcess = 0.4f;

		private float dnaDrain;
		private float bacteriaDrain;

		public ComponentPolymeriserLogic(Machine machine) {
			super(machine, 96000, 2400);
			dnaDrain = 0.0f;
			bacteriaDrain = 0.0f;
		}

		private float getCatalyst() {
			return (getUtil().getSlotCharge(1) > 0.0f) ? 0.2f : 1.0f;
		}

		@Override
		public int getProcessLength() {
			return (int) (super.getProcessLength() * getNumberOfGenes() * getCatalyst());
		}

		@Override
		public int getProcessEnergy() {
			return (int) (super.getProcessEnergy() * getNumberOfGenes() * getCatalyst());
		}

		private float getDNAPerProcess() {
			return getNumberOfGenes() * 50;
		}

		@Override
		public void onTickTask() {
			super.onTickTask();
			getUtil().useCharge(1, ComponentPolymeriserLogic.chargePerProcess * getProgressPerTick() / 100.0f);
			dnaDrain += getDNAPerProcess() * getProgressPerTick() / 100.0f;
			bacteriaDrain += 0.2f * getDNAPerProcess() * getProgressPerTick() / 100.0f;

			if (dnaDrain >= 1.0f) {
				getUtil().drainTank(1, 1);
				dnaDrain--;
			}

			if (bacteriaDrain >= 1.0f) {
				getUtil().drainTank(0, 1);
				bacteriaDrain--;
			}
		}

		private int getNumberOfGenes() {
			ItemStack serum = getUtil().getStack(0);
			if (serum == null) {
				return 1;
			}
			return Engineering.getGenes(serum).length;
		}

		@Override
		public String getTooltip() {
			int n = getNumberOfGenes();
			return "Replicating " + n + " genes" + ((n > 1) ? "s" : "");
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(0)) {
				return new ErrorState.NoItem("No item to replicate", 0);
			}
			if (!getUtil().getStack(0).isItemDamaged()) {
				return new ErrorState.InvalidItem("Item filled", 0);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (getUtil().getFluid(0) == null) {
				return new ErrorState.InsufficientLiquid("Insufficient Bacteria", 0);
			}
			if (getUtil().getFluid(1) == null) {
				return new ErrorState.InsufficientLiquid("Insufficient DNA", 1);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			getUtil().damageItem(0, -1);
		}
	}

	public static class SlotValidatorUnfilledSerum extends SlotValidator {
		public SlotValidatorUnfilledSerum() {
			super(ModuleMachine.IconSerum);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return itemStack.getItem() instanceof IItemChargable;
		}

		@Override
		public String getTooltip() {
			return "Unfilled Serum";
		}
	}

	public static class ComponentPolymeriserFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
		public ComponentPolymeriserFX(IMachine machine) {
			super(machine);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, int x, int y, int z, Random rand) {
			int tick = (int) (world.getTotalWorldTime() % 8L);
			if ((tick == 0 || tick == 3) && getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.8, z + 0.5, 0.0, 0.0, 0.0) {
					double axisX = posX;
					double axisZ = posZ;
					double angle = 0.7 + (int) (worldObj.getTotalWorldTime() % 2L) * 3.1415;

					{
						axisX = 0.0;
						axisZ = 0.0;
						angle = 0.0;
						motionX = 0.0;
						motionZ = 0.0;
						motionY = -0.006;
						particleMaxAge = 140;
						particleGravity = 0.0f;
						noClip = true;
						setRBGColorF(0.8f, 0.0f, 1.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						angle += 0.1;
						motionY = -0.006;
						double dist = 0.2;
						if (particleAge > 60) {
							if (particleAge > 120) {
								dist = 0.1;
							} else {
								dist = 0.2 - 0.1 * ((particleAge - 60.0f) / 60.0f);
							}
						}

						setPosition(axisX + dist * Math.sin(angle), posY, axisZ + dist * Math.cos(angle));
						if (particleAge <= 40) {
							setAlphaF(particleAge / 40.0f);
						}
						if (particleAge > 80) {
							setRBGColorF(particleRed + (0.0f - particleRed) / 10.0f, particleGreen + (1.0f - particleGreen) / 10.0f, particleBlue + (1.0f - particleBlue) / 10.0f);
						}
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
