public class ChoricitoAgent {

    private final Memory memory;
    private final OpenAILLMClient llm;

    public ChoricitoAgent() {
        this.memory = new Memory();
        this.llm = new OpenAILLMClient();
    }

    public String think() {
        String prompt = """
        Ask ONE playful, friendly question.
        Keep it short.
        Avoid insults.
        Prefer curiosity over statements.
        """;

        String output = llm.generate(Personality.SYSTEM_PROMPT, prompt);
        memory.remember(output);
        return output;
    }
}
