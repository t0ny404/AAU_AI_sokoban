import cz.sokoban4j.Sokoban;
import cz.sokoban4j.simulation.SokobanResult;

import java.util.Scanner;

class RunMyAgent {
    public static void main(String[] args) {
		SokobanResult result;

		int level;
		String agent;

		while (true) {
			System.out.println("\nPress '0' to exit or select a level by typing a number between '1' - '15' followed by " +
					"the character 'i' for IDA* agent and 'b' for BFS agent\n");
			level = new Scanner(System.in).nextInt();

			if (level == 0) {
				System.exit(0);
			} else {
				agent = new Scanner(System.in).next();
				// VISUALIZED GAME
				result = Sokoban.playAgentLevel("easy.sok", level,
						agent.equals("i") || agent.equals("I") ? new AgentIDAs() :
								(agent.equals("a") || agent.equals("A") ? new AgentAS() :
										new AgentBFS()));
				System.out.println("Agent result: " + result.getResult());
			}
		}
	}
}
