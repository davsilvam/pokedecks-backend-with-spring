# Changelog - Funcionalidades CRUD Implementadas

## Resumo das Mudanças

Este documento descreve as funcionalidades de **criação, edição e deleção** implementadas para **Series**, **Sets (Coleções)** e **Cards (Cartas)**.

---

## 1. Séries (Series)

### Endpoints Implementados

#### ✅ POST `/api/series`
- **Descrição**: Cria uma nova série
- **Permissão**: Requer `ADMIN`
- **Corpo da requisição**:
```json
{
  "id": "sv",
  "name": "Scarlet & Violet",
  "logoUrl": "https://example.com/sv-logo.png"
}
```
- **Resposta**: 201 CREATED com os dados da série criada

#### ✅ PUT `/api/series/{id}`
- **Descrição**: Atualiza uma série existente
- **Permissão**: Requer `ADMIN`
- **Corpo da requisição** (todos os campos opcionais):
```json
{
  "name": "Scarlet & Violet Series",
  "logoUrl": "https://example.com/new-logo.png"
}
```
- **Resposta**: 200 OK com os dados atualizados

#### ✅ DELETE `/api/series/{id}` (já existia)
- **Descrição**: Deleta uma série
- **Permissão**: Requer `ADMIN`
- **Resposta**: 204 NO CONTENT

#### ✅ GET `/api/series/{id}` (já existia)
- **Descrição**: Obtém detalhes de uma série

#### ✅ GET `/api/series` (já existia)
- **Descrição**: Lista todas as séries

---

## 2. Coleções (Sets)

### Endpoints Implementados

#### ✅ POST `/api/sets`
- **Descrição**: Cria uma nova coleção
- **Permissão**: Requer `ADMIN`
- **Corpo da requisição**:
```json
{
  "id": "sv01",
  "name": "Base Set",
  "logoUrl": "https://example.com/set-logo.png",
  "releaseDate": "2023-03-31T00:00:00",
  "serieId": "sv"
}
```
- **Resposta**: 201 CREATED com os dados da coleção criada

#### ✅ PUT `/api/sets/{id}`
- **Descrição**: Atualiza uma coleção existente
- **Permissão**: Requer `ADMIN`
- **Corpo da requisição** (todos os campos opcionais):
```json
{
  "name": "Base Set - Updated",
  "logoUrl": "https://example.com/new-logo.png",
  "releaseDate": "2023-04-01T00:00:00",
  "serieId": "sv"
}
```
- **Resposta**: 200 OK com os dados atualizados

#### ✅ DELETE `/api/sets/{id}` (já existia)
- **Descrição**: Deleta uma coleção
- **Permissão**: Requer `ADMIN`
- **Resposta**: 204 NO CONTENT

#### ✅ GET `/api/sets/{id}` (já existia)
- **Descrição**: Obtém detalhes de uma coleção

#### ✅ GET `/api/sets` (já existia)
- **Descrição**: Lista todas as coleções

---

## 3. Cartas (Cards)

### Endpoints Implementados

#### ✅ POST `/api/cards`
- **Descrição**: Cria uma nova carta
- **Permissão**: Requer `ADMIN`
- **Corpo da requisição**:
```json
{
  "id": "sv01-001",
  "localId": 1,
  "name": "Pikachu",
  "imageUrl": "https://example.com/pikachu.png",
  "illustrator": "Ken Sugimori",
  "rarity": "Common",
  "price": 2.50,
  "stockQuantity": 100,
  "category": "POKEMON",
  "setId": "sv01"
}
```
- **Resposta**: 201 CREATED com os dados da carta criada

#### ✅ PUT `/api/cards/{id}`
- **Descrição**: Atualiza uma carta existente
- **Permissão**: Requer `ADMIN`
- **Corpo da requisição** (todos os campos opcionais):
```json
{
  "name": "Pikachu EX",
  "price": 5.00,
  "stockQuantity": 50,
  "rarity": "Rare"
}
```
- **Resposta**: 200 OK com os dados atualizados

#### ✅ DELETE `/api/cards/{id}` (já existia)
- **Descrição**: Deleta uma carta
- **Permissão**: Requer `ADMIN`
- **Resposta**: 204 NO CONTENT

#### ✅ GET `/api/cards/{id}` (já existia)
- **Descrição**: Obtém detalhes completos de uma carta

#### ✅ GET `/api/cards` (já existia)
- **Descrição**: Lista todas as cartas (resumo)

#### ✅ GET `/api/cards/search?name={name}` (já existia)
- **Descrição**: Busca cartas por nome

---

## 4. DTOs Criados

### Séries
- `CreateSerieRequestDTO.java` - Para criação de séries
- `UpdateSerieRequestDTO.java` - Para atualização de séries

### Coleções
- `CreateSetRequestDTO.java` - Para criação de coleções
- `UpdateSetRequestDTO.java` - Para atualização de coleções

### Cartas
- `CreateCardRequestDTO.java` - Para criação de cartas
- `UpdateCardRequestDTO.java` - Para atualização de cartas

---

## 5. Validações Implementadas

Todos os DTOs de criação incluem validações Bean Validation:
- `@NotBlank` para campos obrigatórios de texto
- `@NotNull` para campos obrigatórios
- `@PositiveOrZero` para campos numéricos que devem ser >= 0

---

## 6. Tratamento de Erros

### Erros Gerenciados
- **404 Not Found**: Quando um recurso não é encontrado
- **409 Conflict**: Quando tenta criar um recurso com ID duplicado
- **400 Bad Request**: Quando os dados da requisição são inválidos

---

## 7. Segurança

Todos os endpoints de criação, edição e deleção requerem:
- Autenticação (JWT token)
- Autorização com role `ADMIN`

Endpoints de leitura (GET) são públicos.

---

## 8. Correções Adicionais

### Warnings do Lombok Corrigidos
Adicionada anotação `@Builder.Default` nas listas de relacionamentos 1:N nos modelos:
- `Order.orderItems`
- `User.orders`
- `Set.cards`
- `Card.orderItems`

### Dependência Adicionada
- `spring-boot-starter-validation` para suporte a validações

---

## 9. Como Testar

### 1. Iniciar o banco de dados
```bash
docker-compose up -d
```

### 2. Iniciar a aplicação
```bash
./mvnw spring-boot:run
```

### 3. Acessar Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### 4. Autenticar-se
- Criar ou fazer login com usuário ADMIN
- Copiar o token JWT
- Clicar em "Authorize" no Swagger e colar o token

### 5. Testar os endpoints
Use o Swagger UI para testar todos os endpoints implementados.

---

## 10. Exemplo de Fluxo Completo

```bash
# 1. Criar uma série
POST /api/series
{
  "id": "sv",
  "name": "Scarlet & Violet",
  "logoUrl": "https://example.com/sv.png"
}

# 2. Criar uma coleção nessa série
POST /api/sets
{
  "id": "sv01",
  "name": "Base Set",
  "logoUrl": "https://example.com/sv01.png",
  "releaseDate": "2023-03-31T00:00:00",
  "serieId": "sv"
}

# 3. Criar uma carta nessa coleção
POST /api/cards
{
  "id": "sv01-001",
  "localId": 1,
  "name": "Pikachu",
  "imageUrl": "https://example.com/pikachu.png",
  "illustrator": "Ken Sugimori",
  "rarity": "Common",
  "price": 2.50,
  "stockQuantity": 100,
  "category": "POKEMON",
  "setId": "sv01"
}

# 4. Atualizar a carta
PUT /api/cards/sv01-001
{
  "price": 3.00,
  "stockQuantity": 75
}

# 5. Deletar a carta
DELETE /api/cards/sv01-001
```

---

## 11. Correção de Bugs: Deleção em Cascata

### 🐛 Problema 1: Deleção de Cartas
Ao tentar deletar uma carta, ocorria erro de violação de constraint de foreign key:
```
ERROR: update or delete on table "cards" violates foreign key constraint "fk_pokemon_on_card" on table "pokemons"
Detail: Key (id)=(base1-1) is still referenced from table "pokemons".
```

**Causa**: As tabelas `pokemons`, `energies` e `trainers` têm relacionamento 1:1 com `cards`, mas a deleção não estava sendo propagada corretamente.

**Solução**: Modificado o método `deleteCardById()` no `CardService` para deletar explicitamente as entidades relacionadas antes de deletar a carta.

### 🐛 Problema 2: Deleção de Coleções (Sets)
Ao deletar um Set, poderia ocorrer erro similar com as cartas relacionadas.

**Solução**: Modificado o método `deleteSetById()` no `SetService` para:
1. Buscar todas as cartas do set
2. Para cada carta, deletar a entidade relacionada (Pokemon/Energy/Trainer)
3. Deletar a carta
4. Finalmente deletar o set

### 🐛 Problema 3: Deleção de Séries
Ao deletar uma Serie, poderia ocorrer erro com os sets relacionados.

**Solução**: Modificado o método `deleteSerieById()` no `SerieService` para:
1. Buscar todos os sets da série
2. Deletar cada set (que por sua vez deleta as cartas)
3. Finalmente deletar a série

### ⚙️ Melhorias no Modelo
- Adicionado `orphanRemoval = true` no relacionamento `Set.cards` para melhorar a deleção em cascata

### ✅ Resultado
- ✅ Deleção de cartas funciona corretamente
- ✅ Deleção de sets funciona corretamente (deleta todas as cartas)
- ✅ Deleção de séries funciona corretamente (deleta todos os sets e cartas)
- ✅ Nenhuma violação de foreign key constraint

---

## Status Final

✅ **Todas as funcionalidades CRUD implementadas com sucesso!**
✅ **Warnings do Lombok corrigidos**
✅ **Bug de deleção de cartas corrigido**
✅ **Projeto compila sem erros**
✅ **Documentação Swagger atualizada**
