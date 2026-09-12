# 🚗 CampusRide API

O **CampusRide** é uma API REST desenvolvida em Java com Spring Boot, projetada para gerenciar caronas universitárias conectando motoristas e passageiros. 

O principal foco deste projeto é a aplicação rigorosa de **regras de negócio**, tratamento de exceções personalizado, validações customizadas e proteção de integridade de dados (Soft Delete), garantindo uma aplicação robusta e pronta para cenários reais.

---

## Tecnologias Utilizadas

* **Linguagem:** Java (JDK 17+)
* **Framework:** Spring Boot (Web, Data JPA, Validation)
* **Banco de Dados:** H2 Database (In-Memory) para facilitar a execução e os testes locais
* **Gerenciamento de Dependências:** Maven
* **Testes de API:** Insomnia

---

## Arquitetura e Regras de Negócio Implementadas

O sistema foi desenhado seguindo o padrão de arquitetura em camadas (`Controller`, `Service`, `Repository`), utilizando o padrão **DTO (Data Transfer Object)** para blindar as entidades do banco de dados e controlar o tráfego de informações.

### Regras Críticas
1. **Validações Customizadas:** Criação de anotações próprias (como `@ValidaVagasVeiculo`) para garantir que limites lógicos da vida real sejam respeitados na API (Exemplo: Uma carona de `MOTO` não pode ser cadastrada com mais de 1 vaga disponível).
2. **Controle de Lotação:** O sistema deduz automaticamente as vagas a cada reserva. Se as vagas chegarem a zero, o status da carona muda automaticamente para `LOTADA`.
3. **Bloqueio de Segurança:** Não é possível reservar vagas em caronas que não estejam com a situação `ABERTA`. O sistema prioriza a verificação de vagas, retornando um erro `422` caso não existam mais lugares.
4. **Devolução Inteligente de Vagas:** Ao cancelar uma reserva, a vaga é automaticamente devolvida à carona. Se a carona estava `LOTADA`, ela reabre automaticamente (voltando ao status `ABERTA`).
5. **Efeito Cascata (Soft Delete):** O cancelamento de uma carona reflete automaticamente no status de todas as reservas associadas a ela. Nenhuma entidade é apagada do banco (`DELETE`), mantendo o histórico de auditoria intacto através de mudança de status (`CANCELADA`).

---

## Endpoints da API

Abaixo estão as rotas disponíveis no sistema. Para uma visualização completa, importe a coleção do Insomnia localizada na pasta `docs/`.

### Caronas
| Método | Rota | Descrição | Status de Sucesso |
| :--- | :--- | :--- | :--- |
| **POST** | `/caronas` | Cadastra uma nova carona no sistema | `201 Created` |
| **GET** | `/caronas` | Retorna o histórico de todas as caronas registradas | `200 OK` |
| **GET** | `/caronas/disponiveis` | Retorna estritamente as caronas com situação `ABERTA` | `200 OK` |
| **GET** | `/caronas/{id}` | Busca os detalhes de uma carona e a lista de seus passageiros | `200 OK` |
| **PATCH** | `/caronas/{id}/cancelar` | Cancela a carona inteira (e suas reservas em cascata) | `200 OK` |

### Reservas
| Método | Rota | Descrição | Status de Sucesso |
| :--- | :--- | :--- | :--- |
| **POST** | `/reservas` | Reserva uma vaga na carona (se disponível) | `201 Created` |
| **PATCH** | `/reservas/{id}/cancelar` | Cancela a reserva e devolve a vaga para a carona | `200 OK` |
## Como Executar o Projeto

1. Clone este repositório em sua máquina local.
2. Abra a pasta do projeto em sua IDE de preferência.
3. Aguarde o Maven baixar todas as dependências.
4. Execute a classe principal `CampusrideApplication.java`.
5. A aplicação estará rodando na porta `8080` (`http://localhost:8080`).

---

## Como Testar a API (Insomnia)

Para facilitar a validação do projeto, todos os testes já estão disponíveis:

1. Navegue até a pasta `docs/` na raiz deste projeto.
2. Baixe o arquivo `insomnia_collection.yaml`.
3. Abra o seu Insomnia, clique em **Import** e selecione este arquivo.
4. Você terá acesso a todas as requisições (com JSONs de exemplo para primeiros Posts com a estrutura ja feita) prontas para disparo.
