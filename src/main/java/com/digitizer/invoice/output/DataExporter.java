package com.digitizer.invoice.output;

import java.io.IOException;

/**
 * Interface for exporting the processed data.
 */
public interface DataExporter<T> {

    /**
     * Opens the output source the processed data will be exported
     * @throws IOException
     */
    void open() throws IOException;

    /**
     * Exports data and close the output source
     * @throws IOException
     */
    void export() throws IOException;


    /**
     * Adds a new element to the exported buffer, later it will be exported
     * @param element
     */
    void addElement(T element);
}
