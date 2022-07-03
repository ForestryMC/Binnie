package binnie.core.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IHousing;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

class VirtualHousing implements IHousing {
    private EntityPlayer player;

    public VirtualHousing(EntityPlayer player) {
        this.player = player;
    }

    public int getX() {
        return (int) player.posX;
    }

    public int getY() {
        return (int) player.posY;
    }

    public int getZ() {
        return (int) player.posZ;
    }

    public int getBiomeId() {
        return player.worldObj.getBiomeGenForCoords(getX(), getY()).biomeID;
    }

    public EnumTemperature getTemperature() {
        return EnumTemperature.getFromValue(getBiome().temperature);
    }

    public EnumHumidity getHumidity() {
        return EnumHumidity.getFromValue(getBiome().rainfall);
    }

    public World getWorld() {
        return player.worldObj;
    }

    public void setErrorState(int state) {
        // ignored
    }

    public int getErrorOrdinal() {
        return 0;
    }

    public boolean addProduct(ItemStack product, boolean all) {
        return false;
    }

    public GameProfile getOwnerName() {
        return player.getGameProfile();
    }

    public BiomeGenBase getBiome() {
        return player.worldObj.getBiomeGenForCoords(getX(), getZ());
    }

    // public EnumErrorCode getErrorState() {
    // return null;
    // }

    public void setErrorState(IErrorState state) {}

    public boolean setErrorCondition(boolean condition, IErrorState errorState) {
        return false;
    }

    public Set<IErrorState> getErrorStates() {
        return null;
    }

    @Override
    public ChunkCoordinates getCoordinates() {
        return new ChunkCoordinates(getX(), getY(), getZ());
    }
}
