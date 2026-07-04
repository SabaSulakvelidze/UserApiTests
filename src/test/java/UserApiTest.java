import com.credobank.Data.UserDataProvider;
import com.credobank.models.response.ErrorResponse;
import com.credobank.models.response.UsersResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserApiTest {

    @BeforeClass
    public void setUp() {
        WireMockSetup.startServer();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 2424;
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
                .extract()
                .response();

        if (expectedStatus == 200) {
            List<UsersResponse> users = response.as(new TypeRef<List<UsersResponse>>() {
            });
            Assert.assertEquals(users.size(), expectedSize);
        }else {
            ErrorResponse errorResponse = response.as(ErrorResponse.class);
            Assert.assertNotNull(errorResponse.getMessage());
        }


    }
}
