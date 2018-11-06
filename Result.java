import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Result {
	Map<Integer, ArrayList<Integer>> expressions;
	Map<Integer, ArrayList<Integer>> harmonious;
	Map<Integer, ArrayList<Integer>> differing;
	Map<Integer, ArrayList<Integer>> posResponse;
	Map<Integer, ArrayList<Integer>> negResponse;
	Map<Integer, ArrayList<Integer>> goodExpressions;
	Map<Integer, ArrayList<Integer>> evilExpressions;
	
	public Result(){
		expressions = new HashMap<Integer, ArrayList<Integer>>();
		harmonious = new HashMap<Integer, ArrayList<Integer>>();
		differing = new HashMap<Integer, ArrayList<Integer>>();
		posResponse = new HashMap<Integer, ArrayList<Integer>>();
		negResponse = new HashMap<Integer, ArrayList<Integer>>();
		goodExpressions = new HashMap<Integer, ArrayList<Integer>>();
		evilExpressions = new HashMap<Integer, ArrayList<Integer>>();
		}
	
	public static int average(ArrayList<Integer> list){
		int n = 0;
		for(int i : list){
			n = n + i;
		}
		
		return n/list.size();
	}
	
	public static Map<Integer, Integer> avgValue(Map<Integer, ArrayList<Integer>> map){
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < map.size(); i++){
			ArrayList<Integer> lon = map.get(i);
			int avg = average(lon);
			result.put(i, avg);
		}
		
		return result;
	}
	
	
	public void clear(){
		expressions.clear();
		harmonious.clear();
		differing.clear();
		posResponse.clear();
		negResponse.clear();
		goodExpressions.clear();
		evilExpressions.clear();
	}
	
	public void addExpressions(int i, int x){
		if(expressions.containsKey(i)){
			ArrayList<Integer> l = expressions.remove(i);
			l.add(x);
			expressions.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			expressions.put(i, l);
		}
	}
	
	public void addHarmonious(int i, int x){
		if(harmonious.containsKey(i)){
			ArrayList<Integer> l = harmonious.remove(i);
			l.add(x);
			harmonious.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			harmonious.put(i, l);
		}
	}
	
	public void addDiffering(int i, int x){
		if(differing.containsKey(i)){
			ArrayList<Integer> l = differing.remove(i);
			l.add(x);
			differing.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			differing.put(i, l);
		}
	}
	
	public void addPosResponse(int i, int x){
		if(posResponse.containsKey(i)){
			ArrayList<Integer> l = posResponse.remove(i);
			l.add(x);
			posResponse.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			posResponse.put(i, l);
		}
	}
	
	public void addNegResponse(int i, int x){
		if(negResponse.containsKey(i)){
			ArrayList<Integer> l = negResponse.remove(i);
			l.add(x);
			negResponse.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			negResponse.put(i, l);
		}
	}
	
	public void addGoodExpressions(int i, int x){
		if(goodExpressions.containsKey(i)){
			ArrayList<Integer> l = goodExpressions.remove(i);
			l.add(x);
			goodExpressions.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			goodExpressions.put(i, l);
		}
	}
	
	public void addEvilExpressions(int i, int x){
		if(evilExpressions.containsKey(i)){
			ArrayList<Integer> l = evilExpressions.remove(i);
			l.add(x);
			evilExpressions.put(i, l);
		}else{
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(x);
			evilExpressions.put(i, l);
		}
	}
	
	
}
