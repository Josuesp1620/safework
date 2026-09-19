# safework

Permisos de trabajo de alto riesgo: emitirlos, firmarlos en campo y poder
demostrar años después qué se verificó y quién lo autorizó.

El diseño completo — dominio, reglas, modelo de datos y plan por fases — está
en el documento de diseño, y conviene leerlo antes de tocar nada: aquí el
modelo de datos es casi todo el producto.

```
safework/
├── backend/     Spring Boot 4.1 · Java 25 · PostgreSQL
└── frontend/    Angular 22
```

## Levantarlo

Hace falta Java 25 y Maven; el proyecto trae el wrapper, así que basta el JDK.
Si usas SDKMAN:

```bash
sdk env install          # instala lo que dice .sdkmanrc
```

**Base de datos y backend:**

```bash
cd backend
docker compose up -d     # PostgreSQL 17
./mvnw spring-boot:run
```

Spring Boot levanta el compose por sí solo en desarrollo, así que el primer
paso sobra salvo que quieras la base por separado.

**Frontend:**

```bash
cd frontend
npm start
```

## Los módulos

El backend está dividido con **Spring Modulith**, y las fronteras las verifica
la compilación, no la buena voluntad:

| Módulo | Qué contiene |
|---|---|
| `catalog` | Sedes, áreas, tipos de trabajo y los controles que cada uno exige. Todo versionado |
| `people` | Trabajadores, supervisores y sus certificaciones con vigencia |
| `permits` | El permiso: su ciclo de vida, sus firmas y sus reglas |
| `audit` | Qué pasó, quién lo hizo y cuándo. Se escribe una vez y no se toca |

`ModularityTests` falla si un módulo entra en las tripas de otro, y genera la
documentación de módulos en `target/spring-modulith-docs`.

## Decisiones ya tomadas

- **Flyway manda sobre el esquema.** Hibernate está en `validate`: una tabla
  creada sola es una tabla que no existe en ningún otro sitio.
- **Los identificadores los pone la aplicación**, no la base de datos. Un
  permiso se crea en un teléfono sin señal y necesita identidad desde ese
  instante.
- **Un permiso aprobado es inmutable.** Si algo cambia, se emite otro.
- **Todo lleva organización desde la primera tabla.** Añadir multiempresa
  después es reescribir el proyecto.

## Pruebas

```bash
cd backend
./mvnw test
```

Las pruebas usan **Testcontainers**: levantan un PostgreSQL de verdad. Aquí las
reglas son el producto, y probarlas contra una base en memoria sería probar
otra cosa.
