# 🍽️ Guia Completo - Sistema de Refeições

## 📊 Estrutura do Sistema

### **3 Entidades Principais:**

1. **Food (Alimento)** - Base de dados TACO
   - Valores nutricionais por 100g
   - Macros e Micros completos

2. **Meal (Refeição)** - Ex: Café da Manhã, Almoço
   - Contém múltiplos alimentos
   - Totais calculados automaticamente

3. **MealFood (Alimento na Refeição)** - Relacionamento
   - Quantidade específica
   - Valores calculados proporcionalmente

---

## 🔑 Endpoints Principais

### 📌 ALIMENTOS (Foods)

#### Buscar todos
```
GET /foods
```

#### Buscar por nome
```
GET /foods/search?name=arroz
```

#### Buscar por categoria
```
GET /foods/category/Cereais
```

#### Buscar alimento específico
```
GET /foods/{id}
```

#### Criar alimento
```
POST /foods
Body:
{
  "name": "Arroz branco cozido",
  "category": "Cereais",
  "calories": 130,
  "protein": 2.5,
  "carbohydrate": 28.1,
  "fiber": 0.2,
  "totalFat": 0.2,
  "calcium": 1.0,
  "iron": 0.1,
  "sodium": 1.0,
  "source": "TACO"
}
```

---

### 🍴 REFEIÇÕES (Meals)

#### Criar refeição completa
```
POST /meals
Headers: Authorization: Bearer {token}
Body:
{
  "name": "Café da Manhã",
  "type": "BREAKFAST",
  "date": "2024-11-21",
  "notes": "Refeição pré-treino",
  "foods": [
    {
      "foodId": 1,
      "quantity": 200,
      "notes": "Cozido"
    },
    {
      "foodId": 5,
      "quantity": 150,
      "notes": "Grelhado"
    }
  ]
}
```

**Resposta:**
```json
{
  "id": 1,
  "name": "Café da Manhã",
  "type": "BREAKFAST",
  "date": "2024-11-21",
  "foods": [
    {
      "id": 1,
      "food": { "name": "Arroz", ... },
      "quantity": 200,
      "calculatedCalories": 260,
      "calculatedProtein": 5.0,
      ...
    }
  ],
  "totalCalories": 450,
  "totalProtein": 25.5,
  "totalCarbohydrate": 50.2,
  "totalFat": 8.5
}
```

---

#### Buscar todas as refeições
```
GET /meals
```

#### Buscar refeições de hoje
```
GET /meals/today
```

#### Buscar refeições de uma data
```
GET /meals/date/2024-11-21
```

#### Buscar refeições entre datas
```
GET /meals/range?start=2024-11-01&end=2024-11-30
```

#### Buscar refeição específica
```
GET /meals/{id}
```

---

#### Adicionar alimento à refeição existente
```
POST /meals/{mealId}/foods
Body:
{
  "foodId": 3,
  "quantity": 100,
  "notes": "Com azeite"
}
```

#### Atualizar quantidade de alimento
```
PATCH /meals/{mealId}/foods/{foodId}
Body:
{
  "quantity": 250
}
```

#### Remover alimento da refeição
```
DELETE /meals/{mealId}/foods/{foodId}
```

#### Atualizar refeição
```
PUT /meals/{id}
Body:
{
  "name": "Almoço Atualizado",
  "notes": "Nova observação"
}
```

#### Deletar refeição
```
DELETE /meals/{id}
```

---

## 🎯 Tipos de Refeição (MealType)

- `BREAKFAST` - Café da Manhã
- `MORNING_SNACK` - Lanche da Manhã
- `LUNCH` - Almoço
- `AFTERNOON_SNACK` - Lanche da Tarde
- `DINNER` - Jantar
- `EVENING_SNACK` - Ceia
- `OTHER` - Outro

---

## 💡 Como os Cálculos Funcionam

### 1️⃣ Alimento na Base (Food)
```json
{
  "name": "Arroz branco cozido",
  "calories": 130,    // por 100g
  "protein": 2.5,     // por 100g
  "carbohydrate": 28.1 // por 100g
}
```

### 2️⃣ Alimento na Refeição (MealFood)
```json
{
  "foodId": 1,
  "quantity": 200,    // 200g de arroz
  
  // Sistema calcula automaticamente:
  "calculatedCalories": 260,    // (130 × 200) / 100
  "calculatedProtein": 5.0,     // (2.5 × 200) / 100
  "calculatedCarbohydrate": 56.2 // (28.1 × 200) / 100
}
```

### 3️⃣ Totais da Refeição (Meal)
```
totalCalories = soma de todos calculatedCalories
totalProtein = soma de todos calculatedProtein
... e assim por diante
```

---

## 📋 Fluxo Completo de Uso

### **Cenário: Usuário registra o almoço**

#### 1. Buscar alimentos disponíveis
```bash
GET /foods/search?name=frango
```

**Resposta:**
```json
[
  {
    "id": 10,
    "name": "Peito de frango grelhado",
    "calories": 165,
    "protein": 31,
    ...
  }
]
```

---

#### 2. Criar refeição com alimentos
```bash
POST /meals
{
  "name": "Almoço",
  "type": "LUNCH",
  "date": "2024-11-21",
  "foods": [
    { "foodId": 10, "quantity": 150 },  // 150g de frango
    { "foodId": 1, "quantity": 200 },   // 200g de arroz
    { "foodId": 15, "quantity": 100 }   // 100g de feijão
  ]
}
```

---

#### 3. Sistema calcula automaticamente
```json
{
  "id": 5,
  "name": "Almoço",
  "type": "LUNCH",
  "foods": [
    {
      "food": { "name": "Peito de frango" },
      "quantity": 150,
      "calculatedCalories": 247.5,
      "calculatedProtein": 46.5
    },
    {
      "food": { "name": "Arroz" },
      "quantity": 200,
      "calculatedCalories": 260,
      "calculatedProtein": 5.0
    },
    {
      "food": { "name": "Feijão" },
      "quantity": 100,
      "calculatedCalories": 77,
      "calculatedProtein": 4.5
    }
  ],
  "totalCalories": 584.5,    // Soma automática
  "totalProtein": 56.0,       // Soma automática
  "totalCarbohydrate": 70.5,  // Soma automática
  "totalFat": 5.2             // Soma automática
}
```

---

## 🔄 Casos de Uso Avançados

### Atualizar quantidade após pesar comida
```bash
PATCH /meals/5/foods/2
{
  "quantity": 180  // Pesou de novo, eram 180g
}
```
**Sistema recalcula tudo automaticamente!**

---

### Adicionar alimento esquecido
```bash
POST /meals/5/foods
{
  "foodId": 20,     // Salada
  "quantity": 80,
  "notes": "Com azeite"
}
```

---

### Ver progresso do dia
```bash
GET /meals/today
```

**Resposta mostra:**
- Todas as refeições do dia
- Total de calorias consumidas
- Total de macros
- Comparação com meta do perfil (próxima feature!)

---

## 📊 Estrutura de Dados Completa

### Alimento (Food) - 100g
```
Macronutrientes:
- Calorias (kcal)
- Proteína (g)
- Carboidrato (g)
- Fibra (g)
- Gordura Total (g)
- Gordura Saturada (g)
- Gordura Trans (g)
- Gordura Monoinsaturada (g)
- Gordura Poli-insaturada (g)

Micronutrientes:
- Cálcio (mg)
- Ferro (mg)
- Sódio (mg)
- Magnésio (mg)
- Fósforo (mg)
- Potássio (mg)
- Zinco (mg)
- Vitamina C (mg)
- Vitamina B6 (mg)
- Vitamina B12 (mcg)
- Vitamina A (mcg)
- Vitamina D (mcg)
- Vitamina E (mg)
- Colesterol (mg)
```

---

## 🎨 Exemplo Real Completo

### Café da Manhã de um usuário

```bash
POST /meals
{
  "name": "Café da Manhã Pré-Treino",
  "type": "BREAKFAST",
  "date": "2024-11-21",
  "notes": "7h da manhã",
  "foods": [
    {
      "foodId": 30,    // Pão integral
      "quantity": 50,
      "notes": "2 fatias"
    },
    {
      "foodId": 45,    // Ovo
      "quantity": 100,
      "notes": "2 ovos mexidos"
    },
    {
      "foodId": 50,    // Banana
      "quantity": 120,
      "notes": "1 banana média"
    },
    {
      "foodId": 55,    // Aveia
      "quantity": 30
    }
  ]
}
```

**Sistema retorna:**
```json
{
  "id": 8,
  "name": "Café da Manhã Pré-Treino",
  "totalCalories": 485,
  "totalProtein": 22.5,
  "totalCarbohydrate": 65.3,
  "totalFat": 12.8,
  "totalFiber": 8.5,
  "foods": [...detalhes de cada alimento...]
}
```

---

## 🚀 Próximos Passos

Com essa estrutura, você pode adicionar:

1. **Dashboard Diário** - Endpoint que retorna:
   - Todas refeições do dia
   - Total consumido
   - Meta do perfil
   - % de progresso

2. **Histórico Semanal/Mensal** - Gráficos de consumo

3. **Receitas** - Combinar vários alimentos em uma "receita"

4. **Favoritos** - Salvar combinações frequentes

5. **Copiar refeição** - Repetir refeição de outro dia

---

## ⚠️ Importante

- Todos os endpoints de `/meals` precisam de autenticação JWT
- Endpoints de `/foods` podem ser públicos (leitura)
- Usuário só acessa suas próprias refeições
- Cálculos são automáticos - nunca edite manualmente!

---

## 💾 Script SQL para Popular Tabela TACO (exemplo)

```sql
INSERT INTO foods (name, category, calories, protein, carbohydrate, fiber, total_fat, calcium, iron, sodium, source, verified) VALUES
('Arroz branco cozido', 'Cereais', 130, 2.5, 28.1, 0.2, 0.2, 1, 0.1, 1, 'TACO', true),
('Feijão carioca cozido', 'Leguminosas', 77, 4.8, 13.6, 8.5, 0.5, 27, 1.3, 2, 'TACO', true),
('Peito de frango grelhado', 'Carnes', 165, 31, 0, 0, 3.6, 5, 0.4, 63, 'TACO', true),
('Banana prata', 'Frutas', 98, 1.3, 26, 2, 0.1, 8, 0.4, 1, 'TACO', true);
```

Quer que eu crie mais alguma funcionalidade específica? 🚀