🌦️ ClimaApp — Aplicativo Android de Previsão do Tempo

Aplicativo Android nativo desenvolvido em Java, que consulta uma API de clima e apresenta a previsão para vários dias. O usuário informa a cidade e recebe informações como temperatura mínima, máxima, descrição do clima e umidade.

📱 Funcionalidades

✔ Entrada da cidade (Ex.: Passos,MG,BR)
✔ Consulta de previsão do tempo para 5 dias
✔ Consumo de API REST usando método GET
✔ Exibição clara das informações meteorológicas:

Temperatura mínima e máxima

Descrição do clima

Umidade

Ícone representativo
✔ Floating Action Button (FAB) para iniciar a busca
✔ Tratamento de erros:

Falha de conexão

Cidade inválida

URL incorreta

Problemas ao ler os dados
✔ Layout adaptável e responsivo
✔ Exibição organizada em lista utilizando ArrayAdapter

🛠️ Tecnologias

Android Studio

Java

ListView + ArrayAdapter

AsyncTask para requisições

API REST em JSON

Como funciona

O usuário informa a cidade, o app monta a URL com os parâmetros:

city
days
APPID


E faz a requisição para a API:

http://agent-weathermap-env-env.eba-6pzgqekp.us-east-2.elasticbeanstalk.com/api/weather


Depois, o JSON recebido é convertido em objetos Weather e exibido na tela.

Como executar

Abra o projeto no Android Studio

Conecte um celular ou use o emulador

Execute o app

Digite cidade,UF,PAÍS e pressione o botão de busca