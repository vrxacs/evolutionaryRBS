package rbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EvoRBS extends RuleBasedSystem {

	public double mutation;
	public double crossover;
	public ArrayList<String> ant_symbols;
	public ArrayList<String> con_symbols;
	public Random rand;
	public double total_strength;
	public double avg_strength;

	
	public EvoRBS(ArrayList<Rule> r) {
		super(r);
	}
	
	public EvoRBS(double mut_rate, double cross_rate, ArrayList<Rule> r, ArrayList<String> ant_chars, ArrayList<String> con_chars){
		
		mutation = mut_rate;
		crossover = cross_rate;
		ant_symbols = new ArrayList<String>(ant_chars);
		con_symbols = new ArrayList<String>(con_chars);
		rules = new ArrayList<Rule>(r);
		rand = new Random();
		used_rules = new ArrayList<Rule>();
		
	}
	
	public void epoch(){
		
		//calculate statistics
		calculateStats();
		
		//create a temp ArrayList for storing the new population
		ArrayList<Rule> new_pop = new ArrayList<Rule>();
		
		//sort rules according to strength
		Collections.sort(rules);
		
		//elitism
		for(int i = 0; i < 4; ++i){
			new_pop.add(rules.get(rules.size()-1-i));
		}
		
		//now we enter the GA loop
		
		//repeat until a new population is generated
		while(new_pop.size() < rules.size()){
			
			//grab two chromosomes
			Rule m = getChromRoulette();
			Rule d = getChromRoulette();
			
			//create some offspring via crossover
			Rule k1 = new Rule();
			Rule k2 = new Rule();
			
			crossover(m, d, k1, k2);
			
			//mutation
			mutate(k1);
			mutate(k2);
			
			//now we copy them into new_pop
			new_pop.add(k1);
			new_pop.add(k2);
			
		}
		
		//finished so assign new_pop to population
		rules = new_pop;
		
	}

	private void mutate(Rule k) {
		String s = "";
		for(int i = 0; i < k.getAntecedent().length() + k.getConsequent().length(); ++i){
			if(rand.nextDouble() < mutation){
				if(i < k.getAntecedent().length()){
					s = k.getAntecedent().substring(0, i) + ant_symbols.get(rand.nextInt(ant_symbols.size())) + k.getAntecedent().substring(i+1, k.getAntecedent().length());
					k.setAntecedent(s);
				}
				else{
					s = k.getConsequent().substring(0, i - k.getAntecedent().length()) + con_symbols.get(rand.nextInt(con_symbols.size())) + k.getConsequent().substring(i+1-k.getAntecedent().length(), k.getConsequent().length());
					k.setConsequent(s);
				}
			}
		}
		
	}

	private void crossover(Rule m, Rule d, Rule k1, Rule k2) {
		
		//just return parents as offspring depending on crossover rate and whether they are the same
		if((rand.nextFloat() > crossover) || (m.equals(d))){
			
			k1 = m;
			k2 = d;
			
			return;
		}
		
		//create the offspring
		StringBuffer a1 = new StringBuffer();
		StringBuffer c1 = new StringBuffer();
		StringBuffer a2 = new StringBuffer();
		StringBuffer c2 = new StringBuffer();
		
		int cp = rand.nextInt(m.getAntecedent().length()+m.getConsequent().length());
		
		for(int i = 0; i < cp; ++i){
			if(i < m.getAntecedent().length()){
				a1.append(m.getAntecedent().charAt(i));
				a2.append(d.getAntecedent().charAt(i));
			}
			else{
				c1.append(m.getConsequent().charAt(i - m.getAntecedent().length()));
				c2.append(d.getConsequent().charAt(i - m.getAntecedent().length()));
			}
		}
		for(int i = cp; i < m.getAntecedent().length()+m.getConsequent().length(); ++i){
			if(i < m.getAntecedent().length()){
				a1.append(d.getAntecedent().charAt(i));
				a2.append(m.getAntecedent().charAt(i));
			}
			else{
				c1.append(d.getConsequent().charAt(i - m.getAntecedent().length()));
				c2.append(m.getConsequent().charAt(i - m.getAntecedent().length()));
			}
		}
		
		k1.setAntecedent(a1.toString());
		k1.setConsequent(c1.toString());
		
		k2.setAntecedent(a2.toString());
		k2.setConsequent(c2.toString());
		
		return;
		
	}

	private void calculateStats() {
		
		total_strength = 0;
		avg_strength = 0;
		
		for(Rule r : rules)
			total_strength += r.getStrength();
		
		avg_strength = total_strength/rules.size();
		
	}

	private Rule getChromRoulette() {
		
		//create a random number between 0 and total fitness count
		double slice = rand.nextDouble()*total_strength;
		
		//the chosen chromosomes
		Rule r = null;
		
		//go through the chromosomes adding the fitness fo far
		double fitnessSoFar = 0;
		
		for(int i = 0; i < rules.size(); ++i){
			
			fitnessSoFar = fitnessSoFar + rules.get(i).getStrength();
			
			//if the fitness so far > random number, return the chromosome at this point
			if(fitnessSoFar >= slice){
				r = rules.get(i);
				break;
			}
			
		}
		
		return r;

	}

}
