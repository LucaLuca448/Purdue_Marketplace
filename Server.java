import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server Class
 * <p>
 * Server class to facilitate File reading and editing for the client.
 *
 * @author Nirmal Senthilkumar
 * @version December 6, 2023
 */
public class Server {

    private static final int PORT_NUMBER = 6969;

    public static void main(String[] args) throws IOException {

        File f = new File("users.txt");
        f.createNewFile();
        f = new File("market.txt");
        f.createNewFile();
        f = new File("purchases.txt");
        f.createNewFile();
        f = new File("shoppingCart.txt");
        f.createNewFile();

        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!serverSocket.isClosed()) {
                try {
                    String fileName = reader.readLine();
                    File readingFile = new File(fileName);
                    FileReader fr = new FileReader(readingFile);
                    BufferedReader bfr = new BufferedReader(fr);
                    String readerInput = bfr.readLine();
                    while (readerInput != null) {
                        if (!readerInput.isEmpty()) {
                            writer.write(readerInput + "\n");
                        }
                        readerInput = bfr.readLine();
                    }
                    writer.write("STOP\n");
                    writer.flush();
                    readerInput = reader.readLine();
                    if (!readerInput.equals("STOP")) {
                        File writingFile = new File(readerInput);
                        if (writingFile.exists()) {
                            writingFile.delete();
                            writingFile.createNewFile();
                        }
                        FileWriter fw = new FileWriter(writingFile, false);
                        BufferedWriter bfw = new BufferedWriter(fw);
                        readerInput = reader.readLine();
                        do {
                            bfw.write(readerInput + "\n");
                            readerInput = reader.readLine();
                        } while (!readerInput.equals("STOP"));
                        bfw.flush();
                    }
                } catch (IOException ignored) {
                    boolean error = true;
                } catch (NullPointerException ne) {
                    break;
                }
            }
        }
    }
}
