package binnie.core.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IItemMiscProvider extends IItemEnum {

	@SideOnly(Side.CLIENT)
	void addInformation(List<String> tooltip);

	String getModelPath();
}
