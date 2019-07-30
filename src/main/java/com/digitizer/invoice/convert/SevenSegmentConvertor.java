package com.digitizer.invoice.convert;

import java.util.*;


/**
 * Seven segment digit convertor to the regular number in the String representation
 * the algorithm is based on the segment-to-letter mapping for digit recognition.
 *
 */
public class SevenSegmentConvertor {

    private static final HashMap<String, Integer> digitsInSevenSegment = new HashMap<>();
    private static final HashMap<String, String> secondSegmentsLevel = new HashMap<>();
    private static final HashMap<String, String> thirdSegmentsLevel = new HashMap<>();
    private static final String leftSegment = "|_ ";
    private static final String rightSegment = " _|";
    private static final String rightAndLeftSegment = "|_|";
    private static final String twoSegments = "| |";
    private static final String oneSegment = "_";
    private static final String oneLeftSegment = "|  ";
    private static final String oneRightSegment = "  |";

    static {
        digitsInSevenSegment.put("afbedc", 0);
        digitsInSevenSegment.put("bc", 1);
        digitsInSevenSegment.put("abged", 2);
        digitsInSevenSegment.put("abgdc", 3);
        digitsInSevenSegment.put("fgbc", 4);
        digitsInSevenSegment.put("afgdc", 5);
        digitsInSevenSegment.put("afgedc", 6);
        digitsInSevenSegment.put("abc", 7);
        digitsInSevenSegment.put("afgbedc", 8);
        digitsInSevenSegment.put("afgbdc", 9);

        secondSegmentsLevel.put(leftSegment, "fg");
        secondSegmentsLevel.put(rightSegment, "bg");
        secondSegmentsLevel.put(rightAndLeftSegment, "fgb");
        secondSegmentsLevel.put(twoSegments, "fb");
        secondSegmentsLevel.put(oneLeftSegment, "f");
        secondSegmentsLevel.put(oneRightSegment, "b");

        thirdSegmentsLevel.put(leftSegment, "ed");
        thirdSegmentsLevel.put(rightSegment, "dc");
        thirdSegmentsLevel.put(rightAndLeftSegment, "edc");
        thirdSegmentsLevel.put(twoSegments, "ec");
        thirdSegmentsLevel.put(oneLeftSegment, "e");
        thirdSegmentsLevel.put(oneRightSegment, "c");
    }

    /**
     * The main conversion static method
     * @param sevenSegmentNumber the list of rows contain the 7-segment digit
     * @return the converted digit to the string format
     */
    public static String convert(List<String> sevenSegmentNumber) {
        List<String> level_one = new ArrayList<>();
        List<String> level_two = new ArrayList<>();
        List<String> level_three = new ArrayList<>();
        for (String element : parseLineToTriples(sevenSegmentNumber.get(0))) {
            level_one.add(element.trim().equals(oneSegment) ? "a" : "");
        }
        for (String element : parseLineToTriples(sevenSegmentNumber.get(1))) {
            level_two.add(secondSegmentsLevel.get(element));
        }
        for (String element : parseLineToTriples(sevenSegmentNumber.get(2))) {
            level_three.add(thirdSegmentsLevel.get(element));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < level_one.size(); i++) {
            String number = level_one.get(i) + level_two.get(i) + level_three.get(i);
            result.append(digitsInSevenSegment.containsKey(number.trim()) ? digitsInSevenSegment.get(number.trim()) : "?");
        }
        return result.toString();
    }

    private static Collection<String> parseLineToTriples(String line) {
        return Arrays.asList(line.split("(?<=\\G...)"));
    }

}
