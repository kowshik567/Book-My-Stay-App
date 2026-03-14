import java.io.*;
import java.util.*;

// RoomInventory
class RoomInventory {

    private Map<String,Integer> rooms;

    public RoomInventory(){
        rooms = new HashMap<>();
        rooms.put("Single",5);
        rooms.put("Double",3);
        rooms.put("Suite",2);
    }

    public Map<String,Integer> getRooms(){
        return rooms;
    }

    public void setRoom(String type,int count){
        rooms.put(type,count);
    }

    public void showInventory(){

        System.out.println("Current Inventory:");

        for(String key:rooms.keySet()){
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

// FilePersistenceService
class FilePersistenceService {

    public void saveInventory(RoomInventory inventory,String filePath){

        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath))){

            for(Map.Entry<String,Integer> entry :
                    inventory.getRooms().entrySet()){

                writer.println(entry.getKey()+"="+entry.getValue());
            }

            System.out.println("Inventory saved successfully.");

        }catch(Exception e){
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory,String filePath){

        File file = new File(filePath);

        if(!file.exists()){
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try(BufferedReader reader =
                    new BufferedReader(new FileReader(file))){

            String line;

            while((line=reader.readLine())!=null){

                String parts[] = line.split("=");

                inventory.setRoom(parts[0],
                        Integer.parseInt(parts[1]));
            }

            System.out.println("Inventory loaded successfully.");

        }catch(Exception e){
            System.out.println("Error loading inventory.");
        }
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService fileService =
                new FilePersistenceService();

        String filePath = "inventory.txt";

        // Load saved data
        fileService.loadInventory(inventory,filePath);

        System.out.println();

        inventory.showInventory();

        // Save data again
        fileService.saveInventory(inventory,filePath);
    }
}