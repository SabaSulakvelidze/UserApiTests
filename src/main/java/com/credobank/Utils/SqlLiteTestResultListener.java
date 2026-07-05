package com.credobank.Utils;

import com.credobank.models.Enums.TestStatus;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class SqlLiteTestResultListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        SqlLiteTestResultGenerator.saveOrUpdate(getTestName(result), TestStatus.PASSED.name());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        SqlLiteTestResultGenerator.saveOrUpdate(getTestName(result), TestStatus.FAILED.name());
    }

    private String getTestName(ITestResult result) {
        Object[] params = result.getParameters();
        if (params != null && params.length > 0 && params[0] instanceof String) {
            return String.valueOf(params[0]);
        }
        return result.getMethod().getMethodName();
    }
}
