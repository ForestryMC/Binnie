package binnie.botany.gardening;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Locale;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSoil extends ItemBlock {
    protected EnumSoilType type;

    private boolean noWeed;

    public ItemSoil(Block block) {
        super(block);
        type = ((BlockSoil) field_150939_a).getType();
        noWeed = ((BlockSoil) field_150939_a).weedKilled;
        hasSubtypes = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        EnumMoisture moisture = EnumMoisture.values()[stack.getItemDamage() % 3];
        EnumAcidity acidity = EnumAcidity.values()[stack.getItemDamage() / 3];
        String info = "";

        if (moisture == EnumMoisture.DRY) {
            info += EnumChatFormatting.YELLOW + I18N.localise("botany.moisture.dry") + EnumChatFormatting.RESET;
        }
        if (moisture == EnumMoisture.DAMP) {
            info += EnumChatFormatting.YELLOW + I18N.localise("botany.moisture.damp") + EnumChatFormatting.RESET;
        }

        if (acidity == EnumAcidity.ACID) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += EnumChatFormatting.YELLOW + I18N.localise("botany.ph.acid") + EnumChatFormatting.RESET;
        }

        if (acidity == EnumAcidity.ALKALINE) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += EnumChatFormatting.YELLOW + I18N.localise("botany.ph.alkaline") + EnumChatFormatting.RESET;
        }

        if (info.length() > 0) {
            tooltip.add(info);
        }

        if (noWeed) {
            tooltip.add(I18N.localise("botany.soil.weedkiller"));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String soilName = type.name().toLowerCase(Locale.ENGLISH);
        return I18N.localise("botany.soil." + soilName);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
