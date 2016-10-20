package binnie.extratrees.machines;

import binnie.core.machines.TileEntityMachine;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;
import java.util.Set;

public class TileEntityNursery extends TileEntityMachine implements IButterflyNursery {
    public TileEntityNursery(final Nursery.PackageNursery pack) {
        super(pack);
    }

    IButterflyNursery getNursery() {
        return this.getMachine().getInterface(IButterflyNursery.class);
    }

    boolean hasNursery() {
        return this.getNursery() != null;
    }

    public World getWorld() {
        return this.worldObj;
    }


    public EnumTemperature getTemperature() {
        return null;
    }

    public EnumHumidity getHumidity() {
        return null;
    }

    public boolean addProduct(final ItemStack product, final boolean all) {
        return false;
    }

    @Override
    public IButterfly getCaterpillar() {
        return this.hasNursery() ? this.getNursery().getCaterpillar() : null;
    }

    @Override
    public IIndividual getNanny() {
        return null;
    }

    @Override
    public void setCaterpillar(final IButterfly butterfly) {
        if (this.hasNursery()) {
            this.getNursery().setCaterpillar(butterfly);
        }
    }

    @Override
    public boolean canNurse(final IButterfly butterfly) {
        return this.getCaterpillar() == null;
    }

    public Biome getBiome() {
        return this.getWorld().getBiome(getPos());
    }

    public void setErrorState(final IErrorState state) {
    }

    public IErrorState getErrorState() {
        return null;
    }

    public boolean setErrorCondition(final boolean condition, final IErrorState errorState) {
        return false;
    }

    public Set<IErrorState> getErrorStates() {
        return new HashSet<IErrorState>();
    }

    public int getBiomeId() {
        return 0;
    }

    public void setErrorState(final int state) {
    }

    public int getErrorOrdinal() {
        return 0;
    }

    @Override
    public BlockPos getCoordinates() {
        return getPos();
    }
}
