package com.digitizer.invoice.convert;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class SevenSegmentConvertorTest {

    private static final String testData = "_  _  _        _     _  _ \n" +
            "|_ | || |  ||_| _|  ||_ |_ \n" +
            "|_||_||_|  |  | _|  | _| _|";

    private static final String wrongTestData = "_  _  _        _     _  _ \n" +
            "|_ | || |  ||_| _|  ||_ |_ \n" +
            "|_| _||_|  || | _|  | _|  |";

    private static final String wrongTestData2 = " _  _  _  _  _  _  _  _  _ \n" +
                                                 "  ||_||_|| ||_ |_|  || ||_|\n" +
                                                 "  | _||_||_| _| _|  || ||_|";

    @Test
    public void convertCorrectDateShouldReturnAllDigits() {
        MultiLinesIterator iterator = new MultiLinesIterator(new ByteArrayInputStream(testData.getBytes()), 3);
        iterator.open();
        iterator.readNextBatch();
        Assert.assertTrue(SevenSegmentConvertor.convert(iterator.pullLastBatch()).equals("600143155"));
    }

    @Test
    public void convertWrongDataThreeDigitWillBeNotResolved() {
        MultiLinesIterator iterator = new MultiLinesIterator(new ByteArrayInputStream(wrongTestData.getBytes()), 3);
        iterator.open();
        iterator.readNextBatch();
        Assert.assertTrue(SevenSegmentConvertor.convert(iterator.pullLastBatch()).equals("6?01?315?"));

        iterator = new MultiLinesIterator(new ByteArrayInputStream(wrongTestData2.getBytes()), 3);
        iterator.open();
        iterator.readNextBatch();
        Assert.assertTrue(SevenSegmentConvertor.convert(iterator.pullLastBatch()).equals("7980597?8"));
    }
}