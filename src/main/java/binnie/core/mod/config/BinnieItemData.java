package binnie.core.mod.config;

// TODO Why do we need this class?
class BinnieItemData {
    private int item;
    private BinnieConfiguration configFile;
    private String configKey;

    public BinnieItemData(int item, BinnieConfiguration configFile, String configKey) {
        this.item = item;
        this.configFile = configFile;
        this.configKey = configKey;
    }
}
