# README - Cuentas

## Propósito

El controller `CuentaViewController` expone la vista web principal para gestionar cuentas bancarias dentro de la aplicación. Desde esta pantalla se puede:

- Crear una cuenta asociada a un cliente existente.
- Consultar el saldo de un cliente por nombre.
- Realizar transferencias entre dos cuentas con comisión.

Ubicación del controller: [src/main/java/pe/edu/dfernandez/hexagonal/app/infrastructure/adapter/input/web/controller/CuentaViewController.java](../src/main/java/pe/edu/dfernandez/hexagonal/app/infrastructure/adapter/input/web/controller/CuentaViewController.java)

## Vista asociada

La vista Thymeleaf que consume este controller es:

- [src/main/resources/templates/cuentas.html](../src/main/resources/templates/cuentas.html)

Y sus estilos están en:

- [src/main/resources/static/css/cuentas.css](../src/main/resources/static/css/cuentas.css)

## Ruta base

El controller trabaja bajo la ruta:

- `/cuentas`

## Funcionalidades

### 1. Consultar cuentas y saldo por nombre

**Método:** `GET /cuentas`

**Parámetro opcional:**

- `nombre`: nombre del cliente a consultar.

**Comportamiento:**

- Si no se envía nombre, la vista se muestra vacía.
- Si se envía un nombre, el controller busca el cliente por nombre ignorando mayúsculas/minúsculas.
- Si el cliente existe, se obtienen sus cuentas y se calcula el saldo total.
- Si el cliente no existe, se muestra un mensaje de error en la interfaz.

**Ejemplo:**

```text
/cuentas?nombre=Juan Perez
```

### 2. Crear una nueva cuenta

**Método:** `POST /cuentas`

**Formulario usado:** `CuentaCrearRequest`

**Campos:**

- `nombre`: nombre del cliente existente.
- `saldo`: saldo inicial de la cuenta.

**Validaciones:**

- El nombre no puede estar vacío.
- El saldo no puede ser negativo.
- El cliente debe existir previamente.

**Resultado:**

- Se genera un número de cuenta automáticamente.
- Se crea la cuenta con estado `ACTIVO`.
- Luego se redirige a la consulta del saldo del cliente para mostrar el resultado actualizado.

### 3. Transferir entre dos cuentas

**Método:** `POST /cuentas/transferencia`

**Formulario usado:** `TransferenciaFormRequest`

**Campos:**

- `cuentaOrigen`: número de cuenta de origen.
- `cuentaDestino`: número de cuenta de destino.
- `monto`: monto a transferir.
- `comision`: comisión a aplicar.

**Validaciones:**

- Ambas cuentas deben estar informadas.
- La cuenta de origen y destino no pueden ser la misma.
- El monto debe ser mayor que cero.
- La comisión debe ser mayor o igual a cero.
- Ambas cuentas deben existir.
- La cuenta de origen debe tener saldo suficiente para cubrir `monto + comision`.

**Efecto de negocio:**

- Se descuenta de la cuenta origen el monto más la comisión.
- Se acredita el monto en la cuenta destino.
- Se registra una transacción con tipo `TRANSFERENCIA` y estado `COMPLETADA`.

## Modelos de apoyo

### `CuentaCrearRequest`

Se usa para la creación de cuentas desde la vista.

Campos principales:

- `nombre`
- `saldo`

### `TransferenciaFormRequest`

Se usa para capturar los datos de la transferencia.

Campos principales:

- `cuentaOrigen`
- `cuentaDestino`
- `monto`
- `comision`

## Flujo de uso recomendado

1. Abrir la vista `/cuentas`.
2. Crear una cuenta para un cliente que ya exista en el sistema.
3. Consultar por nombre para ver el saldo total y las cuentas asociadas.
4. Usar la sección de transferencia para mover dinero entre dos cuentas.

## Reglas importantes

- Este controller no crea clientes.
- Antes de crear una cuenta, el cliente debe existir.
- Antes de transferir, ambas cuentas deben existir y la cuenta origen debe tener saldo suficiente.
- La comisión es obligatoria en la transferencia y forma parte del débito total de la cuenta origen.

## Relación con otros controllers

El controller web de cuentas reutiliza la lógica de aplicación existente para:

- Buscar clientes.
- Buscar cuentas.
- Crear cuentas.
- Actualizar cuentas.
- Crear transacciones.

Eso mantiene la interfaz web alineada con el núcleo de la arquitectura hexagonal.
