package binnie.extratrees.machines.brewery;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.CoreErrorCode;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.extratrees.machines.ExtraTreesErrorCode;

public class BreweryLogic extends ComponentProcessSetCost implements IProcess, INetwork.GuiNBT {
	@Nullable
	public BreweryCrafting currentCrafting;

	public BreweryLogic(final Machine machine) {
		super(machine, 16000, 800);
		this.currentCrafting = null;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isTankEmpty(BreweryMachine.TANK_INPUT) && this.currentCrafting == null) {
			return new ErrorState(CoreErrorCode.TANK_EMPTY, BreweryMachine.TANK_INPUT);
		}
		if (BreweryRecipes.getOutput(this.getInputCrafting()) == null && this.currentCrafting == null) {
			return new ErrorState(ExtraTreesErrorCode.BREWERY_NO_RECIPE);
		}
		if (!this.getUtil().hasIngredients(new int[]{0, 1, 2, 3, 4}, BreweryMachine.SLOTS_INVENTORY) && this.currentCrafting == null) {
			return new ErrorState(ExtraTreesErrorCode.BREWERY_INSUFFICIENT_INGREDIENTS);
		}
		return super.canWork();
	}

	private BreweryCrafting getInputCrafting() {
		FluidStack inputFluid = this.getUtil().getFluid(BreweryMachine.TANK_INPUT);
		ItemStack inputIngredient = this.getUtil().getStack(BreweryMachine.SLOT_RECIPE_INPUT);
		ItemStack[] inputGrains = this.getUtil().getStacks(BreweryMachine.SLOT_RECIPE_GRAINS);
		ItemStack inputYeast = this.getUtil().getStack(BreweryMachine.SLOT_YEAST);
		return new BreweryCrafting(inputFluid, inputIngredient, inputGrains, inputYeast);
	}

	@Override
	public ErrorState canProgress() {
		if (this.currentCrafting == null) {
			return new ErrorState(CoreErrorCode.TANK_EMPTY);
		}
		if (!this.getUtil().spaceInTank(BreweryMachine.TANK_OUTPUT, Fluid.BUCKET_VOLUME)) {
			return new ErrorState(CoreErrorCode.NO_SPACE_TANK, BreweryMachine.TANK_OUTPUT);
		}
		FluidStack outputFluid = this.getUtil().getFluid(BreweryMachine.TANK_OUTPUT);
		if (outputFluid != null) {
			FluidStack craftingOutputFluid = BreweryRecipes.getOutput(this.currentCrafting);
			if (!outputFluid.isFluidEqual(craftingOutputFluid)) {
				return new ErrorState(CoreErrorCode.TANK_DIFFRENT_FLUID, BreweryMachine.TANK_OUTPUT);
			}
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		Preconditions.checkState(this.currentCrafting != null);
		FluidStack output = BreweryRecipes.getOutput(currentCrafting);
		if (output != null) {
			this.getUtil().fillTank(BreweryMachine.TANK_OUTPUT, output);
			this.currentCrafting = null;
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.canWork() == null && this.currentCrafting == null && this.getUtil().getTank(BreweryMachine.TANK_INPUT).getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			final FluidStack stack = this.getUtil().drainTank(BreweryMachine.TANK_INPUT, Fluid.BUCKET_VOLUME);
			this.currentCrafting = this.getInputCrafting();
			this.currentCrafting.inputFluid = stack;
			this.getUtil().drainTank(BreweryMachine.TANK_INPUT,  stack.amount);
			this.getUtil().removeIngredients(new int[]{0, 1, 2, 3, 4}, BreweryMachine.SLOTS_INVENTORY);
		}
	}

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> data) {
		final NBTTagCompound nbt = new NBTTagCompound();
		if (this.currentCrafting == null) {
			nbt.setBoolean("null", true);
		} else {
			this.currentCrafting.writeToNBT(nbt);
		}
		data.put("brewery-recipe", nbt);
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {

	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("brewery-recipe")) {
			if (nbt.getBoolean("null")) {
				this.currentCrafting = null;
			} else {
				this.currentCrafting = BreweryCrafting.create(nbt);
			}
		}
	}

	@Override
	public String getTooltip() {
		if (this.currentCrafting == null) {
			return I18N.localise("extratrees.machine.machine.brewery.tooltips.empty");
		}
		FluidStack output = BreweryRecipes.getOutput(this.currentCrafting);
		if (output == null) {
			return I18N.localise("extratrees.machine.machine.brewery.tooltips.empty");
		}
		return I18N.localise("extratrees.machine.machine.brewery.tooltips.creating", output.getFluid().getLocalizedName(output));
	}
}
