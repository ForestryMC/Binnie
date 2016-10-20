package binnie.botany.flower;

import binnie.botany.api.EnumFlowerStage;

public class ItemPollen extends ItemBotany {
    public ItemPollen() {
        super("pollen");
    }

    @Override
    public EnumFlowerStage getStage() {
        return EnumFlowerStage.POLLEN;
    }

    @Override
    public String getTag() {
        return " Pollen";
    }
}
