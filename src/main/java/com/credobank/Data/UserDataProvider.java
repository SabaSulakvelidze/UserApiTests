package com.credobank.Data;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "userRequests")
    public static Object[][] userRequests() {
        return new Object[][]{
                {"testFilterByAge_Positive", "age", "30", 200, 1},
                {"testFilterByGender_Positive", "gender", "male", 200, 1},
                {"testGetAllUsers_Positive", null, null, 200, 2},
                // "is-Server-Down" პარამეტრი გამოიყენება სერვერის გათიშვის სიმულაციისთვის
                // სხვა შემთხვევაში WireMock ვერ განასხვავებს ჩვეულებრივი 200-იანი სტაბისგან
                {"testInternalServerError_Negative", "is-Server-Down", "true", 500, -1},
                {"testInvalidAge_Negative", "age", "-1", 400, -1},
                {"testInvalidGender_Negative", "gender", "unknown", 422, -1}
        };
    }
}
