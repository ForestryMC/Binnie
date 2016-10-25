package binnie.core.proxy;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BinnieModProxy implements IBinnieModProxy {
    private AbstractMod mod;

    public BinnieModProxy(final AbstractMod mod) {
        this.mod = mod;
    }
    
    @Override
	public Item registerItem(Item item){
    	return GameRegistry.register(item);
    }
    
    @Override
	public Block registerBlock(Block block){
    	return GameRegistry.register(block);
    }
    
	@Override
	public void registerModels() {
	}
	
	@Override
	public void registerItemAndBlockColors() {
	}

    @Override
    public void openGui(final IBinnieGUID ID, final EntityPlayer player, final BlockPos pos) {
        BinnieCore.proxy.openGui(this.mod, ID.ordinal(), player, pos);
    }

    @Override
    public void sendToAll(final MessageBase packet) {
        this.mod.getNetworkWrapper().sendToAll(packet.GetMessage());
    }

    @Override
    public void sendToPlayer(final MessageBase packet, final EntityPlayer entityplayer) {
        if (entityplayer instanceof EntityPlayerMP) {
            this.mod.getNetworkWrapper().sendTo(packet.GetMessage(), (EntityPlayerMP) entityplayer);
        }
    }

    @Override
    public void sendToServer(final MessageBase packet) {
        this.mod.getNetworkWrapper().sendToServer(packet.GetMessage());
    }

    public void registermodel(Item item, int meta) {

    }

    public void registermodel(Item item, int meta, ModelResourceLocation modelResourceLocation) {

    }


//	@Override
//	public IIcon getIcon(final IIconRegister register, final String string) {
//		return BinnieCore.proxy.getIcon(register, this.mod.getModID(), string);
//	}

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }

    public String localise(final String string) {
        return Binnie.Language.localise(this.mod, string);
    }

    public String localiseOrBlank(final String string) {
        return Binnie.Language.localiseOrBlank(this.mod, string);
    }
}
