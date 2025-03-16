package cadastroserver;

/**
 *
 * @author _joao
 */
import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CadastroServer {

    public static void main(String[] args) throws IOException {
        // Exibe uma mensagem de boas-vindas ao iniciar o servidor
        System.out.println("Bem vindo! Por Favor Aguarde a Conexão");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");

        ProdutoJpaController ctrlProduto = new ProdutoJpaController(emf);
        UsuarioJpaController ctrlUsuario = new UsuarioJpaController(emf);
        MovimentoJpaController ctrlMov = new MovimentoJpaController(emf);
        PessoaJpaController ctrlPessoa = new PessoaJpaController(emf);

        ServerSocket s1 = new ServerSocket(4321);

        while (true) {
            Socket s2 = s1.accept();

            CadastroThreadV2 t1 = new CadastroThreadV2(ctrlProduto, ctrlUsuario, s2, ctrlMov, ctrlPessoa);
            t1.start();
        }
    }
}
