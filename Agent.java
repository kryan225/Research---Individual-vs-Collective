

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
	
	//Decides whether an Agent will express a feature
	// ** Undecided Method - currently set to return binary 0-dont express, 1-express
	public static int express(Agent a){
		return 1;
	}
	
	//Interaction function between 2 agents
	//Should change commitment based off their expressions and openness values
	public static void interact(Agent a1, Agent a2){
		int express1 = express(a1);
		int express2 = express(a2);
		
		//Both agents agree - Move commitment up
		if(Math.round(a1.getExpression()) == Math.round(a2.getExpression())){
			a1.setCommitment(a1.getCommitment() + (0.1 * express2));
			a2.setCommitment(a2.getCommitment() + (0.1 * express1));
			
		}else{
			//Agents don't agree - adjust commitment based off openness
			double threshold = Math.random();
			if(threshold < a1.getOpenness()){
				a1.setCommitment(a1.getCommitment() - .1);
			}
			if(threshold < a2.getOpenness()){
				a2.setCommitment(a2.getCommitment() - .1);
			}
			
			
		}
		
		return;
	}
	
	
	public static void main(String []args) {
		Agent a = new Agent(.95, .7, 1);		
		Agent a2 = new Agent(.1, .5, 0);
		Agent.interact(a, a2);
		System.out.println(a.Commitment);
		System.out.println(a2.Commitment);
	}
}
