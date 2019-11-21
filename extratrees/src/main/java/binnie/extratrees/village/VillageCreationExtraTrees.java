package binnie.extratrees.village;

import binnie.core.Constants;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class VillageCreationExtraTrees implements VillagerRegistry.IVillageCreationHandler {

	public static void registerVillageComponents() {
		MapGenStructureIO.registerStructureComponent(VillageHopeField.class, Constants.EXTRA_TREES_MOD_ID + ":Field");
	}

	@Override
	public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int size) {
		return new StructureVillagePieces.PieceWeight(VillageHopeField.class, 3, MathHelper.getInt(random, 1 + size, 4 + size));
	}

	@Override
	public Class<?> getComponentClass() {
		return VillageHopeField.class;
	}

	@Override
	@Nullable
	public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5) {
		return VillageHopeField.buildComponent(startPiece, pieces, random, p1, p2, p3, facing, p5);
	}
}
