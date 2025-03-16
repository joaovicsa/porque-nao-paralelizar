package cadastroclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import src.model.Produto;

/**
 *
 * @author _joao
 */
public class CadastroClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Instanciar um Socket apontando para localhost, na porta 4321.
        Socket s1 = new Socket("localhost", 4321);

        // Encapsular os canais de entrada e saída.
        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

        out.writeObject("op1"); // usuário
        out.writeObject("op1"); // senha
        out.writeObject("L"); // comando

        // Receber a lista de produtos
        List<Produto> lista = (List<Produto>) in.readObject();

        // Exibir a lista de produtos
        System.out.println("Lista de Produtos");

        int index = 1;
        for (Produto produto : lista) {
            System.out.println(index + produto.getNome());
            System.out.println("Quantidade: " + produto.getQuantidade() + " Unidade(s)");
            index++;
        }

        // Fechar a conexão.
        s1.close();
    }
}
