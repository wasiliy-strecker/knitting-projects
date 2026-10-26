# Prüfbericht

Stand: 26. Oktober 2026, StrickProjekt 0.1.0.

## Automatische Prüfungen

Linux x64, Eclipse Temurin JDK 21.0.11, JavaFX 21.0.11,
SQLite JDBC 3.53.4.0 und Maven 3.9.16 über Maven Wrapper.

```bash
./mvnw spotless:apply
./mvnw verify
bash -n scripts/run.sh scripts/package.sh
git diff --cached --check
```

65 JUnit-Testfälle bestanden. Compiler-Lint mit `-Werror`, Maven Enforcer,
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
| Fester Wertebereich | Genau drei Schlüssel und deutsche Anzeigen, ungültige Werte und `null` abgelehnt, SQLite-CHECK, beschädigter Fremdwert wird gemeldet und bleibt unverändert |
| Statusspeicherung | Alle drei Werte wiederhergestellt, Speicherung als expliziter Text statt Ordinalzahl oder deutscher Anzeige |
| Statuswechsel | Jeder Status in jeden anderen und zurück, Erhalt von ID, allen Texten und vollständigen Fotobytes |
| Atomare Fehler | AFTER-UPDATE-Trigger bricht Änderung ab, Texte/Foto/Status vollständig unverändert, erneutes Speichern gelingt. Fehlgeschlagenes INSERT hinterlässt kein Teilprojekt |
| Datenbanksperren | Fehler bei Anlegen, Ändern, Entfernen, Löschen und reiner Statusänderung erhalten den bisherigen Datenbestand |

Tests verwenden ausschließlich eigene temporäre SQLite-Dateien und erzeugte
Bilder. Keine normalen Benutzersammlungen werden geöffnet.

## Tatsächlich gestartete Oberfläche

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
