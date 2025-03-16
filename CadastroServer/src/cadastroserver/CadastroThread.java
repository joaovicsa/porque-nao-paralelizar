/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroserver;

/**
 *
 * @author _joao
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.Produto;
import model.Usuario;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;

public class CadastroThread extends Thread {

    private ProdutoJpaController ctrlProduto;
    private UsuarioJpaController ctrlUsuario;
    private Socket s1;

    public CadastroThread(ProdutoJpaController ctrlProduto, UsuarioJpaController ctrlUsuario, Socket s1) {
        this.ctrlProduto = ctrlProduto;
        this.ctrlUsuario = ctrlUsuario;
        this.s1 = s1;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());

            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            Usuario usuario = ctrlUsuario.findUsuario(login, senha);
            if (usuario == null) {
                System.out.println("Usuário inválido");
                s1.close();
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            System.out.println("Usuário conectado com sucesso : " + now.format(formatter));

            while (true) {
                String comando = (String) in.readObject();

                if (comando.equals("L")) {
                    List<Produto> produtos = ctrlProduto.findProdutoEntities();
                    out.writeObject(produtos);
                } else {
                    System.out.println("Comando inválido");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
