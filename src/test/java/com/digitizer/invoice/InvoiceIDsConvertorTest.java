package com.digitizer.invoice;

import com.digitizer.invoice.input.FSInvoiceArchiveProvider;
import com.digitizer.invoice.output.FSInvoiceExporter;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class InvoiceIDsConvertorTest {
    @Test
    public void endToEndInvoiceIDsConvertorTestOfTwoIds() {

        FSInvoiceExporter fsInvoiceExporter = new FSInvoiceExporter();
        InvoiceIDsConvertor convertor = new InvoiceIDsConvertor(
                new FSInvoiceArchiveProvider(ClassLoader.getSystemResource("input_two_ids.txt").getPath()),
                fsInvoiceExporter);
        convertor.execute();

        try {
            String expectedOutput = slurp(new File(ClassLoader.getSystemResource("output_two_ids.txt").getPath()));
            Assert.assertEquals(expectedOutput, fsInvoiceExporter.getExportedDataAsString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void endToEndInvoiceIDsConvertorTestOfQ1() {

        FSInvoiceExporter fsInvoiceExporter = new FSInvoiceExporter();
        InvoiceIDsConvertor convertor = new InvoiceIDsConvertor(
                new FSInvoiceArchiveProvider(ClassLoader.getSystemResource("input_Q1a.txt").getPath()),
                fsInvoiceExporter);
        convertor.execute();
        try {
            String expectedOutput = slurp(new File(ClassLoader.getSystemResource("output_Q1a.txt").getPath()));
            Assert.assertEquals(expectedOutput, fsInvoiceExporter.getExportedDataAsString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void endToEndInvoiceIDsConvertorTestOfQ2() {

        FSInvoiceExporter fsInvoiceExporter = new FSInvoiceExporter();
        InvoiceIDsConvertor convertor = new InvoiceIDsConvertor(
                new FSInvoiceArchiveProvider(ClassLoader.getSystemResource("input_Q1b.txt").getPath()),
                fsInvoiceExporter);
        convertor.execute();

        try {
            String expectedOutput = slurp(new File(ClassLoader.getSystemResource("output_Q1b.txt").getPath()));
            Assert.assertEquals(expectedOutput, fsInvoiceExporter.getExportedDataAsString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }


    private static String slurp(final File file)
            throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[] buf = new char[1024];
            int r;
            while ((r = reader.read(buf)) != -1) {
                result.append(buf, 0, r);
            }
        }
        return result.toString();
    }
}