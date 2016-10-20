package binnie.core.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.*;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.IIndividual;
import forestry.apiculture.InventoryBeeHousing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class VirtualBeeHousing extends VirtualHousing implements IBeeHousing, IBeeModifier {
    ArrayList<IBeeModifier> beeModifier = new ArrayList<IBeeModifier>();
    private InventoryBeeHousing beeInventory;

    public VirtualBeeHousing(final EntityPlayer player) {
        super(player);
        beeModifier.add(this);
        beeInventory = new InventoryBeeHousing(12);

    }

    @Override
    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public ItemStack getQueen() {
        return null;
    }

    public ItemStack getDrone() {
        return null;
    }

    public void setQueen(final ItemStack itemstack) {
    }

    public void setDrone(final ItemStack itemstack) {
    }

    public boolean canBreed() {
        return true;
    }

    @Override
    public boolean addProduct(final ItemStack product, final boolean all) {
        return false;
    }

    public void wearOutEquipment(final int amount) {
    }

    public void onQueenChange(final ItemStack queen) {
    }

    @Override
    public boolean isSealed() {
        return false;
    }

    @Override
    public boolean isSelfLighted() {
        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        return false;
    }

    @Override
    public boolean isHellish() {
        return this.getBiome() == Biomes.HELL;
    }

    @Override
    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public void onQueenDeath(final IBee queen) {
    }

    public void onPostQueenDeath(final IBee queen) {
    }

    public boolean onPollenRetrieved(final IBee queen, final IIndividual pollen, final boolean isHandled) {
        return false;
    }

    public boolean onEggLaid(final IBee queen) {
        return false;
    }

    @Override
    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    @Override
    public IErrorLogic getErrorLogic() {
        return null;
    }

    // TODO ??
    @Override
    public boolean canBlockSeeTheSky() {
        return true;
    }

    @Override
    public World getWorldObj() {
        return getWorld();
    }

    @Override
    public Vec3d getBeeFXCoordinates() {
        return new Vec3d(0, -100000, 0);
    }

    @Override
    public IBeeHousingInventory getBeeInventory() {
        return beeInventory;
    }

    // TODO ??
    @Override
    public Iterable<IBeeListener> getBeeListeners() {
        return null;
    }

    @Override
    public Iterable<IBeeModifier> getBeeModifiers() {
        return beeModifier;
    }

    @Override
    public IBeekeepingLogic getBeekeepingLogic() {
        return null;
    }

    @Override
    public int getBlockLightValue() {
        return 0;
    }

    @Override
    public GameProfile getOwner() {
        return getOwnerName();
    }

}
