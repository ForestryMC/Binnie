package binnie.extrabees.items.types;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IEBItemMiscProvider extends IEBEnumItem {

	@SideOnly(Side.CLIENT)
	void addInformation(List<String> tooltip);

	String getModelPath();
}
