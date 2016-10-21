package binnie.extratrees.genetics;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.BlockETLog;
import binnie.extratrees.block.EnumExtraTreeLog;
import forestry.api.arboriculture.*;
import forestry.api.core.ITextureManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

public class WoodProvider implements IWoodProvider {
    private IWoodType type;
    private TextureAtlasSprite trunk;
    private TextureAtlasSprite bark;
    private String modID = "";

    public WoodProvider(IWoodType type) {
        this.type = type;
        if (type instanceof EnumExtraTreeLog) {
            modID = ExtraTrees.MOD_ID;
        } else if (type instanceof EnumVanillaWoodType) {
            modID = "minecraft";
        } else if (type instanceof EnumForestryWoodType) {
            modID = "forestry";
        }
    }

    @Override
    public void registerSprites(Item item, ITextureManager manager) {
        TextureMap textureMap = FMLClientHandler.instance().getClient().getTextureMapBlocks();
        trunk = textureMap.registerSprite(new ResourceLocation(modID, type.getHeartTexture()));
        bark = textureMap.registerSprite(new ResourceLocation(modID, type.getBarkTexture()));

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
        if (type instanceof EnumExtraTreeLog) {
            int group = type.getMetadata()/EnumExtraTreeLog.values().length;
            return GameRegistry.makeItemStack(modID+":log."+group,type.getMetadata()%4,1,null);
        } else {
            return TreeManager.woodAccess.getStack(type, WoodBlockKind.LOG, false);
        }
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
