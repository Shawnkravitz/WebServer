import javax.json.Json;
import javax.json.JsonObject;

public class CreateNode {

    public CreateNode(){

    }

    // returns json object
    public JsonObject createNode(String name, String state){
        JsonObject jsonObj = Json.createObjectBuilder().add("name", name).add("state", state).build();
        return jsonObj;
    }
}
