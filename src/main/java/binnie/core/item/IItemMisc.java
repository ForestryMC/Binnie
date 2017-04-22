package binnie.core.item;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

public interface IItemMisc extends IItemEnum {
	IIcon getIcon(ItemStack itemStack);

	@SideOnly(Side.CLIENT)
	void registerIcons(IIconRegister register);

	void addInformation(List data);
}
