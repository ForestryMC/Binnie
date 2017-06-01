package binnie.extrabees.items.types;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.ItemHiveFrame;
import binnie.extrabees.utils.BeeModifierLogic;
import binnie.extrabees.utils.EnumBeeBooleanModifier;
import binnie.extrabees.utils.EnumBeeModifier;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import forestry.apiculture.PluginApiculture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public enum EnumHiveFrame implements IHiveFrame, IBeeModifier {

	Cocoa,
	Cage,
	Soul(80),
	Clay,
	Debug;

	private final Item item;
	private final int maxDamage;
	private final BeeModifierLogic logic;

	EnumHiveFrame() {
		this(240);
	}

	EnumHiveFrame(int maxDamage) {
		this.maxDamage = maxDamage;
		this.logic = new BeeModifierLogic();
		this.item = new ItemHiveFrame(this).setRegistryName("hive_frame." + name().toLowerCase());
	}

	public static void init() {
		EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.25f);
		EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.Production, 1.5f, 5.0f);
		EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Territory, 0.5f, 0.1f);
		EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.5f);
		EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Production, 0.75f, 0.5f);
		EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Mutation, 1.5f, 5.0f);
		EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.5f);
		EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Production, 0.25f, 0.1f);
		EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Lifespan, 1.5f, 5.0f);
		EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Mutation, 0.5f, 0.2f);
		EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Production, 0.75f, 0.2f);
		EnumHiveFrame.Debug.logic.setModifier(EnumBeeModifier.Lifespan, 1.0E-4f, 1.0E-4f);
		ItemStack impregnatedFrame = PluginApiculture.getItems().frameImpregnated.getItemStack();
		GameRegistry.addRecipe(new ItemStack(EnumHiveFrame.Cocoa.item), " c ", "cFc", " c ", 'F', impregnatedFrame, 'c', new ItemStack(Items.DYE, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(EnumHiveFrame.Cage.item), impregnatedFrame, Blocks.IRON_BARS);
		GameRegistry.addShapelessRecipe(new ItemStack(EnumHiveFrame.Soul.item), impregnatedFrame, Blocks.SOUL_SAND);
		GameRegistry.addRecipe(new ItemStack(EnumHiveFrame.Clay.item), " c ", "cFc", " c ", 'F', impregnatedFrame, 'c', Items.CLAY_BALL);
	}

	public int getIconIndex() {
		return 55 + this.ordinal();
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	@Override
	public ItemStack frameUsed(final IBeeHousing house, final ItemStack frame, final IBee queen, final int wear) {
		frame.setItemDamage(frame.getItemDamage() + wear);
		if (frame.getItemDamage() >= frame.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return frame;
	}

	@Override
	public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Territory, currentModifier);
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Mutation, currentModifier);
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Lifespan, currentModifier);
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Production, currentModifier);
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Flowering, currentModifier);
	}

	@Override
	public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.GeneticDecay, currentModifier);
	}

	@Override
	public boolean isSealed() {
		return this.logic.getModifier(EnumBeeBooleanModifier.Sealed);
	}

	@Override
	public boolean isSelfLighted() {
		return this.logic.getModifier(EnumBeeBooleanModifier.SelfLighted);
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.logic.getModifier(EnumBeeBooleanModifier.SunlightStimulated);
	}

	@Override
	public boolean isHellish() {
		return this.logic.getModifier(EnumBeeBooleanModifier.Hellish);
	}

	public String getName() {
		return ExtraBees.proxy.localise("item.frame." + this.toString().toLowerCase());
	}

	@Override
	public IBeeModifier getBeeModifier() {
		return this;
	}

	public Item getItem() {
		return item;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		this.logic.addInformation(stack, playerIn, tooltip, advanced);
	}

}
