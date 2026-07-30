# APIResposne e gestione errori



### gestione delle rispsote

È stata creata una classe generica `APIResponse<T>` nel package `domain.response` per uniformare tutte le risposte dell'API secondo lo **standard JSend** (`success` / `fail` / `error`).

| Campo     | Tipo           | Descrizione                                      |
|-----------|----------------|--------------------------------------------------|
| `status`  | `String`       | `"success"`, `"fail"` o `"error"`                |
| `data`    | `T` (generico) | Payload della risposta (presente in success/fail)|
| `message` | `String`       | Messaggio di errore (presente solo in error)     |

I campi `data` e `message` sono annotati con `@JsonInclude(Include.NON_NULL)` per essere omessi dal JSON quando non pertinenti.

**Factory methods:**
- `APIResponse.success(data)` → `{ "status": "success", "data": ... }`
- `APIResponse.fail(data)` → `{ "status": "fail", "data": ... }`
- `APIResponse.error(message)` → `{ "status": "error", "message": "..." }`

### GEstione degli errori

Classe `@ControllerAdvice` in `controllers.exception` che centralizza la gestione degli errori, eliminando ogni blocco try-catch nei controller.

| Eccezione                         | Status HTTP               | Risposta                                         |    
|-----------------------------------|---------------------------|--------------------------------------------------|
| `MethodArgumentNotValidException` | 400 Bad Request           | `APIResponse.fail(Map<campo, messaggio>)`        |
| `ResponseStatusException` (4xx)   | Stesso status             | `APIResponse.fail(reason)`                       |
| `ResponseStatusException` (5xx)   | Stesso status             | `APIResponse.error(reason)`                      |
| `Exception` generica              | 500 Internal Server Error | `APIResponse.error("Errore interno del server")` |



### Modifica dei Controller

Tutti gli endpoint ora restituiscono `APIResponse<T>` invece di `ResponseEntity`, `List<T>` o stringhe plain.

- I casi di errore (risorsa non trovata) lanciano `ResponseStatusException`, gestita centralmente dal `GlobalExceptionHandler`.
- La validazione dei dati in ingresso (`@Valid` sui DTO) produce automaticamente una risposta 400 con mappa dettagliata dei campi errati.

