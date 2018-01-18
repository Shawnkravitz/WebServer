import java.util.HashMap;
import javax.json.Json;
import javax.json.JsonObject;

public class Database {

    HashMap<Integer, JsonObject> db = new HashMap();
    public Database(){
        // do nothing
    }

    // doesn't work yet
    public boolean addNode(int id, JsonObject node){
        db.put(id, node);
        return true;
    }

    // the only functional one
    public String getNode(int id){
        return "HTTP/1.1 200 OK\n" + "Content-Type:application/json\n\n" + db.get(id).toString();
    }

    // doesn't do anything yet
    public String getAllNodes(){
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
}
