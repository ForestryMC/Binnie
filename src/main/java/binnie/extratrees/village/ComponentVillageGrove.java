package binnie.extratrees.village;

public class ComponentVillageGrove //extends StructureVillagePieces.Village
{
    /*public ComponentVillageGrove() {
	}

    public ComponentVillageGrove(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox boundingBox, EnumFacing facing) {
        super(start, type);
        this.setCoordBaseMode(facing);
		this.boundingBox = boundingBox;
	}

	@Override
	public boolean addComponentParts(World world, final Random p_74875_2_, final StructureBoundingBox structureBoundingBox) {
        if (this.averageGroundLvl < 0) {
            this.averageGroundLvl = this.getAverageGroundLevel(world, structureBoundingBox);

            if (this.averageGroundLvl < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
        }
		System.out.println("GROVE2");
		this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 3, 3, 3, Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), true);
		return true;
	}
	
    public static ComponentVillageGrove createComponent(StructureVillagePieces.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 3, 3, 3, facing);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null ? new ComponentVillageGrove(start, p_175851_7_, rand, structureboundingbox, facing) : null;
    }*/
}
