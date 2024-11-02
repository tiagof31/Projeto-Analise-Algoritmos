import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) {
        File folder = new File("."); // Diretório atual
        File[] files = folder.listFiles((dir, name) -> name.endsWith("Sort.java") || name.endsWith("Parallel.java"));

        // Criar diretório para arquivos .class
        File classDir = new File(".class");
        if (!classDir.exists()) {
            classDir.mkdir();
        }

        if (files != null) {
            for (File file : files) {
                String javaFileName = file.getName();
                String className = javaFileName.replace(".java", "");
                try {
                    // Compilar o arquivo Java e direcionar a saída para o diretório .class
                    System.out.println("Compilando: " + javaFileName);
                    ProcessBuilder compilePb = new ProcessBuilder("javac", "-d", ".class", javaFileName);
                    Process compileProcess = compilePb.start();
                    compileProcess.waitFor();

                    // Executar a classe compilada do diretório .class
                    System.out.println("Executando: " + className);
                    ProcessBuilder runPb = new ProcessBuilder("java", "-cp", ".class", className);
                    Process runProcess = runPb.start();
                    consumeStream(runProcess.getInputStream(), "OUTPUT");
                    consumeStream(runProcess.getErrorStream(), "ERROR");
                    runProcess.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Chamar o script Python para plotar os gráficos
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "graphs.py");
            Process p = pb.start();
            consumeStream(p.getInputStream(), "OUTPUT");
            consumeStream(p.getErrorStream(), "ERROR");
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para consumir e imprimir as saídas dos processos
    private static void consumeStream(InputStream inputStream, String streamType) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[" + streamType + "] " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}