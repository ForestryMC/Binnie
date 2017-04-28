package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlowerType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum EnumFlowerType implements IFlowerType {
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

	protected int sections;
	protected IIcon[] stem;
	protected IIcon[] petal;
	protected IIcon[] variant;
	protected IIcon[] unflowered;
	protected IIcon seedStem;
	protected IIcon seedPetal;
	protected IIcon seedVariant;
	protected IIcon pollenStem;
	protected IIcon pollenPetal;
	protected IIcon pollenVariant;
	protected IIcon blank;

	EnumFlowerType() {
		this(1);
	}

	EnumFlowerType(int sections) {
		this.sections = sections;
		stem = new IIcon[sections];
		petal = new IIcon[sections];
		variant = new IIcon[sections];
		unflowered = new IIcon[sections];
	}

	@Override
	public IIcon getStem(EnumFlowerStage stage, boolean flowered, int section) {
		if (stage == EnumFlowerStage.SEED) {
			return seedStem;
		}
		if (stage == EnumFlowerStage.POLLEN) {
			return pollenStem;
		}
		return stem[section % sections];
	}

	@Override
	public IIcon getPetalIcon(EnumFlowerStage stage, boolean flowered, int section) {
		if (stage == EnumFlowerStage.SEED) {
			return seedPetal;
		}
		if (stage == EnumFlowerStage.POLLEN){
			return pollenPetal;
		}
		if (flowered) {
			return petal[section % sections];
		}
		return unflowered[section % sections];
	}

	@Override
	public IIcon getVariantIcon(EnumFlowerStage stage, boolean flowered, int section) {
		return (stage == EnumFlowerStage.SEED) ? seedVariant : ((stage == EnumFlowerStage.POLLEN) ? pollenVariant : (flowered ? variant[section % sections] : blank));
	}

	public void registerIcons(IIconRegister register) {
		for (int i = 0; i < sections; ++i) {
			String suf = (i == 0) ? "" : ("" + (i + 1));
			String pre = (sections == 1) ? "" : "double/";
			String id = pre + toString().toLowerCase() + suf;
			stem[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".0");
			petal[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".1");
			variant[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".2");
			unflowered[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".3");
		}

		blank = Botany.proxy.getIcon(register, "flowers/blank");
		seedStem = Botany.proxy.getIcon(register, "flowers/seed.0");
		seedPetal = Botany.proxy.getIcon(register, "flowers/seed.1");
		seedVariant = Botany.proxy.getIcon(register, "flowers/seed.2");
		pollenStem = Botany.proxy.getIcon(register, "flowers/pollen.0");
		pollenPetal = Botany.proxy.getIcon(register, "flowers/pollen.1");
		pollenVariant = Botany.proxy.getIcon(register, "flowers/pollen.2");
	}

	@Override
	public int getID() {
		return ordinal();
	}

	@Override
	public int getSections() {
		return sections;
	}

	public IIcon getBlank() {
		return blank;
	}
}
