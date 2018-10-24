import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;



public class Experiments {


	//iterates through every agent in a population and applies an interaction between two random agents
	//returns an updated list of agents
	public static int evolve(ArrayList<Agent> pop){
		ArrayList<Agent> nextgen = new ArrayList<Agent>();
		Collections.shuffle(pop);
		int expressions = 0;
		for(int i = 0; i < pop.size(); i = i + 2){
			Agent a1 = pop.get(i);
			Agent a2 = pop.get(i+1);
			expressions = expressions + Functions.interact(a1,a2);
			pop.set(i, a1);
			pop.set(i+1, a2);
		}
		/*
		while(pop.size() > 0){
			Agent ag1 = pop.remove(0);
			Agent ag2 = pop.remove(0);
			expressions = expressions + Functions.interact(ag1, ag2);
			nextgen.add(ag1);
			nextgen.add(ag2);
		}
		*/
		
		return expressions;		
	}
	
	//Creates an n sized population with a percentage percent "evil"
	//n: total population size, percentage: percentage evil
	public static ArrayList<Agent> newPop(int n, double percentage, double cmin, double cmax){
		ArrayList<Agent> loa = new ArrayList<>();
		int evils = (int) Math.round(n* percentage);
		int goods = n - evils;
		
		for(int i=0; i < evils; i++){
			double min = .6;
			double max = 1;
			double bias = ThreadLocalRandom.current().nextDouble(min, max);
			double comm = ThreadLocalRandom.current().nextDouble(cmin, cmax);
			
			Agent nAgent = new Agent(bias, comm);
			loa.add(nAgent);
		}
		for(int x=0; x < goods; x++){
			double min = .0;
			double max = .4;
			double bias = ThreadLocalRandom.current().nextDouble(min, max);
			double comm = ThreadLocalRandom.current().nextDouble(cmin, cmax);
			
			Agent nAgent = new Agent(bias, comm);
			loa.add(nAgent);
		}
		return loa;
		
	}
	

	
	
	//Runs an evolution of a population and gives data based on what happened during the iteration 
	//Returns a string that shows how the agents changed their biases from their original values
	public static ArrayList<Integer> biasChange(ArrayList<Agent> pop){
		ArrayList<Integer> changes = new ArrayList<Integer>();
		int nEvils = 0;
		int nGoods = 0;
		int allEvils = 0;
		int allGoods = 0;
		String ans = "";
		
		for(Agent a : pop){
			if(a.getBias() > .5){
				if(a.lastBias() < .5){
					nGoods++;
				}
			}else{
				if(a.lastBias() > .5){
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
		changes.add(nGoods);
		changes.add(nEvils);
		//System.out.println(ans);
		return changes;
	}
	
	public static void runExperiment(int popSize, double percentEvil, int iterrations, double cmin, double cmax, boolean save) throws FileNotFoundException{
		String details = "Pop: " + popSize + ", Percent Evil: " + percentEvil + ", iterrations: " + iterrations + " ---------";
		ArrayList<Agent> pop = newPop(popSize, percentEvil, cmin, cmax);
		
		StringBuilder sb = new StringBuilder();
        sb.append("good to evil");
		sb.append(',');
		sb.append("evil to good");
		sb.append(',');
		sb.append(details);
		sb.append('\n');
		
		for(int i=0; i < iterrations; i++){
			//pop = evolve(pop);
			ArrayList<Integer> cs = biasChange(pop);
			
			sb.append("" + cs.get(0));
			sb.append(',');
			sb.append("" + cs.get(1));
			sb.append('\n');
		}
		System.out.println(details);
		if(save){
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH,mm,ss").format(Calendar.getInstance().getTime());
			String fileName = "Size" + popSize + "_Evil" + percentEvil + "_Min" + cmin + "_Max" + cmax + "_" + timeStamp + ".csv";
			PrintWriter pw = new PrintWriter(new File(fileName));
			pw.write(sb.toString());
			System.out.println("Saved");
	        pw.close();
		}
		System.out.println();
	}
	
	public static void batchExperiment() throws FileNotFoundException{
		runExperiment(1000, .1, 100, .3, 1, true);
		runExperiment(1000, .2, 100, .3, 1,true);
		runExperiment(1000, .3, 100, .3, 1,true);
		runExperiment(1000, .4, 100, .3, 1,true);
		runExperiment(1000, .1, 100, .6, 1, true);
		runExperiment(1000, .2, 100, .6, 1,true);
		runExperiment(1000, .3, 100, .6, 1,true);
		runExperiment(1000, .4, 100, .6, 1,true);
	}
	
	
	
	public static void main(String []args) throws FileNotFoundException {
		
		//runExperiment(1000, .1, 100, .3, 1, true);
		//batchExperiment();
		ArrayList<Agent> pop = newPop(10, .2, .3, 1);
		for(Agent a : pop){
			System.out.println(a.getAgent());
		}
		evolve(pop);
		System.out.println(evolve(pop));
		for(Agent a : pop){
			System.out.println(a.agentReport());
		}
		
	}
}


