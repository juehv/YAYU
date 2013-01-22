/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yayu_pims;

import de.juehvtech.yayu.discovering.util.IdGenerator;
import java.net.SocketException;

/**
 *
 * @author Jens
 */
public class YAYU_PiMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        System.out.println("Code: "+(new IdGenerator().generateServerId(null)));
    }
}
