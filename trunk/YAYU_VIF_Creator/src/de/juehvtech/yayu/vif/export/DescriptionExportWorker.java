/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Jens
 */
public class DescriptionExportWorker {

    public static final String DESCRIPTION_FILE_NAME = "description";
    public static final String DESCRIPTION_FILE_MIME = ".txt";

    public static void writeDescription(String path, List<String> descriptions)
            throws IOException {
        int counter = 0;
        StringBuilder sb;
        for (String item : descriptions) {
            sb = new StringBuilder();
            sb.append(path).append("/").append(DESCRIPTION_FILE_NAME)
                    .append(counter++).append(DESCRIPTION_FILE_MIME);
            OutputStreamWriter outFile = new OutputStreamWriter(
                    new FileOutputStream(sb.toString()), ExportManager.ENCODING);

            try (PrintWriter out = new PrintWriter(outFile)) {
                out.println(item);
                out.flush();
                out.close();
            }
        }
    }
}
