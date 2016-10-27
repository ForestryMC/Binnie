package binnie.extrabees.apiary;

import binnie.core.machines.TileEntityMachine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;
import forestry.api.multiblock.IAlvearyComponent;
import forestry.api.multiblock.IMultiblockComponent;
import forestry.api.multiblock.IMultiblockController;
import forestry.api.multiblock.IMultiblockLogicAlveary;
import forestry.api.multiblock.MultiblockManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;
import java.util.List;

public class TileExtraBeeAlveary extends TileEntityMachine implements IAlvearyComponent.Active, IAlvearyComponent.BeeModifier, IAlvearyComponent.BeeListener, IBeeListener, IBeeModifier {
	IMultiblockLogicAlveary structureLogic;
	BlockPos min;
	BlockPos max;

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
		structureLogic.invalidate(worldObj, this);
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		structureLogic.onChunkUnload(worldObj, this);
	}

	@Override
	public void validate() {
		structureLogic.validate(worldObj, this);
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
	public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
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


	//
	// @Override
	// public void readFromNBT(final NBTTagCompound nbttagcompound) {
	// super.readFromNBT(nbttagcompound);
	// this.isMaster = nbttagcompound.getBoolean("IsMaster");
	// this.masterX = nbttagcompound.getInteger("MasterX");
	// this.masterY = nbttagcompound.getInteger("MasterY");
	// this.masterZ = nbttagcompound.getInteger("MasterZ");
	// if (this.isMaster) {
	// this.makeMaster();
	// }
	// // this.structureLogic.readFromNBT(nbttagcompound);
	// // this.updateAlvearyBlocks();
	// this.init = false;
	// }
	//
	// @Override
	// public void writeToNBT(final NBTTagCompound nbttagcompound) {
	// super.writeToNBT(nbttagcompound);
	// nbttagcompound.setBoolean("IsMaster", this.isMaster);
	// nbttagcompound.setInteger("MasterX", this.masterX);
	// nbttagcompound.setInteger("MasterY", this.masterY);
	// nbttagcompound.setInteger("MasterZ", this.masterZ);
	// //this.structureLogic.writeToNBT(nbttagcompound);
	// }
	//
	// AlvearyMachine.AlvearyPackage getAlvearyPackage() {
	// return (AlvearyMachine.AlvearyPackage)this.getMachine().getPackage();
	// }
	//
	// public TileExtraBeeAlveary() {
	// this.init = false;
	// this.masterY = -99;
	// this.tiles = new ArrayList<TileEntity>();
	// //this.structureLogic =
	// Binnie.Genetics.getBeeRoot().createAlvearyStructureLogic((IAlvearyComponent)this);
	// }
	//
	// public TileExtraBeeAlveary(final AlvearyMachine.AlvearyPackage
	// alvearyPackage) {
	// super(alvearyPackage);
	// this.init = false;
	// this.masterY = -99;
	// this.tiles = new ArrayList<TileEntity>();
	// // this.structureLogic =
	// Binnie.Genetics.getBeeRoot().createAlvearyStructureLogic((IAlvearyComponent)this);
	// }
	//
	//
	// public void makeMaster() {
	// }
	//
	// // public void onStructureReset() {
	// // this.setCentralTE(null);
	// // this.isMaster = false;
	// // this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
	// this.zCoord);
	// // this.updateAlvearyBlocks();
	// // }
	// //
	// // public IMultiblockComponent getCentralTE() {
	// // if (this.worldObj == null || !this.isIntegratedIntoStructure()) {
	// // return null;
	// // }
	// // if (this.isMaster()) {
	// // return (IMultiblockComponent)this;
	// // }
	// // final TileEntity tile = this.worldObj.getTileEntity(this.masterX,
	// this.masterY, this.masterZ);
	// // if (!(tile instanceof IMultiblockComponent)) {
	// // return null;
	// // }
	// // final IMultiblockComponent master =
	// (IMultiblockComponent)this.worldObj.getTileEntity(this.masterX,
	// this.masterY, this.masterZ);
	// // if (master.isMaster()) {
	// // return master;
	// // }
	// // return null;
	// // }
	//
	// // public void validateStructure() {
	// // this.structureLogic.validateStructure();
	// // this.updateAlvearyBlocks();
	// // }
	//
	// private boolean isSameTile(final TileEntity tile) {
	// return tile.xCoord == this.xCoord && tile.yCoord == this.yCoord &&
	// tile.zCoord == this.zCoord;
	// }
	//
	// // public void setCentralTE(final TileEntity tile) {
	// // if (tile == null || tile == this || this.isSameTile(tile)) {
	// // final boolean b = false;
	// // this.masterZ = (b ? 1 : 0);
	// // this.masterX = (b ? 1 : 0);
	// // this.masterY = -99;
	// // this.updateAlvearyBlocks();
	// // return;
	// // }
	// // this.isMaster = false;
	// // this.masterX = tile.xCoord;
	// // this.masterY = tile.yCoord;
	// // this.masterZ = tile.zCoord;
	// // this.markDirty();
	// // if (this.getBeeListener() != null) {
	// // ((IAlvearyComponent)tile).registerBeeListener(this.getBeeListener());
	// // }
	// // if (this.getBeeModifier() != null) {
	// // ((IAlvearyComponent)tile).registerBeeModifier(this.getBeeModifier());
	// // }
	// // this.updateAlvearyBlocks();
	// // }
	//
	// public boolean isMaster() {
	// return this.isMaster;
	// }
	//
	// protected boolean hasMaster() {
	// return this.masterY >= 0;
	// }
	//
	// public boolean isIntegratedIntoStructure() {
	// return this.isMaster || this.masterY >= 0;
	// }
	//
	// public void registerBeeModifier(final IBeeModifier modifier) {
	// }
	//
	// public void removeBeeModifier(final IBeeModifier modifier) {
	// }
	//
	// public void addTemperatureChange(final float change, final float
	// boundaryDown, final float boundaryUp) {
	// }
	//
	// public void addHumidityChange(final float change, final float
	// boundaryDown, final float boundaryUp) {
	// }
	//
	// public boolean hasFunction() {
	// return true;
	// }
	//
	// public IBeeModifier getBeeModifier() {
	// return this.getMachine().getInterface(IBeeModifier.class);
	// }
	//
	// public IBeeListener getBeeListener() {
	// return this.getMachine().getInterface(IBeeListener.class);
	// }
	//
	// public float getTerritoryModifier(final IBeeGenome genome, final float
	// currentModifier) {
	// return (this.getBeeModifier() == null) ? 1.0f :
	// this.getBeeModifier().getTerritoryModifier(genome, currentModifier);
	// }
	//
	// public float getMutationModifier(final IBeeGenome genome, final
	// IBeeGenome mate, final float currentModifier) {
	// return (this.getBeeModifier() == null) ? 1.0f :
	// this.getBeeModifier().getMutationModifier(genome, mate, currentModifier);
	// }
	//
	// public float getLifespanModifier(final IBeeGenome genome, final
	// IBeeGenome mate, final float currentModifier) {
	// return (this.getBeeModifier() == null) ? 1.0f :
	// this.getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
	// }
	//
	// public float getProductionModifier(final IBeeGenome genome, final float
	// currentModifier) {
	// return (this.getBeeModifier() == null) ? 1.0f :
	// this.getBeeModifier().getProductionModifier(genome, currentModifier);
	// }
	//
	// public float getFloweringModifier(final IBeeGenome genome, final float
	// currentModifier) {
	// return (this.getBeeModifier() == null) ? 1.0f :
	// this.getBeeModifier().getFloweringModifier(genome, currentModifier);
	// }
	//
	// public boolean isSealed() {
	// return this.getBeeModifier() != null && this.getBeeModifier().isSealed();
	// }
	//
	// public boolean isSelfLighted() {
	// return this.getBeeModifier() != null &&
	// this.getBeeModifier().isSelfLighted();
	// }
	//
	// public boolean isSunlightSimulated() {
	// return this.getBeeModifier() != null &&
	// this.getBeeModifier().isSunlightSimulated();
	// }
	//
	// public boolean isHellish() {
	// return this.getBeeModifier() != null &&
	// this.getBeeModifier().isHellish();
	// }
	//
	// public void registerBeeListener(final IBeeListener event) {
	// }
	//
	// public void removeBeeListener(final IBeeListener event) {
	// }
	//
	// // public void onQueenChange(final ItemStack queen) {
	// // if (this.getBeeListener() != null) {
	// // this.getBeeListener().onQueenChange(queen);
	// // }
	// // }
	// //
	// public void wearOutEquipment(final int amount) {
	// if (this.getBeeListener() != null) {
	// this.getBeeListener().wearOutEquipment(amount);
	// }
	// }
	//
	// // public void onQueenDeath(final IBee queen) {
	// // if (this.getBeeListener() != null) {
	// // this.getBeeListener().onQueenDeath(queen);
	// // }
	// // }
	// //
	// // public void onPostQueenDeath(final IBee queen) {
	// // if (this.getBeeListener() != null) {
	// // this.getBeeListener().onPostQueenDeath(queen);
	// // }
	// // }
	//
	// public boolean onPollenRetrieved(final IBee queen, final IIndividual
	// pollen, final boolean isHandled) {
	// return false;
	// }
	//
	// public boolean onEggLaid(final IBee queen) {
	// return false;
	// }
	//
	// public float getGeneticDecay(final IBeeGenome genome, final float
	// currentModifier) {
	// return 1.0f;
	// }
	//
	// // public IBeeHousing getBeeHousing() {
	// // return (this.getCentralTE() == null) ? null :
	// ((IBeeHousing)this.getCentralTE());
	// // }
	//
	// // public List<TileEntity> getAlvearyBlocks() {
	// // this.updateAlvearyBlocks();
	// // return this.tiles;
	// // }
	//
	// // private void updateAlvearyBlocks() {
	// // this.tiles.clear();
	// // if (this.getCentralTE() != null) {
	// // final IMultiblockComponent struct = this.getCentralTE();
	// // if (!struct.isIntegratedIntoStructure()) {
	// // return;
	// // }
	// // final TileEntity central = (TileEntity)struct;
	// // for (int x = -2; x <= 2; ++x) {
	// // for (int z = -2; z <= 2; ++z) {
	// // for (int y = -2; y <= 2; ++y) {
	// // final TileEntity tile = this.getWorldObj().getTileEntity(this.xCoord +
	// x, this.yCoord + y, this.zCoord + z);
	// // if (tile != null && tile instanceof IMultiblockComponent &&
	// ((IMultiblockComponent)tile).getMultiblockLogic().getCentralTE() ==
	// struct) {
	// // this.tiles.add(tile);
	// // }
	// // }
	// // }
	// // }
	// // }
	// // }
	//
	// public ISidedInventory getStructureInventory() {
	// return this.getMachine().getInterface(ISidedInventory.class);
	// }
	//

}
