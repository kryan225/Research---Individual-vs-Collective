import java.util.ArrayList;
import java.util.Collections;

public class Functions {
	//Decides whether an Agent will express a feature
		// ** Undecided Method - currently set to return binary 0-dont express, 1-express
		public static int express(Agent a){
			double threshold = Math.random();
			
			//determine the agents neutral zone
			double nRight = ((1-a.getCommitment())/3) * (1-a.getBias());
			double nLeft = ((1-a.getCommitment())/3) * a.getBias();
			//System.out.println("Bias: " + a.getBias() + " - " + nLeft + " + " + nRight);
			//System.out.println("Neutral Zone: " + (a.getBias() - nLeft) + ", " + (a.getBias() + nRight));
			//System.out.println("Threshold: " + threshold);
			
			
			//compare a threshold to the boundaries of the agent's neutral zone
			if(threshold < (a.getBias() - nLeft)){//threshold is in the negative expression zone
				return -1;
			}else if(threshold < (a.getBias() + nRight)){//threshold is in the neutral zone
				return 0;
			}else if(threshold <= 1){//threshold is in the positive expression zone
				return 1;
			}else{
				throw new java.lang.Error("Threshold for expression must be less then or equal to 1");
			}
		}
		
		
		//Updates an agent's bias after it has an interaction
		//Agent a is the agent to be updated, expression is the expression that the agent's partner expressed
		//quality is the quality of the interaction ex: positive: 1, negative: -1,
		//		for neutral expression: expression should be 0, quality has no effect
		public static void updateAgent(Agent a, int expression, int quality){
			int exp = expression * quality;//if it is a negative interaction, the -1 value for quality will switch directions of the bias shift
			
			double bias = a.getBias();
			double comm = a.getCommitment();
			
			double newBias = bias - ( ( (1-comm) /10) * exp);
			//double newComm = comm + ( ( ((1/2) - (bias))/10 ) * exp);
			double newComm = comm - ( ( (1-comm) /10) * quality);
			a.setBias(newBias);
			a.setCommitment(newComm);
			
			return;
		}
		
		//Interaction function between 2 agents
		//Should change commitment based off their bias and openness values
		public static void interact(Agent a1, Agent a2){
			int express1 = express(a1);
			int express2 = express(a2);
			
			//Both agents agree - Move commitment up
			if(Math.round(a1.getBias()) == Math.round(a2.getBias())){
				//a1.setCommitment(a1.getCommitment() + (0.05 * express2));
				//a2.setCommitment(a2.getCommitment() + (0.05 * express1));
				updateAgent(a1, express2, -1);
				updateAgent(a2, express1, -1);
				
			}else{
				//Agents don't agree - adjust commitment based off openness
				double threshold = Math.random();
				if(threshold < a1.getCommitment()){//agents dont agree and A1 has a bad interaction: reinforce bias + commitment
					updateAgent(a1, express2, -1);
				}else{//agents dont agree but A1 has good interaction: decrease bias + commitment
					updateAgent(a1, express2, 1);
				}
				if(threshold < a2.getCommitment()){//agents dont agree and A2 has a bad interaciton: reinforce bias + commitment
					updateAgent(a2, express1, -1);
				}else{//agents dont agree but A2 has a good interaction: decrease bias + commitment
					updateAgent(a2, express1, 1);
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
		//***currently only creates agents with a commitment of 90%
		public static ArrayList<Agent> newPop(int n, double percentage){
			ArrayList<Agent> loa = new ArrayList<>();
			int evils = (int) Math.round(n* percentage);
			int goods = n - evils;
			
			for(int i=0; i < evils; i++){
				Agent nAgent = new Agent(.9, .9);
				loa.add(nAgent);
			}
			for(int x=0; x < goods; x++){
				Agent nAgent = new Agent(.2, .9);
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
			System.out.println("Total commitment changes UP: " + (goodUP + badUP));
			System.out.println("Total commitment changes DOWN: " + (goodDOWN + badDOWN));
			double ret = (goodUP + goodDOWN + badUP + badDOWN);///size;
			//ret = ret/100;
			return ret;
		}
		
		
		public static void main(String []args) {

			Agent a = new Agent(.8,.7);
			updateAgent(a, 0, 1);
			System.out.println(a.getAgent());
			
		}
	
	
}
