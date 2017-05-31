package binnie.extrabees.items.types;

import java.util.List;

public interface IEBItemMiscProvider extends IEBEnumItem {

	void addInformation(List<String> tooltip);

	String getModelPath();
}
