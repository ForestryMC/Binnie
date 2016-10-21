package binnie.botany.genetics;

import binnie.botany.api.IFlowerType;

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

    int sections;
//	IIcon[] stem;
//	IIcon[] petal;
//	IIcon[] variant;
//	IIcon[] unflowered;
//	IIcon seedStem;
//	IIcon seedPetal;
//	IIcon seedVariant;
//	IIcon pollenStem;
//	IIcon pollenPetal;
//	IIcon pollenVariant;
//	IIcon blank;

    EnumFlowerType() {
        this(1);
    }

    EnumFlowerType(final int sections) {
        this.sections = 1;
        this.sections = sections;
//		this.stem = new IIcon[sections];
//		this.petal = new IIcon[sections];
//		this.variant = new IIcon[sections];
//		this.unflowered = new IIcon[sections];
    }

//	@Override
//	public IIcon getStem(final EnumFlowerStage stage, final boolean flowered, final int section) {
//		return (stage == EnumFlowerStage.SEED) ? this.seedStem : ((stage == EnumFlowerStage.POLLEN) ? this.pollenStem : this.stem[section % this.sections]);
//	}
//
//	@Override
//	public IIcon getPetalIcon(final EnumFlowerStage stage, final boolean flowered, final int section) {
//		return (stage == EnumFlowerStage.SEED) ? this.seedPetal : ((stage == EnumFlowerStage.POLLEN) ? this.pollenPetal : (flowered ? this.petal[section % this.sections] : this.unflowered[section % this.sections]));
//	}
//
//	@Override
//	public IIcon getVariantIcon(final EnumFlowerStage stage, final boolean flowered, final int section) {
//		return (stage == EnumFlowerStage.SEED) ? this.seedVariant : ((stage == EnumFlowerStage.POLLEN) ? this.pollenVariant : (flowered ? this.variant[section % this.sections] : this.blank));
//	}
//
//	public void registerIcons(final IIconRegister register) {
//		for (int i = 0; i < this.sections; ++i) {
//			final String suf = (i == 0) ? "" : ("" + (i + 1));
//			final String pre = (this.sections == 1) ? "" : "double/";
//			this.stem[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".0");
//			this.petal[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".1");
//			this.variant[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".2");
//			this.unflowered[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".3");
//		}
//		this.blank = Botany.proxy.getIcon(register, "flowers/blank");
//		this.seedStem = Botany.proxy.getIcon(register, "flowers/seed.0");
//		this.seedPetal = Botany.proxy.getIcon(register, "flowers/seed.1");
//		this.seedVariant = Botany.proxy.getIcon(register, "flowers/seed.2");
//		this.pollenStem = Botany.proxy.getIcon(register, "flowers/pollen.0");
//		this.pollenPetal = Botany.proxy.getIcon(register, "flowers/pollen.1");
//		this.pollenVariant = Botany.proxy.getIcon(register, "flowers/pollen.2");
//	}

    @Override
    public int getID() {
        return this.ordinal();
    }

    @Override
    public int getSections() {
        return this.sections;
    }

//	public IIcon getBlank() {
//		return this.blank;
//	}
}
