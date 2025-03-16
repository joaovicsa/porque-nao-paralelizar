package cadastroclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 *
 * @author _joao
 */

public class CadastroClientV2 {

    private static volatile boolean isRunning = true;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4321);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Digite o login: ");
            String login = reader.readLine();
            System.out.print("Digite a senha: ");
            String senha = reader.readLine();

            outputStream.writeObject(login);
            outputStream.writeObject(senha);

            SaidaFrame saidaFrame = new SaidaFrame(login);
            SwingUtilities.invokeLater(() -> saidaFrame.setVisible(true));

            ThreadClient threadClient = new ThreadClient(inputStream, saidaFrame, login);
            threadClient.start();

            while (isRunning) {

                System.out.println("Selecione uma opcao abaixo: ");
                System.out.println(" (L) Listar");
                System.out.println(" (E) Entrada");
                System.out.println(" (S) Saida");
                System.out.println(" (X) Finalizar");
                String comando = reader.readLine();

                if (comando.equalsIgnoreCase("L")) {
                    outputStream.writeObject("L");
                } else if (comando.equalsIgnoreCase("X")) {
                    isRunning = false;
                    break;
                } else if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    outputStream.writeObject(comando);

                    System.out.print("Digite o ID da pessoa: ");
                    int pessoaId = Integer.parseInt(reader.readLine());
                    outputStream.writeObject(pessoaId);

                    System.out.print("Digite o ID do produto: ");
                    int produtoId = Integer.parseInt(reader.readLine());
                    outputStream.writeObject(produtoId);

                    // Quantidade
                    System.out.print("Digite a quantidade: ");
                    int quantidade = Integer.parseInt(reader.readLine());
                    outputStream.writeObject(quantidade);

                    // Valor Unitário
                    System.out.print("Digite o valor unitário: ");
                    float valorUnitario = Float.parseFloat(reader.readLine());
                    outputStream.writeObject(valorUnitario);
                } else {
                    System.out.println("Comando inválido.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Encerrar o programa corretamente
            System.exit(0);
        }
    }
}
