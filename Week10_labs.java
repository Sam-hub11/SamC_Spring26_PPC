import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.*;
import java.util.function.*;
import java.util.Map;

public class Week10_labs
{
    public static void main(String[] args)
    {
        List<String> fruit = Arrays.asList("cherry","banana","berry","apple","cherry","kiwi","fig","date","lemon","honeydew","cherry","elderberry","apple","banana","grape");

        // Collect elements into a Set
        Set<String> uniqueFruit = fruit.stream().collect(Collectors.toSet());
        System.out.println("--> Unique fruit: " + uniqueFruit);

        // Collect the fruit into groups based on their first character
        Map<Character, List<String>> groupByFirstChar = fruit.stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
        System.out.println("--> Grouped by first letter: " + groupByFirstChar);

        // Group fruit by the length of the name
        Map<Integer, List<String>> groupByLength = fruit.stream().collect(Collectors.groupingBy(s -> s.length()));
        System.out.println("--> Grouped by length: " + groupByLength);

        // Collect the fruit that has "erry" in it
        List<String> containsErry = fruit.stream().filter(s -> s.contains("erry")).collect(Collectors.toList());
        System.out.println("--> Fruit with 'erry': " + containsErry);

        // Create a partition of fruit based on if it contains "erry"
        Map<Boolean, List<String>> partitionErry = fruit.stream().collect(Collectors.partitioningBy(s -> s.contains("erry")));
        System.out.println("--> Partition contains 'erry': " + partitionErry);

        // collect the fruit that has 5 or less symbols
        List<String> fiveOrLess = fruit.stream().filter(s -> s.length() <= 5).collect(Collectors.toList());
        System.out.println("--> Fruit length <=5: " + fiveOrLess);

        // find the total number of symbols in all the fruit stored
        int totalSymbols = fruit.stream().mapToInt(s -> s.length()).sum();
        System.out.println("--> Total characters: " + totalSymbols);

        List<Integer> data = Arrays.asList(87, 23, 45, 100, 6, 78, 92, 44, 13, 56, 34, 99, 82, 19, 1012, 78, 45, 90, 23, 56, 78, 100, 3, 43, 67, 89, 21, 34, 10);

        // Partition data based on if >=50
        Map<Boolean, List<Integer>> partition50 = data.stream().collect(Collectors.partitioningBy(n -> n >= 50));
        System.out.println("--> Partition >=50: " + partition50);

        // divide data into groups based on the remainder when divided by 7
        Map<Integer, List<Integer>> groupByRemainder = data.stream().collect(Collectors.groupingBy(n -> n % 7));
        System.out.println("--> Grouped by remainder (mod 7): " + groupByRemainder);

        // find the sum of the data
        int sum = data.stream().mapToInt(n -> n).sum();
        System.out.println("--> Sum: " + sum);

        // collect the unique values
        Set<Integer> uniqueData = data.stream().collect(Collectors.toSet());
        System.out.println("--> Unique values: " + uniqueData);

        // compute the cube of each values
        List<Integer> cubes = data.stream().map(n -> n * n * n).collect(Collectors.toList());
        System.out.println("--> Cubes: " + cubes);

        // find the sum of the cubes of each value
        int sumCubes = data.stream().mapToInt(n -> n * n * n).sum();
        System.out.println("--> Sum of cubes: " + sumCubes);

        // increase the value of each element by 5
        List<Integer> plusFive = data.stream().map(n -> n + 5).collect(Collectors.toList());
        System.out.println("--> Each value +5: " + plusFive);

        // compute the cube of the even values
        List<Integer> cubeEven = data.stream().filter(n -> n % 2 == 0).map(n -> n * n * n).collect(Collectors.toList());
		System.out.println("--> Cube of even values: " + cubeEven);
    }
}