package binnie.core.item;

import java.util.List;

public interface IItemMiscProvider extends IItemEnum {

    void addInformation(List tooltip);
    
    String getModelPath();
}
