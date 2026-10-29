# StrickProjekt

Deine Ideen. Masche für Masche. Verwalte kleine Strick- und Häkelprojekte mit
Projektname, Garn oder Material, Farbe, Aufbewahrungsort, Notiz und einem
optionalen Foto. Der Status zeigt **Geplant**, **Angefangen** oder **Fertig**.
Alles bleibt lokal und nach einem Neustart erhalten. Die Oberfläche unterstützt
Deutsch, Englisch, Spanisch, Französisch, Italienisch und Portugiesisch (Brasilien).

![StrickProjekt mit synthetischen Testdaten](docs/screenshots/strickprojekt.png)

Stufe 3 der Java-Reihe BücherRegal → GarnBox → StrickProjekt → NähPlan.
KostümPlan ist die separat umgesetzte fünfte Stufe. Gegenüber GarnBox ergänzt diese App
genau den Projektstatus. Garn und Material sind Freitext ohne Verbindung zu
GarnBox. Checklisten, PDF, Zähler, Cloud und weitere Hauptfunktionen fehlen bewusst.

## Sprachen und Vorschau

„Sprache“ speichert die Auswahl für den nächsten Start. Automatisch folgt der
Systemsprache. Nicht unterstützte Sprachen verwenden Englisch. `language.txt`
liegt neben der unveränderten `strickprojekt.db`. Projekttexte, Fotos und
Statuscodes werden nicht übersetzt oder migriert.

[Internationalisierung und reproduzierbare Linux-Vorschau](docs/internationalization.md)
beschreibt die sechs Store-Textfassungen, 24 echten GUI-Aufnahmen, sichere
synthetische Beispieldaten und die Ausgabe als Ordner ohne Archiv.
Die native Windows-Abnahme und endgültige Windows-Store-Bilder stehen aus.

## Starten

Entwicklung mit einem vollständigen JDK 21. `JAVA_HOME` zeigt auf das JDK und
`PATH` enthält dessen `bin`-Verzeichnis. Der Maven Wrapper lädt Maven und
Abhängigkeiten beim ersten Start. Die fertige App funktioniert offline.

Aus `strickprojekt/` unter Linux und macOS:

```bash
./mvnw javafx:run
```

Windows PowerShell:

```powershell
.\mvnw.cmd javafx:run
```

Im vorhandenen Linux-Workspace genügt `./scripts/run.sh`. Der Helfer findet bei
fehlendem `JAVA_HOME` auch das JDK unter `$HOME/.local/jdk-21`.
Das lokal gebaute Linux-App-Image startet mit seiner eingebetteten Laufzeit:

```bash
./target/app-image/StrickProjekt/bin/StrickProjekt
```

## Bedienung

„Projekt hinzufügen“ und „Bearbeiten“ öffnen denselben Dialog. Nur der
Projektname ist erforderlich. Gleichnamige Projekte bleiben unabhängig.
Neue Projekte beginnen mit „Geplant“. Die Statusauswahl erlaubt jederzeit jeden
Wechsel, etwa von „Fertig“ zurück zu „Angefangen“. Der Status ist in der Liste
als Text sichtbar. Die Suche durchsucht alle fünf Textfelder unabhängig von
Groß- und Kleinschreibung, einschließlich Umlauten. Leeren zeigt alle Projekte.
Löschen verlangt eine Bestätigung.

Genau ein JPEG oder PNG kann hinzugefügt, ersetzt oder entfernt werden.
Die App prüft den Bildinhalt und speichert eine eigene Kopie in SQLite.
Die Quelldatei wird niemals verändert. Nach Verschieben oder Löschen der Quelle
bleibt das Foto in der App erhalten. Grenzen: 10 MiB (10.485.760 Bytes),
6000 Pixel je Seite und insgesamt 16 Millionen Pixel.

Abbrechen der Dateiauswahl und fehlgeschlagene Importe erhalten das bisherige
Foto. Abbrechen, Escape und Fensterschließen schützen ungespeicherte Texte,
Fotoänderungen und auch eine alleinige Statusänderung mit einer Rückfrage.
„Weiterbearbeiten“ ist die sichere Vorauswahl. Speicherfehler erhalten den
vollständigen Entwurf und die Statusauswahl. Erst erfolgreiches Speichern
schließt den Dialog. Während Import und Speichern wartet er auf den Abschluss.

| Taste | Aktion |
| --- | --- |
| Strg+N / Cmd+N | Projekt hinzufügen |
| Strg+F / Cmd+F | Suche auswählen |
| Enter oder Doppelklick in der Liste | Projekt bearbeiten |
| Entf in der Liste | Nach Bestätigung löschen |
| Strg+S / Cmd+S | Dialog speichern |
| Escape | Abbrechen mit Verwerfschutz |
| Pfeiltasten in der Statusauswahl | Status auswählen |
| Enter im Notizfeld | Neue Zeile |
| Tab / Umschalt+Tab | Steuerelement wechseln |

## Daten und Prüfungen

App-Kennung: `de.appfabrik.strickprojekt`. `strickprojekt.db` enthält Texte,
Foto und Status. Nutzerdaten liegen außerhalb von Programm und Quellcode.

| System | Datenverzeichnis |
| --- | --- |
| Windows | `%LOCALAPPDATA%\StrickProjekt`, sonst `~/AppData/Local/StrickProjekt` |
| macOS | `~/Library/Application Support/StrickProjekt` |
| Linux | `$XDG_DATA_HOME/strickprojekt`, sonst `~/.local/share/strickprojekt` |

Zum Sichern die App schließen und `strickprojekt.db` kopieren. Entfernte Fotos
können in SQLite intern freien Platz hinterlassen. Die Dateigröße sinkt nicht
unbedingt sofort. Mehrere gleichzeitig bearbeitende Instanzen werden nicht
unterstützt. Keine Fotos oder privaten Datenbanken ins Git-Repository aufnehmen.

Isolierter Teststart mit einem eigenen absoluten Pfad:

```bash
./scripts/run.sh '-Djavafx.args=--data-dir=/tmp/strickprojekt-test'
./target/app-image/StrickProjekt/bin/StrickProjekt --data-dir=/tmp/strickprojekt-test
```

Mit JDK 21 im `PATH`:

```bash
./mvnw spotless:apply
./mvnw verify
```

`verify` umfasst 96 JUnit-Testfälle, Compiler-Lint mit Warnungen als Fehlern,
Maven Enforcer, Spotless und SpotBugs. Tests erzeugen temporäre Datenbanken
und synthetische Bilder. GUI-Prüfungen stehen im [Prüfbericht](docs/verification.md).

## Technik und Auslieferung

Java 21, JavaFX 21.0.11, SQLite JDBC 3.53.4.0, Maven 3.9.16 über Wrapper.
Ein Maven-Modul, eigenes Git-Repository und keine gemeinsame Runtime mit
anderen Apps. [Architektur und Bewerbungsgespräch](docs/architecture.md)
erklärt insbesondere „Projektstatus ändern und speichern“.

Die [Paketierung](docs/packaging.md) bereitet Windows-EXE und macOS-DMG mit
Laufzeit vor. Linux wurde mit sichtbarer Oberfläche, SQLite, Foto und Status
geprüft. Native Windows- und macOS-Tests sowie Signierung und Store-Schritte
sind offen. Ein normaler Installer ist keine geprüfte Store-Version.
Es wurde nichts gepusht oder veröffentlicht.

[Hinweise zu Fremdkomponenten](THIRD_PARTY_NOTICES.md).
