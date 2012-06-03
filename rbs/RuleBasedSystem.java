package rbs;

import java.util.ArrayList;
import java.util.Collections;

public class RuleBasedSystem {

	protected ArrayList<Rule> rules;
	protected ArrayList<Rule> used_rules;

	public RuleBasedSystem(){
		rules = null;
	}
	
	public RuleBasedSystem(ArrayList<Rule> r) {
		rules = r;
	}

	public String chooseRule(String input){
		ArrayList<Rule> r = new ArrayList<Rule>();
		String ant = null;
		boolean match = false;
		
		for(int i = 0; i < this.getRules().size(); ++i){
			
			ant = this.getRules().get(i).getAntecedent();
			
			if(input.equals(ant)){
				r.add(this.getRules().get(i));
			}
			else if(ant.contains("*")){
				
				match = true;
				
				for(int j = 0; j < input.length(); ++j){
					
					if(ant.charAt(j) != '*' && ant.charAt(j) != input.charAt(j))
						match = false;
					
				}
				
				if(match)
					r.add(this.getRules().get(i));
				
			}
			
		}
		
		Collections.sort(r);
		
		//returns the consequent of the rule with highest strength that fits the input
		if(r.size() == 0)
			return "-1";
		else{
			Rule used = r.get(r.indexOf(Collections.max(r)));
			used_rules.add(used);
			return used.getConsequent();
		}

	}
	
	public void printRules(){
		
		for(Rule r : rules){
			System.out.println(r.getAntecedent()+"->"+r.getConsequent()+"   "+r.getStrength());
		}
		
	}
	
	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public void clearUsedRules(){
		used_rules.clear();
	}
	
}
