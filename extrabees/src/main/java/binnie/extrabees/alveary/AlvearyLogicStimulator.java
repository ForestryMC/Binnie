package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;

import binnie.extrabees.circuit.StimulatorCircuit;
import binnie.extrabees.client.gui.ContainerStimulator;
import binnie.extrabees.client.gui.GuiContainerStimulator;

public class AlvearyLogicStimulator extends AlvearyLogicElectrical {
	private final ItemStackHandler inv;
	private int powerUsage;
	private boolean powered;
	private StimulatorCircuit[] modifiers;

	public AlvearyLogicStimulator() {
		super(10000);
		this.powerUsage = 0;
		this.powered = false;
		this.modifiers = new StimulatorCircuit[0];
		this.inv = new ChipsetItemStackHandler();
	}

	public IItemHandlerModifiable getInventory() {
		return inv;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		inv.deserializeNBT(nbt.getCompoundTag(INVENTORY_NBT_KEY));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag(INVENTORY_NBT_KEY, inv.serializeNBT());
		return nbt;
	}

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		this.modifiers = this.getCircuits();
		this.powerUsage = 0;
		for (final StimulatorCircuit beeMod : this.modifiers) {
			this.powerUsage += beeMod.getPowerUsage();
		}
		this.powered = energyStorage.extractEnergy(powerUsage, true) >= powerUsage;
	}

	@Nullable
	private ICircuitBoard getHiveFrame() {
		if (!this.inv.getStackInSlot(0).isEmpty()) {
			return ChipsetManager.circuitRegistry.getCircuitBoard(this.inv.getStackInSlot(0));
		}
		return null;
	}

	public StimulatorCircuit[] getCircuits() {
		final ICircuitBoard board = this.getHiveFrame();
		if (board == null) {
			return new StimulatorCircuit[0];
		}
		final ICircuit[] circuits = board.getCircuits();
		final List<StimulatorCircuit> mod = new ArrayList<>();
		for (final ICircuit circuit : circuits) {
			if (circuit instanceof StimulatorCircuit) {
				mod.add((StimulatorCircuit) circuit);
			}
		}
		return mod.toArray(new StimulatorCircuit[0]);
	}

	@Override
	public float getTerritoryModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getTerritoryModifier(genome, mod);
		}
		return mod;
	}

	@Override
	public float getMutationModifier(@Nonnull final IBeeGenome genome, @Nonnull final IBeeGenome mate, final float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getMutationModifier(genome, mate, mod);
		}
		return mod;
	}

	@Override
	public float getLifespanModifier(@Nonnull final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getLifespanModifier(genome, mate, mod);
		}
		return mod;
	}

	@Override
	public float getProductionModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getProductionModifier(genome, mod);
		}
		return mod;
	}

	@Override
	public float getFloweringModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getFloweringModifier(genome, mod);
		}
		return mod;
	}

	@Override
	public float getGeneticDecay(@Nonnull final IBeeGenome genome, final float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getGeneticDecay(genome, mod);
		}
		return mod;
	}

	@Override
	public boolean isSealed() {
		if (!this.powered) {
			return false;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			if (beeMod.isSealed()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSelfLighted() {
		if (!this.powered) {
			return false;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			if (beeMod.isSelfLighted()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSunlightSimulated() {
		if (!this.powered) {
			return false;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			if (beeMod.isSunlightSimulated()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isHellish() {
		if (!this.powered) {
			return false;
		}
		for (final IBeeModifier beeMod : this.modifiers) {
			if (beeMod.isHellish()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void wearOutEquipment(final int amount) {
		energyStorage.extractEnergy(powerUsage, false);
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return new GuiContainerStimulator(getContainer(player, data));
	}

	@Nonnull
	@Override
	public ContainerStimulator getContainer(@Nonnull EntityPlayer player, int data) {
		return new ContainerStimulator(player, this);
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	private static class ChipsetItemStackHandler extends ItemStackHandler {

		public ChipsetItemStackHandler() {
			super(1);
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if (!ChipsetManager.circuitRegistry.isChipset(stack)) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
	}
}
