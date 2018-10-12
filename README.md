# Research---Individual-vs-Collective
#CS - 198 Research

Building a collective of agents where some portray a negative expression in regard to the overall cultural norms. 

## Agents

An agent is a representation of a person. We want to be able to have a population of people, or agents, that interact with each other. Each agent has two features: an expression bias, and a commitment bias. 
##### Expression
The expression parameter on agents acts as how often or likely an agent is to express an undesirable trait. For example, if an agent has an expression of .25, that agent will have a 25% chance of expressing the undesirable characteristic on every iteration until the expression changes. 
```
Expression(Agent):
		If a random number is less than the Agent’s expression:
			Have the agent express
		Else:
			Dont express
```


##### Commitment
Commitment is a parameter on agents that portrays how committed an agent is to their expressive stance. *Commitment controls how much an agent is affected from a negative interaction.*

## Interactions

Interactions are the types of scenarios that can take place when two agents express to each other. For example, if there are two agents that are chosen to interact with each other, one can express an evil feature while the other can express a good feature. Below is a table of all expression scenarios.
*Note: a neutral expression is when one agent expresses nothing, causing no change in the opposing agent*

| Expression Scenarios  | 
| ------------- | 
| Good vs. Evil  |
| Good vs. Good  | 
| Evil vs. Evil  |
| Neutral vs. Good | 
| Neutral vs. Evil | 
| Neutral vs Neutral |

| Affective Response to Partner Expressions | Description |
| ------------- | ------------- |
| Positive | The agent has taken the opposing "opinion" constructively. Commitment is shifted toward the opposing Agent's bias. This is supposed to resemble a person's willingness to have an open mind and take in all opinions |
| Negative | The agent has taken the opposing "opinion" as offensive. Commitment is shifted away from the opposing Agent's bias. This is supposed to resemble a person "doubling down" on their opinion. |
| Neutral | This happens when an agent's partner does not express anything, or the *express* function returns a 0. There is no change. |

*Note:* If both agents express the same magnitude (-1 and -1, 1 and 1) they will automatically have a positive response

*Note:* If both agents express opposing outcomes (-1 & 1, -1 & 0, 0 & 1) they will roll a fair dice, and when compared to each agent's commitment, the outcome will be determined
```
interact(Agent1, Agent2):
		If Agent1’s expression equals Agent2’s expression:
			Commitment rises for both agents
		Else:
			If a random number is smaller than Agent1/Agent2’s commitment openness:
				Move the corresponding commitment down
			Else:
				Move the corresponding commitment up
```
