package binnie.extratrees.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;

public enum ExtraTreeTexture implements IBinnieTexture {
    Gui(ResourceType.GUI, "gui"),
    Nursery(ResourceType.Tile, "Nursery");

    String texture;
    ResourceType type;
    public static String carpenterTexture;
    public static String panelerTexture;
    public static String tileworkerTexture;
    public static String incubatorTexture;
    public static String lumbermillTexture;
    public static String pressTexture;
    public static String distilleryTexture;
    public static String breweryTexture;
    public static String infuserTexture;

    private ExtraTreeTexture(final ResourceType base, final String texture) {
        this.texture = texture;
        this.type = base;
    }

    @Override
    public BinnieResource getTexture() {
        return Binnie.Resource.getPNG(ExtraTrees.instance, this.type, this.texture);
    }

    static {
        ExtraTreeTexture.carpenterTexture = "extratrees/carpenter_";
        ExtraTreeTexture.panelerTexture = "extratrees/paneler_";
        ExtraTreeTexture.tileworkerTexture = "extratrees/tileworker_";
        ExtraTreeTexture.incubatorTexture = "extratrees/incubator_";
        ExtraTreeTexture.lumbermillTexture = "extratrees/sawmill_";
        ExtraTreeTexture.pressTexture = "extratrees/press_";
        ExtraTreeTexture.distilleryTexture = "extratrees/distillery_";
        ExtraTreeTexture.breweryTexture = "extratrees/brewery_";
        ExtraTreeTexture.infuserTexture = "extratrees/infuser_";
    }
}
