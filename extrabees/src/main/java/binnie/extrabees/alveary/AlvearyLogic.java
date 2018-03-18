package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.DefaultBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.IIndividual;

import binnie.extrabees.client.gui.AlvearyContainer;

public abstract class AlvearyLogic extends DefaultBeeModifier implements IBeeListener, ICapabilityProvider, INbtWritable, INbtReadable {
	/* Constants */
	static final String INVENTORY_NBT_KEY = "inventory";
	static final String ENERGY_NBT_KEY = "Energy";

	/* IBeeListener */
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

	/* Updates */
	public void updateClient(TileEntityExtraBeesAlvearyPart tile) {

	}

	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {

	}

	/* Save and Load*/
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		return nbt;
	}

	/* Capability */
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return false;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return null;
	}

	/* Gui */
	public boolean hasGui() {
		return false;
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return null;
	}

	@Nullable
	public AlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return null;
	}

	/* Multiblock */
	public void onMachineAssembled(TileEntityExtraBeesAlvearyPart tile) {
	}

	public void onMachineBroken(TileEntityExtraBeesAlvearyPart tile) {
	}
}
