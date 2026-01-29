import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public abstract class BasicTest {
    @BeforeClass
    public void checkEnvironmentalVariables() {
        String key = System.getenv("TRELLO_KEY");
        String token = System.getenv("TRELLO_TOKEN");
        Assert.assertNotNull(key, "Trello key was not provided");
        Assert.assertNotNull(token, "Trello token was not provided");
    }
}
