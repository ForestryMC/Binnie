package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.transfer.TransferRequest;
import binnie.genetics.api.IIncubatorRecipe;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Incubator {
	public static int[] slotQueue = new int[]{0, 1, 2};
	public static int[] slotOutput = new int[]{4, 5, 6};
	public static int slotIncubator = 3;
	public static int tankInput = 0;
	public static int tankOutput = 1;
	private static List<IIncubatorRecipe> RECIPES = new ArrayList<>();

	public static void addRecipes() {
		Incubator.RECIPES.add(new IncubatorRecipe(Binnie.Liquid.getLiquidStack("water", 25), GeneticLiquid.GrowthMedium.get(25), 0.2f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.GrowthMedium.get(25), GeneticLiquid.Bacteria.get(5), 0.2f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return new ItemStack(Items.wheat).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(0), GeneticLiquid.Bacteria.get(5), 0.05f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(2), null, 0.5f, 0.2f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return stack.getItem() == Items.sugar;
			}
		}.setOutputStack(GeneticsItems.Enzyme.get(1)));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.BacteriaPoly.get(0), GeneticLiquid.BacteriaPoly.get(5), 0.05f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.BacteriaVector.get(0), GeneticLiquid.BacteriaVector.get(5), 0.05f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaPoly.get(10), 0.1f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return new ItemStack(Items.dye, 1, 15).isItemEqual(stack);
			}
		});
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaVector.get(10), 0.05f) {
			@Override
			public boolean isItemStack(ItemStack stack) {
				return new ItemStack(Items.blaze_powder).isItemEqual(stack);
			}
		});

		if (BinnieCore.isApicultureActive()) {
			Incubator.RECIPES.add(new IncubatorRecipe(GeneticLiquid.GrowthMedium.get(50), null, 1.0f, 0.05f) {
				@Override
				public boolean isItemStack(ItemStack stack) {
					return Binnie.Genetics.getBeeRoot().isMember(stack) && Binnie.Genetics.getBeeRoot().getType(stack) == EnumBeeType.LARVAE;
				}

				@Override
				public ItemStack getOutputStack(MachineUtil machine) {
					ItemStack larvae = machine.getStack(3);
					IBee bee = Binnie.Genetics.getBeeRoot().getMember(larvae);
					return Binnie.Genetics.getBeeRoot().getMemberStack(bee, EnumBeeType.DRONE.ordinal());
				}
			});
		}
	}

	public static class PackageIncubator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageIncubator() {
			super("incubator", GeneticsTexture.Incubator, 16767313, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Incubator);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlotArray(Incubator.slotQueue, "input");
			for (InventorySlot slot : inventory.getSlots(Incubator.slotQueue)) {
				slot.forbidExtraction();
			}
			inventory.addSlot(3, "incubator");
			inventory.getSlot(3).forbidInteraction();
			inventory.getSlot(3).setReadOnly();
			inventory.addSlotArray(Incubator.slotOutput, "output");
			for (InventorySlot slot : inventory.getSlots(Incubator.slotOutput)) {
				slot.forbidInsertion();
				slot.setReadOnly();
			}
			new ComponentPowerReceptor(machine, 2000);
			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(0, "input", 2000).forbidExtraction();
			tanks.addTank(1, "output", 2000).setReadOnly();
			new ComponentIncubatorLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentIncubatorLogic extends ComponentProcessIndefinate implements IProcess {
		IIncubatorRecipe recipe;
		private Random rand;
		private boolean roomForOutput;

		public ComponentIncubatorLogic(Machine machine) {
			super(machine, 2.0f);
			recipe = null;
			rand = new Random();
			roomForOutput = true;
		}

		@Override
		public ErrorState canWork() {
			if (recipe == null) {
				return new ErrorState("No Recipe", "There is no valid recipe");
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (recipe != null) {
				if (!recipe.isInputLiquidSufficient(getUtil().getFluid(0))) {
					return new ErrorState.InsufficientLiquid("Not enough incubation liquid", 0);
				}
				if (!roomForOutput) {
					return new ErrorState.TankSpace("No room for output", 1);
				}
			}
			return super.canProgress();
		}

		@Override
		protected void onTickTask() {
			if (rand.nextInt(20) == 0 && recipe != null && rand.nextFloat() < recipe.getChance()) {
				recipe.doTask(getUtil());
			}
		}

		@Override
		public boolean inProgress() {
			return recipe != null;
		}

		private IIncubatorRecipe getRecipe(ItemStack stack, FluidStack liquid) {
			for (IIncubatorRecipe recipe : Incubator.RECIPES) {
				boolean rightLiquid = recipe.isInputLiquid(liquid);
				boolean rightItem = recipe.isItemStack(stack);
				if (rightLiquid && rightItem) {
					return recipe;
				}
			}
			return null;
		}

		@Override
		public void onInventoryUpdate() {
			super.onInventoryUpdate();
			if (!getUtil().isServer()) {
				return;
			}

			FluidStack liquid = getUtil().getFluid(0);
			ItemStack incubator = getUtil().getStack(3);
			if (recipe != null && (incubator == null || liquid == null || !recipe.isInputLiquid(liquid) || !recipe.isItemStack(incubator))) {
				recipe = null;
				ItemStack leftover = new TransferRequest(incubator, getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation().transfer(true);
				getUtil().setStack(3, leftover);
			}

			if (recipe == null) {
				if (liquid == null) {
					return;
				}
				if (incubator != null) {
					IIncubatorRecipe recipe = getRecipe(incubator, liquid);
					if (recipe != null) {
						this.recipe = recipe;
						return;
					}
				}

				IIncubatorRecipe potential = null;
				int potentialSlot = 0;
				for (int slot : Incubator.slotQueue) {
					ItemStack stack = getUtil().getStack(slot);
					if (stack != null) {
						if (potential == null) {
							for (IIncubatorRecipe recipe2 : Incubator.RECIPES) {
								boolean rightLiquid = recipe2.isInputLiquid(liquid);
								boolean rightItem = recipe2.isItemStack(stack);
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
					TransferRequest removal = new TransferRequest(incubator, getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
					if (removal.transfer(false) == null) {
						recipe = potential;
					}
					removal.transfer(true);
					ItemStack stack2 = getUtil().getStack(potentialSlot);
					getUtil().setStack(potentialSlot, null);
					getUtil().setStack(3, stack2);
				}
			}

			if (recipe != null) {
				roomForOutput = recipe.roomForOutput(getUtil());
			}
		}
	}

	private abstract static class IncubatorRecipe implements IIncubatorRecipe {
		protected FluidStack input;
		protected FluidStack output;
		protected float lossChance;
		protected ItemStack outputStack;
		protected float tickChance;

		public IncubatorRecipe(FluidStack input, FluidStack output, float lossChance) {
			this(input, output, lossChance, 1.0f);
		}

		public IncubatorRecipe(FluidStack input, FluidStack output, float lossChance, float chance) {
			this.input = input;
			this.output = output;
			this.lossChance = lossChance;
			tickChance = chance;
		}

		@Override
		public float getChance() {
			return tickChance;
		}

		@Override
		public boolean isInputLiquid(FluidStack liquid) {
			return liquid != null && input.isFluidEqual(liquid);
		}

		@Override
		public boolean isInputLiquidSufficient(FluidStack liquid) {
			return liquid != null && liquid.amount >= 500;
		}

		@Override
		public void doTask(MachineUtil machine) {
			machine.drainTank(0, input.amount);
			if (output != null) {
				machine.fillTank(1, output);
			}

			outputStack = getOutputStack(machine);
			if (outputStack != null) {
				ItemStack output = outputStack.copy();
				TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
				product.transfer(true);
			}

			Random rand = machine.getRandom();
			if (rand.nextFloat() < lossChance) {
				machine.decreaseStack(3, 1);
			}
		}

		public IncubatorRecipe setOutputStack(ItemStack stack) {
			outputStack = stack;
			return this;
		}

		protected ItemStack getOutputStack(MachineUtil util) {
			return outputStack;
		}

		@Override
		public boolean roomForOutput(MachineUtil machine) {
			if (output != null && !machine.isTankEmpty(1)) {
				if (!machine.getFluid(1).isFluidEqual(output)) {
					return false;
				}
				if (!machine.spaceInTank(1, output.amount)) {
					return false;
				}
			}

			ItemStack outputStack = getOutputStack(machine);
			if (outputStack != null) {
				ItemStack output = outputStack.copy();
				TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
				ItemStack leftover = product.transfer(false);
				return leftover == null;
			}
			return true;
		}
	}
}
