package binnie.core.gui;

import net.minecraft.client.util.ITooltipFlag;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITooltip {
	@SideOnly(Side.CLIENT)
	void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag);
}
