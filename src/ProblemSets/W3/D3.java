package ProblemSets.W3;

import Utils.Annotations.HelperMethod;
import Utils.Annotations.RunnableMethod;

public class D3 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Probability of a B Student getting an A: " + probabilityOfASemesterFromBStudentWithRetakes(10000, .9999, 0.85, 5, 1));
        }
    }

    @RunnableMethod
    public static double probabilityOfASemesterFromBStudentWithRetakes(int trials, double probPerQuiz, double neededGrade, int numQuizzes,
            int retakes) {
        int numOfA = 0;

        for (int i = 1; i <= trials; i++) {
            if (aSemesterFromBStudentWithRetakes(numQuizzes, probPerQuiz, neededGrade, retakes))
                numOfA++;
        }

        return (double) numOfA / trials;
    }

    @HelperMethod
    private static boolean aSemesterFromBStudentWithRetakes(int numQuizzes, double probPerQuiz, double neededGrade, int retakes) {
        double numOfA = 0;
        for (int i = 0; i < numQuizzes; i++) {
            if (Math.random() < probPerQuiz)
                numOfA++;
        }

        if ((numOfA / numQuizzes) < neededGrade) {
            for (int i = 0; i < retakes; i++) {
                if (numOfA / numQuizzes == neededGrade)
                    break;
                if (Math.random() < probPerQuiz)
                    numOfA++;
            }
        }

        return (numOfA / numQuizzes) >= neededGrade;
    }

    @RunnableMethod
    public static double probabilityOfNoAGradeSemesterWithRetakes(int trials, double probPerQuiz, int numQuizzes, int retakes) {
        int numOfNoA = 0;

        for (int i = 1; i <= trials; i++) {
            if (noAGradeSemesterWithRetakes(numQuizzes, probPerQuiz, retakes))
                numOfNoA++;
        }

        return (double) numOfNoA / trials;
    }

    @HelperMethod
    private static boolean noAGradeSemesterWithRetakes(int numQuizzes, double probPerQuiz, int retakes) {
        double numOfA = 0;
        for (int i = 0; i < numQuizzes; i++) {
            if (Math.random() < probPerQuiz)
                numOfA++;
        }

        if ((numOfA / numQuizzes) < probPerQuiz) {
            for (int i = 0; i < retakes; i++) {
                if (numOfA / numQuizzes == probPerQuiz)
                    break;
                if (Math.random() < probPerQuiz)
                    numOfA++;
            }
        }

        return (numOfA / numQuizzes) < probPerQuiz;
    }

    @RunnableMethod
    public static double probababilityOfNoAGradeSemester(int trials, double probPerQuiz, int numQuizzes) {
        int numOfNoA = 0;

        for (int i = 1; i <= trials; i++) {
            if (noAGradeSemester(numQuizzes, probPerQuiz))
                numOfNoA++;
        }

        return (double) numOfNoA / trials;
    }

    @HelperMethod
    private static boolean noAGradeSemester(int numQuizzes, double probPerQuiz) {
        double score = 0;
        for (int i = 0; i < numQuizzes; i++) {
            if (Math.random() < probPerQuiz)
                score++;
        }

        return (score / numQuizzes) < probPerQuiz;
    }

    @RunnableMethod
    public static double probabilityOfNoAonQuiz(int trials, double probPerQuestion, int numQuestions) {
        int numOfNoA = 0;

        for (int i = 1; i <= trials; i++) {
            if (noAonQuiz(numQuestions, probPerQuestion))
                numOfNoA++;
        }

        return (double) numOfNoA / trials;
    }

    @HelperMethod
    private static boolean noAonQuiz(int numQuestions, double probPerQuestion) {
        double score = 0;
        for (int i = 0; i < numQuestions; i++) {
            if (Math.random() < probPerQuestion)
                score++;
        }

        return (score / numQuestions) < probPerQuestion;
    }
}

class Results {
    int trials, startAmt, winAmt;
    double probability, avgBetsMade;

    public Results(int trials, int startAmt, int winAmt) {
        this.trials = trials;
        this.startAmt = startAmt;
        this.winAmt = winAmt;
        this.probability = getProbability(trials, startAmt, winAmt);
        this.avgBetsMade = getAvgBetsMade(trials, startAmt, winAmt);
    }

    @RunnableMethod
    public static double getProbability(int trials, int startAmt, int winAmt) {
        return calcProbabilityOfBigWin(trials, startAmt, winAmt);
    }

    @RunnableMethod
    public static double getAvgBetsMade(int trials, int startAmt, int winAmt) {
        return avgBetsMade(trials, startAmt, winAmt);
    }

    @HelperMethod
    private static double calcProbabilityOfBigWin(int trials, int startAmt, int winAmt) {
        int numOfWins = 0;

        for (int i = 1; i <= trials; i++) {
            // System.out.println("Trial: " + i);
            if (bigWinTrial(startAmt, winAmt)) {
                numOfWins++;
            }
        }

        return (double) numOfWins / trials;
    }

    @HelperMethod
    private static boolean bigWinTrial(int startAmt, int winAmt) {
        int amt = startAmt;
        int step = 0;
        while (true) {
            if ((int) (Math.random() * 2) == 0)
                amt += -1;
            else
                amt += 1;

            if (amt == winAmt)
                return true;
            else if (amt == 0)
                return false;

            step++;
        }
    }

    @HelperMethod
    private static double avgBetsMade(int trials, int startAmt, int winAmt) {
        int totalBets = 0;

        for (int i = 1; i <= trials; i++) {
            totalBets += bigWinTrialBets(startAmt, winAmt);
        }

        return (double) totalBets / trials;
    }

    @HelperMethod
    private static int bigWinTrialBets(int startAmt, int winAmt) {
        int amt = startAmt;
        int step = 0;
        while (true) {
            if ((int) (Math.random() * 2) == 0)
                amt += -1;
            else
                amt += 1;

            if (amt == winAmt)
                return step;
            else if (amt == 0)
                return step;

            step++;
        }
    }

    public String toString() {
        return "Win Amount: " + winAmt + "\tProbability: " + probability + "\tAverage Bets Made: " + avgBetsMade;
    }
}
