package binnie.core.genetics;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import com.mojang.authlib.GameProfile;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IHousing;

class VirtualHousing implements IHousing {
	private final EntityPlayer player;

	public VirtualHousing(final EntityPlayer player) {
		this.player = player;
	}

	public int getXCoord() {
		return (int) this.player.posX;
	}

	public int getYCoord() {
		return (int) this.player.posY;
	}

	public int getZCoord() {
		return (int) this.player.posZ;
	}

	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromValue(this.getBiome().getDefaultTemperature());
	}

	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(this.getBiome().getRainfall());
	}

	public World getWorld() {
		return this.player.world;
	}

	public GameProfile getOwnerName() {
		return this.player.getGameProfile();
	}

	public Biome getBiome() {
		return this.player.world.getBiome(getCoordinates());
	}

	@Override
	public BlockPos getCoordinates() {
		return new BlockPos(getXCoord(), getYCoord(), getZCoord());
	}
}
