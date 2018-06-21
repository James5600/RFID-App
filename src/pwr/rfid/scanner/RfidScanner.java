package pwr.rfid.scanner;

import pwr.rfid.dao.RfidDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RfidScanner {

    private StringBuilder rfidSeparator;
    private String rfid_id;
    private Socket clientSocket;
    private BufferedReader din;
    private ServerSocket soc;

    public RfidScanner() {
        try {
            soc = new ServerSocket(8189);
            //while (true) {
                Socket cSoc = soc.accept();
                acceptClient(cSoc);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptClient(Socket cSoc) throws Exception {
        clientSocket = cSoc;
        din = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        String msgFromClient = din.readLine();
        final Pattern pattern = Pattern.compile("id=(.+)");
        final Matcher matcher = pattern.matcher(msgFromClient);
        if (matcher.find()) {
            rfidSeparator = new StringBuilder(matcher.group(1));
            rfid_id = rfidSeparator.substring(0, 10);
            System.out.println(rfid_id);
        }
    }

    public String getRfid_id() {
        return rfid_id;
    }

    public void close() throws IOException {
        soc.close();
        din.close();
        clientSocket.close();
    }
}

