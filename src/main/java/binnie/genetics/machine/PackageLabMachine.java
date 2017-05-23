package binnie.genetics.machine;

import binnie.core.machines.Machine;
import binnie.genetics.core.GeneticsTexture;
import net.minecraft.client.renderer.RenderBlocks;

public class PackageLabMachine extends PackageGeneticBase {
	public PackageLabMachine() {
		super("labMachine", GeneticsTexture.LabMachine, 0xffffff, false);
	}

	@Override
	public void createMachine(Machine machine) {
		new ComponentGUIHolder(machine);
	}

	@Override
	public void renderMachine(Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
		MachineRendererLab.instance.renderMachine(machine, color, renderTexture, x, y, z, partialTick);
	}
}
