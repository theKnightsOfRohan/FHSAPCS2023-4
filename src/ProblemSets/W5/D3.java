package ProblemSets.W5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class D3 {
    public static void main(String[] args) {
        VotingMachine machine = new VotingMachine();
        machine.addCandidate("jill");
        machine.addCandidate("bill");
        machine.addCandidate("phil");
        machine.addCandidate("gill");

        machine.vote("jill");
        machine.vote("bill");
        machine.vote("phil");
        machine.vote("waldo");
        machine.vote("gill");
        machine.vote("gill");
        machine.vote("gill");

        System.out.println(machine.getVotes());
        System.out.println(machine.getVotes("jill"));

        System.out.println(machine.getWinner());
    }
}

class VotingMachine {
    private HashMap<String, Integer> candidateAndVotes;

    public VotingMachine() {
        candidateAndVotes = new HashMap<String, Integer>();
    }

    public void addCandidate(String candidate) {
        for (char c : candidate.toCharArray()) {
            if (!Character.isLetter(c) || !Character.isLowerCase(c)) {
                System.out.println("Candidate name is invalid");
                return;
            }
        }
        candidateAndVotes.put(candidate, 0);
    }

    public void vote(String candidate) {
        if (candidateAndVotes.containsKey(candidate)) {
            candidateAndVotes.put(candidate, candidateAndVotes.get(candidate) + 1);
            System.out.println("Vote successful!");
        } else {
            System.out.println("Candidate " + candidate + " not found");
        }
    }

    public String getVotes() {
        String statement = "";
        for (String candidate : candidateAndVotes.keySet()) {
            statement += candidate + ": " + candidateAndVotes.get(candidate) + "\n";
        }

        return statement;
    }

    public String getVotes(String candidate) {
        if (candidateAndVotes.containsKey(candidate)) {
            return candidate + ": " + candidateAndVotes.get(candidate);
        } else {
            return "Candidate " + candidate + " not found";
        }
    }

    public List<String> getWinner() {
        ArrayList<String> winners = new ArrayList<String>();
        int maxVotes = 0;
        for (String candidate : candidateAndVotes.keySet()) {
            if (candidateAndVotes.get(candidate) > maxVotes) {
                maxVotes = candidateAndVotes.get(candidate);
                winners.clear();
                winners.add(candidate);
            } else if (candidateAndVotes.get(candidate) == maxVotes) {
                winners.add(candidate);
            }
        }

        return winners;
    }
}