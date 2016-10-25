package binnie.botany.genetics;

import java.util.Locale;

import binnie.Constants;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlowerType;
import forestry.api.core.IModelManager;
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
    ModelResourceLocation[] flowered;
    ModelResourceLocation[] unflowered;
	ModelResourceLocation seed;
	ModelResourceLocation pollen;

    EnumFlowerType() {
        this(1);
    }

    EnumFlowerType(final int sections) {
        this.sections = sections;
		this.flowered = new ModelResourceLocation[sections];
		this.unflowered = new ModelResourceLocation[sections];
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerModels(Item item, IModelManager manager, EnumFlowerStage type){
    	for (int i = 0; i < this.sections; ++i) {
			final String suf = (i == 0) ? "" : ("" + (i + 1));
			final String pre = (this.sections == 1) ? "" : "double/";
			this.flowered[i] = new ModelResourceLocation(Constants.BOTANY_MOD_ID, "flowers/" + pre + this.toString().toLowerCase() + suf + "_flowered");
			this.unflowered[i] = new ModelResourceLocation(Constants.BOTANY_MOD_ID, "flowers/" + pre + this.toString().toLowerCase() + suf + "_unflowered");
		}
		this.seed = new ModelResourceLocation(Constants.BOTANY_MOD_ID, "flowers/seed");
		this.pollen = new ModelResourceLocation(Constants.BOTANY_MOD_ID, "flowers/pollen");
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public ModelResourceLocation getModel(EnumFlowerStage type, boolean flowered, int section) {
    	if(type == EnumFlowerStage.SEED){
    		return seed;
    	}else if(type == EnumFlowerStage.POLLEN){
    		return pollen;
    	}else{
    		if(flowered){
    			return this.flowered[section % this.sections];
    		}else{
    			return unflowered[section % this.sections];
    		}
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
