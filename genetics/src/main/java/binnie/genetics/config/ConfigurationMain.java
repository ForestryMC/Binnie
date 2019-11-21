package binnie.genetics.config;

import binnie.core.config.ConfigFile;
import binnie.core.util.I18N;
import net.minecraftforge.common.config.Configuration;

@ConfigFile(filename = "/config/forestry/genetics/main.conf")
public class ConfigurationMain implements IConfigurable {

	private static final String MACHINES = "Machines";

	public static int acclimatiserEnergy = 2;

	public static int analyserEnergy = 9000;
	public static int analyserTime = 300;

	public static int genepoolEnergy = 8000;
	public static int genepoolTime = 400;

	public static int incubatorEnergy = 2;

	public static int inoculatorEnergy = 60000;
	public static int inoculatorTime = 12000;

	public static int isolatorEnergy = 192000;
	public static int isolatorTime = 4800;

	public static int polymeriserEnergy = 96000;
	public static int polymeriserTime = 2400;

	public static int sequencerTimeMultiplier = 19200;
	public static int sequencerEnergyMultiplier = 20;

	public static int splicerEnergy = 12000000;
	public static int splicerTime = 1200;

	@Override
	public void configure(Configuration config) {
		config.addCustomCategoryComment(MACHINES, "Energy and time settings for machines");

		acclimatiserEnergy = config.getInt("acclimatiserEnergy", MACHINES, acclimatiserEnergy, 0, 10, I18N.localise("genetics.config.acclimatiser.energy"));

		analyserEnergy = config.getInt("analyserEnergy", MACHINES, analyserEnergy, 0, 50000, I18N.localise("genetics.config.analyser.energy"));
		analyserTime = config.getInt("analyserTime", MACHINES, analyserTime, 0, 1500, I18N.localise("genetics.config.analyser.time"));

		genepoolEnergy = config.getInt("genepoolEnergy", MACHINES, genepoolEnergy, 0, 50000, I18N.localise("genetics.config.genepool.energy"));
		genepoolTime = config.getInt("genepoolTime", MACHINES, genepoolTime, 0, 1500, I18N.localise("genetics.config.genepool.time"));

		incubatorEnergy = config.getInt("incubatorEnergy", MACHINES, incubatorEnergy, 0, 10, I18N.localise("genetics.config.incubator.energy"));

		inoculatorEnergy = config.getInt("inoculatorEnergy", MACHINES, inoculatorEnergy, 0, 100000, I18N.localise("genetics.config.inoculator.energy"));
		inoculatorTime = config.getInt("inoculatorTime", MACHINES, inoculatorTime, 0, 20000, I18N.localise("genetics.config.inoculator.time"));

		isolatorEnergy = config.getInt("isolatorEnergy", MACHINES, isolatorEnergy, 0, 400000, I18N.localise("genetics.config.isolator.energy"));
		isolatorTime = config.getInt("isolatorTime", MACHINES, isolatorTime, 0, 10000, I18N.localise("genetics.config.isolator.time"));

		polymeriserEnergy = config.getInt("polymeriserEnergy", MACHINES, polymeriserEnergy, 0, 200000, I18N.localise("genetics.config.polymeriser.energy"));
		polymeriserTime = config.getInt("polymeriserTime", MACHINES, polymeriserTime, 0, 5000, I18N.localise("genetics.config.polymeriser.time"));

		sequencerEnergyMultiplier = config.getInt("sequencerEnergyMultiplier", MACHINES, sequencerEnergyMultiplier, 0, 40, I18N.localise("genetics.config.sequencer.energy"));
		sequencerTimeMultiplier = config.getInt("sequencerTimeMultiplier", MACHINES, sequencerTimeMultiplier, 0, 40000, I18N.localise("genetics.config.sequencer.time"));

		splicerEnergy = config.getInt("splicerEnergy", MACHINES, splicerEnergy, 0, 30000000, I18N.localise("genetics.config.splicer.energy"));
		splicerTime = config.getInt("splicerTime", MACHINES, splicerTime, 0, 500, I18N.localise("genetics.config.splicer.time"));

	}

}
