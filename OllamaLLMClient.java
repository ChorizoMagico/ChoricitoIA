import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OllamaLLMClient {

    private static final String API_URL = "http://localhost:11434/api/generate";
    private final HttpClient client;

    public OllamaLLMClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String generate(String systemPrompt, String userPrompt) {
        try {
            String prompt = systemPrompt + "\n\nUsuario: " + userPrompt + "\nChoricito:";

            String body = """
            {
              "model": "phi3:mini",
              "temperature": 0.8,
              "stream": false,
              "prompt": "%s"
            }
            """.formatted(escape(prompt));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractResponse(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return "Pregunta honesta 👀: ¿crees que pensar demasiado nos hace más sabios o solo más inseguros?";
        }
    }

    // --- helpers ---

    private String extractResponse(String json) {
        int start = json.indexOf("\"response\":\"") + 12;
        int end = json.indexOf("\"", start);
        return json.substring(start, end)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }

    private String escape(String s) {
        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }

}
