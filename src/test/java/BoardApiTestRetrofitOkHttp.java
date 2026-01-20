import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.example.pojos.Root;
import org.example.requests.*;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardApiTestRetrofitOkHttp extends BasicTest {
    private final CreateBoardRetrofitOkHttp createBoard = new CreateBoardRetrofitOkHttp();
    private final GetBoardRetrofitOkHttp getBoard = new GetBoardRetrofitOkHttp();
    private final PutBoardRetrofitOkHttp putBoard = new PutBoardRetrofitOkHttp();
    private final DeleteBoardRetrofitOkHttp deleteBoard = new DeleteBoardRetrofitOkHttp();
    private BoardClient boardClient = new BoardClient();
    private final String boardName = "board name";

    @Test
    public void shouldCreateBoardSuccessfully() throws IOException, ParseException {
        Response<Root> response = createBoard.sendPost(boardName);
        Root result = response.body();

        assertThat(response.body()).isNotNull();
        String idAfterSendPost = result.getId();
        int responseCode = response.code();
        String nameAfterSendPost = result.getName();


        assertThat(responseCode).isEqualTo(HttpStatus.SC_OK);
        assertThat(nameAfterSendPost).isEqualTo(boardName);
        assertThat(idAfterSendPost).isNotNull();
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String idOfCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);

        Response<Root> response = getBoard.sendGet(idOfCreatedBoard);
        Root result = response.body();

        assertThat(response.body()).isNotNull();
        String idAfterSendGet = result.getId();
        int responseCode = response.code();


        assertThat(responseCode).isEqualTo(HttpStatus.SC_OK);
        assertThat(idAfterSendGet).isEqualTo(idOfCreatedBoard);
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String idOfCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        String newBoardName = "new Board Name";
        Response<Root> response = putBoard.sendPut(idOfCreatedBoard, newBoardName);

        Root result = response.body();
        int responseCode = response.code();
        assertThat(response.body()).isNotNull();
        String idAfterSendPut = result.getId();
        String nameAfterSendPut = result.getName();

        assertThat(result).isNotNull();
        assertThat(responseCode).isEqualTo(HttpStatus.SC_OK);
        assertThat(idAfterSendPut).isEqualTo(idOfCreatedBoard);
        assertThat(nameAfterSendPut).isEqualTo(newBoardName);
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String idOfCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        Response<Root> response = deleteBoard.sendDelete(idOfCreatedBoard);

        String idAfterSendDelete = response.body().id;
        int responseCode = response.code();

        assertThat(responseCode).isEqualTo(HttpStatus.SC_OK);
        assertThat(idAfterSendDelete).isNull();
    }
}
