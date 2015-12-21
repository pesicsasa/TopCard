package model;

public class DescriptionVoteStatistics {
	
	private double precise;
	private double understandable;
	private String correct;
	private double standardDeviation_precise;
	private double standardDeviation_understandable;
	private double variance;
	private double variance_precise;
	private double variance_understandable;
	private double mean;
	private double median;
	public double getPrecise() {
		return precise;
	}
	public void setPrecise(double precise) {
		this.precise = precise;
	}
	public double getUnderstandable() {
		return understandable;
	}
	public void setUnderstandable(double understandable) {
		this.understandable = understandable;
	}
	public String getCorrect() {
		return correct;
	}
	public void setCorrect(String correct) {
		this.correct = correct;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getMedian() {
		return median;
	}
	public void setMedian(double median) {
		this.median = median;
	}
	public double getStandardDeviation_precise() {
		return standardDeviation_precise;
	}
	public void setStandardDeviation_precise(double standardDeviation_precise) {
		this.standardDeviation_precise = standardDeviation_precise;
	}
	public double getStandardDeviation_understandable() {
		return standardDeviation_understandable;
	}
	public void setStandardDeviation_understandable(double standardDeviation_understandable) {
		this.standardDeviation_understandable = standardDeviation_understandable;
	}
	public double getVariance_precise() {
		return variance_precise;
	}
	public void setVariance_precise(double variance_precise) {
		this.variance_precise = variance_precise;
	}
	public double getVariance_understandable() {
		return variance_understandable;
	}
	public void setVariance_understandable(double variance_understandable) {
		this.variance_understandable = variance_understandable;
	} 

}
