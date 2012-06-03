package implementations;

import java.util.ArrayList;
import java.util.Collections;

import rbs.Rule;
import rbs.RuleBasedSystem;

public class Test {

	public static void main(String[] args) {
		
		
		ArrayList<Rule> rules = new ArrayList<Rule>();
		
		rules.add(new Rule("****2****", "4", 1));
		rules.add(new Rule("222212222", "0", 9));
		rules.add(new Rule("****1***2", "8", 2));
		
		RuleBasedSystem test = new RuleBasedSystem(rules);
		String board = "222212222";
		int con;
		
		while(true){
			System.out.println(board);
			con = Integer.parseInt(test.chooseRule(board));
			
			if(con > -1){
				board = board.substring(0, con) + "1" + board.substring(con+1, board.length());
				System.out.println(con);
			}
			else{
				System.out.println("Done!");
				break;
			}
		}

	}
	

}
