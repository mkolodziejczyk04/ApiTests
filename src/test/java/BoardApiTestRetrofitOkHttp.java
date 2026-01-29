import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.example.pojos.Root;
import org.example.requests.*;
import org.testng.Assert;
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
        String idOfNewBoard = response.body().getId();

        Response<Root> responseOfGet = getBoard.sendGet(idOfNewBoard);
        Root result = responseOfGet.body();
        Assert.assertNotNull(result);

        int responseCode = responseOfGet.code();
        String nameAfterSendPost = result.getName();
        String idAfterSendCreate = result.getId();

        assertThat(responseCode).isEqualTo(HttpStatus.SC_OK);
        assertThat(nameAfterSendPost).isEqualTo(boardName);
        assertThat(idAfterSendCreate).isNotNull();
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

        Response<Root> responseAfterGet = getBoard.sendGet(idOfCreatedBoard);


        Root result = responseAfterGet.body();

        int responseCode = responseAfterGet.code();

        assertThat(result).isNotNull();
        String idAfterSendPut = result.getId();
        String nameAfterSendPut = result.getName();

        assertThat(responseCode).isEqualTo(HttpStatus.SC_OK);
        assertThat(idAfterSendPut).isEqualTo(idOfCreatedBoard);
        assertThat(nameAfterSendPut).isEqualTo(newBoardName);
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String idOfCreatedBoard = boardClient.getIdOfCreatedBoard(boardName);
        Response<Root> response = deleteBoard.sendDelete(idOfCreatedBoard);

        Response<Root> responseAfterGet = getBoard.sendGet(idOfCreatedBoard);
        int responseCode = responseAfterGet.code();

        assertThat(responseCode).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }
}
