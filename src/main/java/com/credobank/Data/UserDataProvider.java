package com.credobank.Data;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "userRequests")
    public static Object[][] userRequests() {
        return new Object[][]{
                {"testFilterByAge_Positive", "age", "30","schemas/users-list-schema.json", 200, 1},
                {"testFilterByGender_Positive", "gender", "male","schemas/users-list-schema.json", 200, 1},
                {"testGetAllUsers_Positive", null, null,"schemas/users-list-schema.json", 200, 2},
                // "is-Server-Down" პარამეტრი გამოიყენება სერვერის გათიშვის სიმულაციისთვის
                // სხვა შემთხვევაში WireMock ვერ განასხვავებს ჩვეულებრივი 200-იანი სტაბისგან
                {"testInternalServerError_Negative", "is-Server-Down", "true","schemas/error-response-schema.json", 500, -1},
                {"testInvalidAge_Negative", "age", "-1","schemas/error-response-schema.json", 400, -1},
                {"testInvalidGender_Negative", "gender", "unknown","schemas/error-response-schema.json", 422, -1}
        };
    }
}
