package binnie.genetics.core;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.BinnieSprite;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.genetics.Genetics;

public enum GeneticsTexture implements IBinnieTexture {
	GENETIC_MACHINE(ResourceType.Tile, "GeneticMachine"),
	GENEPOOL(ResourceType.Tile, "genepool"),
	INCUBATOR(ResourceType.Tile, "incubator"),
	GUI_PROCESS(ResourceType.GUI, "process"),
	GUI_PROCESS_2(ResourceType.GUI, "process2"),
	GUI_PROCESS_3(ResourceType.GUI, "process3"),
	ISOLATOR(ResourceType.Tile, "isolator"),
	SEQUENCER(ResourceType.Tile, "sequencer"),
	POLYMERISER(ResourceType.Tile, "polymeriser"),
	INOCULATOR(ResourceType.Tile, "inoculator"),
	ANALYSER(ResourceType.Tile, "analyser"),
	MACHINE_GLOW(ResourceType.Tile, "MachineGlow"),
	GENE_BANK(ResourceType.Tile, "GeneBank"),
	LAB_MACHINE(ResourceType.Tile, "lab_machine"),
	ACCLIMATISER(ResourceType.Tile, "acclimatiser"),
	SPLICER(ResourceType.Tile, "splicer");

	public static BinnieSprite dnaIcon = Binnie.RESOURCE.getItemSprite(Genetics.instance, "dna");

	private final String texture;
	private final ResourceType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	GeneticsTexture(ResourceType base, String texture) {
		this.texture = texture;
		type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG(Genetics.instance, type, texture);
		}
		return resource;
	}

}
