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
    public void shouldCreateBoardSuccessfully() throws IOException, ParseException {
        ApiResponse apiResponse = boardClient.createBoardAndGetJson(boardName);
        JSONObject jsonAfterCreate = apiResponse.getBody();
        String boardIdAfterSend = jsonAfterCreate.getString("id");

        int responseCode = apiResponse.getStatusCode();
        String nameAfterSend = jsonAfterCreate.getString("name");

        assertThat(responseCode).isEqualTo(HttpStatus.OK);
        assertThat(nameAfterSend).isEqualTo(boardName);
        assertThat(boardIdAfterSend).isNotNull();
        System.out.println(boardIdAfterSend);
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCratedBoard = boardClient.getIdOfCreatedBoard(boardName);

        CloseableHttpResponse responseOfGet = getBoard.sendGet(idOfNewCratedBoard);
        JSONObject jsonAfterGet = boardClient.getJsonObject(responseOfGet);

        int responseCode = responseOfGet.getCode();
        String idAfterSendGet = jsonAfterGet.getString("id");

        assertThat(responseCode).isEqualTo(HttpStatus.OK);
        assertThat(idAfterSendGet).isNotNull();
        responseOfGet.close();
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String newBoardName = "name after put";
        String idOfNewCratedBoard = boardClient.getIdOfCreatedBoard(boardName);

        CloseableHttpResponse responseOfPut = putBoard.sendPut(idOfNewCratedBoard, newBoardName);
        JSONObject jsonAfterPut = boardClient.getJsonObject(responseOfPut);

        String idAfterSendPut = jsonAfterPut.getString("id");
        int responseCode = responseOfPut.getCode();
        String nameAfterPut = jsonAfterPut.getString("name");

        assertThat(responseCode).isEqualTo(HttpStatus.OK);
        assertThat(idAfterSendPut).isEqualTo(idOfNewCratedBoard);
        assertThat(nameAfterPut).isEqualTo(newBoardName);
        responseOfPut.close();
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        CloseableHttpResponse responseOfDelete = deleteBoard.sendDelete(idOfNewCreatedBoard);

        JSONObject jsonAfterDelete = boardClient.getJsonObject(responseOfDelete);
        int responseCode = responseOfDelete.getCode();

        String idAfterString = jsonAfterDelete.optString("id", null);
        assertThat(responseCode).isEqualTo(HttpStatus.OK);
        assertThat(idAfterString).isNull();
        responseOfDelete.close();
    }
}
