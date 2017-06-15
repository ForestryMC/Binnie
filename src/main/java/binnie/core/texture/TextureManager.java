package binnie.core.texture;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureManager {
	static List<Icon> textures = new ArrayList<>();

	public static void init() {
	}
}
