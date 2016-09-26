# Aufgabenstellung Gadgeothek
Sie kennen bestimmt die Gadgeothek der HSR-Bibliothek. Im Miniprojekt geht es darum, eine App f�r die (fiktiven ? wir haben leider keinen Zugriff auf den echten Bibliotheksserver) Bibliotheksbenutzer zu entwickeln.

In den Vorlagen finden Sie auch das Administrationstool der Gadgeothek, eine Swing-Applikation die im Vorg�ngermodul User Interfaces 1 entwickelt wurde und uns freundlicherweise von den Autoren Samuel Jost und Philipp Schilter zur Verf�gung gestellt wird.


## Szenarios	 	 	
Szenarios helfen Ihnen zu verstehen, in welchem Kontext die Software verwendet wird. Die untenstehenden Szenarien beschreiben, was ihre Applikation am Ende des Miniprojektes unterst�tzen sollte. Verwenden Sie sie zum Testen ihrer Applikation und schauen sie darauf, dass Sie m�glichst alle davon optimal unterst�tzen k�nnen.
### Szenario Registrieren
Dario hat soeben von der App erfahren und hat diese in einer langweiligen MGE-Vorlesungssequenz bereits installiert. Da Dario noch keinen Account hat, muss er sich zuerst mit Angabe seiner E-Mail Adresse, Name, Passwort und Matrikelnummer registrieren. 
### Szenario Ausleihe Pr�fen
Nach dem Abschluss des Miniprojekts im Modul Mobile and GUI Engineering ist Luca nicht mehr sicher, ob er das Android-Smartphone ausgeliehen hatte oder ob es Dario war. Er loggt sich deshalb in der Gadgeothek-App ein und schaut in seinen aktuellen Ausleihen nach, ob es an ihm ist das Smartphone zu retournieren.
### Szenario Reservation Erstellen
Anton ist ein grosser Fan von Apples Produkten, m�chte aber herausfinden, ob Android wirklich so gut ist wie ihm Dario und Luca die ganze Zeit vorschw�rmen. Leider sind im Moment alle Android- Smarthpones ausgeliehen, deshalb tr�gt er sich in die Reservationsliste f�r das neue Samsung Galaxy S6 Edge ein.
### Szenario Reservation L�schen
In der Nacht auf heute hat Apple neue iPhones vorgestellt! Anton hat sich schon eines vorbestellt und will sich nicht doch von Android verf�hren lassen. Deshalb l�scht er seine Reservation des Samsung Galaxy S6 Edge wieder.
### Szenario Bibliothek Wechseln
Dario macht ein Austauschsemester an der ZHAW. Seit neustem gibt es auch dort eine Gadgeothek, und sie verwendet sogar dieselbe Software. Dario braucht nur den Server anzupassen und schon kann er wieder loslegen und neue Gadgets reservieren.
Installation

Zum Projektstart ist ein grosser Teil der Domain und der Businesslogik der Gadgeothek-App vorgegeben. Setzen sie bei ihrer L�sung diesen Code ein! Die nachfolgenden Klassendiagramme bietet ihnen eine �bersicht �ber den vorgebebenen Code. Sollten sie das Bed�rfnis haben diesen Code grundlegend zu modifizieren, so d�rfen sie dies gerne tun. Den Code finden Sie auf GitHub unter github.com/HSR-MGE/Miniprojekt-Vorlage-Android. 

Der Server besteht aus einer einfachen Node.js-Anwendung die eine REST-Schnittstelle anbietet. Sie finden den Code dazu unter github.com/HSR-MGE/Miniprojekt-Server . Sie k�nnen den Server entweder lokal starten, oder Sie verwenden eine unserer zehn Instanzen mge1.dev.ifs.hsr.ch, mge2 ... mge10.dev.ifs.hsr.ch (Achtung: wegen noch nicht gel�ster techischer Probleme mit dem Proxy liefern diese Instanzen keine Push-Notifications an die Admin-Software).

Um den Server lokal zu starten m�ssen Sie Node.js installiert haben. Kopieren Sie dann die package.json und server.js Dateien in ein Verzeichnis (bzw. klonen Sie einfach das Repository). In diesem Verzeichnis installieren Sie dann mit npm (das ist der Node-Packagemanager) die ben�tigten Libraries und starten den Server:

$ npm install
...
$ node server.js

Der Server h�rt auf Port 8080 (HTTP) und gibt Debug-Output aus. Alternativ steht auch ein Docker-Container bereit den Sie stattdessen einsetzen k�nnen. Zum Beispiel so:

$ docker run -t -i -p 8080:8080 misto/miniprojekt-server

Die Administrationsoberfl�che github.com/HSR-MGE/Miniprojekt-Admin starten Sie mit folgendem Befehl und unter der Angabe der Serveradresse und Port:

java -Dserver=localhost:8080 -jar gadgeothek-admin.jar

Sobald sie sp�ter mit dem Android-Client auf denselben Server verbinden werden Sie Updates live in der Administrationsoberfl�che sehen.
Vorlagen
Die Kommunikation mit dem Server erfolgt �ber die (auch vorgegebene) LibraryService-Klasse. Im Lauf der Vorlesung werden Sie erkennen, dass diese Klasse nicht unbedingt gerade Android Best-Practices entspricht, der Einfachheit halber setzten wir sie aber trotzdem ein (Android-Services sind erst in Woche 6 ein Thema ? zu sp�t f�rs Miniprojekt. Fortgeschnittene Teilnehmer die eine zus�tzliche Herausforderung w�nschen k�nnen diese Klasse z.B. mit square.github.io/retrofit ersetzen). 

Achtung: Ihre App ben�tigt f�r den Netzwerkzugriff nat�rlich die entsprechende Berechtigung im Manifest!

<uses-permission android:name="android.permission.INTERNET"/>

Die Klassen haben externe Dependencies, zus�tzlich m�ssen im build.gradle des app-Moduls folgende Dependencies erg�nzt werden:

compile 'com.google.code.gson:gson:2.4'
compile 'com.ning:async-http-client:1.9.31'
compile 'org.slf4j:slf4j-api:1.7.12'
compile 'org.slf4j:slf4j-simple:1.7.12'

Als erstes m�ssen Sie der LibraryService-Klasse mitteilen, wo der Server zu finden ist. Wenn Sie eine unserer Serverinstanzen verwenden, lautet der erste Aufruf:

LibraryService.setServerAddress("http://mgeX.dev.ifs.hsr.ch/public");

Beachten Sie das /public, welches der Serverteil (auch der lokal gestartete) erwartet.

Vor der ersten Anfrage (ausgenommen der Registrierung) an den Server m�ssen Sie sich einloggen, dies geschieht mit der login-Methode:

public static void login(String mail, String password, Callback<Boolean> callback)

Die Methode hat keinen R�ckgabewert, aber nimmt ein Callback-Objekt entgegen, mit welchem der R�ckgabewert des Aufrufs (hier ein Boolean) mitgeteilt wird. Callback ist ein Interface mit zwei Methoden:

public interface Callback<T> {
   void onCompletion(T input);
   void onError(String message);
}

Ist der Request erfolgreich (die Verbindung zum Server k�nnte ja z.B. auch unterbrochen werden) wird die onCompletion-Methode aufgerufen mit dem entsprechenden Resultat (also boolean beim login). Im Fehlerfall wird onError mit der Fehlermeldung aufgerufen. Die Login-Methode wird also folgendermasse benutzt:

LibraryService.login(email, password, new Callback<Boolean>() {
   @Override
   public void onCompletion(Boolean success) {
       if(success) {
           // Jetzt sind wir eingeloggt
       } else {
           // Passwort war falsch oder User unbekannt.
       }
   }

   @Override
   public void onError(String message) {
       // Fehler z.B. in einem Toast/Snackbar darstellen
   }
});

Warum so kompliziert? Wir k�nnen das Resultat nicht einfach als R�ckgabewert der Login-Methode zur�ckgeben, da sonst die App an dieser Stelle einfach blockiert ist und auf das Resultat des Servers wartet. Stattdessen geben wir einen Callback mit ? zeigen dem User in der Zwischenzeit eine Progressbar oder �hnliches ? und aktualisieren das UI dann wenn der Callback ? onCompletion oder onError -- eingetroffen ist.

In der Vorlage finden Sie zudem das Domain-Package, welches die drei Klassen Gadget (entspricht einem konkreten Ger�te), Loan (eine Ausleihe eines Ger�tes) und Reserveration enth�lt. Die Klassen sollten hoffentlich selbsterkl�rend sein, bei Fragen z�gern Sie aber bitte nicht diese in den �bungen zu stellen.

Auf den restlichen Seiten finden Sie noch die Klassendiagramme der Vorlagen. Sie werden wohl nicht alle Attribute der Klassen auch tats�chlich verwenden, da die Klassen aber auch in der Admin-Software eingesetzt werden sind diese dennoch enthalten.

