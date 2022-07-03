package binnie.core.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.IIndividual;
import forestry.apiculture.InventoryBeeHousing;
import forestry.core.access.FakeAccessHandler;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;

public class VirtualBeeHousing extends VirtualHousing implements IBeeHousing, IBeeModifier {
    ArrayList<IBeeModifier> beeModifier = new ArrayList<>();
    private InventoryBeeHousing beeInventory;

    public VirtualBeeHousing(EntityPlayer player) {
        super(player);
        beeModifier.add(this);
        beeInventory = new InventoryBeeHousing(12, FakeAccessHandler.getInstance());
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    public ItemStack getQueen() {
        return null;
    }

    public ItemStack getDrone() {
        return null;
    }

    public void setQueen(ItemStack itemStack) {
        // ignored
    }

    public void setDrone(ItemStack itemStack) {}

    public boolean canBreed() {
        return true;
    }

    @Override
    public boolean addProduct(ItemStack product, boolean all) {
        return false;
    }

    public void wearOutEquipment(int amount) {}

    public void onQueenChange(ItemStack queen) {}

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
        return getBiomeId() == BiomeGenBase.hell.biomeID;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    public void onQueenDeath(IBee queen) {}

    public void onPostQueenDeath(IBee queen) {}

    public boolean onPollenRetrieved(IBee queen, IIndividual pollen, boolean isHandled) {
        return false;
    }

    public boolean onEggLaid(IBee queen) {
        return false;
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
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

    // TODO ??
    @Override
    public Vec3 getBeeFXCoordinates() {
        return Vec3.createVectorHelper(0, -100000, 0);
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
