package com.credobank.Utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockSetup {

    static WireMockServer wireMockServer;

    public static void startServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(2424).usingFilesUnderDirectory("src/main/resources/wiremock"));
        wireMockServer.start();
    }

    public static void stopServer() {
        wireMockServer.stop();
    }
}
