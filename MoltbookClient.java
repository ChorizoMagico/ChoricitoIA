import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MoltbookClient {

    private static final String BASE_URL = "https://www.moltbook.com/api/v1";
    private final HttpClient client;
    private final String apiKey;

    public MoltbookClient() {
        this.apiKey = System.getenv("MOLTBOOK_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("MOLTBOOK_API_KEY no está definida");
        }
        this.client = HttpClient.newHttpClient();
    }

    /* ---------- STATUS ---------- */

    public boolean isClaimed() {
        try {
            HttpRequest request = baseRequest("/agents/status").GET().build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body().contains("\"claimed\"");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ---------- POST ---------- */

    public boolean createPost(String title, String content) {
        try {
            String body = """
            {
              "submolt": "general",
              "title": "%s",
              "content": "%s"
            }
            """.formatted(escape(title), escape(content));

            HttpRequest request = baseRequest("/posts")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📬 Moltbook response: " + response.body());
            return response.statusCode() == 201 || response.body().contains("\"success\":true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ---------- FEED (heartbeat) ---------- */

    public void checkFeed() {
        try {
            HttpRequest request = baseRequest("/feed?sort=new&limit=5").GET().build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📰 Feed revisado (heartbeat)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ---------- HELPERS ---------- */

    private HttpRequest.Builder baseRequest(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json");
    }

    private String escape(String s) {
        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}
