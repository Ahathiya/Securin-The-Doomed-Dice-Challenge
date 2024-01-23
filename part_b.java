package securinprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class part_b {
    public static void main(String[] args) {
    	System.out.println("Problem Statement : The Doomed Dice Challenge \n------------------------------------------------\nPart - B");

    	int[] Die_A = {1,2,3,4,5,6};
        int[] Die_B = {1,2,3,4,5,6};

        List<List<Integer>> new_die_possibilities = transform(Die_A, Die_B);
        for (List<Integer> possibility : new_die_possibilities) {
                printOrderly(possibility.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    public static void printOrderly(int[] array) {
    System.out.print("(");
    for (int i = 0; i < array.length; i++) {
        System.out.print(array[i]);
        if (i < array.length - 1) {
            System.out.print(", ");
        }
    }
    System.out.println(")");
}
    public static int sumOfArray(int[] array) {
        int ans = 0;
        for (int element : array) {
            ans = ans + element;
        }
        return ans;
    }

    public static int[][] sum_Distribution(int[] Die_A, int[] Die_B) {
        int[][] sum_Array = new int[Die_A.length][Die_B.length];
        for (int a = 0; a < Die_A.length; a++) {
            for (int b = 0; b < Die_B.length; b++) {
                sum_Array[a][b] = Die_A[a] + Die_B[b];
            }
        }
        return sum_Array;
    }

    public static double single_Probability(int[] Die_A, int[] Die_B, int sum) {
        int[][] sum_Array = sum_Distribution(Die_A, Die_B);
        int count = 0;
        for (int[] rows : sum_Array) {
            for (int column : rows) {
                if (column == sum) {
                    count = count + 1;
                }
            }
        }
        double probability = (double) count / 36;
        return probability;
    }

    public static Map<Integer, Double> all_Probability(int[] Die_A, int[] Die_B) {
        Map<Integer, Double> prob_dict = new HashMap<>();
        for (int i = 2; i <= 12; i++) {
            double probability = single_Probability(Die_A, Die_B, i);
            prob_dict.put(i, Math.round(probability * 10000.0) / 10000.0);
        }
        return prob_dict;
    }

    public static Set<List<Integer>> posibilities_Calc(List<Integer> curr, int free_space, List<Integer> input_values, Set<List<Integer>> posibilities, List<Integer> fixed_values, boolean repetition) {
        if (free_space == 0) {
            curr.sort(null);
            posibilities.add(new ArrayList<>(curr));
            return posibilities;
        }
        if (repetition) {
            for (int input_value : input_values) {
                posibilities_Calc(new ArrayList<>(curr){{add(input_value);}}, free_space - 1, input_values, posibilities, fixed_values, true);
            }
        } else {
            for (int i = 0; i < input_values.size(); i++) {
                List<Integer> remaining_input_values = new ArrayList<>(input_values);
                remaining_input_values.remove(i);
                final int inputValue = input_values.get(i);
                posibilities_Calc(new ArrayList<>(curr){{add(inputValue);}}, free_space - 1, remaining_input_values, posibilities, fixed_values, false);
            }
        }
        return posibilities;
    }

    public static List<List<Integer>> transform(int[] die_a, int[] die_b) {
        Map<Integer, Double> original_possibilities = all_Probability(die_a, die_b);
        List<Integer> fixed_values_a = List.of(1, 4);
        List<Integer> input_values_a = List.of(1, 2, 3, 4);
        int free_space_a = 4;
        Set<List<Integer>> posibilities_a = new HashSet<>();
        Set<List<Integer>> new_die_a_possibility = posibilities_Calc(new ArrayList<>(fixed_values_a), free_space_a, input_values_a, posibilities_a, fixed_values_a, true);

        List<Integer> fixed_values_b = List.of(1, 8);
        List<Integer> input_values_b = List.of(2, 3, 4, 5, 6, 7);
        int free_space_b = 4;
        Set<List<Integer>> posibilities_b = new HashSet<>();
        Set<List<Integer>> new_die_b_possibility = posibilities_Calc(new ArrayList<>(fixed_values_b), free_space_b, input_values_b, posibilities_b, fixed_values_b, false);

        List<List<Integer>> new_die_possibilities = new ArrayList<>();
        for (List<Integer> a : new_die_a_possibility) {
            for (List<Integer> b : new_die_b_possibility) {
                if ((sumOfArray(a.stream().mapToInt(Integer::intValue).toArray()) + sumOfArray(b.stream().mapToInt(Integer::intValue).toArray())) == 42) {
                    Map<Integer, Double> new_sum_possibility = all_Probability(a.stream().mapToInt(Integer::intValue).toArray(), b.stream().mapToInt(Integer::intValue).toArray());
                    if (original_possibilities.equals(new_sum_possibility)) {
                        new_die_possibilities.add(a);
                        new_die_possibilities.add(b);
                    }
                }
            }
        }
        return new_die_possibilities;
    }
}


