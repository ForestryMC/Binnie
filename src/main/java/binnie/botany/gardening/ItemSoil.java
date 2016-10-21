package binnie.botany.gardening;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSoil extends ItemBlock {
    EnumSoilType type;
    private boolean noWeed;

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        final EnumMoisture moisture = EnumMoisture.values()[stack.getItemDamage() % 3];
        final EnumAcidity acidity = EnumAcidity.values()[stack.getItemDamage() / 3];
        String info = "";
        if (moisture == EnumMoisture.Dry) {
            info += "§eDry§f";
        }
        if (moisture == EnumMoisture.Damp) {
            info += "§9Damp§f";
        }
        if (acidity == EnumAcidity.Acid) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += "§cAcidic§f";
        }
        if (acidity == EnumAcidity.Alkaline) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += "§bAlkaline§f";
        }
        if (info.length() > 0) {
            tooltip.add(info);
        }
        if (this.noWeed) {
            tooltip.add("Weedkiller");
        }
    }

    public ItemSoil(final Block block) {
        super(block);
		this.type = ((BlockSoil) this.block).getType();
		this.noWeed = ((BlockSoil) this.block).weedKilled;
        this.hasSubtypes = true;
    }

    @Override
    public String getItemStackDisplayName(final ItemStack stack) {
        return this.type.name().substring(0, 1) + this.type.name().toLowerCase().substring(1);
    }

    @Override
    public int getMetadata(final int damage) {
        return damage;
    }
}
