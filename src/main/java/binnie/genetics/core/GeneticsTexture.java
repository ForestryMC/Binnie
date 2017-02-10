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
	Genepool(ResourceType.Tile, "genepool"),
	Incubator(ResourceType.Tile, "incubator"),
	GUIProcess(ResourceType.GUI, "process"),
	GUIProcess2(ResourceType.GUI, "process2"),
	Isolator(ResourceType.Tile, "isolator"),
	Sequencer(ResourceType.Tile, "sequencer"),
	Polymeriser(ResourceType.Tile, "polymeriser"),
	Inoculator(ResourceType.Tile, "inoculator"),
	Analyser(ResourceType.Tile, "analyser"),
	MachineGlow(ResourceType.Tile, "MachineGlow"),
	GeneBank(ResourceType.Tile, "GeneBank"),
	LabMachine(ResourceType.Tile, "lab_machine"),
	Acclimatiser(ResourceType.Tile, "acclimatiser"),
	Splicer(ResourceType.Tile, "splicer");

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
