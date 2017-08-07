package binnie.extrabees.items.types;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEBItemMiscProvider extends IEBEnumItem {

	@SideOnly(Side.CLIENT)
	void addInformation(List<String> tooltip);

	String getModelPath();
}
