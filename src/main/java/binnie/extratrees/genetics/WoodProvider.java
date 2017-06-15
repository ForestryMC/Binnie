package binnie.extratrees.genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IWoodProvider;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.ITextureManager;

public class WoodProvider implements IWoodProvider {
	private IWoodType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private TextureAtlasSprite trunk;

	@SideOnly(Side.CLIENT)
	@Nullable
	private TextureAtlasSprite bark;

	public WoodProvider(IWoodType type) {
		this.type = type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites(Item item, ITextureManager manager) {
		TextureMap textureMap = FMLClientHandler.instance().getClient().getTextureMapBlocks();
		trunk = textureMap.registerSprite(new ResourceLocation(type.getHeartTexture()));
		bark = textureMap.registerSprite(new ResourceLocation(type.getBarkTexture()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite(boolean isTop) {
		if (isTop) {
			Preconditions.checkState(trunk != null, "sprites have not been registered");
			return trunk;
		} else {
			Preconditions.checkState(bark != null, "sprites have not been registered");
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
