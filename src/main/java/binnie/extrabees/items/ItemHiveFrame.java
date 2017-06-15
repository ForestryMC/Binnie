package binnie.extrabees.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import forestry.core.utils.Translator;

import binnie.extrabees.items.types.EnumHiveFrame;
import binnie.extrabees.utils.ExtraBeesResourceLocation;

public class ItemHiveFrame extends Item implements IHiveFrame, IBeeModifier, IItemModelRegister {

	private final EnumHiveFrame frame;

	public ItemHiveFrame(final EnumHiveFrame frame) {
		this.frame = frame;
		this.setMaxDamage(frame.getMaxDamage());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("hive_frame");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemStack) {
		return this.frame.getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void registerModel(Item item, IModelManager manager) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ExtraBeesResourceLocation("frames/" + getRegistryName().getResourcePath()), "inventroy"));
	}

	@Override
	public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
		return this.frame.getTerritoryModifier(genome, currentModifier);
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return this.frame.getMutationModifier(genome, mate, currentModifier);
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.frame.getLifespanModifier(genome, mate, currentModifier);
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return this.frame.getProductionModifier(genome, currentModifier);
	}

	@Override
	public ItemStack frameUsed(final IBeeHousing housing, final ItemStack frame, final IBee queen, final int wear) {
		frame.setItemDamage(frame.getItemDamage() + wear);
		if (frame.getItemDamage() >= frame.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return frame;
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		this.frame.addInformation(stack, playerIn, tooltip, advanced);
		if (!stack.isItemDamaged()) {
			tooltip.add(Translator.translateToLocalFormatted("item.for.durability", stack.getMaxDamage()));
		}
	}

	@Override
	public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public IBeeModifier getBeeModifier() {
		return this;
	}
}
