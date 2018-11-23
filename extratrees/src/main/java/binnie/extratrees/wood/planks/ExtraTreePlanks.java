package binnie.extratrees.wood.planks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;

import binnie.core.Constants;
import binnie.core.util.I18N;
import binnie.extratrees.wood.EnumETLog;
import binnie.extratrees.wood.IFenceProvider;

public enum ExtraTreePlanks implements IPlankType, IFenceProvider, IStringSerializable {
	Fir(12815444),
	Cedar(14181940),
	Hemlock(13088108),
	Cypress(16169052),
	Fig(13142058),
	Beech(14784849),
	Alder(12092755),
	Hazel(13480341),
	Hornbeam(12818528),
	Box(16511430),
	Butternut(15510138),
	Hickory(14333070),
	Whitebeam(13222585),
	Elm(15772004),
	Apple(6305064),
	Yew(14722426),
	Pear(12093805),
	Hawthorn(13402978),
	Rowan(13610394),
	Elder(12489337),
	Maclura(15970862),
	Syzgium(15123393),
	Brazilwood(7487063),
	Logwood(10762028),
	Iroko(7681024),
	Locust(12816736),
	Eucalyptus(16165771),
	Purpleheart(5970991),
	Ash(16107368),
	Holly(16512743),
	Olive(11578760),
	Sweetgum(13997656),
	Rosewood(7738624),
	Gingko(16050106),
	PinkIvory(15502496),
	Banana(9466691);

	public static final ExtraTreePlanks[] VALUES = values();
	private final int color;
	private EnumETLog woodType;
	@SideOnly(Side.CLIENT)
	private TextureAtlasSprite sprite;

	ExtraTreePlanks(int color) {
		this.color = color;
	}

	public static void initWoodTypes() {
		Fir.setWoodType(EnumETLog.Fir);
		Cedar.setWoodType(EnumETLog.Cedar);
		Hemlock.setWoodType(EnumETLog.Hemlock);
		Cypress.setWoodType(EnumETLog.Cypress);
		Fig.setWoodType(EnumETLog.Fig);
		Beech.setWoodType(EnumETLog.Beech);
		Alder.setWoodType(EnumETLog.Alder);
		Hazel.setWoodType(EnumETLog.Hazel);
		Hornbeam.setWoodType(EnumETLog.Hornbeam);
		Box.setWoodType(EnumETLog.Box);
		Butternut.setWoodType(EnumETLog.Butternut);
		Hickory.setWoodType(EnumETLog.Hickory);
		Whitebeam.setWoodType(EnumETLog.Whitebeam);
		Elm.setWoodType(EnumETLog.Elm);
		Apple.setWoodType(EnumETLog.Apple);
		Yew.setWoodType(EnumETLog.Yew);
		Pear.setWoodType(EnumETLog.Pear);
		Hawthorn.setWoodType(EnumETLog.Hawthorn);
		Rowan.setWoodType(EnumETLog.Rowan);
		Elder.setWoodType(EnumETLog.Elder);
		Maclura.setWoodType(EnumETLog.Maclura);
		Syzgium.setWoodType(EnumETLog.Syzgium);
		Brazilwood.setWoodType(EnumETLog.Brazilwood);
		Logwood.setWoodType(EnumETLog.Logwood);
		Iroko.setWoodType(EnumETLog.Iroko);
		Locust.setWoodType(EnumETLog.Locust);
		Eucalyptus.setWoodType(EnumETLog.Eucalyptus);
		Purpleheart.setWoodType(EnumETLog.Purpleheart);
		Ash.setWoodType(EnumETLog.Ash);
		Holly.setWoodType(EnumETLog.Holly);
		Olive.setWoodType(EnumETLog.Olive);
		Sweetgum.setWoodType(EnumETLog.Sweetgum);
		Rosewood.setWoodType(EnumETLog.Rosewood);
		Gingko.setWoodType(EnumETLog.Gingko);
		PinkIvory.setWoodType(EnumETLog.PinkIvory);
		Banana.setWoodType(EnumETLog.Banana);
	}

	@Override
	public EnumETLog getWoodType() {
		return woodType;
	}

	public void setWoodType(EnumETLog woodType) {
		this.woodType = woodType;
	}

	@Override
	public String getName() {
		return "block.planks." + this.toString().toLowerCase();
	}

	@Override
	public String getDesignMaterialName() {
		return I18N.localise("extratrees.block.planks." + this.toString().toLowerCase());
	}

	public String getPlankTextureName() {
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/planks/" + name();
	}

	@Override
	public String getDescription() {
		return I18N.localise("extratrees.block.planks." + this.toString().toLowerCase() + ".desc");
	}

	@Override
	public int getColour() {
		return this.color;
	}

	@Override
	public ItemStack getStack(boolean fireproof) {
		return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.PLANKS, fireproof);
	}

	@Override
	public ItemStack getStack() {
		return getStack(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite() {
		return sprite;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerSprites(TextureMap map) {
		sprite = map.registerSprite(new ResourceLocation(getPlankTextureName()));
	}

	@Override
	public ItemStack getFence() {
		return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.FENCE, false);
	}
}
