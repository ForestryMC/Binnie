package binnie.botany.genetics;

import java.util.Locale;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;

import binnie.core.Constants;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IFlowerType;

public enum EnumFlowerType implements IFlowerType<EnumFlowerType> {
	DANDELION,
	POPPY,
	ORCHID,
	ALLIUM,
	BLUET,
	TULIP,
	DAISY,
	CORNFLOWER,
	PANSY,
	IRIS,
	LAVENDER(2),
	VIOLA,
	DAFFODIL,
	DAHLIA,
	PEONY(2),
	ROSE(2),
	LILAC(2),
	HYDRANGEA(2),
	FOXGLOVE(2),
	ZINNIA,
	MUMS,
	MARIGOLD,
	GERANIUM,
	AZALEA,
	PRIMROSE,
	ASTER,
	CARNATION,
	LILY,
	YARROW,
	Petunia,
	AGAPANTHUS,
	FUCHSIA,
	DIANTHUS,
	FORGET,
	ANEMONE,
	AQUILEGIA,
	EDELWEISS,
	SCABIOUS,
	CONEFLOWER,
	GAILLARDIA,
	AURICULA,
	CAMELLIA(2),
	GOLDENROD(2),
	ALTHEA(2),
	PENSTEMON(2),
	DELPHINIUM(2),
	HOLLYHOCK(2);

	public static EnumFlowerType[] VALUES = values();
	public static int highestSection = 2;

	int sections;
	ModelResourceLocation flower;
	ModelResourceLocation seed;
	ModelResourceLocation pollen;

	EnumFlowerType() {
		this(1);
	}

	EnumFlowerType(int sections) {
		this.sections = sections;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(Item item, IModelManager manager, EnumFlowerStage type) {
		String pre = (sections == 1) ? "" : "double/";
		flower = new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":flowers/" + pre + getName(), "inventory");
		seed = new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":flowers/seed", "inventory");
		pollen = new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":flowers/pollen", "inventory");
		ModelBakery.registerItemVariants(item, pollen, seed, flower);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(EnumFlowerStage type, boolean flowered) {
		if (type == EnumFlowerStage.SEED) {
			return seed;
		} else if (type == EnumFlowerStage.POLLEN) {
			return pollen;
		}
		return flower;
	}

	@Override
	public int getSections() {
		return sections;
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
