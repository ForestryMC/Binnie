package binnie.extrabees.alveary;

import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.multiblock.IAlvearyComponent;
import forestry.api.multiblock.IMultiblockComponent;
import forestry.api.multiblock.IMultiblockController;
import forestry.apiculture.multiblock.MultiblockLogicAlveary;
import forestry.core.multiblock.MultiblockTileEntityForestry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class TileEntityExtraBeesAlvearyPart extends MultiblockTileEntityForestry<MultiblockLogicAlveary> implements
	IAlvearyComponent.Active,
	IAlvearyComponent.BeeModifier,
	IAlvearyComponent.BeeListener {

	private String unlocalizedTitle;
	private EnumAlvearyLogicType type;
	private AbstractAlvearyLogic alvearyLogic;

	public TileEntityExtraBeesAlvearyPart() {
		this(null);
	}

	protected TileEntityExtraBeesAlvearyPart(EnumAlvearyLogicType type) {
		super(new MultiblockLogicAlveary());
		if (type != null) {
			this.type = type;
			initFromType();
		}
	}

	private void initFromType() {
		if (alvearyLogic == null) {
			this.unlocalizedTitle = "tile.for.alveary." + type.getName() + ".name";
			this.alvearyLogic = type.createLogic(this);
		}
	}

	public boolean hasGui() {
		return alvearyLogic.hasGui();
	}

	@Nonnull
	public AbstractAlvearyLogic getAlvearyLogic() {
		return alvearyLogic;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		type = EnumAlvearyLogicType.VALUES[data.getByte("avType")];
		initFromType();
		super.readFromNBT(data);
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		data.setByte("avType", (byte) type.ordinal());
		return super.writeToNBT(data);
	}

	@Override
	public void onMachineAssembled(@Nonnull IMultiblockController multiblockController, @Nonnull BlockPos minCoord, @Nonnull BlockPos maxCoord) {
		// Re-render this block on the client
		if (world.isRemote) {
			this.world.markBlockRangeForRenderUpdate(getPos(), getPos());
		}
		world.notifyNeighborsOfStateChange(getPos(), getBlockType(), false);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onMachineBroken() {
		// Re-render this block on the client
		if (world.isRemote) {
			this.world.markBlockRangeForRenderUpdate(getPos(), getPos());
		}
		world.notifyNeighborsOfStateChange(getPos(), getBlockType(), false);
		markDirty();
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return alvearyLogic.getGui(player, data);
	}

	@Nullable
	@Override
	public Container getContainer(@Nonnull EntityPlayer player, int data) {
		return alvearyLogic.getContainer(player, data);
	}

	@Override
	public void updateServer(int tickCount) {
		alvearyLogic.updateServer(this);
	}

	@Override
	public void updateClient(int tickCount) {
		alvearyLogic.updateClient(this);
	}

	@Override
	@Nonnull
	public IBeeListener getBeeListener() {
		return alvearyLogic;
	}

	@Override
	@Nonnull
	public IBeeModifier getBeeModifier() {
		return alvearyLogic;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return alvearyLogic.hasCapability(capability, facing) || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return alvearyLogic.hasCapability(capability, facing) ? alvearyLogic.getCapability(capability, facing) : super.getCapability(capability, facing);
	}

	public Collection<IMultiblockComponent> getConnectedComponents() {
		return getMultiblockLogic().getController().getComponents();
	}

	public Set<TileEntityExtraBeesAlvearyPart> getExtraBeesParts() {
		return getConnectedComponents().stream().filter(p -> p instanceof TileEntityExtraBeesAlvearyPart).map(p -> (TileEntityExtraBeesAlvearyPart) p).collect(Collectors.toSet());
	}
}
