package binnie.extrabees.items.types;

import java.util.List;

/**
 * Created by Elec332 on 13-5-2017.
 */
public interface IEBItemMiscProvider extends IEBEnumItem {

	void addInformation(List<String> tooltip);

	String getModelPath();

}
