import kong.unirest.HttpStatus;
import kong.unirest.json.JSONObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.example.requests.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class BoardApiTestNg extends BasicTest {
    private final CreateBoard createBoard = new CreateBoard();
    private final GetBoard getBoard = new GetBoard();
    private final PutBoard putBoard = new PutBoard();
    private final DeleteBoard deleteBoard = new DeleteBoard();
    private BoardClient boardClient = new BoardClient();
    private final String boardName = "Nice board";

    @Test
    public void shouldCreateBoardSuccessfully() throws IOException, ParseException {
        ApiResponse apiResponse = boardClient.createBoardAndGetJson(boardName);
        JSONObject json = apiResponse.getBody();

        int response = apiResponse.getStatusCode();
        String nameAfterSend = json.getString("name");
        String boardIdAfterSend = json.getString("id");

        Assert.assertEquals(response, HttpStatus.OK);
        Assert.assertEquals(nameAfterSend, boardName);
        Assert.assertNotNull(boardIdAfterSend);
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        CloseableHttpResponse responseOfGet = getBoard.sendGet(idOfNewCreatedBoard);

        JSONObject jsonAfterGet = boardClient.getJsonObject(responseOfGet);

        String idAfterSendGet = jsonAfterGet.getString("id");
        int responseCode = responseOfGet.getCode();

        Assert.assertEquals(responseCode, 200);
        Assert.assertEquals(idAfterSendGet, idOfNewCreatedBoard);
        responseOfGet.close();
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        String newBoardName = "name after put";

        CloseableHttpResponse responseOfPut = putBoard.sendPut(idOfNewCreatedBoard, newBoardName);
        JSONObject jsonAfterPut = boardClient.getJsonObject(responseOfPut);

        String idAfterSendPut = jsonAfterPut.getString("id");
        int responseCode = responseOfPut.getCode();
        String nameAfterPut = jsonAfterPut.getString("name");

        Assert.assertEquals(responseCode, 200);
        Assert.assertEquals(idAfterSendPut, idOfNewCreatedBoard);
        Assert.assertEquals(nameAfterPut, newBoardName);
        responseOfPut.close();
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        CloseableHttpResponse responseOfDelete = deleteBoard.sendDelete(idOfNewCreatedBoard);

        JSONObject jsonAfterDelete = boardClient.getJsonObject(responseOfDelete);
        int responseCode = responseOfDelete.getCode();
        String idAfterSendDelete = jsonAfterDelete.optString("id", null);

        Assert.assertEquals(responseCode, 200);
        Assert.assertNull(idAfterSendDelete);
        responseOfDelete.close();
    }
}
