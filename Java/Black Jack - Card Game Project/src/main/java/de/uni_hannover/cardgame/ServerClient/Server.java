package de.uni_hannover.cardgame.ServerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Server {
    private static final Server instance = new Server();

    private ServerSocket serverSocket;
    private static int counter;
    private static final ArrayList<ClientHandler> clients = new ArrayList<>();
    public boolean isFull = false;
    private static boolean running;

    public static final String COMMAND_SHUTDOWN_CLIENT = "SHUTDOWN_CLIENT";
    public static final String COMMAND_PLAYER_LEFT = "PLAYER_LEFT";
    public static final String COMMAND_PLAYER_JOINED = "PLAYER_JOINED";



    public void start(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        System.out.println("[Server] online!!!");
        while(!isFull){
            counter++;
            if (counter > 4){
                isFull = true;
                System.err.println("[Server] Server full !");
                serverSocket.close();
                break;
            }


            counter = 0;
            System.out.println("[Server] wait for Client ");
            Socket client = serverSocket.accept();

            System.out.println("[Server] Client connected. Adress: " + client.getPort());
            int id = getAvailableID();
            ClientHandler handler = new ClientHandler(client, clients, id);
            clients.add(handler);
            handler.broadcast("Spieler " + id + " entered Server");
            for(int i = 0; i < clients.size(); i++){
                ClientHandler hl = clients.get(i);

                if(hl.clientNumber == id) {
                    for (int j = 0; i < clients.size(); i++){
                        ClientHandler hl2 = clients.get(j);
                        if(hl2.clientNumber != id)
                            hl.out.println(COMMAND_PLAYER_JOINED +  ":" + hl2.clientNumber);
                    }
                }
                hl.out.println(COMMAND_PLAYER_JOINED + ":" + id);
            }
            Thread thread = new Thread(handler);
            thread.start();

        }
    }

    private int getAvailableID(){
        List<Integer> takenIDS = new ArrayList<>();

        for (int i = 0; i < clients.size(); i++){
            takenIDS.add(clients.get(i).clientNumber);
        }

        for(int i = 1; i <= 4; i++){
            if(!takenIDS.contains(i))
                return i;
        }

        return -1;
    }

    public boolean isRunning(){
        return running;
    }

    public void closeServer(){
        try {
            for (int i = 0; i < clients.size(); i++){
                ClientHandler handler = clients.get(i);
                handler.out.println(COMMAND_SHUTDOWN_CLIENT);
            }

            serverSocket.close();
            counter = 0;
            clients.clear();
            isFull = false;
            running = false;
        } catch (IOException ex){}
    }

    public static Server getInstance() {
        return instance;
    }

    public static void main(String[] args) throws IOException {
        Server.getInstance().start(1334); //chua xoa 4444, 8000,1,2

    }

    public static class ClientHandler implements Runnable{


        private Socket client;
        int clientNumber;
        public static ArrayList<ClientHandler> clients;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket client_, ArrayList<ClientHandler> clients_, int clientNumber_) throws IOException {

            this.client = client_;
            this.clients = clients_;
            this.clientNumber = clientNumber_;
            this.out = new PrintWriter(client.getOutputStream(),true);
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));


        }

        @Override
        public void run() {

            try {
                while(running){
                    String messageFromClient = in.readLine();

                    if(messageFromClient == null){
                        clients.remove(this);
                        counter--;
                        broadcast("Spieler" + clientNumber + " hat den Server verlassen");
                        broadcast(COMMAND_PLAYER_LEFT + ":" + clientNumber);
                        break;
                    }

                    broadcast("Spieler " + clientNumber + ": " + messageFromClient);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                out.close();
                try{
                    in.close();
                }catch (IOException i){
                    i.printStackTrace();
                }
            }

        }

        public static void broadcast(String msg) {
            for(int i = 0; i < clients.size(); i++){
                ClientHandler handler = clients.get(i);
                handler.out.println(msg);
            }

        }

    }
}
