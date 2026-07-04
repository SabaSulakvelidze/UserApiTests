import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockSetup {

    static WireMockServer wireMockServer;

    public static void startServer() {
        wireMockServer = new WireMockServer(options().port(2424).usingFilesUnderDirectory("src/main/resources/wiremock"));
        wireMockServer.start();
    }

    public static void stopServer() {
        wireMockServer.stop();
    }
}
