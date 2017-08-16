package binnie.extratrees.genetics.fruits;

import javax.annotation.Nullable;
import java.awt.Color;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IFruitFamily;

public class ETFruitProviderRipening extends ETFruitProviderNone {

	private int diffR, diffG, diffB;
	private int ripeningPeriod;
	private int colourCallow = 0xffffff;
	@Nullable
	private FruitSprite sprite;

	public ETFruitProviderRipening(String name, IFruitFamily family, FruitSprite sprite) {
		super(name, family);
		this.sprite = sprite;
	}

	public ETFruitProviderRipening setColours(Color ripe, Color callow) {
		colourCallow = callow.getRGB();
		int ripeRGB = ripe.getRGB();

		diffR = (ripeRGB >> 16 & 255) - (colourCallow >> 16 & 255);
		diffG = (ripeRGB >> 8 & 255) - (colourCallow >> 8 & 255);
		diffB = (ripeRGB & 255) - (colourCallow & 255);

		return this;
	}

	public ETFruitProviderRipening setRipeningPeriod(int period) {
		ripeningPeriod = period;
		return this;
	}

	private float getRipeningStage(final int ripeningTime) {
		if (ripeningTime >= this.ripeningPeriod) {
			return 1.0f;
		}
		return ripeningTime / this.ripeningPeriod;
	}

	@Override
	public int getColour(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		float stage = getRipeningStage(ripeningTime);
		return getColour(stage);
	}

	private int getColour(float stage) {
		int r = (colourCallow >> 16 & 255) + (int) (diffR * stage);
		int g = (colourCallow >> 8 & 255) + (int) (diffG * stage);
		int b = (colourCallow & 255) + (int) (diffB * stage);

		return (r & 255) << 16 | (g & 255) << 8 | b & 255;
	}

	@Override
	public boolean isFruitLeaf(ITreeGenome genome, World world, BlockPos pos) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		sprite.registerSprites();
	}

	@Nullable
	@Override
	public ResourceLocation getSprite(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		if (sprite != null) {
			return sprite.getLocation();
		} else {
			return null;
		}
	}
}
