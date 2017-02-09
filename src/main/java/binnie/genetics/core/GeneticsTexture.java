package binnie.genetics.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.BinnieSprite;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.genetics.Genetics;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

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

	private final String texture;
	private final ResourceType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	public static BinnieSprite dnaIcon = Binnie.RESOURCE.getItemSprite(Genetics.instance, "dna");

	GeneticsTexture(final ResourceType base, final String texture) {
		this.texture = texture;
		this.type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG(Genetics.instance, this.type, this.texture);
		}
		return resource;
	}

}
