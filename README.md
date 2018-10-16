# Research---Individual-vs-Collective
#CS - 198 Research

Building a collective of agents where some portray a negative expression in regard to the overall cultural norms. 

## Agents

An agent is a representation of a person. We want to be able to have a population of people, or agents, that interact with each other. Each agent has two features: an expression bias, and a commitment bias. 
##### Expression Bias
The expression parameter on agents acts as how often or likely an agent is to express an undesirable trait. The bias is treated as a point on a scale from 0-1. If A fair dice is below the bias an agent will express a negative trait, while if it is above the bias it will express a positive trait. Bias is changed according to the equation:
```
Bias = Bias - [(1-commitment)/10](Partner Expression)(Quality of interaction)
```
Where Expression is -1,0,1 and quality is either -1,1


##### Commitment
Commitment is a parameter on agents that portrays how committed an agent is to their expressive stance. *Commitment controls how much an agent is affected from a negative interaction.* Commitment is updated according the the equation:
```
Commitment = Commitment - [(1-Commitment)/10](Partner Expression)(My Expression)(quality of interaction)
```
Where quality is either -1,1

## Expressions

Expressions are based off each agent's bias. The first thing that is done is a neutral zone is calculated for the agent. The neutral zone is calculated by the following formula:
```
Right border of neutral zone: ((1-Commitment)/3) * (1-Bias)
Left border of neutral zone: ((1-Commitment)/3) * (Bias)
```
This ensures a neutral zone takes a percentage, based off the commitment, from either side of the bias. The less commited an agent, the larger the neutral zone.

After this, think of a scale from zero to one with a neutral zone surrounding the bias on the scale. A fair dice is rolled and based off where the value of the dice lands on the scale, an appropriate trait is expressed:
	Less than the neutral zone: -1 (Evil trait)
	In the neutral zone: 0 (No expression)
	Greater than the neutral zone: 1 (Good trait)

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
