import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Credentials.PORT);
        System.out.println("Server is running");
        int clientId = 0;
        Database db = new Database();
        if (db.establishDBConnection()) {
            System.out.println("Server is now connected to DB");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientId++;
                System.out.println("Client " + clientId + " connected with IP " + clientSocket.getInetAddress().getHostAddress());
                Thread client = new Thread(new ClientHandler(clientSocket, clientId, db));
                client.start();
            }
        } else {
            System.out.println("DB connection fail, stopping.");
        }
    }
}