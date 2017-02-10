package binnie.extratrees.machines.brewery;

import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import com.google.common.base.Preconditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Map;

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
			return new ErrorState.InsufficientLiquid("No Input Liquid", BreweryMachine.TANK_INPUT);
		}
		if (BreweryRecipes.getOutput(this.getInputCrafting()) == null && this.currentCrafting == null) {
			return new ErrorState("No Recipe", "Brewing cannot occur with these ingredients");
		}
		if (!this.getUtil().hasIngredients(new int[]{0, 1, 2, 3, 4}, BreweryMachine.SLOTS_INVENTORY) && this.currentCrafting == null) {
			return new ErrorState("Insufficient Ingredients", "Not enough ingredients for Brewing");
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
			return new ErrorState("Brewery Empty", "No liquid in Brewery");
		}
		if (!this.getUtil().spaceInTank(BreweryMachine.TANK_OUTPUT, Fluid.BUCKET_VOLUME)) {
			return new ErrorState.TankSpace("No Space for Fermented Liquid", BreweryMachine.TANK_OUTPUT);
		}
		FluidStack outputFluid = this.getUtil().getFluid(BreweryMachine.TANK_OUTPUT);
		if (outputFluid != null) {
			FluidStack craftingOutputFluid = BreweryRecipes.getOutput(this.currentCrafting);
			if (!outputFluid.isFluidEqual(craftingOutputFluid)) {
				return new ErrorState.TankSpace("Different fluid in tank", BreweryMachine.TANK_OUTPUT);
			}
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		Preconditions.checkState(this.currentCrafting != null);
		FluidStack output = BreweryRecipes.getOutput(this.currentCrafting);
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
			return "Empty";
		}
		FluidStack output = BreweryRecipes.getOutput(this.currentCrafting);
		if (output == null) {
			return "Empty";
		}
		return "Creating " + output.getFluid().getLocalizedName(output);
	}
}
