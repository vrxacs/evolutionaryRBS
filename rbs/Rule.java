package rbs;

public class Rule implements Comparable<Rule> {

	private String antecedent;
	private String consequent;
	private double strength;

	public Rule(){
		antecedent = null;
		consequent = null;
		strength = 0;
	}
	
	public Rule(String ant, String con){
		antecedent = ant;
		consequent = con;
	}
	
	public Rule(String ant, String con, double str){
		antecedent = ant;
		consequent = con;
		strength = str;
	}
	
	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}
	public String getAntecedent() {
		return antecedent;
	}
	public void setAntecedent(String antecedent) {
		this.antecedent = antecedent;
	}
	public String getConsequent() {
		return consequent;
	}
	public void setConsequent(String consequent) {
		this.consequent = consequent;
	}

	@Override
	public int compareTo(Rule o) {
		if(o.getStrength() < strength)
			return 1;
		else if(o.getStrength() == strength)
			return 0;
		else
			return -1;
	}
	
}
