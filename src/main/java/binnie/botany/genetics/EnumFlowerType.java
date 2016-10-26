package binnie.botany.genetics;

import java.util.Locale;

import binnie.Constants;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlowerType;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumFlowerType implements IFlowerType<EnumFlowerType> {
    Dandelion,
    Poppy,
    Orchid,
    Allium,
    Bluet,
    Tulip,
    Daisy,
    Cornflower,
    Pansy,
    Iris,
    Lavender(2),
    Viola,
    Daffodil,
    Dahlia,
    Peony(2),
    Rose(2),
    Lilac(2),
    Hydrangea(2),
    Foxglove(2),
    Zinnia,
    Mums,
    Marigold,
    Geranium,
    Azalea,
    Primrose,
    Aster,
    Carnation,
    Lily,
    Yarrow,
    Petunia,
    Agapanthus,
    Fuchsia,
    Dianthus,
    Forget,
    Anemone,
    Aquilegia,
    Edelweiss,
    Scabious,
    Coneflower,
    Gaillardia,
    Auricula,
    Camellia(2),
    Goldenrod(2),
    Althea(2),
    Penstemon(2),
    Delphinium(2),
    Hollyhock(2);

    public static int highestSection = 2;
	
    int sections;
    ModelResourceLocation flower;
	ModelResourceLocation seed;
	ModelResourceLocation pollen;

    EnumFlowerType() {
        this(1);
    }

    EnumFlowerType(final int sections) {
        this.sections = sections;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerModels(Item item, IModelManager manager, EnumFlowerStage type){
		String pre = (this.sections == 1) ? "" : "double/";
		flower = new ModelResourceLocation(Constants.BOTANY_MOD_ID + "flowers/" + pre + this.toString().toLowerCase(), "inventory");
		seed = new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":flowers/seed", "inventory");
		pollen = new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":flowers/pollen", "inventory");
		ModelBakery.registerItemVariants(item, pollen, seed, flower);
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public ModelResourceLocation getModel(EnumFlowerStage type, boolean flowered) {
    	if(type == EnumFlowerStage.SEED){
    		return seed;
    	}else if(type == EnumFlowerStage.POLLEN){
    		return pollen;
    	}else{
    		return flower;
    	}
	}

    @Override
    public int getID() {
        return this.ordinal();
    }

    @Override
    public int getSections() {
        return this.sections;
    }
	
	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
