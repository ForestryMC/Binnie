package binnie.extratrees.genetics;

public enum LeafType {
    NORMAL((short) 10, (short) 11, (short) 12, "Deciduous"),
    CONIFER((short) 15, (short) 16, (short) 17, "Conifers"),
    JUNGLE((short) 20, (short) 21, (short) 22, "Jungle"),
    WILLOW((short) 25, (short) 26, (short) 27, "Willow"),
    MAPLE((short) 30, (short) 31, (short) 32, "Maple"),
    PALM((short) 35, (short) 36, (short) 37, "Palm");

    public short fancyUID;
    public short plainUID;
    public short changedUID;
    public String description;

    LeafType(short fancyUID, short plainUID, short changedUID, String description) {
        this.fancyUID = fancyUID;
        this.plainUID = plainUID;
        this.changedUID = changedUID;
        this.description = description;
    }
}
