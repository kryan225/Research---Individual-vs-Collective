import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Experiments {


	//iterates through every agent in a population and applies an interaction between two random agents
	//returns an updated list of agents
	public static ArrayList<Agent> evolve(ArrayList<Agent> pop){
		ArrayList<Agent> nextgen = new ArrayList<Agent>();
		Collections.shuffle(pop);
		while(pop.size() > 0){
			Agent ag1 = pop.remove(0);
			Agent ag2 = pop.remove(0);
			Functions.interact(ag1, ag2);
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
			double min = .6;
			double max = 1;
			double bias = ThreadLocalRandom.current().nextDouble(min, max);
			double comm = ThreadLocalRandom.current().nextDouble(min, max);
			
			Agent nAgent = new Agent(bias, comm);
			loa.add(nAgent);
		}
		for(int x=0; x < goods; x++){
			double min = .0;
			double max = .4;
			double bias = ThreadLocalRandom.current().nextDouble(min, max);
			min = .6;
			max = 1;
			double comm = ThreadLocalRandom.current().nextDouble(min, max);
			
			Agent nAgent = new Agent(bias, comm);
			loa.add(nAgent);
		}
		return loa;
		
	}
	
	//Counts how many agents changed their commitment up/down
	public static double countChange(ArrayList<Agent> pop){
		String ans = "";
		
		ArrayList<Agent> evs = evolve(pop);
		int goodUP = 0;
		int goodDOWN = 0;
		int badUP = 0;
		int badDOWN = 0;
		for(Agent a : evs){
			if(a.getCommitment() > a.origionalComm()){
				goodUP++;
			}else{
				goodDOWN++;
			}
			/*
			if(a.getBias() > .5){
				if(a.getCommitment() > a.origionalComm()){
					badUP++;
				}else if(a.getCommitment() < a.origionalComm()){
					badDOWN++;
				}
			}else{
				if(a.getCommitment() > a.origionalComm()){
					goodUP++;
				}else if(a.getCommitment() < a.origionalComm()){
					goodDOWN++;
				}
				
			}
			*/
		}
		
		System.out.println("Total commitment changes UP: " + (goodUP + badUP));
		System.out.println("Total commitment changes DOWN: " + (goodDOWN + badDOWN));
		double ret = (goodUP + goodDOWN + badUP + badDOWN);///size;
		//ret = ret/100;
		return ret;
	}
	
	
	//Returns a string that shows how the agents changed their biases from their origional values
	public static String biasChange(ArrayList<Agent> pop){
		int nEvils = 0;
		int nGoods = 0;
		int allEvils = 0;
		int allGoods = 0;
		String ans = "";
		
		for(Agent a : pop){
			if(a.origionalBias() > .5){
				if(a.getBias() < .5){
					nGoods++;
				}
			}else{
				if(a.getBias() > .5){
					nEvils++;
				}
			}
			
			if(a.getBias() > .5){
				allEvils++;
			}else{
				allGoods++;
			}
		}
		
		ans = nGoods + " have changed their bias from evil to good\n"+ nEvils + " have changed their bias from good to evil\n";
		ans = ans + "There are " + allEvils + " total evil agents\n" + "There are " + allGoods + " total good agents";
		return ans;
	}
	
	
	
	
	public static void main(String []args) {
		ArrayList<Agent> pop = newPop(100, .3);
		for(int i=0; i < 10000; i++){
			pop = evolve(pop);
		}
		for(Agent a : pop){
			//System.out.println(a.getAgent());
		}
		System.out.println(biasChange(pop));
	}
}


