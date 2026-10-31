# Prüfbericht

## Übertragung des Yarn-Box-Stands, 31. Oktober 2026

Übernommen wurden die Verbesserungen dieses Chats und die Nachkorrekturen
bis Yarn-Box-Commit `7fc3363`: Dialogzentrierung, Editor ohne Tabellenblinken,
Farbauswahl mit lesbaren Farbcodes und Alttext-Erhalt, Fotooptimierung,
gemerkter Fotoordner, ergänzender JSON-Transfer, Sicherungsgrenzen,
entfernte Neuladen-Schaltfläche, englischer Produktname und englischer Erststart.
Status werden im app-eigenen JSON-Format gesichert.

Auf ausdrücklichen Nutzerwunsch keine Tests und keine automatisierten
GUI-Abläufe. Formatter, Compiler-Lint, Enforcer, Paketbau und SpotBugs bestanden mit
`./mvnw --no-transfer-progress spotless:apply package spotbugs:check -Dmaven.test.skip=true`.
Protokoll: `target/yarn-parity-build.log`. Das Linux-App-Image wurde
mit gebündelter Laufzeit und den benötigten JSON-Bibliotheken erstellt.
Paketprotokoll: `target/yarn-parity-package.log`. Native Windows- und macOS-Abnahme bleiben offen.
Vorhandene Testquellen und Vorschauhelfer sind nur angepasst, nicht ausgeführt.
Die nachfolgenden Ergebnisse beschreiben frühere Stände.

Manuelle Abnahme durch den Nutzer, in diesem Auftrag nicht ausgeführt:

- Hinzufügen und Bearbeiten auf Zentrierung und fehlendes Tabellenblinken prüfen.
- Farbe wählen, speichern, in der Liste ansehen und wieder entfernen. Alttexte
  bei einer reinen Notizänderung erhalten. Ausgewählte Zeilen müssen lesbar sein.
- Großes Foto, kleines Foto, transparentes PNG und gedrehtes Handyfoto auswählen.
  Originaldateien bleiben gleich, neue gespeicherte Kopien haben höchstens
  1600 Pixel Kantenlänge. Abbrechen oder Fehler erhalten den Entwurf.
- Die Fotoauswahl in einem anderen Eintrag und nach Neustart erneut öffnen.
  Der bestätigte Ordner soll erhalten bleiben, Abbrechen darf ihn nicht ändern.
- Ganze Sammlung bei aktivem Filter exportieren. Status
  kontrollieren, wieder importieren und mehrfachen Import ohne zusätzliche
  Duplikate durchführen. Ungültige Dateien dürfen keine Teilimporte hinterlassen.
- Einträge mit gleichen Texten und Fotos, aber anderem Status getrennt erhalten.
- Überschrittene Text-, Eintrags-, Foto- und Dateigrenzen müssen eine Erklärung
  liefern. Bereits vorhandene größere Bestände dürfen nicht gekürzt werden.
- Erststart ohne Spracheinstellung bei deutscher Systemsprache soll Englisch
  verwenden. Gespeicherte Sprache und ausdrücklich gewähltes Automatisch erhalten.


## App-Icons auf den drei Desktop-Plattformen, 31. Oktober 2026

Vorhandene Motive in zusätzliche Fenstergrößen, Windows-ICO und macOS-ICNS
überführt. Fenster und Startfehler verwenden die App-Icons. Linux erhält einen
benutzerspezifischen Anwendungseintrag mit passender JavaFX-Fensterklasse und
Icons. Windows- und macOS-Pakethelfer verknüpfen die jeweiligen Icon-Dateien
explizit mit Launcher/Bundle und Installer.

Tatsächlich ausgeführt: Icon-Erzeugung mit Pillow, Formatter und Java-Paketbau
mit `-Dmaven.test.skip=true`. Protokoll: `target/desktop-icons-build.log`.
Vorhandene Linux-App-Images mit App-JAR, Icons, Desktop-Installer und Anleitung
aktualisiert. Die Linux-Desktop-Registrierung wurde für das aktuelle Benutzerkonto
ausgeführt. Keine vollständigen nativen Installer neu gebaut.

Die Anwendung wurde in diesem Auftrag nicht gestartet. Der neue Menüeintrag
verwendet den bisherigen Standarddatenpfad. Visuelle Abnahme bleibt offen.

Auf Nutzerwunsch keine Tests, kein `verify` und keine automatisierten GUI-Prüfungen.
Manuelle Abnahme: App über die Ubuntu-Anwendungsübersicht starten, eigenes Icon
im Dock und beim Fensterwechsel ansehen, Dialoge öffnen und nach einem Neustart
die Zuordnung kontrollieren. Windows separat in Explorer, Startmenü und Taskleiste
prüfen, macOS separat im Finder, Dock und App-Umschalter. Native Windows-/macOS-
Pakete und Signierungen wurden hier nicht erzeugt oder geprüft.

## CJK-Schriftdarstellung, 31. Oktober 2026

Der gemeldete Beispieltitel „Bücher 日本語 📚“ ist in der synthetischen
BücherRegal-Datenbank vollständig gespeichert. Die drei Kästchen betreffen die
Darstellung. Alle fünf Apps verwenden jetzt eine app-lokal mitgelieferte,
explizit geladene Schrift Noto Sans CJK JP 2.004 mit Regular und Bold.
Die Änderung gilt für Tabellen, Eingaben und die übrige gestylte Oberfläche.
Gespeicherte Texte und Sprachwahl werden nicht umgeschrieben.

Die eingesehene native Linux-Schriftauswahl von OpenJFX 21.0.11 filtert bei der
Fallback-Ermittlung auf TrueType. Die hier installierten Noto-CJK-Schriften
verwenden CFF-Outlines. Die primäre, direkt geladene OTF-Schrift umgeht diesen
Fallback für die enthaltenen Zeichen. Andere Unicode-Zeichen und die Darstellung
von Emojis hängen weiterhin von der JavaFX-Ersatzschrift des Zielsystems ab.

Quelle: [OpenJFX 21.0.11, fontpath_linux.c](https://github.com/openjdk/jfx21u/blob/21.0.11%2B4/modules/javafx.graphics/src/main/native-font/fontpath_linux.c).
Die Einbindung nutzt die öffentliche API
[Font.loadFont](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/text/Font.html).

Formatter und Java-Kompilierung mit Paketbau erfolgreich. Pro App wurde
`./mvnw spotless:apply package -Dmaven.test.skip=true` mit dem eigenen
Temurin 21.0.13+9 ausgeführt. Protokoll: `target/ui-font-build.log`.
Die vorhandenen Linux-App-Images wurden mit dem neuen App-JAR und den
zusätzlichen Lizenzhinweisen aktualisiert. Die Java-Laufzeit blieb unverändert.

Auf Nutzerwunsch keine Tests, kein `verify` und keine automatisierte GUI-Abnahme.
Manuell abzunehmen: den obigen Beispieltitel in Tabelle und Bearbeitungsdialog
lesen, zusätzlich Umlaute und Akzente in den sechs UI-Sprachen ansehen und
Dialoggrößen sowie fette Überschriften kontrollieren. In den anderen Apps dafür
nur eigene temporäre Beispieldaten verwenden. Windows und macOS bleiben offen.

## Datenschutz und lokale Paketvorbereitung, 31. Oktober 2026

Aus BücherRegal übernommen: Info-Menü, vollständige Offline-Datenschutzerklärung
in sechs Sprachen, größerer scrollbar-kopierbarer Dialog, Zentrierung nach nativen
Größenkorrekturen und verzögertes Einblenden. Die Texte beschreiben die tatsächlichen
Daten dieser App. Fotos bleiben lokale Datenbankkopien. Status und Arbeitsschritte
werden nur in den Apps mit diesen Funktionen genannt. KostümPlan beschreibt
zusätzlich seinen PDF-Export. Es wurde kein JSON-Import/-Export ergänzt.

Destruktive Schaltflächen haben einen roten Fokusrahmen. Die übrigen Aktionen
behalten die jeweilige App-Farbe. Die Auswahlfläche der Tabelle bleibt erhalten,
der zusätzliche Fokusrahmen der Tabellenzeile ist transparent.

Tatsächlich ausgeführt: Formatter, Java-Compiler und Linux-App-Image-Bau mit
Temurin 21.0.13+9. Tests waren durch `-Dmaven.test.skip=true` ausdrücklich
ausgeschaltet. Der Pakethelfer führte nur seinen Launcher-Versionsaufruf aus.
Der Datenschutztextexport erzeugte sechs Dateien aus den produktiven Ressourcen.
Keine Tests, kein `verify`, keine Fehleranalyse und kein GUI-Prüflauf dieses
Standes. Frühere Prüfergebnisse gelten ausschließlich für die damaligen Stände.

Buildprotokoll: `target/portfolio-privacy-build.log`.
Datenschutztexte: `build/releases/privacy/2026-10-31`.
Die Pakete enthalten die eigene Laufzeit, Lizenzhinweise, Bedienungsanleitung
und die originale Herkunftsdatei des Build-JDK. Die geänderten CI-Workflows und
native Windows-/macOS-Paketierung wurden nicht ausgeführt. Keine neuen Store-
Screenshots erzeugt. Frühere Lieferungen wurden nicht überschrieben.

Manuelle Abnahme durch den Nutzer bleibt offen:

1. Einen Eintrag wählen und mit Tab „Bearbeiten“ sowie „Löschen“ fokussieren.
   Nur der destruktive Fokusrahmen ist rot. Die Zeilenauswahl bleibt sichtbar.
2. Unter „Info“ die Erklärung öffnen. Positionierung und Einblenden beurteilen,
   scrollen, Text kopieren und über Escape sowie „Schließen“ zurückkehren.
3. Den Dialog nach einem Sprachwechsel und Neustart in den sechs Sprachen prüfen.
   Texte, Fotos und die vorhandenen Status-/Schrittdaten müssen erhalten bleiben.
4. Die bisherigen Kernfunktionen der App einschließlich Fotoauswahl und
   gegebenenfalls Status, Checkliste oder PDF manuell abnehmen.

Eine Store-Freigabe ist damit nicht belegt. Identität, MSIX, Signierung,
Installation, Updates, WACK und Windows-Aufnahmen bleiben offen.

Stand: 29. Oktober 2026, StrickProjekt 0.1.0.

## Internationalisierung vom 29. Oktober 2026

Die sechs Sprachkataloge haben jeweils 96 geprüfte Schlüssel. Die zusätzlichen
Tests kontrollieren vollständige Kataloge, Platzhalter, regionale Zahlen,
Statusübersetzungen, Sprachauflösung und atomare Einstellungen. Für jede Sprache
bleibt eine vorhandene SQLite-Datei samt Unicode-Text, Foto und Status bytegenau
unverändert, wenn ausschließlich die Spracheinstellung geschrieben wird.

Der reproduzierbare GUI-Ablauf nutzt echte JavaFX-Fenster, native Fotoauswahl,
Maus- und Tastaturereignisse auf einem eigenen Xvfb-Bildschirm. Pflichtfelder,
Kontextmenüs, Fotoersatz und -entfernung, Unicode, lange Notizen, sichere
Abbruchvorgaben und ein echter SQLite-Schreibfehler mit Wiederholung werden
geprüft. Reiner Statusabbruch und direkte Wechsel zwischen allen drei Werten
sind zusätzlich nachgewiesen. Der Neustart in einem neuen Prozess prüft
Sprache, Text, Statuscode und Fotobytes gemeinsam.

Das eigenständige Linux-App-Image enthält `jdk.localedata`, damit auch ohne
installiertes JDK die regionalen Zahlenformate vorhanden sind. Der echte
Launcher läuft außerhalb des Projekts mit eingebetteter JVM, ohne `JAVA_HOME`
und mit `PATH=/nonexistent`. Eine vorhandene synthetische Schema-1-Sammlung mit
1234 Projekten bleibt erhalten. Die Bedienung speichert Unicode, ein über den
nativen Dialog ersetztes JPEG und den Status `finished`. Nach Entfernung der
Bildquelle wechselt ein neuer Prozess von Französisch zu Englisch und zeigt
das gespeicherte Foto samt Status. Die Laufzeitdateien bleiben unverändert.

Der finale Sechssprachendurchlauf besteht mit 18 neuen JVM-Prozessen und
510 GUI-Prüfassertionen. Die 24 unveränderten Store-Aufnahmen mit 1920 × 1080
Pixeln wurden visuell kontrolliert. Auch die französische Gruppierung von
1234 Projekten und die Paket-Neustartbilder wurden nach dem Locale-Modul-Fix
visuell geprüft. Ruff-Formatprüfung, Ruff-Lint, Shell-Syntax und Git-Whitespace
bestehen. Die neuen Werkzeuge erzeugen keine Lieferarchive.

Finale Nachweise liegen in `target/gui-international-final` und
`target/packaged-international-final`. Die Auslieferung prüft deren Hashes gegen
den lokalen Commit. Die initialen Pilot- und Zwischenläufe werden nicht geliefert.

Die genauen Wiederholungsbefehle, Ausgabestruktur und weiterhin ausstehende native
Windows-Abnahme stehen in [Internationalisierung](internationalization.md).

## Automatische Prüfungen

Linux x64, Eclipse Temurin JDK 21.0.11, JavaFX 21.0.11,
SQLite JDBC 3.53.4.0 und Maven 3.9.16 über Maven Wrapper.

```bash
./mvnw spotless:apply
./mvnw verify
bash -n scripts/run.sh scripts/package.sh
git diff --cached --check
```

96 JUnit-Testfälle bestanden. Compiler-Lint mit `-Werror`, Maven Enforcer,
Spotless und SpotBugs bestanden ohne Befunde.

| Bereich | Geprüft |
| --- | --- |
| Pflichtfeld | Fehlend, leer, Unicode-Leerraum, Bereinigung, optionale Felder, mehrzeilige Notiz |
| CRUD und Suche | Anlegen, Bearbeiten, Löschen, stabile IDs, unabhängige gleichnamige Projekte, alle fünf Textfelder, Umlaute, Unicode-Normalisierung, leere Suche, keine Treffer |
| SQLite | Wiederöffnen temporärer Dateien, SQL-Sonderzeichen als Daten, ungültiger Pfad, beschädigte Datenbank, neuere Schema-Version |
| Foto | Hinzufügen, Ersetzen, Entfernen, Quelle löschen und Datenbank wiederöffnen, unveränderte Quelldateien, defensive Bytekopien |
| Bildprüfung | JPEG/PNG nach Inhalt, falsche Endung, GIF, Text, leere und abgeschnittene Dateien, fehlende Datei, Verzeichnis |
| Bildgrenzen | Mehr als 10 MiB, mehr als 6000 Pixel je Seite, mehr als 16 Millionen Pixel, zulässige Dimensionsgrenzen |
| Statusstandard | Geplant bei neuen Entwürfen, gespeichertem Projekt und direktem SQL-INSERT ohne Status |
| Fester Wertebereich | Genau drei Schlüssel und übersetzte Anzeigen, ungültige Werte und `null` abgelehnt, SQLite-CHECK, beschädigter Fremdwert wird gemeldet und bleibt unverändert |
| Statusspeicherung | Alle drei Werte wiederhergestellt, Speicherung als expliziter Text statt Ordinalzahl oder deutscher Anzeige |
| Statuswechsel | Jeder Status in jeden anderen und zurück, Erhalt von ID, allen Texten und vollständigen Fotobytes |
| Atomare Fehler | AFTER-UPDATE-Trigger bricht Änderung ab, Texte/Foto/Status vollständig unverändert, erneutes Speichern gelingt. Fehlgeschlagenes INSERT hinterlässt kein Teilprojekt |
| Datenbanksperren | Fehler bei Anlegen, Ändern, Entfernen, Löschen und reiner Statusänderung erhalten den bisherigen Datenbestand |

Tests verwenden ausschließlich eigene temporäre SQLite-Dateien und erzeugte
Bilder. Keine normalen Benutzersammlungen werden geöffnet.

## Erste GUI-Abnahme vom 26. Oktober 2026

Die JavaFX-Anwendung lief auf einem separaten Linux-Xvfb-Bildschirm. Maus- und
Tastatureingaben über `xdotool`, native Dateiauswahl und echte Fensterereignisse
wurden verwendet. Screenshots wurden visuell geprüft. Eine unabhängige Python-
SQLite-Verbindung kontrollierte die gespeicherten Werte und Bildbytes.
Testdaten liegen ausschließlich im ignorierten `target/gui-test-data`.
Diese GUI-Abnahme läuft nicht automatisch in `mvn verify`.

| Ablauf | Ergebnis |
| --- | --- |
| Leere Sammlung, dann leeren Namen speichern | Einladung zum ersten Projekt, Geplant voreingestellt, Pflichtfeldmeldung, kein Eintrag |
| Projekt mit allen Texten und PNG anlegen, Angefangen wählen | Texte, mehrzeilige Notiz, Vorschau und Status korrekt gespeichert |
| Suche nach ÖMER, keine Treffer, Suche leeren | Umlautsuche funktioniert, eigener Leerzustand für keine Treffer, Sammlung wieder sichtbar |
| Bearbeiten und auf Fertig setzen | Geänderte Texte und Status in der Liste sichtbar |
| Bildquelle löschen und als Paket neu starten | Texte, Foto und Fertig erhalten |
| Fertig zurück auf Angefangen | Möglich, alle anderen gespeicherten Inhalte einschließlich Fotobytes unverändert |
| Nur Status ändern und Escape | Rückfrage. Enter wählt Weiterbearbeiten und erhält den Entwurf |
| Nur Status ändern und Dialogfenster schließen | Rückfrage. Verwerfen lässt gespeicherten Status unverändert |
| Nur Status ändern und Abbrechen klicken | Rückfrage. Verwerfen lässt gespeicherten Status unverändert |
| Dateiauswahl abbrechen | Foto unverändert. Der sonst unveränderte Dialog schließt ohne Rückfrage |
| Text ändern und Foto entfernen, Escape | Verwerfschutz, gespeicherte Texte und Foto erhalten |
| Textdatei als PNG wählen | Verständliche Meldung, bisherige Vorschau und Entwurf erhalten |
| PNG durch JPEG ersetzen, Texte und Status ändern, Datenbank sperren | Speicherfehler. Dialog bleibt mit vollständigem Entwurf offen, keine Erfolgsmeldung und keine Teiländerung |
| Fehlerdialog auf 660 × 540 Pixel verkleinern | Formular scrollbar. Fehlermeldung, Abbrechen und Speichern vollständig lesbar |
| Sperre lösen und denselben Entwurf speichern | Ohne erneute Eingabe erfolgreich gespeichert |
| JPEG-Quelle löschen und Paket neu starten | Ersetztes Foto, Texte und Status erhalten |
| Neues Projekt direkt im Paket anlegen | Geplant ohne manuelle Statuswahl gespeichert |
| Fotoladen durch Datenbanksperre verzögern und anderes Projekt wählen | Nach Freigabe bleibt beim Projekt ohne Foto der neutrale Platzhalter, kein verspätetes Fremdfoto |
| Weiteres Projekt mit PNG direkt im Paket anlegen | Bildimport, Vorschau und Angefangen funktionieren in der eingebetteten Laufzeit |
| Foto entfernen, abbrechen, danach entfernen und speichern | Abbruch erhält Foto. Speichern entfernt BLOB und zeigt Platzhalter |
| Löschbestätigung abbrechen und bestätigen | Abbruch erhält Projekt. Bestätigung löscht ausschließlich ausgewählten Eintrag |
| Löschen bei gesperrter Datenbank | Eintrag und Auswahl erhalten. Meldung bei 860 × 700 Pixeln lesbar |
| Alle synthetischen Testprojekte bestätigt löschen | Sammlung wieder leer |
| Abschließendes Paket nach Korrektur der Spaltenbreite | Angefangen bei 860 × 700 Pixeln vollständig lesbar, Status im Paket gespeichert |

[Übersicht mit drei Statuswerten](screenshots/strickprojekt.png),
[Speicherfehler bei kleinem Dialog](screenshots/save-failed.png) und
[Verwerfschutz](screenshots/status-discard.png) zeigen ausschließlich synthetische
Daten. Weitere lokale Screenshots liegen in `target/gui-evidence`.

## Startbares Paket mit eingebetteter Laufzeit

```bash
./scripts/package.sh app-image
./target/app-image/StrickProjekt/bin/StrickProjekt --version
```

Linux-x64-App-Image erfolgreich gebaut, Versionsausgabe `StrickProjekt 0.1.0`.
Das Paket wurde mit entfernter Umgebungsvariable `JAVA_HOME` und
`PATH=/nonexistent` mit echter Oberfläche gestartet. Die Prozessbibliotheken
zeigen `target/app-image/StrickProjekt/lib/runtime/lib/server/libjvm.so`.

Im Paket wurden gespeicherte Daten erneut geöffnet, Statuswerte geändert,
JPEG und PNG importiert, angezeigt und entfernt, neue Projekte angelegt und
bestätigt gelöscht. Auch der Speicherfehler und erneute Start nach Löschen der
Bildquelle wurden im Paket geprüft. Damit sind JavaFX, ImageIO und natives
SQLite praktisch nachgewiesen. Der Versionsaufruf allein ist kein GUI-Nachweis.

## Wiederholbare manuelle Abnahme

Ein eigenes leeres Testverzeichnis mit `--data-dir=/absoluter/testpfad` verwenden.
Ein selbst erzeugtes PNG und JPEG auswählen. Die Abläufe der Tabelle auf dem
Zielsystem durchführen, insbesondere alle drei Statuswerte, Rückwechsel,
reinen Statusabbruch und Neustart nach Löschen der Bildquelle.

Für den Fehlerfall ausschließlich diese Testdatenbank über eine zweite
SQLite-Verbindung mit `BEGIN EXCLUSIVE` sperren. Nach der erwarteten Meldung
mit `ROLLBACK` freigeben. Denselben unveränderten Dialog erneut speichern.
Für verzögerte Vorschauen die Sperre kurz halten und währenddessen zwischen
Projekten mit und ohne Foto wechseln.

## Noch offen

Windows und macOS sind in dieser Umgebung nicht verfügbar. EXE-, DMG- und
PowerShell-Skripte sowie GitHub-Workflows sind vorbereitet, aber dort noch nicht
gebaut oder ausgeführt. Intel und Apple Silicon benötigen eigene native Tests.
Installation, Update, Deinstallation ohne Datenverlust, Skalierung,
Tastenkürzel, native Dateiauswahl und Sicherheitsdialoge bleiben dort zu prüfen.

Store-Registrierung, Signierung, Mac-App-Sandbox, Entitlements, konkrete
Einreichungspakete und Zertifizierung stehen aus. Siehe [Paketierung](packaging.md).
Ein normaler Installer ist keine geprüfte Store-Version. Kein Push, Upload oder
Veröffentlichen wurde durchgeführt.
