import java.util.ArrayList;
import java.util.Collections;

public class Functions {
	//Decides whether an Agent will express a feature
		// ** Undecided Method - currently set to return binary 0-dont express, 1-express
		//CONTINUE TO WORK ON THIS - MAKE SURE IT DUPLICATES WHAT YOU GOT IN YOUR NOTES
		public static int express(Agent a){
			double threshold = Math.random();
			double nStart = ((1-a.getCommitment())/3) * (1-a.getBias());
			double nEnd = ((1-a.getCommitment())/3) * a.getBias();
			System.out.println("Neutral Zone: " + (a.getBias() - nStart) + ", " + (a.getBias() + nEnd));
			System.out.println("Left Expression: " + (a.getBias() - nStart) + ", Neutral Expression: " + (nEnd-nStart) + ", Right Expression: " + (1-(a.getBias() + nEnd)));
			return 0;
		}
		
		//Interaction function between 2 agents
		//Should change commitment based off their biass and openness values
		public static void interact(Agent a1, Agent a2){
			int express1 = express(a1);
			int express2 = express(a2);
			
			//Both agents agree - Move commitment up
			if(Math.round(a1.getBias()) == Math.round(a2.getBias())){
				a1.setCommitment(a1.getCommitment() + (0.05 * express2));
				a2.setCommitment(a2.getCommitment() + (0.05 * express1));
				
			}else{
				//Agents don't agree - adjust commitment based off openness
				double threshold = Math.random();
				if(threshold > a1.getOpenness()){//agents dont agree and A1 has a bad interaction: reinforce bias + commitment
					a1.setCommitment(a1.getCommitment() + (express2 * .05 * a1.getCommitment()));
					a1.setBias(a1.getBias() + (express2 * .05 * a1.getCommitment()));
				}else{//agents dont agree but A1 has good interaction: decrease bias + commitment
					a1.setCommitment(a1.getCommitment() - (express2 * .05 * a1.getCommitment()));
					a1.setBias(a1.getBias() - (express2 * .05 * a1.getCommitment()));
				}
				if(threshold > a2.getOpenness()){//agents dont agree and A2 has a bad interaciton: reinforce bias + commitment
					a2.setCommitment(a2.getCommitment() + (express1 * .05 * a2.getCommitment()));
					a2.setBias(a2.getBias() + (express1 * .05 * a2.getCommitment()));
				}else{//agents dont agree but A2 has a good interaction: decrease bias + commitment
					a2.setCommitment(a2.getCommitment() - (express1 * .05 * a2.getCommitment()));
					a2.setBias(a2.getBias() - (express1 * .05 * a2.getCommitment()));
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
				if(a.getBias() > .5){
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
			Agent a = new Agent(.6, .7, 1);		
			Agent a2 = new Agent(.9, .8, 0);
			//Functions.interact(a, a2);
			
			System.out.println();
			express(a);
			System.out.println();
			express(a2);
			System.out.println();
			
		}
	
	
}
