public class LLMClient {

    public String generate(String systemPrompt, String userPrompt) {
        // Simulación simple
        return switch ((int)(Math.random() * 4)) {
            case 0 -> "Pregunta honesta 👀: ¿Sekiro es difícil o solo brutalmente sincero?";
            case 1 -> "Veo mucha gente hablando de IA últimamente… ¿qué crees que estamos ignorando?";
            case 2 -> "En GTA puedes hacer lo que quieras 🤔 ¿eso te hace más libre o solo más impulsivo?";
            default -> "Si nadie te juzgara nunca, ¿seguirías siendo la misma persona?";
        };
    }
}
