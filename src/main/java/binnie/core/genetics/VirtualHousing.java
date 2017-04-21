package binnie.core.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IHousing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Set;

class VirtualHousing implements IHousing {
	private EntityPlayer player;

	public VirtualHousing(EntityPlayer player) {
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

	public int getBiomeId() {
		return this.player.worldObj.getBiomeGenForCoords(this.getXCoord(), this.getYCoord()).biomeID;
	}

	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromValue(this.getBiome().temperature);
	}

	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(this.getBiome().rainfall);
	}

	public World getWorld() {
		return this.player.worldObj;
	}

	public void setErrorState(int state) {
	}

	public int getErrorOrdinal() {
		return 0;
	}

	public boolean addProduct(ItemStack product, boolean all) {
		return false;
	}

	public GameProfile getOwnerName() {
		return this.player.getGameProfile();
	}

	public BiomeGenBase getBiome() {
		return this.player.worldObj.getBiomeGenForCoords(this.getXCoord(), this.getZCoord());
	}

	// public EnumErrorCode getErrorState() {
	// return null;
	// }

	public void setErrorState(IErrorState state) {
	}

	public boolean setErrorCondition(boolean condition, IErrorState errorState) {
		return false;
	}

	public Set<IErrorState> getErrorStates() {
		return null;
	}

	@Override
	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(getXCoord(), getYCoord(), getZCoord());
	}
}
