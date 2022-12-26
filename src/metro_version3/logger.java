
package metro_version3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;

public class logger {

    static String pathFichero = "log.txt";

    public static void mandarMsg(String msg, String tipo) {
        String linea = new Timestamp(System.currentTimeMillis()) + " - " + tipo + " :: " + msg + "\r\n";

        try {
            File fichero = new File(logger.pathFichero);
            FileWriter writer = new FileWriter(fichero, true);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write(linea);

            bw.close();
            writer.close();
        } catch (Exception error) {
           logger.mandarMsg(error.getMessage(), "Critico");
        }
    }
}
