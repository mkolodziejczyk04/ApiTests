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
    public void shouldCreateBoardSuccessfully() throws Exception {
        try (ApiResponse apiResponse = boardClient.createBoardAndGetJson(boardName)) {
            JSONObject json = apiResponse.getBody();
            int response = apiResponse.getStatusCode();
            String nameAfterSend = json.getString("name");
            String boardIdAfterSend = json.getString("id");

            Assert.assertEquals(response, HttpStatus.OK);
            Assert.assertEquals(nameAfterSend, boardName);
            Assert.assertNotNull(boardIdAfterSend);
        }
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        try (CloseableHttpResponse responseOfGet = getBoard.sendGet(idOfNewCreatedBoard)) {

            JSONObject jsonAfterGet = boardClient.getJsonObject(responseOfGet);

            String idAfterSendGet = jsonAfterGet.getString("id");
            int responseCode = responseOfGet.getCode();

            Assert.assertEquals(responseCode, HttpStatus.OK);
            Assert.assertEquals(idAfterSendGet, idOfNewCreatedBoard);
        }
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        String newBoardName = "name after put";

        try (CloseableHttpResponse responseOfPut = putBoard.sendPut(idOfNewCreatedBoard, newBoardName)) {
        }


        try (CloseableHttpResponse responseAfterGetBoard = getBoard.sendGet(idOfNewCreatedBoard)) {
            JSONObject jsonAfterGet = boardClient.getJsonObject(responseAfterGetBoard);


            String idAfterSendPut = jsonAfterGet.getString("id");
            int responseCode = responseAfterGetBoard.getCode();

            String nameAfterPut = jsonAfterGet.getString("name");

            Assert.assertEquals(responseCode, HttpStatus.OK);
            Assert.assertEquals(idAfterSendPut, idOfNewCreatedBoard);
            Assert.assertEquals(nameAfterPut, newBoardName);
        }
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String idOfNewCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);

        try (CloseableHttpResponse responseOfDelete = deleteBoard.sendDelete(idOfNewCreatedBoard)) {
        }

        try (CloseableHttpResponse responseAfterGetBoard = getBoard.sendGet(idOfNewCreatedBoard)) {
            int responseCode = responseAfterGetBoard.getCode();

            Assert.assertEquals(responseAfterGetBoard.getCode(), HttpStatus.NOT_FOUND);
        }
    }
}
