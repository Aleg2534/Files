package threads;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.factories.DwellingFactory;
import buildings.factories.HotelFactory;
import buildings.factories.OfficeFactory;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;
import exceptions.BuildingUnderArrestException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    private static Socket clientSocket;
    public ClientSocketThread(Socket socket)
    {
        clientSocket=socket;
    }

    private static void setType(String type) {
        switch (type) {
            case "Dwelling" -> Buildings.setBuildingFactory(new DwellingFactory());
            case "OfficeBuilding" -> Buildings.setBuildingFactory(new OfficeFactory());
            case "Hotel" -> Buildings.setBuildingFactory(new HotelFactory());
            default -> {
            }
        }
    }

    private static boolean seize() {
        return ((int) (Math.random() * 10)) == 6;
    }

    private static double estimation(Building building)
            throws BuildingUnderArrestException, IOException {
        if (seize()) throw new BuildingUnderArrestException();
        System.out.println("estimation");
        double result = building.getSquare();
        if (building instanceof Hotel) {
            result *= 2;
        } else if (building instanceof OfficeBuilding) {
            result *= 1.5;
        } else {
            result *= 1;
        }
        return result;
    }

    @Override
    public void run() {
        try {
            System.out.println("Creating dis...");
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            System.out.println("Creating dos...");
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {

                System.out.println("Reading type...");
                String type = dataInputStream.readUTF();
                System.out.println("Type: " + type);
                System.out.println("Setting type...");
                setType(type);
                System.out.println("Reading building...");
                Building building = Buildings.inputBuilding(dataInputStream);
                System.out.println("Building: " + building.toString());
                String result;
                try {
                    System.out.println("Calculating price...");
                    double price = estimation(building);
                    System.out.println("Price of building:" + price);
                    result = Double.toString(price);
                } catch (BuildingUnderArrestException e) {
                    result = "Building is under arrest!";
                }
                System.out.println("Writing result...");
                dataOutputStream.writeUTF(result);
                System.out.println("Result writing succeed!");
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
