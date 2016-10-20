// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.flower;

import binnie.botany.api.EnumFlowerStage;

public class ItemFlower extends ItemBotany
{
	public ItemFlower() {
		super("itemFlower");
	}

	@Override
	public EnumFlowerStage getStage() {
		return EnumFlowerStage.FLOWER;
	}

	@Override
	public String getTag() {
		return "";
	}
}
