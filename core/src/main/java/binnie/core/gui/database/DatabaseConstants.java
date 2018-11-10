package binnie.core.gui.database;

public class DatabaseConstants {
	private static final String TAB_PRE_FIX = ".tab";
	private static final String TAB_SPECIES_PRE_FIX = TAB_PRE_FIX + ".species";
	public static final String CONTROL_PRE_FIX = ".control";
	public static final String PRODUCTS_PRE_FIX = ".products";
	public static final String PAGES_PRE_FIX = ".pages";

	public static final String KEY = "binniecore.gui.database";
	public static final String DISCOVERED_KEY = KEY + ".discovered";
	public static final String MUTATIONS_KEY = KEY + ".mutations";
	public static final String BREEDER_KEY = KEY + ".breeder";
	public static final String SPECIES_KEY = KEY + ".species";
	public static final String BRANCH_KEY = KEY + ".branch";
	public static final String MODE_KEY = KEY + ".mode";
	public static final String CONTROL_KEY = KEY + CONTROL_PRE_FIX;

	public static final String GENETICS_KEY = "genetics.gui.database";

	public static final String BEE_GENOME_KEY = GENETICS_KEY + TAB_SPECIES_PRE_FIX + ".genome.extrabees";
	public static final String BEE_CONTROL_KEY = GENETICS_KEY + CONTROL_PRE_FIX + ".extrabees";
	public static final String BEE_PRODUCTS_KEY = GENETICS_KEY + PRODUCTS_PRE_FIX + ".extrabees";

	public static final String BOTANY_KEY = "botany.gui.database";
	public static final String BOTANY_CONTROL_KEY = BOTANY_KEY + CONTROL_PRE_FIX;
	public static final String BOTANY_PAGES_KEY = BOTANY_KEY + PAGES_PRE_FIX;
	public static final String BOTANY_TAB_KEY = BOTANY_KEY + TAB_PRE_FIX;
	public static final String BOTANY_GENOME_KEY = BOTANY_TAB_KEY + ".genome";

	public static final String EXTRATREES_KEY = "extratrees.gui.database";
}
