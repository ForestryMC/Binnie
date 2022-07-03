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
import java.util.LinkedList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class TileExtraBeeAlveary extends TileEntityMachine
        implements IAlvearyComponent.Active,
                IAlvearyComponent.BeeModifier,
                IAlvearyComponent.BeeListener,
                IBeeListener,
                IBeeModifier {
    protected IMultiblockLogicAlveary structureLogic;
    protected ChunkCoordinates min;
    protected ChunkCoordinates max;

    public TileExtraBeeAlveary() {
        structureLogic = MultiblockManager.logicFactory.createAlvearyLogic();
    }

    public TileExtraBeeAlveary(AlvearyMachine.AlvearyPackage alvearyPackage) {
        super(alvearyPackage);
        structureLogic = MultiblockManager.logicFactory.createAlvearyLogic();
    }

    @Override
    public ChunkCoordinates getCoordinates() {
        return new ChunkCoordinates(xCoord, yCoord, zCoord);
    }

    @Override
    public GameProfile getOwner() {
        return getMachine().getOwner();
    }

    public List<TileEntity> getAlvearyBlocks() {
        LinkedList<TileEntity> tiles = new LinkedList<>();
        if (min == null || max == null) {
            return tiles;
        }

        for (int x = min.posX; x <= max.posX; ++x) {
            for (int z = min.posZ; z <= max.posZ; ++z) {
                for (int y = min.posY; y <= max.posY; ++y) {
                    TileEntity tile = getWorldObj().getTileEntity(x, y, z);
                    if (tile instanceof IMultiblockComponent) {
                        tiles.add(tile);
                    }
                }
            }
        }
        return tiles;
    }

    @Override
    public void onMachineAssembled(IMultiblockController arg0, ChunkCoordinates min, ChunkCoordinates max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void onMachineBroken() {
        min = null;
        max = null;
    }

    @Override
    public IMultiblockLogicAlveary getMultiblockLogic() {
        return structureLogic;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void updateClient(int arg0) {
        super.updateEntity();
    }

    @Override
    public void updateServer(int arg0) {
        super.updateEntity();
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
        return getMachine().getInterface(IBeeModifier.class);
    }

    @Override
    public IBeeListener getBeeListener() {
        return getMachine().getInterface(IBeeListener.class);
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        if (getBeeModifier() == null) {
            return 1.0f;
        }
        return getBeeModifier().getTerritoryModifier(genome, currentModifier);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        if (getBeeModifier() == null) {
            return 1.0f;
        }
        return getBeeModifier().getMutationModifier(genome, mate, currentModifier);
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        if (getBeeModifier() == null) {
            return 1.0f;
        }
        return getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        if (getBeeModifier() == null) {
            return 1.0f;
        }
        return getBeeModifier().getProductionModifier(genome, currentModifier);
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        if (getBeeModifier() == null) {
            return 1.0f;
        }
        return getBeeModifier().getFloweringModifier(genome, currentModifier);
    }

    @Override
    public boolean isSealed() {
        return getBeeModifier() != null && getBeeModifier().isSealed();
    }

    @Override
    public boolean isSelfLighted() {
        return getBeeModifier() != null && getBeeModifier().isSelfLighted();
    }

    @Override
    public boolean isSunlightSimulated() {
        return getBeeModifier() != null && getBeeModifier().isSunlightSimulated();
    }

    @Override
    public boolean isHellish() {
        return getBeeModifier() != null && getBeeModifier().isHellish();
    }

    @Override
    public void wearOutEquipment(int amount) {
        if (getBeeListener() != null) {
            getBeeListener().wearOutEquipment(amount);
        }
    }

    @Override
    public void onQueenDeath() {
        if (getBeeListener() != null) {
            getBeeListener().onQueenDeath();
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
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagCompound tag = new NBTTagCompound();
        structureLogic.writeToNBT(tag);
        nbtTagCompound.setTag("structureLogic", tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        NBTTagCompound tag = nbtTagCompound.getCompoundTag("structureLogic");
        if (tag != null) {
            structureLogic.readFromNBT(tag);
        }
    }

    // TODO why its commented?
    // boolean init;
    // //IStructureLogic structureLogic;
    // private boolean isMaster;
    // protected int masterX;
    // protected int masterZ;
    // protected int masterY;
    // List<TileEntity> tiles;
    //
    // @Override
    // public void updateEntity() {
    // super.updateEntity();
    // if (!BinnieCore.proxy.isSimulating(this.worldObj)) {
    // return;
    // }
    // if (this.worldObj.getWorldTime() % 200L == 0L) {
    // if (!this.isIntegratedIntoStructure() || this.isMaster()) {
    // // this.validateStructure();
    // }
    // // IMultiblockComponent master = this.getCentralTE();
    // // if (master == null) {
    // // return;
    // // }
    // // if (this.getBeeListener() != null) {
    // //
    // ((IAlvearyComponent)master).registerBeeListener(this.getBeeListener());
    // // }
    // if (this.getBeeModifier() != null) {
    // //
    // ((IAlvearyComponent)master).registerBeeModifier(this.getBeeModifier());
    // }
    // this.init = true;
    // }
    // }
    //
    // @Override
    // public void readFromNBT(NBTTagCompound nbttagcompound) {
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
    // public void writeToNBT(NBTTagCompound nbttagcompound) {
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
    // public TileExtraBeeAlveary(AlvearyMachine.AlvearyPackage
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
    // // TileEntity tile = this.worldObj.getTileEntity(this.masterX,
    // this.masterY, this.masterZ);
    // // if (!(tile instanceof IMultiblockComponent)) {
    // // return null;
    // // }
    // // IMultiblockComponent master =
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
    // private boolean isSameTile(TileEntity tile) {
    // return tile.xCoord == this.xCoord && tile.yCoord == this.yCoord &&
    // tile.zCoord == this.zCoord;
    // }
    //
    // // public void setCentralTE(TileEntity tile) {
    // // if (tile == null || tile == this || this.isSameTile(tile)) {
    // // boolean b = false;
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
    // public void registerBeeModifier(IBeeModifier modifier) {
    // }
    //
    // public void removeBeeModifier(IBeeModifier modifier) {
    // }
    //
    // public void addTemperatureChange(float change, float
    // boundaryDown, float boundaryUp) {
    // }
    //
    // public void addHumidityChange(float change, float
    // boundaryDown, float boundaryUp) {
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
    // public float getTerritoryModifier(IBeeGenome genome, float
    // currentModifier) {
    // return (this.getBeeModifier() == null) ? 1.0f :
    // this.getBeeModifier().getTerritoryModifier(genome, currentModifier);
    // }
    //
    // public float getMutationModifier(IBeeGenome genome, final
    // IBeeGenome mate, float currentModifier) {
    // return (this.getBeeModifier() == null) ? 1.0f :
    // this.getBeeModifier().getMutationModifier(genome, mate, currentModifier);
    // }
    //
    // public float getLifespanModifier(IBeeGenome genome, final
    // IBeeGenome mate, float currentModifier) {
    // return (this.getBeeModifier() == null) ? 1.0f :
    // this.getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
    // }
    //
    // public float getProductionModifier(IBeeGenome genome, float
    // currentModifier) {
    // return (this.getBeeModifier() == null) ? 1.0f :
    // this.getBeeModifier().getProductionModifier(genome, currentModifier);
    // }
    //
    // public float getFloweringModifier(IBeeGenome genome, float
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
    // public void registerBeeListener(IBeeListener event) {
    // }
    //
    // public void removeBeeListener(IBeeListener event) {
    // }
    //
    // // public void onQueenChange(ItemStack queen) {
    // // if (this.getBeeListener() != null) {
    // // this.getBeeListener().onQueenChange(queen);
    // // }
    // // }
    // //
    // public void wearOutEquipment(int amount) {
    // if (this.getBeeListener() != null) {
    // this.getBeeListener().wearOutEquipment(amount);
    // }
    // }
    //
    // // public void onQueenDeath(IBee queen) {
    // // if (this.getBeeListener() != null) {
    // // this.getBeeListener().onQueenDeath(queen);
    // // }
    // // }
    // //
    // // public void onPostQueenDeath(IBee queen) {
    // // if (this.getBeeListener() != null) {
    // // this.getBeeListener().onPostQueenDeath(queen);
    // // }
    // // }
    //
    // public boolean onPollenRetrieved(IBee queen, IIndividual
    // pollen, boolean isHandled) {
    // return false;
    // }
    //
    // public boolean onEggLaid(IBee queen) {
    // return false;
    // }
    //
    // public float getGeneticDecay(IBeeGenome genome, float
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
    // // IMultiblockComponent struct = this.getCentralTE();
    // // if (!struct.isIntegratedIntoStructure()) {
    // // return;
    // // }
    // // TileEntity central = (TileEntity)struct;
    // // for (int x = -2; x <= 2; ++x) {
    // // for (int z = -2; z <= 2; ++z) {
    // // for (int y = -2; y <= 2; ++y) {
    // // TileEntity tile = this.getWorldObj().getTileEntity(this.xCoord +
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
