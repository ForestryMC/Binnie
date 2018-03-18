package binnie.extrabees.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;

import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.SlotItemHandler;

import binnie.extrabees.alveary.AlvearyLogicStimulator;
import binnie.extrabees.alveary.EnumAlvearyLogicType;

public class ContainerStimulator extends AlvearyContainer {
	private static final int ENERGY_PROPERTY = 9;

	private final AlvearyLogicStimulator logic;
	private final IEnergyStorage storage;
	protected final int maxPower;
	protected int power;

	public ContainerStimulator(EntityPlayer player, AlvearyLogicStimulator logic) {
		super(player, logic.getInventory(), EnumAlvearyLogicType.STIMULATOR, DEFAULT_DIMENSION);
		this.storage = logic.getEnergyStorage();
		this.logic = logic;
		this.maxPower = storage.getMaxEnergyStored();
	}

	@Override
	protected void setupContainer() {
		offset = -21;
		addPlayerInventory();
		title = "Stimulator";
		addSlotToContainer(new SlotItemHandler(inv, 0, 44, 37));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (power != storage.getEnergyStored()) {
			power = storage.getEnergyStored();
			for (IContainerListener listener : listeners) {
				listener.sendWindowProperty(this, ENERGY_PROPERTY, power);
			}
		}
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if (id == ENERGY_PROPERTY) {
			power = data;
		}
	}

	public int getEnergyScaled(int i) {
		return power * i / maxPower;
	}
}
