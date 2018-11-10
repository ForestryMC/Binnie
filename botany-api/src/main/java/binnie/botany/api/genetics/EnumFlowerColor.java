package binnie.botany.api.genetics;

import java.awt.Color;

import net.minecraft.util.text.TextFormatting;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.IBotanyColored;

public enum EnumFlowerColor implements IBotanyColored {
	Aquamarine("aquamarine", new Color(8388564)),
	Black("black", new Color(2631720)),
	Blue("blue", new Color(255)),
	Brown("brown", new Color(10824234)),
	CadetBlue("cadetBlue", new Color(6266528)),
	Chocolate("chocolate", new Color(13789470)),
	Coral("coral", new Color(16744272)),
	Crimson("crimson", new Color(14423100)),
	Cyan("cyan", new Color(65535)),
	DarkGoldenrod("darkGoldenrod", new Color(12092939)),
	DarkGray("darkGray", new Color(11119017)),
	DarkGreen("darkGreen", new Color(25600)),
	DarkKhaki("darkKhaki", new Color(12433259)),
	DarkOliveGreen("darkOliveGreen", new Color(5597999)),
	DarkOrange("darkOrange", new Color(16747520)),
	DarkSalmon("darkSalmon", new Color(15308410)),
	DarkSeaGreen("darkSeaGreen", new Color(9419915)),
	DarkSlateBlue("darkSlateBlue", new Color(4734347)),
	DarkSlateGray("darkSlateGray", new Color(3100495)),
	DarkTurquoise("darkTurquoise", new Color(52945)),
	DarkViolet("darkViolet", new Color(9699539)),
	DeepPink("deepPink", new Color(16716947)),
	DeepSkyBlue("deepSkyBlue", new Color(49151)),
	DimGray("dimGray", new Color(6908265)),
	DodgerBlue("dodgerBlue", new Color(2003199)),
	Gold("gold", new Color(16766720)),
	Goldenrod("goldenrod", new Color(14329120)),
	Gray("gray", new Color(8421504)),
	Green("green", new Color(32768)),
	HotPink("hotPink", new Color(16738740)),
	IndianRed("indianRed", new Color(13458524)),
	Indigo("indigo", new Color(4915330)),
	Khaki("khaki", new Color(15787660)),
	Lavender("lavender", new Color(15132410)),
	LemonChiffon("lemonChiffon", new Color(16775885)),
	LightGray("lightGray", new Color(13882323)),
	LightSeaGreen("lightSeaGreen", new Color(2142890)),
	LightSteelBlue("lightSteelBlue", new Color(11584734)),
	Lime("lime", new Color(65280)),
	LimeGreen("limeGreen", new Color(3329330)),
	Magenta("magenta", new Color(16711935)),
	Maroon("maroon", new Color(8388608)),
	MediumAquamarine("mediumAquamarine", new Color(6737322)),
	MediumOrchid("mediumOrchid", new Color(12211667)),
	MediumPurple("mediumPurple", new Color(9662683)),
	MediumSeaGreen("mediumSeaGreen", new Color(3978097)),
	MediumVioletRed("mediumVioletRed", new Color(13047173)),
	MistyRose("mistyRose", new Color(16770273)),
	Navy("navy", new Color(128)),
	Olive("olive", new Color(8421376)),
	OliveDrab("oliveDrab", new Color(7048739)),
	Orange("orange", new Color(16753920)),
	PaleGreen("paleGreen", new Color(10025880)),
	PaleTurquoise("paleTurquoise", new Color(11529966)),
	PaleVioletRed("paleVioletRed", new Color(14381203)),
	Peru("peru", new Color(13468991)),
	Pink("pink", new Color(16761035)),
	Plum("plum", new Color(14524637)),
	Purple("purple", new Color(8388736)),
	Red("red", new Color(16711680)),
	RosyBrown("rosyBrown", new Color(12357519)),
	RoyalBlue("royalBlue", new Color(4286945)),
	Salmon("salmon", new Color(16416882)),
	SandyBrown("sandyBrown", new Color(16032864)),
	SeaGreen("seaGreen", new Color(3050327)),
	Sienna("sienna", new Color(10506797)),
	SkyBlue("skyBlue", new Color(8900331)),
	SlateBlue("slateBlue", new Color(6970061)),
	SlateGray("slateGray", new Color(7372944)),
	SpringGreen("springGreen", new Color(65407)),
	SteelBlue("steelBlue", new Color(4620980)),
	Tan("tan", new Color(13808780)),
	Teal("teal", new Color(32896)),
	Thistle("thistle", new Color(14204888)),
	Turquoise("turquoise", new Color(4251856)),
	Violet("violet", new Color(15631086)),
	Wheat("wheat", new Color(16113331)),
	White("white", new Color(16777215)),
	Yellow("yellow", new Color(16776960)),
	YellowGreen("yellowGreen", new Color(10145074));

	public static final EnumFlowerColor[] VALUES = values();

	private final String ident;
	private final IAlleleFlowerColor allele;

	EnumFlowerColor(String ident, Color color) {
		this.ident = ident;
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		r = (int) (0.45 * (r + 214));
		g = (int) (0.45 * (g + 174));
		b = (int) (0.45 * (b + 131));
		Color colorWilted = new Color(r, g, b);
		String uid = "botany.color." + name().toLowerCase();
		allele = BotanyAPI.flowerFactory.createFlowerColorAllele(uid, ordinal(), color, colorWilted, name().toLowerCase(), uid, true);
	}

	public String getIdent() {
		return ident;
	}

	public static EnumFlowerColor get(int i) {
		return values()[Math.max(0, i) % values().length];
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Override
	public TextFormatting getColor() {
		return null;
	}

	public IAlleleFlowerColor getFlowerColorAllele() {
		return allele;
	}
}
