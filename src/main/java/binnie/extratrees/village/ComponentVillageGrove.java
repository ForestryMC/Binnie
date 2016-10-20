package binnie.extratrees.village;

public class ComponentVillageGrove //extends StructureVillagePieces.Village
{
//	public ComponentVillageGrove() {
//	}
//
//	public ComponentVillageGrove(final StructureVillagePieces.Start start, final int p_i2097_2_, final Random p_i2097_3_, final StructureBoundingBox boundingBox, final int coordBaseMode) {
//		super(start, p_i2097_2_);
//		this.coordBaseMode = coordBaseMode;
//		this.boundingBox = boundingBox;
//	}
//
//	public static StructureBoundingBox func_74904_a(final StructureVillagePieces.Start p_74904_0_, final List p_74904_1_, final Random p_74904_2_, final int p_74904_3_, final int p_74904_4_, final int p_74904_5_, final int p_74904_6_) {
//		final StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74904_3_, p_74904_4_, p_74904_5_, 0, 0, 0, 3, 3, 3, p_74904_6_);
//		return (StructureComponent.findIntersecting(p_74904_1_, structureboundingbox) != null) ? null : structureboundingbox;
//	}
//
//	@Override
//	public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
//		if (this.field_143015_k < 0) {
//			this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
//			if (this.field_143015_k < 0) {
//				return true;
//			}
//			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
//		}
//		System.out.println("GROVE2");
//		this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 3, 3, Blocks.air, Blocks.air, false);
//		this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 3, 3, Blocks.stone, Blocks.stone, true);
//		return true;
//	}
//
//	public static ComponentVillageBeeHouse buildComponent(final StructureVillagePieces.Start startPiece, final List par1List, final Random random, final int par3, final int par4, final int par5, final int par6, final int par7) {
//		System.out.println("GROVE1");
//		final StructureBoundingBox bbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 9, 10, par6);
//		return (canVillageGoDeeper(bbox) && StructureComponent.findIntersecting(par1List, bbox) == null) ? new ComponentVillageBeeHouse(startPiece, par7, random, bbox, par6) : null;
//	}
}
