package binnie.core.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IHousing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Collections;
import java.util.Set;

class VirtualHousing implements IHousing {
	private EntityPlayer player;

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
		return EnumTemperature.getFromValue(this.getBiome().getTemperature());
	}

	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(this.getBiome().getRainfall());
	}

	public World getWorld() {
		return this.player.world;
	}

	public void setErrorState(final int state) {
	}

	public int getErrorOrdinal() {
		return 0;
	}

	public boolean addProduct(final ItemStack product, final boolean all) {
		return false;
	}

	public GameProfile getOwnerName() {
		return this.player.getGameProfile();
	}

	public Biome getBiome() {
		return this.player.world.getBiome(getCoordinates());
	}

	/*public EnumErrorCode getErrorState() {
		return null;
	}*/

	public void setErrorState(final IErrorState state) {
	}

	public boolean setErrorCondition(final boolean condition, final IErrorState errorState) {
		return false;
	}

	public Set<IErrorState> getErrorStates() {
		return Collections.emptySet();
	}

	@Override
	public BlockPos getCoordinates() {
		return new BlockPos(getXCoord(), getYCoord(), getZCoord());
	}
}
