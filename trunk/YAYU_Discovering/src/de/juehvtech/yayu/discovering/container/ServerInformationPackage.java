/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.container;

/**
 *
 * @author Jens
 */
public class ServerInformationPackage {

    private final String id;
    private final String ip;
    private final String rom;
    private final String status;

    public ServerInformationPackage(String id, String ip, String rom, String status) {
        this.id = id;
        this.ip = ip;
        this.rom = rom;
        this.status = status;
    }

    public ServerInformationPackage() {
        this("none", "", "", "error");
    }

    public static ServerInformationPackage parseString(String input) {
        if (input == null || input.isEmpty()) {
            return new ServerInformationPackage();
        }
        // parse string
        String[] args = input.split(";");
        if (args.length == 4) {
            return new ServerInformationPackage(args[1], args[0], args[2], args[3]);
        } else {
            return new ServerInformationPackage();
        }

    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getStatus() {
        return status;
    }

    public String getRom() {
        return rom;
    }
}
