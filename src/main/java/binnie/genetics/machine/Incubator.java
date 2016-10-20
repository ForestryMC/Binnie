// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import binnie.core.machines.transfer.TransferRequest;
import binnie.core.machines.power.ErrorState;
import java.util.Random;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.genetics.core.GeneticsGUI;
import binnie.core.machines.Machine;
import binnie.genetics.core.GeneticsTexture;
import binnie.craftgui.minecraft.IMachineInformation;
import java.util.ArrayList;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IIndividual;
import binnie.core.machines.MachineUtil;
import forestry.api.apiculture.EnumBeeType;
import binnie.core.BinnieCore;
import net.minecraft.init.Items;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import binnie.genetics.item.GeneticLiquid;
import binnie.Binnie;
import binnie.genetics.api.IIncubatorRecipe;
import java.util.List;

public class Incubator
{
	public static final int[] slotQueue;
	public static final int slotIncubator = 3;
	public static final int[] slotOutput;
	public static final int tankInput = 0;
	public static final int tankOutput = 1;
	private static List<IIncubatorRecipe> RECIPES;

	public static void addRecipes() {
		Incubator.RECIPES.add(new IncubatorRecipe(Binnie.Liquid.getLiquidStack("water", 25), GeneticLiquid.GrowthMedium.get(25), 0.2f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.GrowthMedium.get(25), GeneticLiquid.Bacteria.get(5), 0.2f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return new ItemStack(Items.wheat).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(0), GeneticLiquid.Bacteria.get(5), 0.05f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(2), null, 0.5f, 0.2f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return stack.getItem() == Items.sugar;
			}
		}.setOutputStack(GeneticsItems.Enzyme.get(1)));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.BacteriaPoly.get(0), GeneticLiquid.BacteriaPoly.get(5), 0.05f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.BacteriaVector.get(0), GeneticLiquid.BacteriaVector.get(5), 0.05f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaPoly.get(10), 0.1f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return new ItemStack(Items.dye, 1, 15).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaVector.get(10), 0.05f) {
			@Override
			public boolean isItemStack(final ItemStack stack) {
				return new ItemStack(Items.blaze_powder).isItemEqual(stack);
			}
		});
		if (BinnieCore.isApicultureActive()) {
			Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.GrowthMedium.get(50), null, 1.0f, 0.05f) {
				@Override
				public boolean isItemStack(final ItemStack stack) {
					return Binnie.Genetics.getBeeRoot().isMember(stack) && Binnie.Genetics.getBeeRoot().getType(stack) == EnumBeeType.LARVAE;
				}

				@Override
				public ItemStack getOutputStack(final MachineUtil machine) {
					final ItemStack larvae = machine.getStack(3);
					final IBee bee = Binnie.Genetics.getBeeRoot().getMember(larvae);
					return Binnie.Genetics.getBeeRoot().getMemberStack(bee, EnumBeeType.DRONE.ordinal());
				}
			});
		}
	}

	static {
		slotQueue = new int[] { 0, 1, 2 };
		slotOutput = new int[] { 4, 5, 6 };
		Incubator.RECIPES = new ArrayList<IIncubatorRecipe>();
	}

	public static class PackageIncubator extends GeneticMachine.PackageGeneticBase implements IMachineInformation
	{
		public PackageIncubator() {
			super("incubator", GeneticsTexture.Incubator, 16767313, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Incubator);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlotArray(Incubator.slotQueue, "input");
			for (final InventorySlot slot : inventory.getSlots(Incubator.slotQueue)) {
				slot.forbidExtraction();
			}
			inventory.addSlot(3, "incubator");
			inventory.getSlot(3).forbidInteraction();
			inventory.getSlot(3).setReadOnly();
			inventory.addSlotArray(Incubator.slotOutput, "output");
			for (final InventorySlot slot : inventory.getSlots(Incubator.slotOutput)) {
				slot.forbidInsertion();
				slot.setReadOnly();
			}
			new ComponentPowerReceptor(machine, 2000);
			final ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(0, "input", 2000).forbidExtraction();
			tanks.addTank(1, "output", 2000).setReadOnly();
			new ComponentIncubatorLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class ComponentIncubatorLogic extends ComponentProcessIndefinate implements IProcess
	{
		IIncubatorRecipe recipe;
		private Random rand;
		private boolean roomForOutput;

		public ComponentIncubatorLogic(final Machine machine) {
			super(machine, 2.0f);
			this.recipe = null;
			this.rand = new Random();
			this.roomForOutput = true;
		}

		@Override
		public ErrorState canWork() {
			if (this.recipe == null) {
				return new ErrorState("No Recipe", "There is no valid recipe");
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (this.recipe != null) {
				if (!this.recipe.isInputLiquidSufficient(this.getUtil().getFluid(0))) {
					return new ErrorState.InsufficientLiquid("Not enough incubation liquid", 0);
				}
				if (!this.roomForOutput) {
					return new ErrorState.TankSpace("No room for output", 1);
				}
			}
			return super.canProgress();
		}

		@Override
		protected void onTickTask() {
			if (this.rand.nextInt(20) == 0 && this.recipe != null && this.rand.nextFloat() < this.recipe.getChance()) {
				this.recipe.doTask(this.getUtil());
			}
		}

		@Override
		public boolean inProgress() {
			return this.recipe != null;
		}

		private IIncubatorRecipe getRecipe(final ItemStack stack, final FluidStack liquid) {
			for (final IIncubatorRecipe recipe : Incubator.RECIPES) {
				final boolean rightLiquid = recipe.isInputLiquid(liquid);
				final boolean rightItem = recipe.isItemStack(stack);
				if (rightLiquid && rightItem) {
					return recipe;
				}
			}
			return null;
		}

		@Override
		public void onInventoryUpdate() {
			super.onInventoryUpdate();
			if (!this.getUtil().isServer()) {
				return;
			}
			final FluidStack liquid = this.getUtil().getFluid(0);
			final ItemStack incubator = this.getUtil().getStack(3);
			if (this.recipe != null && (incubator == null || liquid == null || !this.recipe.isInputLiquid(liquid) || !this.recipe.isItemStack(incubator))) {
				this.recipe = null;
				final ItemStack leftover = new TransferRequest(incubator, this.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation().transfer(true);
				this.getUtil().setStack(3, leftover);
			}
			if (this.recipe == null) {
				if (liquid == null) {
					return;
				}
				if (incubator != null) {
					final IIncubatorRecipe recipe = this.getRecipe(incubator, liquid);
					if (recipe != null) {
						this.recipe = recipe;
						return;
					}
				}
				IIncubatorRecipe potential = null;
				int potentialSlot = 0;
				for (final int slot : Incubator.slotQueue) {
					final ItemStack stack = this.getUtil().getStack(slot);
					if (stack != null) {
						if (potential == null) {
							for (final IIncubatorRecipe recipe2 : Incubator.RECIPES) {
								final boolean rightLiquid = recipe2.isInputLiquid(liquid);
								final boolean rightItem = recipe2.isItemStack(stack);
								if (rightLiquid && rightItem) {
									potential = recipe2;
									potentialSlot = slot;
									break;
								}
							}
						}
					}
				}
				if (potential != null) {
					final TransferRequest removal = new TransferRequest(incubator, this.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
					if (removal.transfer(false) == null) {
						this.recipe = potential;
					}
					removal.transfer(true);
					final ItemStack stack2 = this.getUtil().getStack(potentialSlot);
					this.getUtil().setStack(potentialSlot, null);
					this.getUtil().setStack(3, stack2);
				}
			}
			if (this.recipe != null) {
				this.roomForOutput = this.recipe.roomForOutput(this.getUtil());
			}
		}
	}

	private class IncubatorCrafting
	{
		ItemStack input;
		FluidStack fluid;
	}

	private abstract static class IncubatorRecipe implements IIncubatorRecipe
	{
		FluidStack input;
		FluidStack output;
		float lossChance;
		ItemStack outputStack;
		float tickChance;

		@Override
		public float getChance() {
			return this.tickChance;
		}

		public IncubatorRecipe(final FluidStack input, final FluidStack output, final float lossChance) {
			this(input, output, lossChance, 1.0f);
		}

		public IncubatorRecipe(final FluidStack input, final FluidStack output, final float lossChance, final float chance) {
			this.input = input;
			this.output = output;
			this.lossChance = lossChance;
			this.tickChance = chance;
		}

		@Override
		public boolean isInputLiquid(final FluidStack fluid) {
			return fluid != null && this.input.isFluidEqual(fluid);
		}

		@Override
		public boolean isInputLiquidSufficient(final FluidStack fluid) {
			return fluid != null && fluid.amount >= 500;
		}

		@Override
		public void doTask(final MachineUtil machine) {
			machine.drainTank(0, this.input.amount);
			if (this.output != null) {
				machine.fillTank(1, this.output);
			}
			this.outputStack = this.getOutputStack(machine);
			if (this.outputStack != null) {
				final ItemStack output = this.outputStack.copy();
				final TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
				product.transfer(true);
			}
			final Random rand = machine.getRandom();
			if (rand.nextFloat() < this.lossChance) {
				machine.decreaseStack(3, 1);
			}
		}

		public IncubatorRecipe setOutputStack(final ItemStack stack) {
			this.outputStack = stack;
			return this;
		}

		protected ItemStack getOutputStack(final MachineUtil util) {
			return this.outputStack;
		}

		@Override
		public boolean roomForOutput(final MachineUtil machine) {
			if (this.output != null && !machine.isTankEmpty(1)) {
				if (!machine.getFluid(1).isFluidEqual(this.output)) {
					return false;
				}
				if (!machine.spaceInTank(1, this.output.amount)) {
					return false;
				}
			}
			final ItemStack outputStack = this.getOutputStack(machine);
			if (outputStack != null) {
				final ItemStack output = outputStack.copy();
				final TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
				final ItemStack leftover = product.transfer(false);
				return leftover == null;
			}
			return true;
		}
	}
}
