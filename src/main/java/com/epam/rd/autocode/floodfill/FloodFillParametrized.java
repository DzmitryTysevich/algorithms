package com.epam.rd.autocode.floodfill;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FloodFillParametrized implements FloodFill {
    private final char water = FloodFill.WATER;

    @Override
    public void flood(String map, FloodLogger logger) {
        logger.log(map);
        getFlood(map, logger);

    }

    private void getFlood(String map, FloodLogger logger) {
        StringBuilder stringBuilderMap = new StringBuilder(map);
        int lineLength = getLineLength(stringBuilderMap);
        addWaterPositions(stringBuilderMap, lineLength);
        logger.log(stringBuilderMap.toString());

        if (isLand(stringBuilderMap))
            getFlood(stringBuilderMap.toString(), logger);
    }

    private void addWaterPositions(StringBuilder stringBuilderMap, int lineLength) {
        List<Integer> indexes = getWaterIndexes(stringBuilderMap);
        indexes.forEach(index -> {
            addWaterInLeft(stringBuilderMap, index);
            addWaterInRight(stringBuilderMap, index);
            addWaterInUp(stringBuilderMap, lineLength, index);
            addWaterInDown(stringBuilderMap, lineLength, index);
        });
    }

    private void addWaterInDown(StringBuilder stringBuilderMap, int lineLength, Integer index) {
        if ((index + lineLength) < stringBuilderMap.length() && (index + lineLength) >= 0
                && stringBuilderMap.charAt(index + lineLength) != '\n') {
            stringBuilderMap.setCharAt(index + lineLength, water);
        }
    }

    private void addWaterInUp(StringBuilder stringBuilderMap, int lineLength, Integer index) {
        if ((index - lineLength) < stringBuilderMap.length() && (index - lineLength) >= 0
                && stringBuilderMap.charAt(index - lineLength) != '\n') {
            stringBuilderMap.setCharAt(index - lineLength, water);
        }
    }

    private void addWaterInRight(StringBuilder stringBuilderMap, Integer index) {
        if ((index + 1) < stringBuilderMap.length() && (index + 1) >= 0
                && stringBuilderMap.charAt(index + 1) != '\n') {
            stringBuilderMap.setCharAt(index + 1, water);
        }
    }

    private void addWaterInLeft(StringBuilder stringBuilderMap, Integer index) {
        if ((index - 1) < stringBuilderMap.length() && (index - 1) >= 0
                && stringBuilderMap.charAt(index - 1) != '\n') {
            stringBuilderMap.setCharAt(index - 1, water);
        }
    }

    private boolean isLand(StringBuilder stringBuilderMap) {
        boolean flag = false;
        for (int i = 0; i < stringBuilderMap.length(); i++) {
            if (stringBuilderMap.charAt(i) == FloodFill.LAND)
                flag = true;
        }
        return flag;
    }

    private int getLineLength(StringBuilder newMap) {
        return newMap.indexOf("\n") + 1;
    }

    private List<Integer> getWaterIndexes(StringBuilder newMap) {
        return IntStream.range(0, newMap.length())
                .filter(index -> newMap.charAt(index) == water)
                .boxed()
                .collect(Collectors.toList());
    }
}