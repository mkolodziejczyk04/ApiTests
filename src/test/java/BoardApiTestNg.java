import kong.unirest.json.JSONObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.requests.CreateBoard;
import org.example.requests.DeleteBoard;
import org.example.requests.GetBoard;
import org.example.requests.PutBoard;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class BoardApiTestNg extends BasicTest{
    private final CreateBoard createBoard = new CreateBoard();
    private final GetBoard getBoard = new GetBoard();
    private final PutBoard putBoard = new PutBoard();
    private final DeleteBoard deleteBoard = new DeleteBoard();

    @Test
    public void shouldCreateBoardSuccessfully() throws IOException, ParseException {
        String boardName = "Nice board";
        CloseableHttpResponse response = createBoard.sendPost(boardName);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);

        String nameAfterSend = json.getString("name");
        String boardIdAfterSend = json.getString("id");

        Assert.assertEquals(response.getCode(), 200);
        Assert.assertEquals(nameAfterSend, boardName);
        Assert.assertNotNull(boardIdAfterSend);
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String id = "6967a5b8505b0dcec636a39f";
        CloseableHttpResponse response = getBoard.sendGet(id);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);

        String idAfterSendGet = json.getString("id");
        int responseCode = response.getCode();
        String idAfterSend = json.optString("id", null);

        Assert.assertEquals(responseCode, 200);
        Assert.assertEquals(idAfterSend, id);
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String id = "6967a5c45b1654578c2cd325";
        String newBoardName = "new Board Name";
        CloseableHttpResponse response = putBoard.sendPut(id, newBoardName);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);

        String idAfterSendPut = json.getString("id");
        int responseCode = response.getCode();
        String nameAfterPut = json.getString("name");

        Assert.assertEquals(responseCode, 200);
        Assert.assertEquals(idAfterSendPut, id);
        Assert.assertEquals(nameAfterPut, newBoardName);
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String id = "6967a5cc4c65093a250dfa0d";
        CloseableHttpResponse response = deleteBoard.sendDelete(id);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);
        int responseCode = response.getCode();
        String idAfterString = json.optString("id", null);

        Assert.assertEquals(responseCode, 200);
        Assert.assertNull(idAfterString);
    }
}
