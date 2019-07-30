package com.digitizer.invoice.convert;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class MultiLinesIteratorTest {

    private InputStream in;

    @Before
    public void setup() {
        String inputOneBatch = "line1\n" +
                "line2\n" +
                "line3\n" +
                "line4\n" +
                "line5\n" +
                "line6\n" +
                "line7\n" +
                "line8\n" +
                "line9";
        in = new ByteArrayInputStream(inputOneBatch.getBytes());
    }




    @Test
    public void testOneFullBatch() {
        MultiLinesIterator iterator = new MultiLinesIterator(in, 5);
        iterator.open();
        Assert.assertTrue(iterator.readNextBatch());
        Assert.assertEquals(5, iterator.pullLastBatch().size());
        Assert.assertTrue(iterator.readNextBatch());
        Assert.assertFalse(iterator.pullLastBatch().isEmpty());
        iterator.close();
    }

    @Test
    public void testTwoFullBatch() {
        String inputTwoBatch = "line1\n" +
                "line2\n" +
                "line3\n" +
                "line4\n" +
                "line5\n" +
                "line6\n" +
                "line7\n" +
                "line8\n" +
                "line9\n" +
                "line10";
        in = new ByteArrayInputStream(inputTwoBatch.getBytes());
        MultiLinesIterator iterator = new MultiLinesIterator(in, 5);
        iterator.open();
        Assert.assertTrue(iterator.readNextBatch());
        Assert.assertEquals(5, iterator.pullLastBatch().size());
        Assert.assertTrue(iterator.readNextBatch());
        Assert.assertEquals(5, iterator.pullLastBatch().size());
        Assert.assertFalse(iterator.readNextBatch());
        Assert.assertTrue(iterator.pullLastBatch().isEmpty());
        iterator.close();
    }
}