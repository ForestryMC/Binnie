package binnie.extratrees.genetics.fruits;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IFruitFamily;

import binnie.core.Constants;
import binnie.core.util.I18N;

public class ETFruitProviderNone implements IFruitProvider {
	protected final Map<ItemStack, Float> drops;
	protected final IFruitFamily family;
	protected final String name;
	protected static final int RIPENING_PERIOD = 2;

	public ETFruitProviderNone(String name, IFruitFamily family) {
		this.name = name;
		this.family = family;
		this.drops = new HashMap<>();
	}

	public void addDrop(ItemStack product, float chance) {
		this.drops.put(product, chance);
	}

	@Override
	public Map<ItemStack, Float> getSpecialty() {
		return Collections.emptyMap();
	}

	@Override
	public int getDecorativeColor() {
		return 0xffffff;
	}

	@Override
	public String getModID() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	public int getColour(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		return 0xffffff;
	}

	@Override
	public String getDescription() {
		return I18N.localise("extratrees.fruits." + name);
	}

	public String getName() {
		return name;
	}

	@Override
	public IFruitFamily getFamily() {
		return this.family;
	}

	@Override
	public int getRipeningPeriod() {
		return this.RIPENING_PERIOD;
	}

	@Override
	public Map<ItemStack, Float> getProducts() {
		return Collections.unmodifiableMap(drops);
	}

	@Override
	public boolean isFruitLeaf(ITreeGenome genome, World world, BlockPos pos) {
		return false;
	}

	@Override
	public NonNullList<ItemStack> getFruits(ITreeGenome genome, World world, BlockPos pos, int ripeningTime) {
		return NonNullList.create();
	}

	@Nullable
	@Override
	public String getModelName() {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getSprite(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getDecorativeSprite() {
		return null;
	}

	@Override
	public boolean requiresFruitBlocks() {
		return false;
	}

	@Override
	public boolean trySpawnFruitBlock(ITreeGenome genome, World world, Random rand, BlockPos pos) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
	}
}
