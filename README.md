# porque-nao-paralelizar

  Criar o projeto do servidor, utilizando o nome CadastroServer, do tipo console, no
    modelo Ant padrão, para implementar o protocolo apresentado a seguir:

        Cliente conecta e envia login e senha.
        Servidor valida credenciais e, se forem inválidas, desconecta.
        Com credenciais válidas, fica no ciclo de resposta    .

            Cliente envia letra L.
            Servidor responde com o conjunto de produtos.

2.    Criar a camada de persistência em CadastroServer.

        Criar o pacote model para implementação das entidades.
        Utilizar a opção New..Entity Classes from Database.

    c.    Selecionar a conexão com o SQL Server, previamente configurada na aba
Services, divisão Databases, do NetBeans e adicionar todas as tabelas.

    d.    Acrescentar ao projeto a biblioteca Eclipse Link (JPA 2.1).

    e.    Acrescentar o arquivo jar do conector JDBC para o SQL Server.

Observação! Por não executar o servidor sob o Tomcat, não será necessário
ajustar os pacotes para a distribuição do Jakarta.

3.    Criar a camada de controle em CadastroServer:

        Criar o pacote controller para implementação dos controladores.
        Utilizar a opção New..JPA Controller Classes from Entity Classes.
        Na classe UsuarioJpaController, adicionar o método findUsuario, tendo como
        parâmetros o login e a senha, retornando o usuário a partir de uma consulta JPA,
        ou nulo, caso não haja um usuário com as credenciais.
        Ao final o projeto ficará como o que é apresentado a seguir.

4.    No pacote principal, cadastroserver, adicionar a Thread de comunicação, com o
nome CadastroThread.

        Acrescentar os atributos ctrl e ctrlUsu, dos tipos ProdutoJpaController e
        UsuarioJpaController, respectivamente.
        Acrescentar o atributo s1 para receber o Socket.
        Definir um construtor recebendo os controladores e o Socket, com a passagem
        dos valores para os atributos internos.
        Implementar o método run para a Thread, cujo funcionamento será o descrito a
        seguir.

            Encapsular os canais de entrada e saída do Socket em objetos dos tipos
            ObjectOutputStream (saída) e ObjectInputStream (entrada).
            Obter o login e a senha a partir da entrada.
            Utilizar ctrlUsu para verificar o login, terminando a conexão caso o retorno seja
            nulo.
            Com o usuário válido, iniciar o loop de resposta, que deve obter o comando a
            partir da entrada.
            Caso o comando seja L, utilizar ctrl para retornar o conjunto de produtos
            através da saída.

5.    Implementar a classe de execução (main), utilizando as características que são
apresentadas a seguir.

        Instanciar um objeto do tipo EntityManagerFactory a partir da unidade de
        persistência.
        Instanciar o objeto ctrl, do tipo ProdutoJpaController.
        Instanciar o objeto ctrlUsu do tipo UsuarioJpaController.
        Instanciar um objeto do tipo ServerSocket, escutando a porta 4321.
        Dentro de um loop infinito, obter a requisição de conexão do cliente, instanciar
        uma Thread, com a passagem de ctrl, ctrlUsu e do Socket da conexão, iniciando-
        a em seguida.
        Com a Thread respondendo ao novo cliente, o servidor ficará livre para escutar a
        próxima solicitação de conexão.

6.    Criar o cliente de teste, utilizando o nome CadastroClient, do tipo console, no
modelo Ant padrão, para implementar a funcionalidade apresentada a seguir:

        Instanciar um Socket apontando para localhost, na porta 4321.
        Encapsular os canais de entrada e saída do Socket em objetos dos tipos
        ObjectOutputStream (saída) e ObjectInputStream (entrada).
        Escrever o login e a senha na saída, utilizando os dados de algum dos registros
        da tabela de usuários (op1/op1).
        Enviar o comando L no canal de saída.
        Receber a coleção de entidades no canal de entrada.
        Apresentar o nome de cada entidade recebida.
        Fechar a conexão.

7.    Configurar o projeto do cliente para uso das entidades:

        Copiar o pacote model do projeto servidor para o cliente.
        Adicionar a biblioteca Eclipse Link (JPA 2.1).
        A configuração final pode ser observada a seguir.

8.   Testar o sistema criado, com a execução dos dois projetos:

        Executar o projeto servidor.
        Executar, em seguida, o cliente.
        A saída do cliente deverá ser como a que é apresentada a seguir.

✅ Resultados esperados

1. É importante que o código seja organizado.

2. Outro ponto importante é explorar as funcionalidades oferecidas pelo NetBeans para
melhoria da produtividade.

3. Nesse exercício, é esperado que o estudante demonstre as habilidades básicas no
uso prático de Threads em ambientes cliente e servidor.

📝 Relatório discente de acompanhamento

Os Relatórios de Práticas deverão ser confeccionados em arquivo no formato PDF, com
a Logo da Universidade, nome do Campus, nome do Curso, nome da Disciplina, número
da Turma, semestre letivo, nome dos integrantes da Prática. Além disso, o projeto deve
ser armazenado em um repositório no GIT e o respectivo endereço deve constar na
documentação. A documentação do projeto deve conter:

    Título da Prática;
    Objetivo da Prática;
    Todos os códigos solicitados neste roteiro de aula;
    Os resultados da execução dos códigos também devem ser apresentados;
    Análise e Conclusão:

        Como funcionam as classes Socket e ServerSocket?
        Qual a importância das portas para a conexão com servidores?
        Para que servem as classes de entrada e saída ObjectInputStream e
        ObjectOutputStream, e por que os objetos transmitidos devem ser serializáveis?
        Por que, mesmo utilizando as classes de entidades JPA no cliente, foi possível
        garantir o isolamento do acesso ao banco de dados?

👉 2º Procedimento | Servidor Completo e Cliente Assíncrono

    Criar uma segunda versão da Thread de comunicação, no projeto do servidor, com o
    acréscimo da funcionalidade apresentada a seguir:

        Servidor recebe comando E, para entrada de produtos, ou S, para saída.
        Gerar um objeto Movimento, configurado com o usuário logado e o tipo, que
        pode ser E ou S.
        Receber o Id da pessoa e configurar no objeto Movimento.
        Receber o Id do produto e configurar no objeto Movimento.
        Receber a quantidade e configurar no objeto Movimento.
        Receber o valor unitário e configurar no objeto Movimento.
        Persistir o movimento através de um MovimentoJpaController com o nome
        ctrlMov.
        Atualizar a quantidade de produtos, de acordo com o tipo de movimento, através
        de ctrlProd.

Observação! Devem ser acrescentados os atributos ctrlMov e ctrlPessoa, dos tipos
MovimentoJpaController e PessoaJpaController, alimentados por meio de parâmetros
no construtor

2.    Acrescentar os controladores necessários na classe principal, método main, e
trocar a instância da Thread anterior pela nova Thread no loop de conexão.

3.   Criar o cliente assíncrono, utilizando o nome CadastroClientV2, do tipo console, no
modelo Ant padrão, para implementar a funcionalidade apresentada a seguir:

        Instanciar um Socket apontando para localhost, na porta 4321.
        Encapsular os canais de entrada e saída do Socket em objetos dos tipos
        ObjectOutputStream (saída) e ObjectInputStream (entrada).
        Escrever o login e a senha na saída, utilizando os dados de algum dos registros
        da tabela de usuários (op1/op1) .
        Encapsular a leitura do teclado em um BufferedReader.
        Instanciar a janela para apresentação de mensagens (Passo 4) e a Thread para
        preenchimento assíncrono (Passo 5), com a passagem do canal de entrada do
        Socket.
        Apresentar um menu com as opções: L – Listar,  X – Finalizar, E – Entrada, S –
        Saída.
        Receber o comando a partir do teclado.
        Para o comando L, apenas enviá-lo para o servidor.
        Para os comandos E ou S, enviar para o servidor e executar os seguintes passos:

            Obter o Id da pessoa via teclado e enviar para o servidor.
            Obter o Id do produto via teclado e enviar para o servidor.
            Obter a quantidade via teclado e enviar para o servidor.
            Obter o valor unitário via teclado e enviar para o servidor.

    j.    Voltar ao passo f até que o comando X seja escolhido

4.    Criar a janela para apresentação das mensagens:

        Definir a classe SaidaFrame como descendente de JDialog
        Acrescentar um atributo público do tipo JTextArea, com o nome texto
        Ao nível do construtor, efetuar os passos apresentados a seguir:

            Definir as dimensões da janela via setBounds
            Definir o status modal como false
            Acrescentar o componente JTextArea na janela

5.    Definir a Thread de preenchimento assíncrono, com o nome ThreadClient, de
acordo com as características apresentadas a seguir :

        Adicionar o atributo entrada, do tipo ObjectInputStream, e textArea, do tipo
        JTextArea, que devem ser preenchidos via construtor da Thread.
        Alterar o método run, implementando um loop de leitura contínua.
        Receber os dados enviados pelo servidor via método readObject.
        Para objetos do tipo String, apenas adicionar ao JTextArea.
        Para objetos do tipo List, acrescentar o nome e a quantidade de cada produto ao
        JTextArea.

Observação! É necessário utilizar invokeLater nos acessos aos componentes do tipo
Swing.

6.    Com o projeto CadastroServer em execução, iniciar o sistema do cliente, e testar
todas as funcionalidades oferecidas.
n - 5.png (Moderate)

✅ Resultados esperados

1. É importante que o código seja organizado.

2. Outro ponto importante é explorar as funcionalidades oferecidas pelo NetBeans para
melhoria da produtividade.

3. Nesse exercício, é esperado que o estudante demonstre as habilidades básicas no
uso de elementos assíncronos em ambientes cliente e servidor.

📝 Relatório discente de acompanhamento

Os Relatórios de Práticas deverão ser confeccionados em arquivo no formato PDF, com
a Logo da Universidade, nome do Campus, nome do Curso, nome da Disciplina, número
da Turma, semestre letivo, nome dos integrantes da Prática. Além disso, o projeto deve
ser armazenado em um repositório no GIT e o respectivo endereço deve constar na
documentação. A documentação do projeto deve conter:

    Título da Prática;
    Objetivo da Prática;
    Todos os códigos solicitados neste roteiro de aula;
    Os resultados da execução dos códigos também devem ser apresentados;
    Análise e Conclusão:

        Como as Threads podem ser utilizadas para o tratamento assíncrono das
        respostas enviadas pelo servidor?
        Para que serve o método invokeLater, da classe SwingUtilities?
        Como os objetos são enviados e recebidos pelo Socket Java?
        Compare a utilização de comportamento assíncrono ou síncrono nos clientes com
        Socket Java, ressaltando as características relacionadas ao bloqueio do
        processamento.

