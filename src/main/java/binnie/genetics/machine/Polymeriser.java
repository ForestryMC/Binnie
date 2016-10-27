package binnie.genetics.machine;

import binnie.core.BinnieCore;
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
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import net.minecraft.client.particle.Particle;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class Polymeriser {
	public static final int tankBacteria = 0;
	public static final int tankDNA = 1;
	public static final int slotSerum = 0;
	public static final int slotGold = 1;
	public static final int[] slotSerumReserve = new int[]{2, 3, 4, 5};
	public static final int[] slotSerumFinished = new int[]{6, 7, 8, 9};

	public static class PackagePolymeriser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackagePolymeriser() {
			super("polymeriser", GeneticsTexture.Polymeriser, 58819, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Replicator);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(1, "catalyst");
			inventory.getSlot(1).setValidator(new SlotValidator.Item(new ItemStack(Items.GOLD_NUGGET, 1), ModuleMachine.IconNugget));
			inventory.getSlot(1).forbidExtraction();
			inventory.addSlot(0, "process");
			inventory.getSlot(0).setValidator(new SlotValidatorUnfilledSerum());
			inventory.getSlot(0).forbidInteraction();
			inventory.getSlot(0).setReadOnly();
			for (final InventorySlot slot : inventory.addSlotArray(Polymeriser.slotSerumReserve, "input")) {
				slot.setValidator(new SlotValidatorUnfilledSerum());
				slot.forbidExtraction();
			}
			for (final InventorySlot slot : inventory.addSlotArray(Polymeriser.slotSerumFinished, "output")) {
				slot.setReadOnly();
			}
			final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Polymeriser.slotSerumReserve, 0, 1);
			transfer.addStorage(0, Polymeriser.slotSerumFinished, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(final ItemStack stack) {
					return !stack.isItemDamaged();
				}
			});
			final ComponentTankContainer tank = new ComponentTankContainer(machine);
			tank.addTank(0, "input", 1000);
			tank.getTankSlot(0).setValidator(new Validator<FluidStack>() {
				@Override
				public boolean isValid(final FluidStack itemStack) {
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
				public boolean isValid(final FluidStack itemStack) {
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

		@Override
		public void register() {
		}
	}

	public static class ComponentPolymeriserLogic extends ComponentProcessSetCost implements IProcess {
		private static float chargePerProcess = 0.4f;
		private float dnaDrain;
		private float bacteriaDrain;

		public ComponentPolymeriserLogic(final Machine machine) {
			super(machine, 96000, 2400);
			this.dnaDrain = 0.0f;
			this.bacteriaDrain = 0.0f;
		}

		private float getCatalyst() {
			return (this.getUtil().getSlotCharge(1) > 0.0f) ? 0.2f : 1.0f;
		}

		@Override
		public int getProcessLength() {
			return (int) (super.getProcessLength() * this.getNumberOfGenes() * this.getCatalyst());
		}

		@Override
		public int getProcessEnergy() {
			return (int) (super.getProcessEnergy() * this.getNumberOfGenes() * this.getCatalyst());
		}

		private float getDNAPerProcess() {
			return this.getNumberOfGenes() * 50;
		}

		@Override
		public void onTickTask() {
			super.onTickTask();
			this.getUtil().useCharge(1, ComponentPolymeriserLogic.chargePerProcess * this.getProgressPerTick() / 100.0f);
			this.dnaDrain += this.getDNAPerProcess() * this.getProgressPerTick() / 100.0f;
			this.bacteriaDrain += 0.2f * this.getDNAPerProcess() * this.getProgressPerTick() / 100.0f;
			if (this.dnaDrain >= 1.0f) {
				this.getUtil().drainTank(1, 1);
				--this.dnaDrain;
			}
			if (this.bacteriaDrain >= 1.0f) {
				this.getUtil().drainTank(0, 1);
				--this.bacteriaDrain;
			}
		}

		private int getNumberOfGenes() {
			final ItemStack serum = this.getUtil().getStack(0);
			if (serum == null) {
				return 1;
			}
			return Engineering.getGenes(serum).length;
		}

		@Override
		public String getTooltip() {
			final int n = this.getNumberOfGenes();
			return "Replicating " + n + " genes" + ((n > 1) ? "s" : "");
		}

		@Override
		public ErrorState canWork() {
			if (this.getUtil().isSlotEmpty(0)) {
				return new ErrorState.NoItem("No item to replicate", 0);
			}
			if (!this.getUtil().getStack(0).isItemDamaged()) {
				return new ErrorState.InvalidItem("Item filled", 0);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (this.getUtil().getFluid(0) == null) {
				return new ErrorState.InsufficientLiquid("Insufficient Bacteria", 0);
			}
			if (this.getUtil().getFluid(1) == null) {
				return new ErrorState.InsufficientLiquid("Insufficient DNA", 1);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			this.getUtil().damageItem(0, -1);
		}
	}

	public static class SlotValidatorUnfilledSerum extends SlotValidator {
		public SlotValidatorUnfilledSerum() {
			super(ModuleMachine.IconSerum);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return itemStack.getItem() instanceof IItemChargable;
		}

		@Override
		public String getTooltip() {
			return "Unfilled Serum";
		}
	}

	public static class ComponentPolymeriserFX extends MachineComponent implements IRender.DisplayTick {
		public ComponentPolymeriserFX(final IMachine machine) {
			super(machine);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, BlockPos pos, Random rand) {
			final int tick = (int) (world.getTotalWorldTime() % 8L);
			if ((tick == 0 || tick == 3) && this.getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new Particle(world, pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5, 0.0, 0.0, 0.0) {
					double axisX = this.posX;
					double axisZ = this.posZ;
					double angle = 0.7 + (int) (this.worldObj.getTotalWorldTime() % 2L) * 3.1415;

					{
						this.axisX = 0.0;
						this.axisZ = 0.0;
						this.angle = 0.0;
						this.motionX = 0.0;
						this.motionZ = 0.0;
						this.motionY = -0.006;
						this.particleMaxAge = 140;
						this.particleGravity = 0.0f;
						this.field_190017_n = true;
						this.setRBGColorF(0.8f, 0.0f, 1.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						this.angle += 0.1;
						this.motionY = -0.006;
						double dist = 0.2;
						if (this.particleAge > 60) {
							if (this.particleAge > 120) {
								dist = 0.1;
							}
							else {
								dist = 0.2 - 0.1 * ((this.particleAge - 60.0f) / 60.0f);
							}
						}
						this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
						if (this.particleAge <= 40) {
							this.setAlphaF(this.particleAge / 40.0f);
						}
						if (this.particleAge > 80) {
							this.setRBGColorF(this.particleRed + (0.0f - this.particleRed) / 10.0f, this.particleGreen + (1.0f - this.particleGreen) / 10.0f, this.particleBlue + (1.0f - this.particleBlue) / 10.0f);
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
