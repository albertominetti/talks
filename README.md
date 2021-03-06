> :warning: **outdated software**: this small project has been done before start to work seriously with Java, it was more than 8 years ago.

> :skull: **run away if you look for good development practices**

# talks

L'applicazione è scritta in Java e basata su framework Spring, dipende da spring-boot così da poter essere lanciarla direttamente da Maven usando il goal spring-boot:run (o con il jar generato) ed è indipendente da app-container esterni; segue lo standard docker S2I utilizzato da diverse piattaforme PaaS tra cui OpenShift di RedHat o Amazon AWS. OpenShift è stata mia prima opzione di hosting ma le limitazioni dell'utenza free mi hanno imposto di cercare altrove, quindi ho optato per Heroku di cui ho apprezzato il supporto SSL, necessario per il modulo 4 (vedi sotto).

L'applicazione si compone di 5 package o moduli:
1) il modulo persistenza: sviluppato con spring-data-jpa e permette di scegliere il database sql approprato; attualmente utilizza un database H2 in-memory che viene ricreato ad ogni avvio a cui si può accedere tramite console H2, cambiando una dipendenza nel pom.xml e le proprietà dell'utenza nel file di properties si può interfacciare con gli altri db sql, al momento l'ho testato solo con H2 e MySQL.
2) il modulo di logica: puro Java per mantenere la logica di business centralizzata e per condividere le operazioni comuni verso gli altri moduli, dividendo le responsabilità tra i moduli per mantenere un buon livello di astrazione e poche dipendenze dallo strato di persistenza.
3) il modulo di api rest: sviluppato con spring-mvc espone le api rest per fruitori e altre app frontend, è disponibile la documentazione dei singoli end-point via swagger.
4) il modulo api per telegram: questo è un bot minimale che trovi sotto il nome di @ApiTalksBot su Telegram. Ho pensato al caso d'uso in azienda, in cui non solo si aggiungono partecipanti e si seleziona un presentatore casuale, ma si chatta anche per comunicare molteplici informazioni sull'argomento, data, luogo, etc. quindi per organizzare questo tipo di eventi si può utilizzare una chat di gruppo, e sfruttare un bot in chat per creare talk, registrare i partecipanti e marcare le assenze, sia da parte organizzatori sia dai singoli in modo distribuito. Il modulo sfrutta il RestController per il webhook e il RestTemplate per l'invio messaggi; per il comportamento del bot ho implementato una piccola state-machine in Java che offre buone potenzialità per il caso d'uso.
5) il modulo di front-end: sviluppato con JSF si compone di una semplice pagina xhtml, di un backing bean con scope session e le logiche di view.
