import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Connect to micro-task C++ CLI");

        final Socket socket = new Socket("127.0.0.1", 5005);

        System.out.println("Connected");

        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        JSONObject versionRequest = new JSONObject();
        versionRequest.put("command", 1);

        String string = versionRequest.toString();

        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();

        PrintWriter writer = new PrintWriter(outputBytes);
        versionRequest.write(writer);
        writer.flush();
        short size = (short) (2 + outputBytes.size());

        output.write(ByteBuffer.allocate(2).putShort(size).array());
        output.write(outputBytes.toByteArray());


        output.flush();

        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            int packetLength;
            while ((packetLength = in.readShort()) != -1) {
                int expectedBytes = packetLength - 2;

                byte[] bytes = new byte[expectedBytes];

                int totalRead = 0;

                while (totalRead < expectedBytes) {
                    int read = in.read(bytes, totalRead, bytes.length - totalRead);
                    if (read == -1) {
                        totalRead = -1;
                        break;
                    }
                    totalRead += read;
                }

                if (totalRead == -1) {
                    break;
                }

                String source = new String(Arrays.copyOfRange(bytes, 0, bytes.length));
                JSONObject obj = new JSONObject(source);

                switch (obj.getInt("command")) {
                    case 1: // version request response
                        System.out.println("Version: " + obj.getString("version"));
                        break;
                    case 2: // add task
                    case 3: // start task
                        break;
                    case 4: // get task response
                        break;
                }
            }
        }
    }
}
