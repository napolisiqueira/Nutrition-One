# 📊 Guia Completo - API UserProfile

## 🎯 Funcionalidades

A API calcula **automaticamente**:
- ✅ **TMB/BMR** (Taxa Metabólica Basal) - Fórmula Mifflin-St Jeor
- ✅ **GET/TDEE** (Gasto Energético Total Diário)
- ✅ **Calorias Alvo** baseado no objetivo
- ✅ **Macronutrientes** (Proteína, Carboidrato, Gordura)
- ✅ **IMC** (Índice de Massa Corporal)
- ✅ **Massa Magra** (se informar % de gordura)

---

## 🔐 Autenticação

**TODAS as rotas de perfil precisam do token JWT!**

```
Authorization: Bearer seu_token_aqui
```

---

## 📍 Endpoints

### 1️⃣ Criar Perfil
**POST** `/profile`

```json
{
  "weight": 80.5,
  "height": 175,
  "age": 25,
  "gender": "MALE",
  "birthDate": "1998-05-15",
  "goal": "GAIN_MUSCLE",
  "activityLevel": "MODERATE",
  "bodyFatPercentage": 15.0
}
```

**Resposta:**
```json
{
  "id": 1,
  "username": "joao",
  "weight": 80.5,
  "height": 175,
  "age": 25,
  "gender": "MALE",
  "birthDate": "1998-05-15",
  "goal": "GAIN_MUSCLE",
  "activityLevel": "MODERATE",
  "bmr": 1825.63,
  "tdee": 2829.72,
  "targetCalories": 3129.72,
  "targetProtein": 177.1,
  "targetCarbs": 395.8,
  "targetFat": 104.3,
  "bmi": 26.29,
  "bodyFatPercentage": 15.0,
  "leanMass": 68.43,
  "createdAt": "2024-11-21T10:30:00",
  "updatedAt": "2024-11-21T10:30:00"
}
```

---

### 2️⃣ Buscar Perfil
**GET** `/profile`

**Resposta:** Retorna o perfil completo do usuário logado

---

### 3️⃣ Atualizar TODO o Perfil
**PUT** `/profile`

Substitui **TODOS** os dados (precisa enviar tudo de novo)

```json
{
  "weight": 82.0,
  "height": 175,
  "age": 26,
  "gender": "MALE",
  "birthDate": "1998-05-15",
  "goal": "MAINTAIN",
  "activityLevel": "VERY_ACTIVE"
}
```

---

### 4️⃣ Atualizar Campos Específicos
**PATCH** `/profile`

Atualiza **APENAS** os campos enviados. Os outros permanecem iguais.

**Exemplo 1:** Atualizar só o peso
```json
{
  "weight": 78.5
}
```

**Exemplo 2:** Atualizar peso e objetivo
```json
{
  "weight": 78.5,
  "goal": "LOSE_WEIGHT"
}
```

**Exemplo 3:** Mudar nível de atividade
```json
{
  "activityLevel": "LIGHT"
}
```

---

### 5️⃣ Atualizar Apenas o Peso
**PATCH** `/profile/weight`

```json
{
  "weight": 79.0
}
```

---

### 6️⃣ Atualizar Apenas o Objetivo
**PATCH** `/profile/goal`

```json
{
  "goal": "LOSE_WEIGHT"
}
```

---

### 7️⃣ Deletar Perfil
**DELETE** `/profile`

Remove o perfil do usuário logado.

---

## 📋 Valores Aceitos

### Gender (Gênero)
- `MALE` - Masculino
- `FEMALE` - Feminino
- `OTHER` - Outro

### Goal (Objetivo)
- `LOSE_WEIGHT` - Perder peso (déficit de 500 kcal)
- `MAINTAIN` - Manter peso (manutenção)
- `GAIN_WEIGHT` - Ganhar peso (superávit de 500 kcal)
- `GAIN_MUSCLE` - Ganhar massa muscular (superávit de 300 kcal + alta proteína)

### ActivityLevel (Nível de Atividade)
- `SEDENTARY` - Sedentário (pouco ou nenhum exercício) - x1.2
- `LIGHT` - Levemente ativo (1-3 dias/semana) - x1.375
- `MODERATE` - Moderadamente ativo (3-5 dias/semana) - x1.55
- `VERY_ACTIVE` - Muito ativo (6-7 dias/semana) - x1.725
- `EXTRA_ACTIVE` - Extremamente ativo (2x por dia, atleta) - x1.9

---

## 🧮 Como os Cálculos Funcionam

### 1. TMB/BMR (Taxa Metabólica Basal)
Usa a **Fórmula de Mifflin-St Jeor** (mais precisa):

**Homens:** TMB = (10 × peso) + (6,25 × altura) - (5 × idade) + 5  
**Mulheres:** TMB = (10 × peso) + (6,25 × altura) - (5 × idade) - 161

### 2. GET/TDEE (Gasto Energético Total)
TDEE = TMB × Fator de Atividade

### 3. Calorias Alvo
- **Perder peso:** TDEE - 500 kcal
- **Manter:** TDEE
- **Ganhar peso:** TDEE + 500 kcal
- **Ganhar músculo:** TDEE + 300 kcal

### 4. Macronutrientes
**Proteína:**
- Perder peso: 2.0 g/kg
- Ganhar músculo: 2.2 g/kg
- Ganhar peso: 1.8 g/kg
- Manter: 1.6 g/kg

**Gordura:** 30% das calorias totais

**Carboidrato:** Restante das calorias

### 5. IMC
IMC = peso(kg) / (altura(m))²

### 6. Massa Magra
Massa Magra = Peso - (Peso × % Gordura)

---

## 🎯 Exemplos de Uso Prático

### Cenário 1: Usuário Quer Emagrecer
```json
{
  "weight": 90,
  "height": 170,
  "age": 30,
  "gender": "FEMALE",
  "goal": "LOSE_WEIGHT",
  "activityLevel": "LIGHT"
}
```
**Resultado:** Déficit de 500 kcal + alta proteína para preservar massa muscular

---

### Cenário 2: Usuário Quer Ganhar Massa
```json
{
  "weight": 70,
  "height": 180,
  "age": 22,
  "gender": "MALE",
  "goal": "GAIN_MUSCLE",
  "activityLevel": "VERY_ACTIVE"
}
```
**Resultado:** Superávit moderado + proteína extra (2.2g/kg)

---

### Cenário 3: Atualizar Peso Semanalmente
```json
PATCH /profile/weight
{
  "weight": 88.5
}
```
**Resultado:** Recalcula tudo baseado no novo peso

---

## ⚠️ Validações

- **Peso:** Deve ser > 0and 
- **Altura:** Deve ser > 0 (em cm)
- **Idade:** Deve ser > 0
- **Gender:** Obrigatório para cálculos
- **Goal:** Define o ajuste calórico
- **ActivityLevel:** Define o multiplicador do TDEE
- **bodyFatPercentage:** Opcional (0-100)

---

## 🔄 Fluxo Típico

1. **Usuário se cadastra** → POST `/users`
2. **Usuário faz login** → POST `/login` (recebe token)
3. **Cria perfil nutricional** → POST `/profile` (com token)
4. **Sistema calcula tudo automaticamente** ✨
5. **Usuário atualiza peso** → PATCH `/profile/weight` (recalcula tudo)
6. **Usuário muda objetivo** → PATCH `/profile/goal` (recalcula calorias/macros)

---

## 💡 Dicas

- Use **PATCH** para atualizar apenas alguns campos
- Use **PUT** para substituir todo o perfil
- Todos os cálculos são **automáticos** - o frontend só envia os dados básicos!
- Atualizar peso/altura/idade/objetivo **recalcula tudo automaticamente**
- O `bodyFatPercentage` é opcional mas melhora a precisão