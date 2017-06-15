package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;

import binnie.extrabees.client.gui.AbstractAlvearyContainer;

public abstract class AbstractAlvearyLogic implements IBeeModifier, IBeeListener, ICapabilityProvider {

	@Override
	public float getTerritoryModifier(@Nonnull IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getMutationModifier(@Nonnull IBeeGenome genome, @Nonnull IBeeGenome mate, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getLifespanModifier(@Nonnull IBeeGenome genome, @Nullable IBeeGenome mate, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getProductionModifier(@Nonnull IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getFloweringModifier(@Nonnull IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getGeneticDecay(@Nonnull IBeeGenome genome, float currentModifier) {
		return 1.0f;
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
		return false;
	}

	@Override
	public void wearOutEquipment(int amount) {

	}

	@Override
	public void onQueenDeath() {

	}

	@Override
	public boolean onPollenRetrieved(@Nonnull IIndividual pollen) {
		return false;
	}

	public void updateClient(TileEntityExtraBeesAlvearyPart tile) {

	}

	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {

	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return null;
	}

	@Nullable
	public AbstractAlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return null;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return false;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return null;
	}

	public IEnergyStorage getEnergyStorage() {
		return null;
	}

	public boolean hasGui() {
		return false;
	}
}
