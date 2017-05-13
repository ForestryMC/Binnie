package binnie.extrabees.apiary;

import binnie.core.machines.TileEntityMachine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;
import forestry.api.multiblock.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class TileExtraBeeAlveary extends TileEntityMachine implements IAlvearyComponent.Active, IAlvearyComponent.BeeModifier, IAlvearyComponent.BeeListener, IBeeListener, IBeeModifier {
	private IMultiblockLogicAlveary structureLogic;
	@Nullable
	private BlockPos min;
	@Nullable
	private BlockPos max;

	public TileExtraBeeAlveary() {
		this.structureLogic = MultiblockManager.logicFactory.createAlvearyLogic();
	}

	public TileExtraBeeAlveary(final AlvearyMachine.AlvearyPackage alvearyPackage) {
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

	public List<TileEntity> getAlvearyBlocks() {
		LinkedList<TileEntity> tiles = new LinkedList<>();
		if (min != null && max != null) {
			for (int x = min.getX(); x <= max.getX(); ++x) {
				for (int z = min.getZ(); z <= max.getZ(); ++z) {
					for (int y = min.getY(); y <= max.getY(); ++y) {
						TileEntity tile = this.getWorld().getTileEntity(new BlockPos(x, y, z));
						if (tile instanceof IMultiblockComponent) {
							tiles.add(tile);
						}
					}
				}
			}
		}
		return tiles;
	}

	@Override
	public void onMachineAssembled(IMultiblockController arg0, BlockPos min, BlockPos max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public void onMachineBroken() {
		this.min = null;
		this.max = null;

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
		return this.getMachine().getInterface(IBeeModifier.class);
	}

	@Override
	public IBeeListener getBeeListener() {
		return this.getMachine().getInterface(IBeeListener.class);
	}

	@Override
	public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
		return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getTerritoryModifier(genome, currentModifier);
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getMutationModifier(genome, mate, currentModifier);
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getProductionModifier(genome, currentModifier);
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
		return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getFloweringModifier(genome, currentModifier);
	}

	@Override
	public boolean isSealed() {
		return this.getBeeModifier() != null && this.getBeeModifier().isSealed();
	}

	@Override
	public boolean isSelfLighted() {
		return this.getBeeModifier() != null && this.getBeeModifier().isSelfLighted();
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.getBeeModifier() != null && this.getBeeModifier().isSunlightSimulated();
	}

	@Override
	public boolean isHellish() {
		return this.getBeeModifier() != null && this.getBeeModifier().isHellish();
	}

	@Override
	public void wearOutEquipment(int amount) {
		if (this.getBeeListener() != null) {
			this.getBeeListener().wearOutEquipment(amount);
		}
	}

	@Override
	public void onQueenDeath() {
		if (this.getBeeListener() != null) {
			this.getBeeListener().onQueenDeath();
		}
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public boolean onPollenRetrieved(IIndividual arg0) {
		return false;
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
		if (tag != null) {
			structureLogic.readFromNBT(tag);
		}
	}

}
