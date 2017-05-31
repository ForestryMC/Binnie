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
import forestry.apiculture.FakeBeekeepingLogic;
import forestry.apiculture.InventoryBeeHousing;
import forestry.core.errors.FakeErrorLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

public class VirtualBeeHousing extends VirtualHousing implements IBeeHousing, IBeeModifier {
	ArrayList<IBeeModifier> beeModifier = new ArrayList<>();
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
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

	public ItemStack getQueen() {
		return ItemStack.EMPTY;
	}

	public ItemStack getDrone() {
		return ItemStack.EMPTY;
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
		return FakeErrorLogic.instance;
	}

	@Override
	public boolean canBlockSeeTheSky() {
		return true;
	}

	@Override
	public boolean isRaining() {
		return false;
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

	@Override
	public Iterable<IBeeListener> getBeeListeners() {
		return Collections.emptyList();
	}

	@Override
	public Iterable<IBeeModifier> getBeeModifiers() {
		return beeModifier;
	}

	@Override
	public IBeekeepingLogic getBeekeepingLogic() {
		return FakeBeekeepingLogic.instance;
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
