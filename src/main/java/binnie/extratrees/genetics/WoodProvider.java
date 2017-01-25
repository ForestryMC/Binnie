package binnie.extratrees.genetics;

import binnie.Constants;
import binnie.extratrees.block.EnumETLog;
import forestry.api.arboriculture.EnumForestryWoodType;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodProvider;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.ITextureManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import javax.annotation.Nonnull;

public class WoodProvider implements IWoodProvider {
	private IWoodType type;
	private TextureAtlasSprite trunk;
	private TextureAtlasSprite bark;
	private String modID = "";

	public WoodProvider(IWoodType type) {
		this.type = type;
		if (type instanceof EnumETLog) {
			modID = Constants.EXTRA_TREES_MOD_ID;
		} else if (type instanceof EnumVanillaWoodType) {
			modID = "minecraft";
		} else if (type instanceof EnumForestryWoodType) {
			modID = "forestry";
		}
	}

	@Override
	public void registerSprites(Item item, ITextureManager manager) {
		TextureMap textureMap = FMLClientHandler.instance().getClient().getTextureMapBlocks();
		trunk = textureMap.registerSprite(new ResourceLocation(type.getHeartTexture()));
		bark = textureMap.registerSprite(new ResourceLocation(type.getBarkTexture()));

	}

	@Nonnull
	@Override
	public TextureAtlasSprite getSprite(boolean isTop) {
		if (isTop) {
			return trunk;
		} else {
			return bark;
		}
	}

	@Override
	public ItemStack getWoodStack() {
		return TreeManager.woodAccess.getStack(type, WoodBlockKind.LOG, false);
	}

	@Override
	public int getCarbonization() {
		return type.getCarbonization();
	}

	@Override
	public float getCharcoalChance(int numberOfCharcoal) {
		return type.getCharcoalChance(numberOfCharcoal);
	}
}
