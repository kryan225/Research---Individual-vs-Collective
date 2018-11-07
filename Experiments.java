import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;



public class Experiments {


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

	//iterates through every agent in a population and applies an interaction between two random agents
	//returns an updated list of agents
	public static ArrayList<Integer> evolve(ArrayList<Agent> pop){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		ArrayList<Integer> interactReport = new ArrayList<Integer>();
		
		
		Collections.shuffle(pop);
		int expressions = 0;
		int harmonious = 0;
		int differing = 0;
		int posResponse = 0;
		int negResponse = 0;
		int gExp = 0;
		int eExp = 0;
		for(int i = 0; i < pop.size(); i = i + 2){
			Agent a1 = pop.get(i);
			Agent a2 = pop.get(i+1);
			interactReport = Functions.interact(a1,a2);
			
			expressions = expressions + interactReport.get(0);//gets how many expressions there were
			harmonious = harmonious + interactReport.get(1); // gets number of harmonious interactions
			differing = differing + interactReport.get(2); //gets number of differing interactions
			posResponse = posResponse + interactReport.get(3);//gets # of good responses to interactions
			negResponse = negResponse + interactReport.get(4);//gets the # of negative responses to interactions
			gExp = gExp + interactReport.get(5);//gets the # of good expressions
			eExp = eExp + interactReport.get(6);//gets the # of evil expressions
			
			pop.set(i, a1);
			pop.set(i+1, a2);
		}
		ret.add(expressions);
		ret.add(harmonious);
		ret.add(differing);
		ret.add(posResponse);
		ret.add(negResponse);
		ret.add(gExp);
		ret.add(eExp);
		return ret;		
	}
	
	//computes the total bias change of a population based off the previous state of the population 
	//compares to the previous evolution
	public static double commitmentChange(ArrayList<Agent> pop){
		
		double change = 0;
		for(Agent a : pop){
			change = change + (a.lastBias() - a.getBias());
		}
		return change;
	}
	
	
	//counts how many agents crossed the .5 bias threshold
	//also counts how many agents changed from good->evil, evil->good
	public static ArrayList<Integer> countCrossed(ArrayList<Agent> pop){
		ArrayList<Integer> report = new ArrayList<Integer>();
		int totCrossed = 0;
		int toGood = 0;
		int toEvil = 0;
		
		for(Agent a : pop){
			if( (a.getCrossed() > 0) ){ //&& (a.getCrossed() % 2 > 0)){
				totCrossed++;
			}
			if(a.origionalBias() < .5){//this was originally a good agent
				if(a.getBias() > .5){//switched to evil
					toEvil++;
				}
			}else{//original evil agent
				if(a.getBias() < .5){//switched to good
					toGood++;
				}
			}
		}
		
		report.add(totCrossed);
		report.add(toGood);
		report.add(toEvil);
		return report;
	}
	
	

	//Returns a string that shows how the agents changed their biases from their most recent values
	//Shows how many agents changed their bias from their previous state
	public static ArrayList<Integer> biasChange(ArrayList<Agent> pop){
		ArrayList<Integer> changes = new ArrayList<Integer>();
		int nEvils = 0;
		int nGoods = 0;
		int allEvils = 0;
		int allGoods = 0;
		int goodAgents = 0;
		int evilAgents = 0;
		String ans = "";
		
		for(Agent a : pop){
			if(a.getBias() > .5){
				evilAgents++;
				if(a.lastBias() < .5){
					nEvils++;
				}
			}else{
				goodAgents++;
				if(a.lastBias() > .5){
					nGoods++;
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
		changes.add(goodAgents);
		changes.add(evilAgents);
		//System.out.println(ans);
		return changes;
	}
	
	public static void runExperiment(int popSize, double percentEvil, int iterrations, double cmin, double cmax, boolean save, Result result) throws FileNotFoundException{
		String details = "Pop: " + popSize + ", Percent Evil: " + percentEvil + ", iterrations: " + iterrations + " ---------";
		ArrayList<Agent> pop = newPop(popSize, percentEvil, cmin, cmax);
		ArrayList<Integer> evoReport = new ArrayList<Integer>();//will hold info passed from an evolution
		
		StringBuilder sb = new StringBuilder();
		sb.append("iteration, total changes, changes to good, changes to bad, total commitment change, good agents, bad agents,"
				+ " number of expressions, harmonious interactions, differing interactions, positive responses, negative responses,"
				+ " good expressions, evil Expressions" + '\n');
		
		
		int expressions = 0;
		int harmonious = 0;
		int differing = 0;
		int posResponse = 0;
		int negResponse = 0;
		int gExp = 0;
		int eExp = 0;
		
		for(int i=0; i < iterrations; i++){
			//pop = evolve(pop);
			evoReport = evolve(pop);
			expressions = evoReport.get(0);// gets total number of expressions
			harmonious = evoReport.get(1);//gets number of harmonious interactions
			differing = evoReport.get(2);//gets # of differing interactions
			posResponse = evoReport.get(3);//gets # of positive responses
			negResponse = evoReport.get(4);//gets # of negative responses
			gExp = evoReport.get(5);//gets # of good expressions
			eExp = evoReport.get(6);//gets # of evil expressions
						
			ArrayList<Integer> cs = biasChange(pop);
			Double commC = commitmentChange(pop);
			sb.append("" + i+1);//iteration number
			sb.append("," + (cs.get(0) + cs.get(1))); //Total changes
			sb.append("," + cs.get(0)); //Changes to good
			sb.append("," + cs.get(1)); //changes to bad
			sb.append("," + commC); //total commitment change
			sb.append("," + cs.get(2)); //total good agents
			sb.append("," + cs.get(3)); //total bad agents
			sb.append("," + expressions); // number of expressions per iteration
			sb.append("," + harmonious);//number of harmonious interactions
			sb.append("," + differing);//number of differing interactions
			sb.append("," + posResponse);//number of positive responses
			sb.append("," + negResponse);//number of negative responses
			sb.append("," + gExp);//number of good expressions
			sb.append("," + eExp);//number of evil expressions
			sb.append('\n');
			
			result.addExpressions(i,expressions);			
			result.addHarmonious(i, harmonious);
			result.addDiffering(i, differing);
			result.addPosResponse(i, posResponse);
			result.addNegResponse(i, negResponse);
			result.addGoodExpressions(i, gExp);
			result.addEvilExpressions(i, eExp);
			
		}
		System.out.println(details);
		if(save){
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH,mm,ss").format(Calendar.getInstance().getTime());
			String fileName = "By Iteration " + "Size" + popSize + "_Evil" + percentEvil + "_Min" + cmin + "_Max" + cmax + "_" + timeStamp + ".csv";
			PrintWriter pw = new PrintWriter(new File(fileName));
			pw.write(sb.toString());
			System.out.println("Saved Bias");
	        pw.close();
	        
	        
	        
	        StringBuilder biasStrength = new StringBuilder();
	        biasStrength.append("Bias, Commitment, total crossed, to good, to evil" + '\n');
	        ArrayList<Integer> crossedReport = countCrossed(pop);
	        for(Agent a : pop){
	        	double b = a.getBias();
	        	DecimalFormat df = new DecimalFormat("#.##");
	        	String bias = df.format(b);
	        	biasStrength.append(bias);
	        	biasStrength.append("," + df.format(a.getCommitment()));
	        	biasStrength.append("," + crossedReport.get(0));//total crossed
	        	biasStrength.append("," + crossedReport.get(1));//tot changed from evil to good
	        	biasStrength.append("," + crossedReport.get(2));//tot changed from good to evil
	        	biasStrength.append('\n');
	        }
	        
	        String strength = "Experiment End " + "Size" + popSize + "_Evil" + percentEvil + "_Min" + cmin + "_Max" + cmax + "_" + timeStamp + ".csv";
	        PrintWriter str = new PrintWriter(new File(strength));
	        str.write(biasStrength.toString());
	        System.out.println("Saved bias strength");
	        str.close();
		}
		System.out.println();
	}
	
	public static Result batchExperiment(Double evils, double cMin, double cMax) throws FileNotFoundException{
		Result r = new Result();
		runExperiment(1000, evils, 100, cMin, cMax, false, r);
		runExperiment(1000, evils, 100, cMin, cMax, false, r);
		runExperiment(1000, evils, 100, cMin, cMax, false, r);
		runExperiment(1000, evils, 100, cMin, cMax, false, r);
		runExperiment(1000, evils, 100, cMin, cMax, false, r);
		runExperiment(1000, evils, 100, cMin, cMax, false, r);
		
		
		return r;
		
	}
	
	
	
	public static void main(String []args) throws FileNotFoundException {
		Result repo = batchExperiment(.3, .3, 1);
		
		System.out.println(repo.expressions.get(0));
		System.out.println(Result.avgValue(repo.expressions));
		System.out.println(Result.avgValue(repo.differing));
		System.out.println(Result.avgValue(repo.harmonious));
		System.out.println(Result.avgValue(repo.evilExpressions));
		System.out.println(Result.avgValue(repo.goodExpressions));
		System.out.println(Result.avgValue(repo.negResponse));
		System.out.println(Result.avgValue(repo.posResponse));
		
	}
}


