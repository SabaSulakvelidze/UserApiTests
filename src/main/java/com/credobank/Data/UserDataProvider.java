package com.credobank.Data;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "userRequests")
    public static Object[][] userRequests() {
        return new Object[][]{
                {"testFilterByAge_Positive", "age", "30", 200, 1},
                {"testFilterByGender_Positive", "gender", "male", 200, 1},
                {"testGetAllUsers_Positive", null, null, 200, 2},
                {"testInternalServerError_Negative", "is-Server-Down", "true", 500, -1},
                {"testInvalidAge_Negative", "age", "-1", 400, -1},
                {"testInvalidGender_Negative", "gender", "unknown", 422, -1}
        };
    }
}
