package com.digitizer.invoice;
import com.digitizer.invoice.convert.MultiLinesIterator;
import com.digitizer.invoice.convert.SevenSegmentConvertor;
import com.digitizer.invoice.input.DataProvider;
import com.digitizer.invoice.input.FSInvoiceArchiveProvider;
import com.digitizer.invoice.output.DataExporter;
import com.digitizer.invoice.output.FSInvoiceExporter;

import java.io.IOException;


/**
 * The class define the Invoice IDs Convertor which knows to get data as a {@link DataProvider}
 * and {@link DataExporter} as an exporter, the class invokes 7-segment algorithm to convert
 * invoice id to the digit in regular representation.
 */
public class InvoiceIDsConvertor {

    private final DataProvider dataProvider;
    private final DataExporter exporter;

    InvoiceIDsConvertor(DataProvider dataProvider, DataExporter exporter) {
        this.dataProvider = dataProvider;
        this.exporter = exporter;
    }

    /**
     * The main InvoiceIDsConvertor main which starts the conversion process.
     */
    public void execute() {
        try {
            dataProvider.openStream();
        } catch (IOException e) {
            System.err.println("Error occurred while opening input stream:" + e.getMessage());
        }
        try {
            exporter.open();
        } catch (IOException e) {
            System.err.println("Error occurred while opening export stream:" + e.getMessage());
        }

        MultiLinesIterator iterator = new MultiLinesIterator(dataProvider.getDataAsStream(), 4);
        iterator.open();
        while (iterator.readNextBatch()) {
            exporter.addElement(SevenSegmentConvertor.convert(iterator.pullLastBatch()));
        }
        iterator.close();

        try {
            dataProvider.closeStream();
        } catch (IOException e) {
            System.err.println("Error occurred while close input stream:" + e.getMessage());
        }
        try {
            exporter.export();
        } catch (IOException e) {
            System.err.println("Error occurred while  export stream:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // parse app input arguments
        if (args.length != 2) {
            String usage = "Wrong or missing required argument: \n" +
                    "\n" +
                    "usage: com.digitizer.invoice.InvoiceIDsConvertor " +
                    "  <arg1>   path to the input file " +
                    "  <arg2>   path to  output file";
            System.out.println(usage);
            System.exit(0);
        }

        InvoiceIDsConvertor convertor = new InvoiceIDsConvertor(new FSInvoiceArchiveProvider(args[0]),
                new FSInvoiceExporter(args[1]));
        convertor.execute();
    }
}
