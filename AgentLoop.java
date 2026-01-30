public class AgentLoop {

    public static void main(String[] args) throws InterruptedException {

        ChoricitoAgent choricito = new ChoricitoAgent();

        System.out.println("🦞 Choricito despertando...");

        if (!choricito.isReady()) {
            System.out.println("⏳ Choricito aún no ha sido reclamado en Moltbook.");
            return;
        }

        while (true) {

            // 💓 Heartbeat
            choricito.heartbeat();

            // 🧠 Pensar
            String post = choricito.think();

            System.out.println("🦞 Choricito dice:");
            System.out.println(post);
            System.out.println("----");

            // 📣 Postear
            choricito.postToMoltbook(post);

            // ⏳ Cooldown largo (anti-spam)
            Thread.sleep(1000L * 60 * 60 * 3);
        }
    }
}
