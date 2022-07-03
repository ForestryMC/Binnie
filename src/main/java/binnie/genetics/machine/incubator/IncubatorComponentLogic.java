package binnie.genetics.machine.incubator;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.transfer.TransferRequest;
import binnie.core.util.I18N;
import binnie.genetics.api.IIncubatorRecipe;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class IncubatorComponentLogic extends ComponentProcessIndefinate implements IProcess {
    IIncubatorRecipe recipe;
    private final Random rand;
    private boolean roomForOutput;

    public IncubatorComponentLogic(Machine machine) {
        super(machine, Incubator.ENERGY_PER_TICK);
        recipe = null;
        rand = new Random();
        roomForOutput = true;
    }

    private static boolean isStackValid(ItemStack stack, IIncubatorRecipe recipe) {
        return OreDictionary.itemMatches(recipe.getInputStack(), stack, false);
    }

    @Override
    public ErrorState canWork() {
        if (recipe != null) {
            return super.canWork();
        }
        return new ErrorState(
                I18N.localise("genetics.machine.incubator.error.noRecipe.title"),
                I18N.localise("genetics.machine.incubator.error.noRecipe"));
    }

    @Override
    public ErrorState canProgress() {
        if (recipe != null) {
            if (!recipe.isInputLiquidSufficient(getUtil().getFluid(Incubator.TANK_INPUT))) {
                return new ErrorState.InsufficientLiquid(
                        I18N.localise("genetics.machine.incubator.error.noLiquid"), Incubator.TANK_INPUT);
            }
            if (!roomForOutput) {
                return new ErrorState.TankSpace(
                        I18N.localise("genetics.machine.incubator.error.noRoom"), Incubator.TANK_OUTPUT);
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
            boolean rightItem = isStackValid(stack, recipe);
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

        FluidStack liquid = getUtil().getFluid(Incubator.TANK_INPUT);
        ItemStack incubator = getUtil().getStack(Incubator.SLOT_INCUBATOR);
        if (recipe != null
                && (incubator == null
                        || liquid == null
                        || !recipe.isInputLiquid(liquid)
                        || !isStackValid(incubator, recipe))) {
            recipe = null;
            ItemStack leftover = new TransferRequest(incubator, getInventory())
                    .setTargetSlots(Incubator.SLOT_OUTPUT)
                    .ignoreValidation()
                    .transfer(true);
            getUtil().setStack(Incubator.SLOT_INCUBATOR, leftover);
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
            for (int slot : Incubator.SLOT_QUEUE) {
                ItemStack stack = getUtil().getStack(slot);
                if (stack == null || potential != null) {
                    continue;
                }

                for (IIncubatorRecipe recipe2 : Incubator.RECIPES) {
                    boolean rightLiquid = recipe2.isInputLiquid(liquid);
                    boolean rightItem = isStackValid(stack, recipe2);
                    if (rightLiquid && rightItem) {
                        potential = recipe2;
                        potentialSlot = slot;
                        break;
                    }
                }
            }

            if (potential != null) {
                TransferRequest removal = new TransferRequest(incubator, getInventory())
                        .setTargetSlots(Incubator.SLOT_OUTPUT)
                        .ignoreValidation();

                if (removal.transfer(false) == null) {
                    recipe = potential;
                }

                removal.transfer(true);
                ItemStack stack2 = getUtil().getStack(potentialSlot);
                getUtil().setStack(potentialSlot, null);
                getUtil().setStack(Incubator.SLOT_INCUBATOR, stack2);
            }
        }

        if (recipe != null) {
            roomForOutput = recipe.roomForOutput(getUtil());
        }
    }
}
