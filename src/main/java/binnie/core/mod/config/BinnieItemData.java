package binnie.core.mod.config;

class BinnieItemData {
    private int item;
    private BinnieConfiguration configFile;
    private String configKey;

    public BinnieItemData(final int item, final BinnieConfiguration configFile, final String configKey) {
        this.item = item;
        this.configFile = configFile;
        this.configKey = configKey;
    }
}
