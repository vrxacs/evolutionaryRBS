package implementations;

import java.util.ArrayList;
import java.util.Random;

import rbs.EvoRBS;
import rbs.Rule;
import rbs.RuleBasedSystem;

public class FillInTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<Rule> r = new ArrayList<Rule>();
		int size = 10;
		
		//list of antecedent symbols
		ArrayList<String> ant_symbols = new ArrayList<String>();
		ant_symbols.add("0");
		ant_symbols.add("1");
		ant_symbols.add("2");
		ant_symbols.add("*");
		
		//list of consequent symbols
		ArrayList<String> con_symbols = new ArrayList<String>();
		for(int i = 0; i < 9; ++i)
			con_symbols.add(Integer.toString(i));
		
		//initialize population randomly
		StringBuffer a;
		String c;
		double s;
		Random rand = new Random();
		
		for(int i = 0; i < size; ++i){
			a = new StringBuffer();
			s = 10;
			
			for(int j = 0; j < 9; ++j){
				a.append(ant_symbols.get(rand.nextInt(ant_symbols.size())));
			}

			c = con_symbols.get(rand.nextInt(con_symbols.size()));
			
			for(int j = 0; j < a.length(); ++j){
				if(a.charAt(j) == '*')
					s--;
			}
			
			r.add(new Rule(a.toString(), c, s));
			
		}
		
		EvoRBS rbs = new EvoRBS(0.01, 0.5, r, ant_symbols, con_symbols);
		
		int n = 0;
		String input = "222222222";
		int con;
		
		while(true){
			
			con = Integer.parseInt(rbs.chooseRule(input));
			

					
		}
		
	}

}
