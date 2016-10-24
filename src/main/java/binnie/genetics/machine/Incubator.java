package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.Mods;
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
import binnie.craftgui.minecraft.IMachineInformation;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Incubator {
    public static final int[] slotQueue = new int[]{0, 1, 2};
    public static final int slotIncubator = 3;
    public static final int[] slotOutput = new int[]{4, 5, 6};
    public static final int tankInput = 0;
    public static final int tankOutput = 1;
    private static List<IIncubatorRecipe> RECIPES = new ArrayList<>();

    public static void addRecipes() {
        Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), Binnie.Liquid.getLiquidStack("water", 25), GeneticLiquid.GrowthMedium.get(25), 0.2f));
        Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.WHEAT), GeneticLiquid.GrowthMedium.get(25), GeneticLiquid.Bacteria.get(5), 0.2f));
        Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), GeneticLiquid.Bacteria.get(0), GeneticLiquid.Bacteria.get(5), 0.05f));
        Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.SUGAR), GeneticLiquid.Bacteria.get(2), null, 0.5f, 0.2f)
                .setOutputStack(GeneticsItems.Enzyme.get(1)));
        Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), GeneticLiquid.BacteriaPoly.get(0), GeneticLiquid.BacteriaPoly.get(5), 0.05f));
        Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.get(1), GeneticLiquid.BacteriaVector.get(0), GeneticLiquid.BacteriaVector.get(5), 0.05f));
        Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.DYE, 1, 15), GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaPoly.get(10), 0.1f));
        Incubator.RECIPES.add( new IncubatorRecipe(new ItemStack(Items.BLAZE_POWDER), GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaVector.get(10), 0.05f));
        if (BinnieCore.isApicultureActive()) {
            Incubator.RECIPES.add(new IncubatorRecipe(Mods.Forestry.stack("beeLarvaeGE"), GeneticLiquid.GrowthMedium.get(50), null, 1.0f, 0.05f) {
                @Override
                public ItemStack getOutputStack(final MachineUtil machine) {
                    final ItemStack larvae = machine.getStack(3);
                    final IBee bee = Binnie.Genetics.getBeeRoot().getMember(larvae);
                    return Binnie.Genetics.getBeeRoot().getMemberStack(bee, EnumBeeType.DRONE);
                }
            });
        }
    }

    public static List<IIncubatorRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }

    public static class PackageIncubator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
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

    public static class ComponentIncubatorLogic extends ComponentProcessIndefinate implements IProcess {
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
                final boolean rightItem = isStackValid(stack, recipe);
                if (rightLiquid && rightItem) {
                    return recipe;
                }
            }
            return null;
        }

        private static boolean isStackValid(ItemStack stack, IIncubatorRecipe recipe) {
            for (ItemStack validItemStack : recipe.getValidItemStacks()) {
                if (ItemStack.areItemsEqual(validItemStack, stack)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onInventoryUpdate() {
            super.onInventoryUpdate();
            if (!this.getUtil().isServer()) {
                return;
            }
            final FluidStack liquid = this.getUtil().getFluid(0);
            final ItemStack incubator = this.getUtil().getStack(3);
            if (this.recipe != null && (incubator == null || liquid == null || !this.recipe.isInputLiquid(liquid) || !isStackValid(incubator, recipe))) {
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
                                final boolean rightItem = isStackValid(stack, recipe2);
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

    private class IncubatorCrafting {
        ItemStack input;
        FluidStack fluid;
    }

}
