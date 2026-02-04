# Sistema de contador de estacionameto construido em Java☕️
[![Java 17](https://img.shields.io/badge/Java-17-red?style=flat&logo=openjdk&logoColor=white)](https://adoptium.net/temurin/releases/?version=17)
![Maven](https://img.shields.io/badge/Maven-3.9.11-0090CD?style=flat&logo=apachemaven&logoColor=white)

## 🎯Objetivo
Este projeto tem como objetivo simular um sistema de controle de estacionamento, sendo capaz de:
- Identificar a entrada do usuário
- Realizar o cadastro automático
- Calcular o valor final (tempo + taxa)
- Efetuar o pagamento e liberar a saída do veículo

Cada novo usuário recebe automaticamente um ID e um código único, utilizados durante todo o ciclo de permanência no estacionamento. 

## ⚙️Logica do sistema
Para evitar conflitos na contagem de tempo e permitir múltiplos usuários simultâneos, foi implementado um modelo onde:
- Cada novo cadastro cria uma Thread independente
- Os dados são mapeados usando HashMap
- Relacionamentos direto:
    - Cadastro <-> Thread
    - Cadastro <-> Tempo
    - Cadastro <-> Valor
    - 
Esse modelo permite que o sistema não tenha limite fixo de usuários ativos.

Ao finalizar o cadastro (pagamento + saída), os dados são removidos da memória, permitindo que o mesmo código possa ser reutilizado futuramente, sem comprometer a integridade do sistema.

## Sistema de Pagamento
Foi criado um modelo de pagamento para uso prático dentro do sistema:
- O pagamento é realizado através do código do usuário
- Após a confirmação:
  - A Thread é finalizada
  - A saída é liberada
  - Cadastro removido da memoria

## 🧾 Sistema de Cadastro
Cada usuário recebe:
- ID
- Código de acesso
Os dados são armazenados em memória utilizando HashMap
Garante:
- Persistência temporária
- Relacionamento seguro entre dados
- Controle correto das Threads
- 
⚠️ Banco de dados não foi utilizado neste projeto, pois o foco está na lógica, concorrência e arquitetura.⚠️ 

## Interface Swing
A interface gráfica foi desenvolvida com Swing, focada exclusivamente em:
- Controle das Threads com:
   - Inicialização e finalização da contagem
   - Criação e limpeza do sistema de cadastro
     
A UI foi mantida simples para priorizar a lógica do sistema.
  
## 🛠️Tecnologias:
- JavaSE 17
- Swing
- Maven para build
- bibliotecas JDk-21 - JUnit 5(para testes)
- ZXing (geração de QR Code)

## 📌Foco do Projeto
- Desenvolvimento Java
- qualificação e aprendizado na arquitetura e desenvolvimento de projeto

## Autor
Edson Salles
Projeto pessoal para estudo e pratica de:
- Threads
- Padrão Observer
- Encapsulamento
- Controle de concorrência
- Arquitetura Java
📢 Siga e acompanhe meu aprendizado e minha jornada nesse mundo incrivel na area de desenvolviemento de software
- 📱https://www.instagram.com/coder_salles/

Deixe também sua Issue pois cada contribuição é bem vinda! Uma comunidade que compartilha conhecimmento tende a crescer cada vez mais✌️ 

