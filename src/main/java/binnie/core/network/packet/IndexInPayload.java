package binnie.core.network.packet;

public class IndexInPayload {
    public int intIndex;
    public int floatIndex;
    public int stringIndex;

    public IndexInPayload(final int intIndex, final int floatIndex, final int stringIndex) {
        this.intIndex = 0;
        this.floatIndex = 0;
        this.stringIndex = 0;
        this.intIndex = intIndex;
        this.floatIndex = floatIndex;
        this.stringIndex = stringIndex;
    }
}
