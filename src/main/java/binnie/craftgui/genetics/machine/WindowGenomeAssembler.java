package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.Genetics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowGenomeAssembler extends WindowMachine {
    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowGenomeAssembler(player, inventory, side);
    }

    public WindowGenomeAssembler(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(320, 240, player, inventory, side);
    }

    @Override
    public String getTitle() {
        return "Genome Assembler";
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "GenomeAssembler";
    }
}
