package interfaces.interfaceprocessor;

import interfaces.filters.Filter;
import interfaces.filters.Waveform;

public class BandPass extends Filter {
	double lowCutoff;
	double highCutoff;
	public BandPass(double lowCutoff,double highCutoff) {
		this.lowCutoff = lowCutoff;
		this.highCutoff = highCutoff;
	}
	public Waveform pocess(Waveform input) {
		return input;
	}
}
