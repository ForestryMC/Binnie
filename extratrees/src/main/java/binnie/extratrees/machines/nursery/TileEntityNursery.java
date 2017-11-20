package binnie.extratrees.machines.nursery;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;

import binnie.core.machines.TileEntityMachine;

public class TileEntityNursery extends TileEntityMachine implements IButterflyNursery {
	public TileEntityNursery(final Nursery.PackageNursery pack) {
		super(pack);
	}

	@Nullable
	IButterflyNursery getNursery() {
		return this.getMachine().getInterface(IButterflyNursery.class);
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.NORMAL;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.NORMAL;
	}

	@Override
	@Nullable
	public IButterfly getCaterpillar() {
		IButterflyNursery nursery = this.getNursery();
		return nursery != null ? nursery.getCaterpillar() : null;
	}

	@Override
	public void setCaterpillar(@Nullable final IButterfly butterfly) {
		IButterflyNursery nursery = this.getNursery();
		if (nursery != null) {
			nursery.setCaterpillar(butterfly);
		}
	}

	@Override
	@Nullable
	public IIndividual getNanny() {
		return null;
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return this.getCaterpillar() == null;
	}

	@Override
	public Biome getBiome() {
		return this.getWorld().getBiome(getPos());
	}

	@Override
	public BlockPos getCoordinates() {
		return getPos();
	}

	@Override
	public World getWorldObj() {
		return world;
	}
}
