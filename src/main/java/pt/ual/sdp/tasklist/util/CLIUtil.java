package pt.ual.sdp.tasklist.util;

public class CLIUtil {

    public static int getPort(String[] args, int idx) throws CLIException {
        int port;
        if (args.length < idx + 1) {
            throw new CLIException("Missing parameters.");
        }
        try {
            port = Integer.parseInt(args[idx]);
        } catch (NumberFormatException nfe) {
            throw new CLIException("Invalid format for port parameter.");
        }
        if (port < 0 || port > 65353) {
            throw new CLIException("Invalid port range.");
        }
        if (port <= 1023) {
            throw new CLIException("Port should be above 1023 to avoid collision with running services.");
        }
        return port;
    }

    public static String getAddress(String[] args, int idx) throws CLIException {
        String address = null;
        if (args.length < idx + 1) {
            throw new CLIException("Missing parameters.");
        }
        return args[idx];
    }

}
