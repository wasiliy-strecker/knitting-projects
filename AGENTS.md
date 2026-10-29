# StrickProjekt

Eigenständiges Java-Projekt mit eigenem Git-Repository. Die übergeordneten
Workspace-Regeln gelten ergänzend. BücherRegal und GarnBox nicht verändern.

## Umfang und Architektur

Stufe 3 des Hobby-Portfolios. Projekte mit Name, Garn oder Material, Farbe, Ort,
Notiz, genau einem optionalen Foto und Status verwalten. Genau drei Statuswerte:
Geplant, Angefangen, Fertig. Keine Checklisten, PDFs oder zusätzlichen Hauptfunktionen.
Java 21, JavaFX 21.0.11, SQLite JDBC und ein Maven-Modul. Pakete `domain`,
`application`, `persistence` und `ui`. Keine gemeinsame Runtime mit anderen Apps.
Java-Bezeichner englisch, Projektdokumentation deutsch. Die Oberfläche unterstützt
genau de-DE, en-US, es-ES, fr-FR, it-IT und pt-BR mit Englisch als Rückfall.

## Tatsächlich verwendete Befehle

Aus dem App-Verzeichnis mit vollständigem JDK 21:

```bash
./mvnw spotless:apply
./mvnw verify
./scripts/run.sh
./scripts/package.sh app-image
./target/app-image/StrickProjekt/bin/StrickProjekt
bash -n scripts/run.sh scripts/package.sh
git diff --cached --check
```

`run.sh` und `package.sh` verwenden bei fehlendem `JAVA_HOME` ein vorhandenes
JDK unter `$HOME/.local/jdk-21`. Direkte Wrapper-Aufrufe benötigen `JAVA_HOME`
und dessen `bin` im `PATH`. `./mvnw javafx:run` startet die Entwicklungsversion.
Unter Windows `mvnw.cmd` verwenden.

Vorbereitete native Wege, hier noch nicht ausgeführt:

```powershell
.\mvnw.cmd verify
.\scripts\package.ps1 -PackageType exe
```

```bash
./scripts/package.sh dmg
```

## Prüf- und Speicherregeln

`verify` umfasst Tests, Compiler-Lint, Enforcer, Spotless und SpotBugs.
GUI-Abläufe mit eigenem `--data-dir` gemäß `docs/verification.md` prüfen.
Ein Versionsaufruf ersetzt keine gestartete Oberfläche mit SQLite, Foto und Status.

`ProjectStatus` trennt stabile gespeicherte Werte von übersetzten Anzeigenamen.
Niemals Ordinalzahlen speichern. Neue Projekte sind `PLANNED`. Alle Wechsel
sind erlaubt. Der SQLite-CHECK begrenzt die Werte zusätzlich. Unbekannte Werte
beim Lesen melden, niemals still auf Geplant zurücksetzen.

Texte, Foto und Status werden gemeinsam atomar gespeichert. Statusänderungen
gehören zum Verwerfschutz bei Abbrechen, Escape und Fensterschließen.
Ein Dialog schließt erst nach erfolgreichem Speichern. Fehler erhalten den
vollständigen Entwurf. Kein UI-Callback darf eine veraltete Vorschau anzeigen.

Bildinhalt vollständig prüfen. Grenzen: 10 MiB, 6000 Pixel je Seite,
16 Millionen Pixel. Datenbankzugriff und Bildladen außerhalb des JavaFX-Threads.
Keine Originaldateien ändern oder löschen. Nur synthetische Testdaten nutzen.

Vor lokalem Commit Formatter, `verify` und erforderliche GUI-Prüfungen abschließen.
Die Sprachwahl wird in `language.txt` neben SQLite gespeichert und gilt beim
nächsten Start. Schema, Datenpfade und Statuscodes nicht für Übersetzungen ändern.
Alle sechs UTF-8-Kataloge vollständig halten. Eigene Text-Kontextmenüs übersetzen.
GUI-Prüfungen mit `scripts/store_assets/run_gui.py` verwenden einen eigenen Xvfb
über `-displayfd`. Niemals einen vorhandenen Desktop oder Benutzerdaten verwenden.
Die Lieferung ist ein Ordner ohne ZIP oder anderes Archiv. Siehe
[Internationalisierung](docs/internationalization.md).

Changelog unter `CODEX/changelog` im Workspace ergänzen. Keine Datenbanken,
Build-Ausgaben oder Secrets committen. Kein Push oder Veröffentlichen ohne Auftrag.
