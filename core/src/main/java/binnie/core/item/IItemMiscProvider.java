package binnie.core.item;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemMiscProvider extends IItemEnum {

	@SideOnly(Side.CLIENT)
	void addInformation(List<String> tooltip);

	String getModelPath();
}
