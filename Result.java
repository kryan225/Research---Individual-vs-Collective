import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Result {
	//these are supposed to represent the iteration and corresponding value/(list of values) for each iterration
	Map<Integer, ArrayList<Double>> totalChanges;
	Map<Integer, ArrayList<Double>> changesToGood;
	Map<Integer, ArrayList<Double>> changesToBad;
	Map<Integer, ArrayList<Double>> totalCommitmentChange;
	Map<Integer, ArrayList<Double>> totalGood;
	Map<Integer, ArrayList<Double>> totalBad;	
	Map<Integer, ArrayList<Double>> expressions;
	Map<Integer, ArrayList<Double>> harmonious;
	Map<Integer, ArrayList<Double>> differing;
	Map<Integer, ArrayList<Double>> posResponse;
	Map<Integer, ArrayList<Double>> negResponse;
	Map<Integer, ArrayList<Double>> goodExpressions;
	Map<Integer, ArrayList<Double>> evilExpressions;
	Double totalCrossed;

	public Result(){
		totalChanges = new HashMap<Integer, ArrayList<Double>>();
		changesToGood = new HashMap<Integer, ArrayList<Double>>();
		changesToBad = new HashMap<Integer, ArrayList<Double>>();
		totalCommitmentChange = new HashMap<Integer, ArrayList<Double>>();
		totalGood = new HashMap<Integer, ArrayList<Double>>();
		totalBad = new HashMap<Integer, ArrayList<Double>>();
		expressions = new HashMap<Integer, ArrayList<Double>>();
		harmonious = new HashMap<Integer, ArrayList<Double>>();
		differing = new HashMap<Integer, ArrayList<Double>>();
		posResponse = new HashMap<Integer, ArrayList<Double>>();
		negResponse = new HashMap<Integer, ArrayList<Double>>();
		goodExpressions = new HashMap<Integer, ArrayList<Double>>();
		evilExpressions = new HashMap<Integer, ArrayList<Double>>();
		totalCrossed = 0.0;
	}

	//wipes a result clean
	public void clear(){
		totalChanges.clear();
		changesToGood.clear();
		changesToBad.clear();
		totalCommitmentChange.clear();
		totalGood.clear();
		totalBad.clear();
		expressions.clear();
		harmonious.clear();
		differing.clear();
		posResponse.clear();
		negResponse.clear();
		goodExpressions.clear();
		evilExpressions.clear();
	}
	
	//returns all map features in a list
	public ArrayList<Map<Integer, Double>> features(){
		ArrayList<Map<Integer, Double>> f = new ArrayList<Map<Integer, Double>>();

		f.add(avgValue(totalChanges));
		f.add(avgValue(changesToGood));
		f.add(avgValue(changesToBad));
		f.add(avgValue(totalCommitmentChange));
		f.add(avgValue(totalGood));
		f.add(avgValue(totalBad));
		f.add(avgValue(expressions));
		f.add(avgValue(harmonious));
		f.add(avgValue(differing));
		f.add(avgValue(posResponse));
		f.add(avgValue(negResponse));
		f.add(avgValue(goodExpressions));
		f.add(avgValue(evilExpressions));
		return f;
	}

	//finds the average value in a list of integers
	public static Double average(ArrayList<Double> list){
		Double n = 0.0;
		for(Double i : list){
			n = n + i;
		}

		return n/list.size();
	}


	//takes in a map with an index, (list of) integers
	//returns a map with an index, and an average over the corresponding (list of)integers
	public static Map<Integer, Double> avgValue(Map<Integer, ArrayList<Double>> map){
		Map<Integer, Double> result = new HashMap<Integer, Double>();

		for(int i = 0; i < map.size(); i++){
			ArrayList<Double> lon = map.get(i);
			Double avg = average(lon);
			result.put(i, avg);
		}

		return result;
	}




	//function to add to a map feature 
	public void addTo(int i, Double x, Map<Integer, ArrayList<Double>> map){
		if(map.containsKey(i)){
			ArrayList<Double> l = map.remove(i);
			l.add((double) x);
			map.put(i, l);
		}else{
			ArrayList<Double> l = new ArrayList<Double>();
			l.add((double) x);
			map.put(i, l);
		}
	}

	
	/*
	 *iteration, total changes, changes to good, changes to bad, total commitment change, good agents, bad agents,"
				+ " number of expressions, harmonious interactions, differing interactions, positive responses, negative responses,"
				+ " good expressions, evil Expressions 
	 * 
	 */
	 
	
	//Prints out a full report of the information in the result object to a csv file
	public void printReport(int popSize, double percentEvil, int iterrations, double cmin, double cmax) throws FileNotFoundException{
		StringBuilder sb = new StringBuilder();
		sb.append("Iterration, Total Changes, Changes To Good, Changes To Bad, Total Commitment Changes, Total Good Agents, Total Bad Agents,"
				+ "Expressions, Harmonious, Differing, Positive Response, Negative Response, Good Expressions, Evil Expressions, "
				+ "" + '\n');

		ArrayList<Map<Integer, Double>> features = features();
		for(int i = 0; i < expressions.size(); i++){
			sb.append(i + ",");
			for(Map<Integer, Double> mp : features){
				sb.append(mp.get(i) + ",");
			}
			sb.append('\n');
		}
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH,mm,ss").format(Calendar.getInstance().getTime());
		String fileName = "By Iteration " + "Size" + popSize + "_Evil" + percentEvil + "_Min" + cmin + "_Max" + cmax + "_" + timeStamp + ".csv";
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.write(sb.toString());
		pw.close();
	}

}
