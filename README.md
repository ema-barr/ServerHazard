# ServerHazard

## Per l'installazione
- Installare git
- Installare node.js
- Andare nella cartella Server ed eseguire i comandi:
<pre>
npm install express
npm install socket.io
npm install fs
npm install xmldom
</pre>

## Per l'avvio del sistema e dell'interfaccia di debug
- Andare nella cartella Server ed eseguire il comando
<pre>node server.js</pre>
su Windows oppure
<pre>nodejs server.js</pre>
su Linux.
- Avviare il progetto ServerHazard (usando IntelliJ), tramite la classe Engine/src/it/uniba/hazard/engine/test/ServerTest
- Per aprire l'interfaccia di debug aprire una finestra del browser e inserire nella barra degli indirizzi:
<pre>http://localhost:6882</pre>
- Nel campo "Message name" inserire il nome della richiesta da effettuare, e nel campo "Message data" inserire i dati della richiesta (in formato JSON)
