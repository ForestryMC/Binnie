package binnie.extrabees.machines;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import com.mojang.authlib.GameProfile;

import forestry.api.apiculture.DefaultBeeListener;
import forestry.api.apiculture.DefaultBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.multiblock.IAlvearyComponent;
import forestry.api.multiblock.IMultiblockComponent;
import forestry.api.multiblock.IMultiblockController;
import forestry.api.multiblock.IMultiblockLogicAlveary;
import forestry.api.multiblock.MultiblockManager;

import binnie.core.machines.IMachine;
import binnie.core.machines.TileEntityMachine;

public class TileExtraBeeAlveary extends TileEntityMachine implements
	IAlvearyComponent.Active,
	IAlvearyComponent.BeeModifier,
	IAlvearyComponent.BeeListener {
	private static final IBeeModifier BLANK_MODIFIER = new DefaultBeeModifier();
	private static final IBeeListener BLANK_LISTENER = new DefaultBeeListener();

	private final IMultiblockLogicAlveary structureLogic;

	public TileExtraBeeAlveary() {
		this.structureLogic = MultiblockManager.logicFactory.createAlvearyLogic();
	}

	public TileExtraBeeAlveary(ExtraBeeMachines.AlvearyPackage alvearyPackage) {
		super(alvearyPackage);
		this.structureLogic = MultiblockManager.logicFactory.createAlvearyLogic();
	}

	@Override
	public BlockPos getCoordinates() {
		return getPos();
	}

	@Override
	public GameProfile getOwner() {
		return getMachine().getOwner();
	}

	public Collection<IMultiblockComponent> getAlvearyBlocks() {
		return structureLogic.getController().getComponents();
	}

	@Override
	public void onMachineAssembled(IMultiblockController multiblockController, BlockPos minCoord, BlockPos maxCoord) {
	}

	@Override
	public void onMachineBroken() {
	}

	@Override
	public IMultiblockLogicAlveary getMultiblockLogic() {
		return structureLogic;
	}

	@Override
	public void updateClient(int arg0) {
		super.update();
	}

	@Override
	public void updateServer(int arg0) {
		super.update();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		structureLogic.invalidate(world, this);
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		structureLogic.onChunkUnload(world, this);
	}

	@Override
	public void validate() {
		structureLogic.validate(world, this);
	}

	@Override
	public IBeeModifier getBeeModifier() {
		IMachine machine = getMachine();
		if (machine == null) {
			return BLANK_MODIFIER;
		}
		IBeeModifier modifier = machine.getInterface(IBeeModifier.class);
		if (modifier != null) {
			return modifier;
		}
		return BLANK_MODIFIER;
	}

	@Override
	public IBeeListener getBeeListener() {
		IMachine machine = getMachine();
		if (machine == null) {
			return BLANK_LISTENER;
		}
		IBeeListener listener = machine.getInterface(IBeeListener.class);
		if (listener != null) {
			return listener;
		}
		return BLANK_LISTENER;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound2) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(nbtTagCompound2);
		NBTTagCompound tag = new NBTTagCompound();
		structureLogic.writeToNBT(tag);
		nbtTagCompound.setTag("structureLogic", tag);
		return nbtTagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		NBTTagCompound tag = nbtTagCompound.getCompoundTag("structureLogic");
		if (!tag.hasNoTags()) {
			structureLogic.readFromNBT(tag);
		}
	}

}
