import com.credobank.Data.UserDataProvider;
import com.credobank.Utils.Deserializer;
import com.credobank.Utils.SqlLiteTestResultListener;
import com.credobank.Utils.WireMockSetup;
import com.credobank.models.response.ErrorResponse;
import com.credobank.models.response.UsersResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Listeners(SqlLiteTestResultListener.class)
public class UserApiTest extends BaseApiTest{

    @BeforeClass
    public void setUp() {
        WireMockSetup.startServer();
    }

    @AfterClass
    public void tearDown() {
        WireMockSetup.stopServer();
    }

    @Test(dataProvider = "userRequests", dataProviderClass = UserDataProvider.class)
    public void testUserEndPoint(
            String testName,
            String qParamKey,
            String qParamValue,
            String expectedSchemasPath,
            int expectedStatus,
            int expectedSize
    ) {

        RequestSpecBuilder specBuilder = new RequestSpecBuilder();

        if (qParamKey != null && qParamValue != null)
            specBuilder.addQueryParam(qParamKey, qParamValue);

        Response response = given()
                .when()
                .spec(specBuilder.build())
                .get("/users")
                .then()
                .statusCode(expectedStatus)
                .body(matchesJsonSchemaInClasspath(expectedSchemasPath))
                .extract()
                .response();

        if (expectedStatus == 200) {
            List<UsersResponse> users = Deserializer.deserializeList(response, UsersResponse.class);
            //ალტერნატივა, RestAssured ავტომატურად აკეთებს დესერიალიზაციას Jackson ბიბლიოთეკის გამოყენებით
            //List<UsersResponse> users = response.as(new TypeRef<List<UsersResponse>>() {});
            Assert.assertEquals(users.size(), expectedSize);
        } else {
            ErrorResponse errorResponse = Deserializer.deserialize(response, ErrorResponse.class);
            //ErrorResponse errorResponse = response.as(ErrorResponse.class);
            Assert.assertNotNull(errorResponse.getMessage());
        }


    }
}
