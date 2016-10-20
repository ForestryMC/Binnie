// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.gardening;

import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import binnie.botany.api.EnumSoilType;
import net.minecraft.item.ItemBlock;

public class ItemSoil extends ItemBlock
{
	EnumSoilType type;
	private boolean noWeed;

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
		super.addInformation(stack, p_77624_2_, p_77624_3_, p_77624_4_);
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
			p_77624_3_.add(info);
		}
		if (this.noWeed) {
			p_77624_3_.add("Weedkiller");
		}
	}

	public ItemSoil(final Block p_i45328_1_) {
		super(p_i45328_1_);
		this.type = ((BlockSoil) this.field_150939_a).getType();
		this.noWeed = ((BlockSoil) this.field_150939_a).weedKilled;
		this.hasSubtypes = true;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		return this.type.name().substring(0, 1) + this.type.name().toLowerCase().substring(1);
	}

	@Override
	public int getMetadata(final int p_77647_1_) {
		return p_77647_1_;
	}
}
