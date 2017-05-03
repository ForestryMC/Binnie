package binnie.extratrees.village;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

// TODO Unused class?
public class VillageHandlerExtraTrees implements VillagerRegistry.IVillageCreationHandler {
	public static void registerVillageComponents() {
		try {
			MapGenStructureIO.func_143031_a(ComponentVillageGrove.class, "ExtraTrees:Grove");
		} catch (Throwable t) {
			// ignored
		}
	}

	@Override
	public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int size) {
		return new StructureVillagePieces.PieceWeight(ComponentVillageGrove.class, 505, MathHelper.getRandomIntegerInRange(random, size, 1 + size));
	}

	@Override
	public Class<?> getComponentClass() {
		return ComponentVillageGrove.class;
	}

	@Override
	public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
		return ComponentVillageGrove.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}
}
