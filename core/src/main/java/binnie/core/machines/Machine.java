package binnie.core.machines;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.core.machines.component.IInteraction;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageBase;
import binnie.core.network.packet.MessageSyncTile;
import binnie.core.network.packet.PacketPayload;

public class Machine implements INetworkedEntity, INbtReadable, INbtWritable, INetwork.TilePacketSync, IMachine, INetwork.GuiNBT {
	private final MachinePackage machinePackage;
	private final Map<Class<?>, List<MachineComponent>> componentInterfaceMap;
	private final Map<Class<?>, MachineComponent> componentMap;
	private final TileEntity tile;
	private boolean queuedInventoryUpdate;
	@Nullable
	private GameProfile owner;

	public Machine(MachinePackage pack, TileEntity tile) {
		this.componentInterfaceMap = new LinkedHashMap<>();
		this.componentMap = new LinkedHashMap<>();
		this.queuedInventoryUpdate = false;
		this.owner = null;
		this.tile = tile;
		pack.createMachine(this);
		this.machinePackage = pack;
	}

	@Nullable
	public static IMachine getMachine(@Nullable Object inventory) {
		if (inventory instanceof IMachine) {
			return (IMachine) inventory;
		}
		if (inventory instanceof TileEntityMachine) {
			return ((TileEntityMachine) inventory).getMachine();
		}
		if (inventory instanceof MachineComponent) {
			return ((MachineComponent) inventory).getMachine();
		}
		return null;
	}

	@Nullable
	public static <T> T getInterface(Class<T> interfac, @Nullable Object inventory) {
		IMachine machine = getMachine(inventory);
		if (machine != null) {
			return machine.getInterface(interfac);
		}
		if (interfac.isInstance(inventory)) {
			return interfac.cast(inventory);
		}
		return null;
	}

	@Override
	public void addComponent(MachineComponent component) {
		Preconditions.checkNotNull(component, "Can't have a null machine component!");
		component.setMachine(this);
		this.componentMap.put(component.getClass(), component);
		for (Class<?> inter : component.getComponentInterfaces()) {
			if (!this.componentInterfaceMap.containsKey(inter)) {
				this.componentInterfaceMap.put(inter, new ArrayList<>());
			}
			this.componentInterfaceMap.get(inter).add(component);
		}
	}

	public Collection<MachineComponent> getComponents() {
		return this.componentMap.values();
	}

	public <T extends MachineComponent> T getComponent(Class<T> componentClass) {
		if (this.hasComponent(componentClass)) {
			return componentClass.cast(this.componentMap.get(componentClass));
		}
		throw new IllegalArgumentException("No component found for " + componentClass);
	}

	@Override
	@Nullable
	public <T> T getInterface(Class<T> interfaceClass) {
		if (this.hasInterface(interfaceClass)) {
			return this.getInterfaces(interfaceClass).get(0);
		}
		if (interfaceClass.isInstance(this.getPackage())) {
			return interfaceClass.cast(this.getPackage());
		}
		for (MachineComponent component : this.getComponents()) {
			if (interfaceClass.isInstance(component)) {
				return interfaceClass.cast(component);
			}
		}
		return null;
	}

	@Override
	public <T> List<T> getInterfaces(Class<T> interfaceClass) {
		List<T> interfaces = new ArrayList<>();
		if (!this.hasInterface(interfaceClass)) {
			return interfaces;
		}
		for (MachineComponent component : this.componentInterfaceMap.get(interfaceClass)) {
			interfaces.add(interfaceClass.cast(component));
		}
		return interfaces;
	}

	public boolean hasInterface(Class<?> interfaceClass) {
		return this.componentInterfaceMap.containsKey(interfaceClass);
	}

	public boolean hasComponent(Class<?> componentClass) {
		return this.componentMap.containsKey(componentClass);
	}

	@Override
	public TileEntity getTileEntity() {
		return this.tile;
	}

	@Override
	public void writeToPacket(PacketPayload payload) {
		for (MachineComponent component : this.getComponents()) {
			if (component instanceof INetworkedEntity) {
				((INetworkedEntity) component).writeToPacket(payload);
			}
		}
	}

	@Override
	public void readFromPacket(PacketPayload payload) {
		for (MachineComponent component : this.getComponents()) {
			if (component instanceof INetworkedEntity) {
				((INetworkedEntity) component).readFromPacket(payload);
			}
		}
	}

	public void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		List<IInteraction.RightClick> interfaces = this.getInterfaces(IInteraction.RightClick.class);
		for (IInteraction.RightClick component : interfaces) {
			component.onRightClick(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
		}
	}

	@Override
	public void markDirty() {
		this.queuedInventoryUpdate = true;
	}

	public void onUpdate() {
		if (!this.getWorld().isRemote) {
			for (MachineComponent component : this.getComponents()) {
				component.onUpdate();
			}
		} else {
			//noinspection MethodCallSideOnly
			updateClient();
		}
		if (this.queuedInventoryUpdate) {
			for (MachineComponent component : this.getComponents()) {
				component.onInventoryUpdate();
			}
			TileEntity tileEntity = getTileEntity();
			getWorld().markChunkDirty(tileEntity.getPos(), tileEntity);
			this.queuedInventoryUpdate = false;
		}
	}

	@SideOnly(Side.CLIENT)
	private void updateClient() {
		for (IRender.DisplayTick renders : this.getInterfaces(IRender.DisplayTick.class)) {
			renders.onDisplayTick(this.getWorld(), this.getTileEntity().getPos(), this.getWorld().rand);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		for (MachineComponent component : this.getComponents()) {
			component.readFromNBT(nbttagcompound);
		}
		this.owner = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("owner"));
		this.markDirty();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		for (MachineComponent component : this.getComponents()) {
			component.writeToNBT(nbttagcompound);
		}
		if (this.owner != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbt, this.owner);
			nbttagcompound.setTag("owner", nbt);
		}
		return nbttagcompound;
	}

	@Override
	public MachinePackage getPackage() {
		return this.machinePackage;
	}

	@Override
	public MachineUtil getMachineUtil() {
		return new MachineUtil(this);
	}

	@Override
	public World getWorld() {
		return this.getTileEntity().getWorld();
	}

	public void onBlockDestroy() {
		for (MachineComponent component : this.getComponents()) {
			component.onDestruction();
		}
	}

	@Override
	@Nullable
	public GameProfile getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(GameProfile owner) {
		this.owner = owner;
	}

	@Nullable
	public MessageBase getRefreshPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.syncToNBT(nbt);
		if (nbt.isEmpty()) {
			return null;
		}
		return new MessageSyncTile(BinnieCorePacketID.TILE_DESCRIPTION_SYNC.ordinal(), this.getTileEntity(), nbt);
	}

	@Override
	public void syncToNBT(NBTTagCompound nbt) {
		for (INetwork.TilePacketSync comp : this.getInterfaces(INetwork.TilePacketSync.class)) {
			comp.syncToNBT(nbt);
		}
	}

	@Override
	public void syncFromNBT(NBTTagCompound nbt) {
		for (INetwork.TilePacketSync comp : this.getInterfaces(INetwork.TilePacketSync.class)) {
			comp.syncFromNBT(nbt);
		}
	}

	@Override
	public void receiveGuiNBTOnServer(EntityPlayer player, String name, NBTTagCompound nbt) {
		for (INetwork.ReceiveGuiNBT receive : this.getInterfaces(INetwork.ReceiveGuiNBT.class)) {
			receive.receiveGuiNBTOnServer(player, name, nbt);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		for (INetwork.ReceiveGuiNBT receive : this.getInterfaces(INetwork.ReceiveGuiNBT.class)) {
			receive.receiveGuiNBTOnClient(player, name, nbt);
		}
	}

	@Override
	public void sendGuiNBTToClient(Map<String, NBTTagCompound> data) {
		for (INetwork.SendGuiNBT send : this.getInterfaces(INetwork.SendGuiNBT.class)) {
			send.sendGuiNBTToClient(data);
		}
	}
}
