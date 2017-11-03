package binnie.extrabees.items.types;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;

import binnie.core.Mods;
import binnie.core.util.I18N;
import binnie.core.util.RecipeUtil;
import binnie.extrabees.items.ItemHiveFrame;
import binnie.extrabees.utils.BeeModifierLogic;
import binnie.extrabees.utils.EnumBeeBooleanModifier;
import binnie.extrabees.utils.EnumBeeModifier;

public enum EnumHiveFrame implements IHiveFrame, IBeeModifier {

	COCOA{
		@Override
		protected void init(RecipeUtil recipeUtil, ItemStack impregnatedFrame) {
			logic.setModifier(EnumBeeModifier.LIFESPAN, 0.75f, 0.25f);
			logic.setModifier(EnumBeeModifier.PRODUCTION, 1.5f, 5.0f);
			recipeUtil.addRecipe("cocoa_frame", new ItemStack(EnumHiveFrame.COCOA.item),
				" c ",
				"cFc",
				" c ",
				'F', impregnatedFrame,
				'c', new ItemStack(Items.DYE, 1, 3));
		}
	},
	CAGE{
		@Override
		protected void init(RecipeUtil recipeUtil, ItemStack impregnatedFrame) {
			logic.setModifier(EnumBeeModifier.TERRITORY, 0.5f, 0.1f);
			logic.setModifier(EnumBeeModifier.LIFESPAN, 0.75f, 0.5f);
			logic.setModifier(EnumBeeModifier.PRODUCTION, 0.75f, 0.5f);
			recipeUtil.addShapelessRecipe("cage_frame", new ItemStack(EnumHiveFrame.CAGE.item), impregnatedFrame, Blocks.IRON_BARS);
		}
	},
	SOUL(80){
		@Override
		protected void init(RecipeUtil recipeUtil, ItemStack impregnatedFrame) {
			logic.setModifier(EnumBeeModifier.MUTATION, 1.5f, 5.0f);
			logic.setModifier(EnumBeeModifier.LIFESPAN, 0.75f, 0.5f);
			logic.setModifier(EnumBeeModifier.PRODUCTION, 0.25f, 0.1f);
			recipeUtil.addShapelessRecipe("soul_frame", new ItemStack(EnumHiveFrame.SOUL.item), impregnatedFrame, Blocks.SOUL_SAND);
		}
	},
	CLAY{
		@Override
		protected void init(RecipeUtil recipeUtil, ItemStack impregnatedFrame) {
			logic.setModifier(EnumBeeModifier.LIFESPAN, 1.5f, 5.0f);
			logic.setModifier(EnumBeeModifier.MUTATION, 0.5f, 0.2f);
			logic.setModifier(EnumBeeModifier.PRODUCTION, 0.75f, 0.2f);
			recipeUtil.addRecipe("clay_frame", new ItemStack(EnumHiveFrame.CLAY.item),
				" c ",
				"cFc",
				" c ",
				'F', impregnatedFrame,
				'c', Items.CLAY_BALL);
		}
	},
	DEBUG{
		@Override
		protected void init(RecipeUtil recipeUtil, ItemStack impregnatedFrame) {
			logic.setModifier(EnumBeeModifier.LIFESPAN, 1.0E-4f, 1.0E-4f);
		}
	};

	private final Item item;
	private final int maxDamage;
	protected final BeeModifierLogic logic;

	EnumHiveFrame() {
		this(240);
	}

	EnumHiveFrame(int maxDamage) {
		this.maxDamage = maxDamage;
		this.logic = new BeeModifierLogic();
		this.item = new ItemHiveFrame(this).setRegistryName("hive_frame." + name().toLowerCase());
	}

	protected void init(RecipeUtil recipeUtil, ItemStack impregnatedFrame){

	}

	public static void init(RecipeUtil recipeUtil) {
		ItemStack impregnatedFrame = Mods.Forestry.stack("frame_impregnated");
		for(EnumHiveFrame frame : values()){
			frame.init(recipeUtil, impregnatedFrame);
		}
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
		return this.logic.getModifier(EnumBeeModifier.TERRITORY, currentModifier);
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.MUTATION, currentModifier);
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.LIFESPAN, currentModifier);
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.PRODUCTION, currentModifier);
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.FLOWERING, currentModifier);
	}

	@Override
	public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.GENETIC_DECAY, currentModifier);
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
		return I18N.localise("extrabees.item.frame." + this.toString().toLowerCase() + ".name");
	}

	@Override
	public IBeeModifier getBeeModifier() {
		return this;
	}

	public Item getItem() {
		return item;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag flagIn) {
		this.logic.addInformation(stack, tooltip, flagIn);
	}

}
