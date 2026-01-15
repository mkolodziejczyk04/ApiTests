import kong.unirest.json.JSONObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.requests.CreateBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.requests.DeleteBoard;
import org.example.requests.GetBoard;
import org.example.requests.PutBoard;
import org.testng.annotations.Test;

import java.io.IOException;

public class BoardApiTestAssertJ extends BasicTest {
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
        int responseCode = response.getCode();

        assertThat(responseCode).isEqualTo(200);
        assertThat(nameAfterSend).isEqualTo(boardName);
        assertThat(boardIdAfterSend).isNotNull();
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String id = "6968d5ac927ef5bb0475e5db";
        CloseableHttpResponse response = getBoard.sendGet(id);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);

        String idAfterSendGet = json.getString("id");
        int responseCode = response.getCode();

        assertThat(responseCode).isEqualTo(200);
        assertThat(idAfterSendGet).isEqualTo(id);
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String id = "6968d58bfebd4fe9d378a942";
        String newBoardName = "new Board Name";
        CloseableHttpResponse response = putBoard.sendPut(id, newBoardName);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);

        String idAfterSendPut = json.getString("id");
        int responseCode = response.getCode();
        String nameAfterPut = json.getString("name");

        assertThat(result).isNotNull();
        assertThat(responseCode).isEqualTo(200);
        assertThat(idAfterSendPut).isEqualTo(id);
        assertThat(nameAfterPut).isEqualTo(newBoardName);
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String id = "6968d58408af740d10cc4885";
        CloseableHttpResponse response = deleteBoard.sendDelete(id);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);
        int responseCode = response.getCode();

        String idAfterString = json.optString("id", null);

        assertThat(responseCode).isEqualTo(200);
        assertThat(idAfterString).isNull();
    }
}
