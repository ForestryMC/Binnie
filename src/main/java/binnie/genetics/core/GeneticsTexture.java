package binnie.genetics.core;

import binnie.Binnie;
import binnie.core.resource.BinnieIcon;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.genetics.Genetics;

public enum GeneticsTexture implements IBinnieTexture {
    GeneticMachine(ResourceType.Tile, "GeneticMachine"),
    Genepool(ResourceType.Tile, "Genepool"),
    Incubator(ResourceType.Tile, "Incubator"),
    GUIProcess(ResourceType.GUI, "process"),
    GUIProcess2(ResourceType.GUI, "process2"),
    Isolator(ResourceType.Tile, "Isolator"),
    Sequencer(ResourceType.Tile, "Sequencer"),
    Polymeriser(ResourceType.Tile, "Polymeriser"),
    Inoculator(ResourceType.Tile, "Inoculator"),
    Analyser(ResourceType.Tile, "Analyser"),
    MachineGlow(ResourceType.Tile, "MachineGlow"),
    GeneBank(ResourceType.Tile, "GeneBank"),
    LabMachine(ResourceType.Tile, "LabMachine"),
    Acclimatiser(ResourceType.Tile, "Acclimatiser"),
    Splicer(ResourceType.Tile, "Splicer");

    String texture;
    ResourceType type;
    public static BinnieIcon dnaIcon;

    private GeneticsTexture(final ResourceType base, final String texture) {
        this.texture = texture;
        this.type = base;
    }

    @Override
    public BinnieResource getTexture() {
        return Binnie.Resource.getPNG(Genetics.instance, this.type, this.texture);
    }

    static {
        GeneticsTexture.dnaIcon = Binnie.Resource.getItemIcon(Genetics.instance, "dna");
    }
}
