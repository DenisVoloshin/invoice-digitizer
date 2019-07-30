package com.digitizer.invoice.convert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * The class represents a custom iterator which allows to iterate on the
 * {@link InputStream} by returning a batches of parameterized number of rows
 */
public class MultiLinesIterator {

    private final int linesBatch;
    private Scanner scanner;
    private final InputStream inputStream;
    private final ConcurrentLinkedQueue<List<String>> batchesQueue;

    public MultiLinesIterator(InputStream inputStream, int linesBatch) {
        this.linesBatch = linesBatch;
        this.inputStream = inputStream;
        this.batchesQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Creates the active iterator cursor.
     */
    public void open() {
        scanner = new Scanner(inputStream, "UTF-8");
    }

    /**
     * Close the active iterator cursor.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
        scanner = null;
    }

    /**
     * Pulls a current batch from the queue.
     *
     * @return the last read batch of there is not any batch return empty list.
     */
    public List<String> pullLastBatch() {
        if (scanner != null && !batchesQueue.isEmpty()) {
            return batchesQueue.poll();
        }
        return Collections.emptyList();
    }

    /**
     * Reads the next batch and puts it into the queue
     *
     * @return return true if the next batch was read otherwise false
     */
    public boolean readNextBatch() {
        if (scanner == null) {
            return false;
        }

        List<String> currentBatch = new ArrayList<>();
        int readLine = linesBatch;
        while (scanner.hasNext() && readLine > 0) {
            currentBatch.add(scanner.nextLine());
            readLine--;
        }

        if (currentBatch.size() == linesBatch) {
            batchesQueue.add(currentBatch);
        } else if (!scanner.hasNext()) {
            //in the case we have a last batch which might have less lines
            batchesQueue.add(currentBatch);
        } else {
            currentBatch.clear();
        }
        return currentBatch.size() > 0;
    }
}
