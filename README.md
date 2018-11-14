# Research---Individual-vs-Collective
#CS - 198 Research

Building a collective of agents where some portray a negative expression in regard to the overall cultural norms. The outline for experimentation is to generate a population of agents. Each agent will have the ability to either express as evil or good. We want to look at how the evil population interacts with the good population over time, as every agent has the potential to switch from evil to good or good to evil. 

## Agents

An agent is a representation of a person. We want to be able to have a population of people, or agents, that interact with each other. Each agent has two features: an expression bias, and a commitment bias. 
##### Expression Bias
The expression parameter on agents acts as how often or likely an agent is to express an undesirable trait. The bias is treated as a point on a scale from 0-1. If a random number between 0 & 1 is below the bias an agent will express a negative trait, while if it is above the bias it will express a positive trait.


##### Commitment
Commitment is a parameter on agents that portrays how committed an agent is to their expressive stance. *Commitment controls how much an agent is affected from a negative interaction.* 

## An Iterration
In a single Iterration, an entire population of Agents is randomly shuffled. Then each agent will have an interaction with a partner and respond to its partner according to what happened in the interaction in terms of how each agent expressed.

## Expressions

Expressions are based off each agent's bias. The first thing that is done is a neutral zone is calculated for the agent. The neutral zone is calculated by the following formula:
```
Right border of neutral zone: ((1-Commitment)/3) * (1-Bias)
Left border of neutral zone: ((1-Commitment)/3) * (Bias)
```
This ensures a neutral zone takes a percentage, based off the commitment, from either side of the bias. The less commited an agent, the larger the neutral zone.

After this, think of a scale from zero to one with a neutral zone surrounding the bias on the scale. A random number is generated between 0 & 1 and based off where the value is on the scale, an appropriate trait is expressed:
	Less than the neutral zone: -1 (Evil trait)
	In the neutral zone: 0 (No expression)
	Greater than the neutral zone: 1 (Good trait)

## Interactions

Interactions are the types of scenarios that can take place when two agents express to each other. For example, if there are two agents that are chosen to interact with each other, one can express an evil feature while the other can express a good feature. Below is a table of all expression scenarios.

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

*Note:* If both agents express opposing outcomes (-1 & 1, -1 & 0, 0 & 1) they each generate a random number between 0 & 1, and when compared to each agent's commitment, their separate responses to their neighbor will be determined:
```
interact(Agent1, Agent2):
		If Agent1’s expression equals Agent2’s expression:
			Positive Affective Response
		Else:
			If a random number is smaller than the current Agent's commitment:
				Negative Affective Response
			Else:
				Positive Affective Response
```



## Updates

The purpose of the ```updateAgent``` function is to update an agent's bias and commitment bassed off it's interaction with a partner.
The update function takes in 5 parameters:

| Parameter Name | Description |
| ------------- | --------------- | 
| ```Agent a``` | The agent that will be updated |
| ```int partnerExpress``` | The expression that the agent's partner expressed. This is the expression that ```Agent a``` is reacting to. Can be -1 (evil), 0 (no expression/neutral), 1 (good) | 
| ```int myExpress``` | The expression that the ```Agent a``` expressed for that round. Can be -1 (evil), 0 (no expression/neutral), 1 (good). |
| ```int quality``` | This is the quality of the interaction with the partner agent. This will decide whether ```Agent a``` reacts positively or negatively to its partner. Can be -1 (negative interaction), 1 (positive interaction) |
| ```int learningRate``` | This is rate at which the bias and commitment for ```Agent a``` is changed. The lower the learning rate the more the bias and commitment are changed per round. Our default value for learning rate is 10. | 

```Agent a```'s bias is changed according to the following formula:
```
Bias = Bias - [(1-commitment)/10](Partner Expression)(Quality of interaction)
```
and commitment is changed according to: 
```
Commitment = Commitment + [(1-Commitment)/10](Partner Expression)(My Expression)(quality of interaction)
```

