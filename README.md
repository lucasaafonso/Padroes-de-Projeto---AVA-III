# Gestão de Documentos — Padrões de Projeto ✅

# AVA III
Alunos: Henrique Cardoso(20231160037), Lis Maria(20232160024) e Lucas Afonso(20201160030)

Este projeto demonstra a aplicação de vários padrões de projeto em Java, com foco em: **Strategy**, **Factory Method**, **Command** e **Observer**. Abaixo está uma descrição concisa de cada padrão e os participantes (classes) presentes no código-fonte.

---

## Strategy (Autenticação) 🔧

- Propósito: Encapsular algorítmos de geração de número/autenticação e permitir trocá-los em tempo de execução.

- Participantes no projeto:
  - Context: `Autenticador` (em `br.ifba.edu.inf011.model.Autenticador`) — contém uma referência a `AutenticadorStrategy` e delega a geração do número ao strategy.
  - Strategy (interface): `AutenticadorStrategy` (`br.ifba.edu.inf011.strategy.AutenticadorStrategy`).
  - Concrete Strategies:
    - `AutenticadorPadraoStrategy` (`br.ifba.edu.inf011.strategy`) — estratégia padrão.
    - `AutenticadorPessoalStrategy` — estratégia pessoal.
    - `AutenticadorPrivacidadeStrategy` — estratégia para privacidade.
    - `AutenticadorCriminalStrategy` — estratégia criminal.
  - Cliente que configura o strategy: `CriarDocumentoCommand` recebe o `AutenticadorStrategy` e chama `autenticador.setAutenticadorStrategy(...)` antes de `autenticar`.

- Onde é usado: Ao criar um documento (`CriarDocumentoCommand`), é possível escolher a estratégia de autenticação (via `AutenticadorFactory` ou pela UI) e o `Autenticador` aplica a estratégia para gerar o número do documento.

---

## Factory Method (Criação de Atenticador) 🏭

- Propósito: Encapsular a criação de objetos (Autenticador), permitindo variar a implementação sem acoplar o cliente aos tipos concretos.

- Participantes no projeto:
  - Factory (interface): `AutenticadorFactory` (`br.ifba.edu.inf011.factory.AutenticadorFactory`)
  - Concrete Factories (creators):
    - `AutenticadorPadraoStrategy` (`br.ifba.edu.inf011.strategy`) — estratégia padrão.
    - `AutenticadorPessoalStrategy` — estratégia pessoal.
    - `AutenticadorPrivacidadeStrategy` — estratégia para privacidade.
    - `AutenticadorCriminalStrategy` — estratégia criminal.
  - Cliente: `CriarDocumentoCommand` (usa a factory para obter `Documento` e `Operador`) e `GerenciadorDocumentoModel` (recebe a factory e a repassa aos comandos).

- Onde é usado: A UI / `GerenciadorDocumentoModel` passa uma factory concreta ao modelo; o comando `CriarDocumentoCommand` usa a factory para instanciar o documento e o operador corretos.

---

## Command (Ações, Undo/Redo, Macro) ⛏️

- Propósito: Encapsular solicitações como objetos, permitindo enfileirar, fazer undo/redo e compor macros.

- Participantes no projeto:
  - Command (interface): `Command` (`br.ifba.edu.inf011.command.Command`).
  - Concrete Commands:
    - `CriarDocumentoCommand` — cria um documento (usa factory + autenticador).
    - `SalvarDocumentoCommand` — salva conteúdo em um `Documento`.
    - `AssinarDocumentoCommand` — assina um documento (usa `GestorDocumento` e `Operador`).
    - `ProtegerDocumentoCommand` — protege um documento.
    - `TornarUrgenteCommand` — marca documento como urgente.
    - `MacroCommand` — compõe vários comandos em um só (ações rápidas).
  - Invoker: `CommandManager` — executa comandos, mantém pilhas de undo/redo e registra operações no `DocumentoLogger`.
  - Receiver(s): `GestorDocumento` (opera sobre `Documento`) e o próprio `Documento` quando aplicável.
  - Client: `GerenciadorDocumentoModel` cria/empacota comandos e pede ao `CommandManager` para executá-los.

- Onde é usado: Todas as ações do usuário (criar, salvar, assinar, proteger, tornar urgente, macros) são implementadas como comandos; `CommandManager` fornece suporte a desfazer/refazer e consolidação do histórico.

---

## Observer (Logging) 👀

- Propósito: Permitir que múltiplos observadores sejam notificados quando ocorrem eventos (ex.: operações sobre documentos), desacoplando quem gera eventos de quem os consome.

- Participantes no projeto:
  - Subject (singleton): `DocumentoLogger` (`br.ifba.edu.inf011.model.DocumentoLogger`) — mantém lista de `LogObserver` e notifica usando `log(...)`.
  - Observer (interface): `LogObserver` (`br.ifba.edu.inf011.observer.LogObserver`).
  - Concrete Observer: `FileLogObserver` — escreve logs em `log_operacoes.txt`.
  - Emitentes: `CommandManager` (chama `DocumentoLogger.log(...)` após executar/undo/redo/consolidar) e comandos em geral também chamam `DocumentoLogger.log(...)`.
  - Registro de observadores: em `AppAvaliacaoIII.run(...)` (ex.: `DocumentoLogger.getInstance().adicionarObservador(new FileLogObserver());`).

---

## Mapa rápido de localizações (packages)

- Strategy: `src/br/ifba/edu/inf011/strategy`
- Factory (DocumentOperatorFactory): `src/br/ifba/edu/inf011/factory`
- Command: `src/br/ifba/edu/inf011/command`
- Observer: `src/br/ifba/edu/inf011/observer`
- Modelo/Coordenação: `src/br/ifba/edu/inf011/model` (ex.: `GerenciadorDocumentoModel`, `DocumentoLogger`, `Autenticador`, `GestorDocumento`)
- UI de exemplo: `src/br/ifba/edu/inf011/ui`

---

## Como executar 🏃

- Importar o projeto em sua IDE Java (ou compilar com `javac`) e executar a classe `AppAvaliacaoIII`.
- Logs de operações são gravados em `log_operacoes.txt` (via `FileLogObserver`).
