package binnie.extrabees.apiary;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHiveFrame extends Item implements IHiveFrame, IBeeModifier {
    protected EnumHiveFrame frame;

    public ItemHiveFrame(EnumHiveFrame frame) {
        this.frame = frame;
        setMaxDamage(frame.maxDamage);
        setCreativeTab(Tabs.tabApiculture);
        setMaxStackSize(1);
        setUnlocalizedName("hiveFrame");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return frame.getName();
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return frame.getTerritoryModifier(genome, currentModifier);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return frame.getMutationModifier(genome, mate, currentModifier);
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return frame.getLifespanModifier(genome, mate, currentModifier);
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return frame.getProductionModifier(genome, currentModifier);
    }

    @Override
    public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);
        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }
        return frame;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    @Override
    public boolean isSealed() {
        return false;
    }

    @Override
    public boolean isSelfLighted() {
        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        return false;
    }

    @Override
    public boolean isHellish() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = ExtraBees.proxy.getIcon(register, "frame" + frame.toString());
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    @Override
    public IBeeModifier getBeeModifier() {
        return this;
    }
}
