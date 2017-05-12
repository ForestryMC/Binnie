package binnie.core.item;

import binnie.extrabees.utils.IEBEnumItem;

import java.util.List;

public interface IItemMiscProvider extends IEBEnumItem {

	void addInformation(List<String> tooltip);

	String getModelPath();

}
