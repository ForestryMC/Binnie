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
	GENETIC_MACHINE(ResourceType.TILE, "GeneticMachine"),
	GENEPOOL(ResourceType.TILE, "genepool"),
	INCUBATOR(ResourceType.TILE, "incubator"),
	GUI_PROCESS(ResourceType.GUI, "process"),
	GUI_PROCESS_2(ResourceType.GUI, "process2"),
	GUI_PROCESS_3(ResourceType.GUI, "process3"),
	ISOLATOR(ResourceType.TILE, "isolator"),
	SEQUENCER(ResourceType.TILE, "sequencer"),
	POLYMERISER(ResourceType.TILE, "polymeriser"),
	INOCULATOR(ResourceType.TILE, "inoculator"),
	ANALYSER(ResourceType.TILE, "analyser"),
	MACHINE_GLOW(ResourceType.TILE, "MachineGlow"),
	GENE_BANK(ResourceType.TILE, "GeneBank"),
	LAB_MACHINE(ResourceType.TILE, "lab_machine"),
	ACCLIMATISER(ResourceType.TILE, "acclimatiser"),
	SPLICER(ResourceType.TILE, "splicer");

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
