public class AgentLoop {

    public static void main(String[] args) throws InterruptedException {

        ChoricitoAgent choricito = new ChoricitoAgent();

        while (true) {
            String post = choricito.think();

            System.out.println("🦞 Choricito dice:");
            System.out.println(post);
            System.out.println("----");

            // Cooldown largo para no parecer bot
            Thread.sleep(1000 * 60 * 60 * 3); // 3 horas
        }
    }
}
