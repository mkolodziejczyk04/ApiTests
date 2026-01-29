import kong.unirest.HttpStatus;
import kong.unirest.json.JSONObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import org.example.requests.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import java.io.IOException;

public class BoardApiTestAssertJ extends BasicTest {
    private final CreateBoard createBoard = new CreateBoard();
    private final GetBoard getBoard = new GetBoard();
    private final PutBoard putBoard = new PutBoard();
    private final DeleteBoard deleteBoard = new DeleteBoard();
    private BoardClient boardClient = new BoardClient();
    private String boardName = "board Name";

    @Test
    public void shouldCreateBoardSuccessfully() throws Exception {
        try (ApiResponse apiResponse = boardClient.createBoardAndGetJson(boardName)) {
            JSONObject jsonAfterCreate = apiResponse.getBody();
            String boardIdAfterSend = jsonAfterCreate.getString("id");

            int responseCode = apiResponse.getStatusCode();
            String nameAfterSend = jsonAfterCreate.getString("name");

            assertThat(responseCode).isEqualTo(HttpStatus.OK);
            assertThat(nameAfterSend).isEqualTo(boardName);
            assertThat(boardIdAfterSend).isNotNull();
        }
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCratedBoard = boardClient.getIdOfCreatedBoard(boardName);

        try (CloseableHttpResponse responseOfGet = getBoard.sendGet(idOfNewCratedBoard)) {
            JSONObject jsonAfterGet = boardClient.getJsonObject(responseOfGet);
            int responseCode = responseOfGet.getCode();
            String idAfterSendGet = jsonAfterGet.getString("id");
            String nameAfterSendGet = jsonAfterGet.getString("name");

            assertThat(responseCode).isEqualTo(HttpStatus.OK);
            assertThat(idAfterSendGet).isEqualTo(idOfNewCratedBoard);
            assertThat(nameAfterSendGet).isEqualTo(boardName);
        }
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String newBoardName = "name after put";
        String idOfNewCratedBoard = boardClient.getIdOfCreatedBoard(boardName);

        try (CloseableHttpResponse responseOfPut = putBoard.sendPut(idOfNewCratedBoard, newBoardName)) {
        }

        try (CloseableHttpResponse response = getBoard.sendGet(idOfNewCratedBoard)) {
            JSONObject jsonAfterGet = boardClient.getJsonObject(response);
            int responseCode = response.getCode();
            String nameAfterPut = jsonAfterGet.getString("name");
            String idAfterPut = jsonAfterGet.getString("id");


            assertThat(responseCode).isEqualTo(HttpStatus.OK);
            assertThat(idAfterPut).isEqualTo(idOfNewCratedBoard);
            assertThat(nameAfterPut).isEqualTo(newBoardName);
        }
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);

        try (CloseableHttpResponse responseOfDelete = deleteBoard.sendDelete(idOfNewCreatedBoard)) {
        }

        try (CloseableHttpResponse responseOfGet = getBoard.sendGet(idOfNewCreatedBoard)) {
            int responseCode = responseOfGet.getCode();
            assertThat(responseCode).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
