package binnie.core.texture;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TextureManager {
	static List<Icon> textures = new ArrayList<>();

	public static void init() {
	}
}
