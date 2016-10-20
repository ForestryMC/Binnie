// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.village;

import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import java.util.Random;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class VillageHandlerExtraTrees implements VillagerRegistry.IVillageCreationHandler
{
	public static void registerVillageComponents() {
		try {
			MapGenStructureIO.func_143031_a(ComponentVillageGrove.class, "ExtraTrees:Grove");
		} catch (Throwable t) {
		}
	}

	@Override
	public StructureVillagePieces.PieceWeight getVillagePieceWeight(final Random random, final int size) {
		return new StructureVillagePieces.PieceWeight(ComponentVillageGrove.class, 505, MathHelper.getRandomIntegerInRange(random, size, 1 + size));
	}

	@Override
	public Class<?> getComponentClass() {
		return ComponentVillageGrove.class;
	}

	@Override
	public Object buildComponent(final StructureVillagePieces.PieceWeight villagePiece, final StructureVillagePieces.Start startPiece, final List pieces, final Random random, final int p1, final int p2, final int p3, final int p4, final int p5) {
		return ComponentVillageGrove.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}
}
