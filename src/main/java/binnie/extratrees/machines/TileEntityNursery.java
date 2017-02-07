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

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class TileEntityNursery extends TileEntityMachine implements IButterflyNursery {
	public TileEntityNursery(final Nursery.PackageNursery pack) {
		super(pack);
	}

	@Nullable
	IButterflyNursery getNursery() {
		return this.getMachine().getInterface(IButterflyNursery.class);
	}

	boolean hasNursery() {
		return this.getNursery() != null;
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}


	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.NORMAL;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.NORMAL;
	}

	public boolean addProduct(final ItemStack product, final boolean all) {
		return false;
	}

	@Override
	@Nullable
	public IButterfly getCaterpillar() {
		IButterflyNursery nursery = this.getNursery();
		return nursery != null ? nursery.getCaterpillar() : null;
	}

	@Override
	@Nullable
	public IIndividual getNanny() {
		return null;
	}

	@Override
	public void setCaterpillar(final IButterfly butterfly) {
		IButterflyNursery nursery = this.getNursery();
		if (nursery != null) {
			nursery.setCaterpillar(butterfly);
		}
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return this.getCaterpillar() == null;
	}

	@Override
	public Biome getBiome() {
		return this.getWorld().getBiome(getPos());
	}

	public void setErrorState(final IErrorState state) {
	}

	@Nullable
	public IErrorState getErrorState() {
		return null;
	}

	public boolean setErrorCondition(final boolean condition, final IErrorState errorState) {
		return false;
	}

	public Set<IErrorState> getErrorStates() {
		return new HashSet<>();
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
