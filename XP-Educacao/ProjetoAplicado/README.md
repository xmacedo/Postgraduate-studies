# Projeto Aplicado - XPE Educação

## Migração para novo executor de regras e políticas

> Por: Felipe Xavier de Macedo Silva, 
> Orientador(a): Reinaldo Galvão

### API
> Servidor rodando e executando na porta 8081

- Validar Transação
```
curl --location 'http://localhost:8081/validaTransacao' \
--header 'Content-Type: application/json' \
--data '{
    "valorTransacao": 100000,
    "paisOrigem": "Nigeria"
}'
```



### Swagger Documentation
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`