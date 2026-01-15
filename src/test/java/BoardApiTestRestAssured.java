import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BoardApiTestRestAssured extends BasicTest {
    public static String key = System.getenv("TRELLO_KEY");
    public static String token = System.getenv("TRELLO_TOKEN");

    @Test
    public static void PostApiTest() {
        String requestBody = """ 
                {
                         "name": "rest assured board"
                }
                """;
        JsonPath expectedResponse = new JsonPath(requestBody);

        given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("name", equalTo(expectedResponse.getString("name")))
                .body("id", notNullValue())
                .log().all();
    }

    @Test
    public static void GetApiTest() {
        String boardId = "6967a17183189bfe9273a34c";

        given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .pathParam("id", boardId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(boardId))
                .log().all();
    }

    @Test
    public static void PutApiTest() {
        String boardId = "6967b2208d8c75dc21c1bccb";

        String requestBody = """ 
                {
                         "name": "new board name"
                }
                """;

        JsonPath expectedResponse = new JsonPath(requestBody);

        given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .pathParam("id", boardId)
                .body(requestBody)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(boardId))
                .body("name", equalTo(expectedResponse.getString("name")))
                .log().all();
    }

    @Test
    public static void DeleteApiTest() {
        String boardId = "6967f455a9825dc4b218db15";

        given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .pathParam("id", boardId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .body("id", nullValue())
                .log().all();
    }
}
