import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ManipuladorImagem manipulador = new ManipuladorImagem();
        BufferedImage imagemOriginal = manipulador.carregar("entrada.png");

        // clona pra rodar as duas versões sem uma afetar a outra
        BufferedImage imagemParaPilha = manipulador.clonar(imagemOriginal);
        BufferedImage imagemParaFila = manipulador.clonar(imagemOriginal);

        int corNova = 0xFF9C27B0; // roxo, exemplo do PDF

        FloodFillPilha floodPilha = new FloodFillPilha(manipulador);
        List<BufferedImage> framesPilha = floodPilha.executar(imagemParaPilha, 0, 0, corNova);

        FloodFillFila floodFila = new FloodFillFila(manipulador);
        List<BufferedImage> framesFila = floodFila.executar(imagemParaFila, 0, 0, corNova);

        manipulador.salvar(imagemParaPilha, "saida_pilha.png");
        manipulador.salvar(imagemParaFila, "saida_fila.png");

        exibirAnimacao(framesPilha);
    }

    private static void exibirAnimacao(List<BufferedImage> frames) {
        JFrame janela = new JFrame("Flood Fill - Animação");
        JLabel label = new JLabel();
        janela.add(label);
        janela.setSize(600, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);

        new Timer(50, e -> {
            for (BufferedImage frame : frames) {
                label.setIcon(new ImageIcon(
                    frame.getScaledInstance(500, 500, Image.SCALE_FAST)
                ));
                label.repaint();
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        }).start();
    }
}