/*
Problems:
1. A club can view the Salable players list until he buys a player i.e. sends a "buy" request (line 68).
    Meaning, after making a "buy" request, if a club makes a "ShowSale" request (line 91), the ReadThread of the club shows error

2. After buying a player, the buying club's list (list of his ALL players) get updated but the seller club's doesnt
    But if the seller club then sends a "ShowSale" request, his list gets updated (For some unknown reason, the server's
    message is waiting in the seller club's Input Stream until the server sends another message)
3. A club can buy his own player (Fixable)
* */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PlayerServer {
    private Search Database = new Search();
    private List<Player>playerList=Database.player_list;   //Initially loads the player from File (Works Fine)
    private ServerSocket serverSocket;
    private Map<String, SocketWrapper> clients; //Map to store connected clients
    private List<Player> forSale = Collections.synchronizedList(new ArrayList<>(Arrays.asList())); //List of Salable players
    private Set<String> forSaleChecker = Collections.synchronizedSet(new HashSet<>());  //A checker that ensures a club does not make sale request for a same player
    private Map<String,Player>newPlayerList=Collections.synchronizedMap(new HashMap<>());  //Player Name->Player

    public PlayerServer() {
        try {
            for (Player player : playerList) {
                newPlayerList.put(player.getName(), player);   //Works fine
            }

            clients = Collections.synchronizedMap(new HashMap<>()); // Thread-safe map for clients
            serverSocket = new ServerSocket(33333);

            while (true) {
                // Accept a new client connection
                Socket clientSocket = serverSocket.accept();
                // Create a new SocketWrapper for the client
                SocketWrapper clientWrapper = new SocketWrapper(clientSocket);

                // Read the club name from the client
                String clubName = (String) clientWrapper.read();
                // Add the client to the map
                clients.put(clubName, clientWrapper);

                // Send initial data to the client
                clientWrapper.write(new Request(makeList())); //the makeList() method makes a list from the HashMap newPlayerList //Works fine

                // Start a thread to serve this client
                serve(clientWrapper);
            }
        } catch (Exception e) {
        }
    }

    private void serve(SocketWrapper clientWrapper) {
        Thread clientThread = new Thread(() -> {
            try {
                while (true) {

                    Request request = (Request) clientWrapper.read();


                    if ("buy".equalsIgnoreCase(request.getRequestType())) {

                        String newClub=request.getBuyerClub();
                        String pastClub=request.getPlayer().getClub();

                        handlePlayerPurchase(request.getPlayer()); // Process the purchase
                        newPlayerList.get(request.getPlayer().getName()).setClub(request.getBuyerClub());
                        AddtoFile(); //This Function writes the changes to the File //Works Fine

                        //The code works fine till now
                        //If the following block is omitted, each club has to relogin to see their updated own players' list, but there would be no error
                        {
                            clients.get(newClub).getOutputStream().flush();
                            clients.get(newClub).write(new Request(makeList())); //The Buying club is being updated


                            clients.get(pastClub).getOutputStream().flush();
                            clients.get(pastClub).write(new Request(makeList())); //The Selling club gets updated when he makes a "ShowSale" request
                        }

                    } else if ("sale".equalsIgnoreCase(request.getRequestType())) {
                        handlePlayerSale(request.getPlayer()); // Handle Sale Request //Update forSale // Works Fine

                    } else if ("ShowSale".equalsIgnoreCase(request.getRequestType())) {
                        synchronized (forSale) {
                            clientWrapper.getOutputStream().reset();
                            clientWrapper.write(new Request(forSale,1)); // Send a copy of the list
                                                                            //Works Fine until this club has bought a player
                            Print.show(forSale); //Print.show(List<PLayer>) prints player names of a list
                        }

                    } else {
                        //
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    clientWrapper.closeConnection();
                    System.out.println("Client Disconnected");
                } catch (IOException e) {
                }
            }
        });

        clientThread.start();
    }

    private void handlePlayerPurchase(Player boughtPlayer) {
        synchronized (forSale) {
            forSale.removeIf(player -> player.getName().equals(boughtPlayer.getName())); // Remove the player
            forSaleChecker.remove(boughtPlayer.getName()); //Remove the Player from checker
        }
        sendPlayerList(); //Sends the updated Salable Player list
    }

    private void handlePlayerSale(Player player) throws IOException {
        synchronized (forSale) {
            // Check if the player is already for sale
            if (forSaleChecker.contains(player.getName())) {
                //
            } else {
                // Add the player to the forSale list and checker
                forSale.add(player);
                forSaleChecker.add(player.getName());
            }
        }
        sendPlayerList(); // Update all clients with the new player list
    }

    private void sendPlayerList() {
        synchronized (clients) {
            for (SocketWrapper client : clients.values()) {
                try {
                    client.getOutputStream().reset();
                    client.write(new Request(forSale,1)); // Send the updated list //The 1 here is just a flag
                } catch (IOException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        new PlayerServer();
    }

    public void AddtoFile() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("players.txt"))) {
            for (Player player : newPlayerList.values()) { // Iterate over the values of the map
                String line = String.format("%s,%s,%d,%.2f,%s,%s,%d,%.2f",
                        player.getName(), player.getCountry(), player.getAge(), player.getHeight(),
                        player.getClub(), player.getPosition(),
                        player.getNumber() != -1 ? player.getNumber() : 0, player.getSalary());
                writer.write(line + "\n"); // Write the line and add a newline
            }
        } catch (IOException e) {
            throw new IOException("Error writing to the file", e);
        }
    }

    List<Player> makeList() {
        List<Player> result;

        // Iterate through the values of the map
        synchronized (newPlayerList) {
            result = new ArrayList<>(newPlayerList.values());
        }

        return result;
    }
}
