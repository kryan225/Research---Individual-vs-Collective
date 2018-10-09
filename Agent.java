import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Agent {
	double Bias;
	double Commitment;
	double Openness; 
	
	public Agent(double e, double c, double o) {
		Bias = e;
		Commitment = c;
		Openness = o;
	}

	//Return the Agent's Bias value
	public double getBias(){
		return Bias;
	}
	//Change the Agents Bias value to given variable
	public void setBias(double e){
		Bias = e;
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
		components.add("Expr: " + Bias);
		components.add("Comm: " + Commitment);
		components.add("Open: " + Openness);
		
		return components;
	}
	
	
}
