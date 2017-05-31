package binnie.genetics.machine.incubator;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.MachineUtil;
import binnie.genetics.api.IIncubatorRecipe;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Incubator {
	public static final int[] SLOT_QUEUE = new int[]{0, 1, 2};
	public static final int[] SLOT_OUTPUT = new int[]{4, 5, 6};
	public static final int SLOT_INCUBATOR = 3;
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;
	public static final float ENERGY_PER_TICK = 2.0f;
	public static final List<IIncubatorRecipe> RECIPES = new ArrayList<>();
	public static final int POWER_CAPACITY = 2000;
	public static final int INPUT_TANK_CAPACITY = 2000;
	public static final int OUTPU_TANK_CAPACITY = 2000;

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
					ItemStack larvae = machine.getStack(SLOT_INCUBATOR);
					IBee bee = Binnie.Genetics.getBeeRoot().getMember(larvae);
					return Binnie.Genetics.getBeeRoot().getMemberStack(bee, EnumBeeType.DRONE.ordinal());
				}
			});
		}
	}
}
