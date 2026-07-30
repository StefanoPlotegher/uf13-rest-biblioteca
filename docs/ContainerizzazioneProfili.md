# Containerizzazione e gestione dei profili

## 1. Dockerfile multi-stage
Il progetto include un Dockerfile multi-stage che separa:
- la fase di build, con Maven e JDK 21;
- la fase di esecuzione, con un runtime Java minimale.

Questa struttura consente di:
- ridurre l'impronta dell'immagine finale;
- evitare di includere strumenti di compilazione nell'ambiente di esecuzione;
- rendere la build completamente automatizzata all'interno di Docker.


## 2. Gestione dei profili Spring
Sono stati introdotti due profili:
- dev: orientato allo sviluppo, con logging molto dettagliato e query SQL visibili;
- prod: orientato alla produzione, con logging più ridotto e output su file rotante.

### File di configurazione
- application.properties: contiene la configurazione comune e l'attivazione del profilo.
- application-dev.properties: impostazioni per lo sviluppo.
- application-prod.properties: impostazioni per la produzione.

### Profilo attivo
Il profilo può essere selezionato tramite variabile d'ambiente:
```bash
SPRING_PROFILES_ACTIVE=dev
```
oppure:
```bash
SPRING_PROFILES_ACTIVE=prod
```

## 3. Docker Compose
Il file docker-compose.yaml consente di avviare il backend insieme a MySQL e di impostare il profilo prod automaticamente.

### Avvio
```bash
docker compose up --build
```

## 4. Note operative
- In ambiente dev il logging è più verboso per facilitare il debugging.
- In ambiente prod i log vengono indirizzati su file rotante per una gestione più ordinata e efficiente.
- Il backend espone la porta 8080 e si collega al database MySQL tramite variabili di ambiente.
