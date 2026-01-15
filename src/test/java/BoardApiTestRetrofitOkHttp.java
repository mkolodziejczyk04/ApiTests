import org.apache.hc.core5.http.ParseException;
import org.example.pojos.Root;
import org.example.requests.CreateBoardRetrofitOkHttp;
import org.example.requests.DeleteBoardRetrofitOkHttp;
import org.example.requests.GetBoardRetrofitOkHttp;
import org.example.requests.PutBoardRetrofitOkHttp;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardApiTestRetrofitOkHttp extends BasicTest {
    private final CreateBoardRetrofitOkHttp createBoard = new CreateBoardRetrofitOkHttp();
    private final GetBoardRetrofitOkHttp getBoard = new GetBoardRetrofitOkHttp();
    private final PutBoardRetrofitOkHttp putBoard = new PutBoardRetrofitOkHttp();
    private final DeleteBoardRetrofitOkHttp deleteBoard = new DeleteBoardRetrofitOkHttp();

    @Test
    public void shouldCreateBoardSuccessfully() throws IOException, ParseException {
        String boardName = "Nice board";

        Response<Root> response = createBoard.sendPost(boardName);
        Root result = response.body();

        String idAfterSendPost = result.getId();
        int responseCode = response.code();
        String nameAfterSendPost = result.getName();


        assertThat(responseCode).isEqualTo(200);
        assertThat(nameAfterSendPost).isEqualTo(boardName);
        assertThat(idAfterSendPost).isNotNull();
    }

    @Test
    public void shouldGetBoardSuccessfully() throws IOException, ParseException {
        String id = "6968d3c5c504f49ecb14bb01";

        Response<Root> response = getBoard.sendGet(id);
        Root result = response.body();

        String idAfterSendGet = result.getId();
        int responseCode = response.code();


        assertThat(responseCode).isEqualTo(200);
        assertThat(idAfterSendGet).isEqualTo(id);
    }

    @Test
    public void shouldPutBoardSuccessfully() throws IOException, ParseException {
        String id = "6968d3ce17bd0f3b340b0da0";
        String newBoardName = "new Board Name";
        Response<Root> response = putBoard.sendPut(id, newBoardName);

        Root result = response.body();
        int responseCode = response.code();
        String idAfterSendPut = result.getId();
        String nameAfterSendPut = result.getName();

        assertThat(result).isNotNull();
        assertThat(responseCode).isEqualTo(200);
        assertThat(idAfterSendPut).isEqualTo(id);
        assertThat(nameAfterSendPut).isEqualTo(newBoardName);
    }

    @Test
    public void shouldDeleteBoardSuccessfully() throws IOException, ParseException {
        String id = "6968d3d6be0d0b9dee7db686";
        Response<Root> response = deleteBoard.sendDelete(id);

        Root result = response.body();
        int responseCode = response.code();
        String idAfterSendDelete = result.getId();

        assertThat(responseCode).isEqualTo(200);
        assertThat(idAfterSendDelete).isNull();
    }
}
