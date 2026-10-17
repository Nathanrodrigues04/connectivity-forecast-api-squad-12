# Connectivity Forecast API

Reimplementação Java/Spring Boot da [API de referência](https://github.com/LiniiS/connectivity-forecast-api), usada como artefato educacional de APS II.

## Executar

Requisitos: Java 17 e Maven 3.9+.

```bash
mvn test
mvn spring-boot:run
```

Swagger UI: `http://localhost:8080/swagger-ui.html`  
OpenAPI: `http://localhost:8080/v3/api-docs`

## Escopo

A Connectivity Forecast API é uma aplicação educacional desenvolvida em Java 17 e Spring Boot, destinada ao estudo de APIs REST e à integração com aplicações de monitoramento da qualidade da internet.

A API permite consultar modelos de previsão, pontos de monitoramento e estimativas relacionadas à latência e à perda de pacotes. Também oferece recursos de comparação de modelos e recomendações sobre atividades que dependem de uma conexão estável.

### Dados disponíveis

O projeto utiliza dados locais de exemplo, organizados em arquivos JSON e CSV, contendo:

- 4 modelos ativos de previsão;
- 10 pontos de monitoramento;
- 24 instantes de previsão;
- 960 registros de previsão.

### Limitações

**A API não realiza medições reais nem previsões calculadas dinamicamente.** Os resultados são obtidos de dados fictícios previamente cadastrados.

Além disso:

- Não consulta o RIPE Atlas em tempo real;
- Não treina nem executa modelos de aprendizado de máquina;
- Não utiliza banco de dados;
- Não mede diretamente a velocidade de download ou upload;
- As classificações e recomendações são baseadas em regras experimentais, sem validação como padrões científicos.

A API pode servir como backend de demonstração para o NetStatus, mas o monitoramento real da conexão deverá ser implementado separadamente.

## Estrutura

Separação em controllers, services, repositories, modelos de domínio/DTOs e configuração. API versionada em `/api/v1`, com recursos de health, modelos, localizações, previsões e atividades.

## Documentação

README e OpenAPI são pontos de partida. Completar em exercício: descrição dos endpoints, parâmetros e validações, exemplos de requisição/resposta, códigos de erro e origem dos campos. Referência à licença MIT da API de origem preservada neste projeto.
