package binnie.extratrees.machines;

import binnie.core.machines.TileEntityMachine;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashSet;
import java.util.Set;

public class TileEntityNursery extends TileEntityMachine implements IButterflyNursery {
	public TileEntityNursery(Nursery.PackageNursery pack) {
		super(pack);
	}

	IButterflyNursery getNursery() {
		return getMachine().getInterface(IButterflyNursery.class);
	}

	boolean hasNursery() {
		return getNursery() != null;
	}

	public World getWorld() {
		return worldObj;
	}

	public int getXCoord() {
		return xCoord;
	}

	public int getYCoord() {
		return yCoord;
	}

	public int getZCoord() {
		return zCoord;
	}

	public EnumTemperature getTemperature() {
		return null;
	}

	public EnumHumidity getHumidity() {
		return null;
	}

	public boolean addProduct(ItemStack product, boolean all) {
		return false;
	}

	@Override
	public IButterfly getCaterpillar() {
		return hasNursery() ? getNursery().getCaterpillar() : null;
	}

	@Override
	public void setCaterpillar(IButterfly butterfly) {
		if (hasNursery()) {
			getNursery().setCaterpillar(butterfly);
		}
	}

	@Override
	public IIndividual getNanny() {
		return null;
	}

	@Override
	public boolean canNurse(IButterfly butterfly) {
		return getCaterpillar() == null;
	}

	public BiomeGenBase getBiome() {
		return getWorld().getBiomeGenForCoords(xCoord, zCoord);
	}

	public void setErrorState(IErrorState state) {
	}

	public IErrorState getErrorState() {
		return null;
	}

	public void setErrorState(int state) {
	}

	public boolean setErrorCondition(boolean condition, IErrorState errorState) {
		return false;
	}

	public Set<IErrorState> getErrorStates() {
		return new HashSet<>();
	}

	public int getBiomeId() {
		return 0;
	}

	public int getErrorOrdinal() {
		return 0;
	}

	@Override
	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(xCoord, yCoord, zCoord);
	}
}
