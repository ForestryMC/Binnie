package binnie.extrabees.client.gui;

import binnie.extrabees.alveary.AlvearyLogicStimulator;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerStimulator extends AbstractAlvearyContainer {

	private final AlvearyLogicStimulator logic;
	private final IEnergyStorage storage;
	protected int power, maxPower;

	public ContainerStimulator(EntityPlayer player, AlvearyLogicStimulator logic) {
		super(player, logic.getInventory(), EnumAlvearyLogicType.STIMULATOR, DEFAULT_DIMENSION);
		this.storage = logic.getEnergyStorage();
		this.logic = logic;
		this.maxPower = storage.getMaxEnergyStored();
		if (!player.world.isRemote) {
			logic.onContainerOpened(this);
		}
	}

	@Override
	protected void setupContainer() {
		offset = -21;
		addPlayerInventory();
		title = "Stimulator";
		addSlotToContainer(new SlotItemHandler(inv, 0, 44, 37));
	}

	public void checkPower() {
		if (power != storage.getEnergyStored()) {
			for (IContainerListener listener : listeners) {
				listener.sendProgressBarUpdate(this, 9, power = storage.getEnergyStored());
			}
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		logic.onGuiClosed(this);
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if (id == 9) {
			power = data;
		}
	}
}
