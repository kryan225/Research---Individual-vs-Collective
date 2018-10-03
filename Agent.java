import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Agent {
	double Expression;
	double Commitment;
	double Openness; 
	
	public Agent(double e, double c, double o) {
		Expression = e;
		Commitment = c;
		Openness = o;
	}

	//Return the Agent's Expression value
	public double getExpression(){
		return Expression;
	}
	//Change the Agents Expression value to given variable
	public void setExpression(double e){
		Expression = e;
		return;
	}
	
	//Return the Agent's Commitment Value
	public double getCommitment(){
		return Commitment;
	}
	//Change the Agent's Commitment Value to given variable
	public void setCommitment(double c){
		Commitment = c;
		return;
	}
	
	//Return the Agent's Openness value
	public double getOpenness(){
		return Openness;
	}
	//Change the Agent's Openness value to given variable
	public void setOpenness(double o){
		Openness = o;
		return;
	}
	
	//Returns a list of all the Agent's variables
	public ArrayList<String> getAgent(){
		ArrayList<String> components = new ArrayList<>();
		components.add("Expr: " + Expression);
		components.add("Comm: " + Commitment);
		components.add("Open: " + Openness);
		
		return components;
	}
	
	//Decides whether an Agent will express a feature
	// ** Undecided Method - currently set to return binary 0-dont express, 1-express
	public static int express(Agent a){
		double threshold = Math.random();
		if(threshold < a.getExpression()){
			return 1;
		}else{
			return 0;
		}
	}
	
	//Interaction function between 2 agents
	//Should change commitment based off their expressions and openness values
	public static void interact(Agent a1, Agent a2){
		int express1 = express(a1);
		int express2 = express(a2);
		
		//Both agents agree - Move commitment up
		if(Math.round(a1.getExpression()) == Math.round(a2.getExpression())){
			a1.setCommitment(a1.getCommitment() + (0.05 * express2));
			a2.setCommitment(a2.getCommitment() + (0.05 * express1));
			
		}else{
			//Agents don't agree - adjust commitment based off openness
			double threshold = Math.random();
			if(threshold <= a1.getOpenness()){
				a1.setCommitment(a1.getCommitment() + .05);
			}else{
				a1.setCommitment(a1.getCommitment() - .05);
			}
			if(threshold <= a2.getOpenness()){
				a2.setCommitment(a2.getCommitment() + .05);
			}else{
				a2.setCommitment(a2.getCommitment() - .05);
			}
			
			
		}
		
		return;
	}
	
	
	//iterates through every agent in a population and applies an interaction between two random agents
	//returns an updated list of agents
	public static ArrayList<Agent> evolve(ArrayList<Agent> pop){
		ArrayList<Agent> nextgen = new ArrayList<Agent>();
		Collections.shuffle(pop);
		while(pop.size() > 0){
			Agent ag1 = pop.remove(0);
			Agent ag2 = pop.remove(0);
			interact(ag1, ag2);
			nextgen.add(ag1);
			nextgen.add(ag2);
		}
		
		return nextgen;		
	}
	
	//Creates an n sized population with a percentage percent "evil"
	//n: total population size, percentage: percentage evil
	public static ArrayList<Agent> newPop(int n, double percentage){
		ArrayList<Agent> loa = new ArrayList<>();
		int evils = (int) Math.round(n* percentage);
		int goods = n - evils;
		
		for(int i=0; i < evils; i++){
			Agent nAgent = new Agent(.9, .9, 1);
			loa.add(nAgent);
		}
		for(int x=0; x < goods; x++){
			Agent nAgent = new Agent(.2, .9, 1);
			loa.add(nAgent);
		}
		return loa;
		
	}
	
	//returns percentage of how many agents expressed
	//HARD CODED FOR COMMITMENT=0.9
	public static double countChange(ArrayList<Agent> pop, int size){
		String ans = "";
		
		ArrayList<Agent> evs = evolve(pop);
		int goodUP = 0;
		int goodDOWN = 0;
		int badUP = 0;
		int badDOWN = 0;
		for(Agent a : evs){
			if(a.getExpression() > .5){
				if(a.getCommitment() > .9){
					badUP++;
				}else if(a.getCommitment() < .9){
					badDOWN++;
				}
			}else{
				if(a.getCommitment() > .9){
					goodUP++;
				}else if(a.getCommitment() < .9){
					goodDOWN++;
				}
			}
		}
		//ans = "percentage: " + percentage + '\n' + "Good up: " + goodUP + ", Good down: " + goodDOWN + ", Bad up: " + badUP + ", Bad down: " + badDOWN + '\n' + "Total: " + (goodUP + goodDOWN + badUP + badDOWN);
		//return ans;
		double ret = (goodUP + goodDOWN + badUP + badDOWN);///size;
		ret = ret/100;
		return ret;
	}
	
	
	public static void main(String []args) {
		Agent a = new Agent(.9, .7, 1);		
		Agent a2 = new Agent(.81, .5, 0);
		Agent.interact(a, a2);
		System.out.println(a.getAgent());
		System.out.println(a2.Commitment);
		System.out.println();
		ArrayList<Agent> pop = newPop(100, .3);
		/*
		for(Agent ag : pop){
			System.out.print(ag.getAgent() + ", ");
		}
		
		System.out.println();
		ArrayList<Agent> evv = evolve(pop);
		System.out.println("--");
		for(Agent ag : evv){
			System.out.print(ag.getAgent() + ", ");
		}
		*/
		
		double count = countChange(newPop(100, .5), 100);
		for(int i = 0; i < 100; i++){
			count = count + countChange(newPop(100, .5), 100);
			
		}
		System.out.println("polar pop: " + count/101);
		//System.out.println(countChange(newPop(100, .5), 100));
		System.out.println();
		count = 0;
		for(int i = 0; i < 100; i++){
			double randpop = Math.random();
			count = count + countChange(newPop(100, randpop), 100);
			
		}
		System.out.println("rand pops: " + count/100);
		System.out.println();
		count = 0;
		for(int i = 0; i < 100; i++){
			double randpop = Math.random();
			count = count + countChange(newPop(100, .1), 100);
			
		}
		System.out.println("rand pops: " + count/100);
		//System.out.println(countChange(newPop(100, randpop), 100));
		//System.out.println();
		//System.out.println(countChange(newPop(100, .1), 100));
		
	}
}
