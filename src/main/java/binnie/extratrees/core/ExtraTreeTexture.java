package binnie.extratrees.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;

public enum ExtraTreeTexture implements IBinnieTexture {
    Gui(ResourceType.GUI, "gui"),
    Nursery(ResourceType.Tile, "Nursery");

    public static String carpenterTexture = "extratrees/carpenter_";
    public static String panelerTexture = "extratrees/paneler_";
    public static String tileworkerTexture = "extratrees/tileworker_";
    public static String incubatorTexture = "extratrees/incubator_";
    public static String lumbermillTexture = "extratrees/sawmill_";
    public static String pressTexture = "extratrees/press_";
    public static String distilleryTexture = "extratrees/distillery_";
    public static String breweryTexture = "extratrees/brewery_";
    public static String infuserTexture = "extratrees/infuser_";

    protected String texture;
    protected ResourceType type;

    ExtraTreeTexture(ResourceType base, String texture) {
        this.texture = texture;
        type = base;
    }

    @Override
    public BinnieResource getTexture() {
        return Binnie.Resource.getPNG(ExtraTrees.instance, type, texture);
    }
}
