package talant.skyqfreeremote;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.*;

class RemoteFunc{

    //final static private String skyqhost = "192.168.1.38";
    public final static int sPort = 49160;


    public static final Map<String, Integer> CODE_DIC = new HashMap() {{
        put("power",0);
        put("select",1);
        put("backup",2);
        put("dismiss",2);
        put("ch-up",6);
        put("ch-down",7);
        put("interactive",8);
        put("sidebar",8);
        put("help",9);
        put("services",10);
        put("search",10);
        put("tvguide",11);
        put("home",11);
        put("info",14);
        put("text",15);
        put("up",16);
        put("down",17);
        put("left",18);
        put("right",19);
        put("red",32);
        put("green",33);
        put("yellow",34);
        put("blue",35);
        put("0",48);
        put("1",49);
        put("2",50);
        put("3",51);
        put("4",52);
        put("5",53);
        put("6",54);
        put("7",55);
        put("8",56);
        put("9",57);
        put("play",64);
        put("pause",65);
        put("stop",66);
        put("record",67);
        put("ffwd",69);
        put("rewind",71);
        put("boxoffice",240);
        put("sky",241);
    }};


    public static void doCode(final String s) {

        Thread bgThread = new Thread() {
            public void run() {
                sendRemCode(CODE_DIC.get(s));
            }
        }   ;
        bgThread.start();
    }


    private static String byteToHexString(byte[] b) {
        StringBuilder a = new StringBuilder();

        for (int x = 0; x < b.length; x++) {
            a.append(String.format("%02x", b[x])).append("/");
        }

        return a.toString();
    }



    private static void sendRemCode(int remCode)  {

       byte[] command_bytes = { 4, 1, 0, 0, 0, 0, (byte) Math.floor(224 + (remCode / 16)), (byte) (remCode % 16) };

        //System.out.println("Command Bytes :" + byteToHexString(command_bytes));


        Socket socket;
        try {
            socket = new Socket(MainActivity.hostadd, sPort);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            OutputStream dout = socket.getOutputStream();

            byte[] dataRec = null;
            byte[] data = new byte[100];
            int count;
            int l = 12;

            while ((count = dis.read(data, 0, 24)) != -1) {

                dataRec = new byte[count];

                for (int i = 0; i < count; i++) {
                    dataRec[i] = data[i];
                }

                //System.out.println("Received length :" + dataRec.length);
                //System.out.println("Received Bytes (in hex format) : " + byteToHexString(dataRec));

                if (dataRec.length < 24) {

                    byte[] dataSend = Arrays.copyOfRange(dataRec, 0, l);

                   //System.out.println("Length of data to send:" + dataSend.length);
                   //System.out.println("Data to send (in hex format) :" + byteToHexString(dataSend));

                    dout.write(dataSend);
                    dout.flush();

                    //System.out.println("data sent");

                    l = 1;
                }

                else {
                    break;
                }

            }

            //System.out.println("24 bytes received - handshake complete");
            //System.out.println("sending command part 1");

            dout.write(command_bytes);
            //System.out.println("part 1 sent");

            command_bytes[1] = 0;

            //System.out.println("sending command part 2");
            dout.write(command_bytes);
            dout.flush();

            //System.out.println("part 2 sent");

            socket.close();
            dout.close();
            dis.close();
            //System.out.println("socket closed. finished.");

        }

        catch (UnknownHostException u) {
            MainActivity.errormsg = "Host not found. Check you are on the same network as your Sky Q Box and that your IP address is correct in Settings.";
            return;
        }
        catch (IOException e) {
            MainActivity.errormsg = "Data transfer/handshake error with Sky Q Box. Try rebooting Sky Q Box.";
            return;
        }

        MainActivity.errormsg = null;
    }

}