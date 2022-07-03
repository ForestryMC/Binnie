package binnie.extratrees.village;

import forestry.apiculture.worldgen.ComponentVillageBeeHouse;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class ComponentVillageGrove extends StructureVillagePieces.Village {
    public ComponentVillageGrove() {}

    public ComponentVillageGrove(
            StructureVillagePieces.Start start,
            int type,
            Random rand,
            StructureBoundingBox boundingBox,
            int coordBaseMode) {
        super(start, type);
        this.coordBaseMode = coordBaseMode;
        this.boundingBox = boundingBox;
    }

    public static StructureBoundingBox func_74904_a(
            StructureVillagePieces.Start start,
            List pieces,
            Random rand,
            int p_74904_3_,
            int p_74904_4_,
            int p_74904_5_,
            int p_74904_6_) {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(
                p_74904_3_, p_74904_4_, p_74904_5_, 0, 0, 0, 3, 3, 3, p_74904_6_);
        return (StructureComponent.findIntersecting(pieces, structureboundingbox) != null)
                ? null
                : structureboundingbox;
    }

    public static ComponentVillageBeeHouse buildComponent(
            StructureVillagePieces.Start startPiece,
            List par1List,
            Random random,
            int par3,
            int par4,
            int par5,
            int par6,
            int par7) {
        System.out.println("GROVE1");
        StructureBoundingBox bbox =
                StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 9, 10, par6);
        return (canVillageGoDeeper(bbox) && StructureComponent.findIntersecting(par1List, bbox) == null)
                ? new ComponentVillageBeeHouse(startPiece, par7, random, bbox, par6)
                : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
        if (field_143015_k < 0) {
            field_143015_k = getAverageGroundLevel(world, box);
            if (field_143015_k < 0) {
                return true;
            }
            boundingBox.offset(0, field_143015_k - boundingBox.maxY + 4 - 1, 0);
        }
        System.out.println("GROVE2");
        fillWithBlocks(world, box, 0, 0, 0, 3, 3, 3, Blocks.air, Blocks.air, false);
        fillWithBlocks(world, box, 0, 0, 0, 3, 3, 3, Blocks.stone, Blocks.stone, true);
        return true;
    }
}
