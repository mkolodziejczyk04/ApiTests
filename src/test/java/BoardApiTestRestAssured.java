import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.example.requests.BoardClient;
import org.example.requests.CreateBoard;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BoardApiTestRestAssured extends BasicTest {
    public static String key = System.getenv("TRELLO_KEY");
    public static String token = System.getenv("TRELLO_TOKEN");
    private final BoardClient boardClient = new BoardClient();
    private final String boardName = "Nice board";


    @Test
    public void PostApiTest() {
        String requestBody = """ 
                {
                         "name": "rest assured board"
                }
                """;
        JsonPath expectedResponse = new JsonPath(requestBody);

        Response postResponse = given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        String idOfNewCreatedBoard = postResponse.path("id");

        getBoardById(idOfNewCreatedBoard)
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(idOfNewCreatedBoard))
                .log().all();
    }

    @Test
    public void GetApiTest() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);

        getBoardById(idOfNewCreatedBoard)
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(idOfNewCreatedBoard))
                .log().all();
    }

    @Test
    public void PutApiTest() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);

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
                .pathParam("id", idOfNewCreatedBoard)
                .body(requestBody)
                .when()
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        getBoardById(idOfNewCreatedBoard)
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(idOfNewCreatedBoard))
                .log().all();
    }

    @Test
    public void DeleteApiTest() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);

        given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .pathParam("id", idOfNewCreatedBoard)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        getBoardById(idOfNewCreatedBoard)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log().all();
    }

    private static ValidatableResponse getBoardById(String idOfNewCreatedBoard) {
        return given()
                .baseUri("https://api.trello.com/1/boards")
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .pathParam("id", idOfNewCreatedBoard)
                .when()
                .get("/{id}")
                .then();
    }
}
