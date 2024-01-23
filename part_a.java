package securinprogram;
import java.util.Arrays;

public class part_a 
{
    public static void main(String[] args) 
    {
        int[] originalDieA = {1, 2, 3, 4, 5, 6};
        int[] originalDieB = Arrays.copyOf(originalDieA, originalDieA.length);
        System.out.println("Problem Statement : The Doomed Dice Challenge \n------------------------------------------------");

        // Part-A-1: Total Combinations
        int totalCombinations = originalDieA.length * originalDieB.length;
        System.out.println("Part - A \n1.Total Combinations : " + totalCombinations);

        // Part-A-2: Combinations Distribution
        int[][] combinationsDistribution = calculateCombinationsDistribution(originalDieA, originalDieB);
        System.out.println("\n2.Distribution of All Possible Combinations \n------------------------------------------------ ");
        combinedDisplay(originalDieA,originalDieB);   
        System.out.println("\nSum of Distribution of All Possible Combinations \n------------------------------------------------ ");
        displayMatrix(combinationsDistribution);

        // Part-A-3: Probability of Sums
        System.out.println("\n3.Probability of Sums \n------------------------------------------------");
        calculateSumProbabilities(combinationsDistribution, totalCombinations);          
    }

    public static void combinedDisplay(int[] dieA, int[] dieB) {
        for (int a : dieA) {
            System.out.print("[");
            for (int b : dieB) {
                System.out.print("(" + a + ", " + b + ") ");
            }
            System.out.println("]");
        }
    }

    private static int[][] calculateCombinationsDistribution(int[] dieA, int[] dieB) 
    {
        int[][] distribution = new int[6][6];

        for (int i = 0; i < dieA.length; i++) 
        {
            for (int j = 0; j < dieB.length; j++) 
            {
                int sum = dieA[i] + dieB[j];
                distribution[dieA[i] - 1][dieB[j] - 1] = sum;
            }
        }
        return distribution;
    }

    private static int calculateSumProbabilities(int[][] distribution, double totalCombinations) 
    {
        for (int targetSum = 2; targetSum <= 12; targetSum++) 
        {
            int count = 0;   
            for (int i = 0; i < distribution.length; i++) 
            {
            	for (int j = 0; j < distribution[i].length; j++) 
            	{
            		if (distribution[i][j] == targetSum) 
            		{
            			count++;
            		}
            	}
            }
            float probability = (float) ((float) count / totalCombinations);
            System.out.println("P(Sum = " + targetSum + ") = " + probability);
       }
		return 0;
    }
       
    private static void displayMatrix(int[][] matrix) 
    {
        for (int[] row : matrix) 
        {
            System.out.println(Arrays.toString(row));
        }
    }
    
}
