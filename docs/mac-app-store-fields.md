# Knitting Projects in App Store Connect vorbereiten

Produkt **Knitting Projects**, macOS-Version **1.0**, Store-Sprache **Deutsch**.
Der Nutzer hat die registrierte Bundle-ID **de.appfabrik.knittingprojects**
bestätigt. Diese Lieferung enthält ausschließlich Store-Inhalte und Bildentwürfe.
Die App startet weiterhin auf Englisch. Alle sechs UI-Sprachen bleiben verfügbar.

## Die Versionsseite ausfüllen

Die Dateien unter `Store/de-DE` in der Übergabe enthalten jeweils nur den
Feldinhalt. Im Repository liegen sie unter
`store-listings/mac-app-store/de-DE`. Den Inhalt ohne Dateiname oder
Markdown-Markierung kopieren. Werbetext und Beschreibung sind unterschiedlich.

| Feld in App Store Connect | Datei oder Einstellung |
| --- | --- |
| Werbetexte | `promotional-text.txt` |
| Beschreibung | `description.txt` |
| Schlüsselwörter | `keywords.txt` |
| Support-URL | `support-url.txt` |
| Marketing-URL | `marketing-url.txt` |
| Version | `version.txt`, Wert 1.0 |
| Copyright | `copyright.txt` |
| Game Center | Ausgeschaltet lassen |
| Anmeldung erforderlich | Häkchen entfernen. Kein Demo-Konto nötig |
| Anmerkungen zur App-Prüfung | `review-notes.txt` |
| Anhang | Für diese App nicht erforderlich |
| Versionsveröffentlichung | Die bereits gewählte automatische Veröffentlichung nach Apples Freigabe beibehalten |

Die vorhandenen Texte anschließend in App Store Connect sichern.
Der letzte Schritt „Zur Prüfung hinzufügen“ gehört nicht zu dieser Vorbereitung.

Die Kontaktperson für die App-Prüfung muss vor der Einreichung erreichbar sein.
Vorname Wasiliy und Nachname Strecker sind bekannt. Die für Rückfragen gewünschte
E-Mail und eine erreichbare Telefonnummer mit Landesvorwahl im Konto ergänzen.
Diese persönlichen Angaben stehen absichtlich nicht in öffentlichen Feldtexten.
Die öffentliche Support-Adresse ersetzt keine bestätigten Prüferkontaktdaten.

`release-notes.txt` ist eine vorbereitete Erstversionsnotiz. Für die erste Version
zeigt Apple kein Feld „Neu in dieser Version“ an. Die Notiz deshalb nicht
zusätzlich in andere Felder einfügen.

## App-Informationen

Name aus `name.txt`, Untertitel aus `subtitle.txt`, Primärsprache Deutsch.
Kategorie Produktivität. `bundle-id.txt` dokumentiert die bestätigte Kennung
de.appfabrik.knittingprojects. Die vorhandene Registrierung beibehalten.

Die Texte beschreiben die vorhandenen Funktionen: Strick- und Häkelprojekte,
genau ein optionales Foto, Farbauswahl, drei Statuswerte, Notizen, Suche,
Sortierung und JSON-Transfer. Keine Checklisten, PDFs, Konten oder Cloud-Funktionen.
Der Stand enthält keine Werbung, Analytics, In-App-Käufe oder Netzwerkübertragung
der Sammlung. Datenschutz- und Altersfreigabe-Fragen anhand dieses Funktionsumfangs
und des späteren endgültigen Mac-Builds beantworten. Es gibt kein öffentliches
Teilen von Nutzerinhalten und keine Kommunikation innerhalb der App.

## Bildentwürfe und spätere Mac-Aufnahmen

`Screenshot-Drafts-Linux/de-DE` enthält vier echte Aufnahmen der aktuellen
JavaFX-App. Die Bilder sind Linux-Entwürfe und noch nicht für den Mac-Store-Upload
freigegeben. Keine macOS-Fensterdekoration wurde nachgebaut. Sämtliche Daten und
die drei app-eigenen Illustrationen sind synthetisch.

| Bild | Ansicht | Bildtext |
| --- | --- | --- |
| 01-overview.png | Sammlung mit fünf Projekten, allen drei Statuswerten und ausgewähltem Foto | Deine Projekte mit Foto und Status im Überblick |
| 02-edit-status.png | Projekteditor mit Foto, Farbfeld und geöffneter Statusauswahl | Von geplant bis fertig: alles im Projekt festhalten |
| 03-search.png | Suche nach Strickjacke mit sichtbarer aktiver Sortierung | Strick- und Häkelideen schnell wiederfinden |
| 04-json-export.png | Tatsächlich erfolgreich abgeschlossener JSON-Export mit Bestätigung | Projekte mit Fotos und Status als JSON sichern |

Alle Entwürfe haben 2560 × 1600 Pixel, RGB und keine Transparenz. Die Bildtexte
stehen zusätzlich zeilenweise in `Store/de-DE/captions.txt`. Es sind redaktionelle
Zuordnungen, keine in App Store Connect benötigten separaten Screenshot-Felder.
Ein Vorschauvideo wird nicht erstellt.

Für die endgültigen Aufnahmen die später gebaute Mac-App mit einem separaten
Testbenutzer verwenden. Deutsch einstellen, neu starten und
`Demo/knitting-projects-de-DE.json` importieren. Die fünf Projekte enthalten
Geplant, Angefangen und Fertig, fünf Farbcodes und drei eigene Illustrationen.
Die vier beschriebenen Ansichten erneut als RGB-PNG in 2560 × 1600 aufnehmen.
Die fertigen Mac-Bilder unter `Screenshots-Final-Mac/de-DE` ablegen.
Dieser Ordner bleibt in der aktuellen Lieferung leer.

## Datenschutz

`Privacy/de-DE.txt` und `Privacy/index.html` enthalten die vollständige bestehende
deutsche In-App-Erklärung, ohne zusätzliche Behauptungen zu einem ungeprüften
Mac-Build. Der Export verwendet dieselbe produktive Quelle wie der Info-Dialog.
Beschrieben sind lokale Projekttexte und Statuswerte, Fotos, Fotooptimierung,
Sprache, gemerkter Fotoordner sowie JSON-Import und JSON-Export.

Die öffentliche Datenschutz-URL ist noch offen. Vor der Einreichung die Erklärung
öffentlich ohne Anmeldung bereitstellen und die Adresse in App Store Connect
eintragen. Eine lokale HTML-Datei ist noch keine öffentliche Datenschutzseite.
Diese Lieferung veröffentlicht nichts. Beim späteren Mac-Release die vorhandenen
Bezugskanal-Hinweise der Erklärung auf Apple erweitern und App-Dialog und
veröffentlichte Erklärung weiterhin aus derselben Quelle erzeugen.

## Build und Sandbox bleiben ein eigener Auftrag

Diese Lieferung enthält weder Mac-App, PKG, Laufzeitarchive noch Signierungshelfer.
Der Bereich „Build“ bleibt bis zum tatsächlichen Mac-Bau und Upload offen.
Informationen zur App-Sandbox und zur Verschlüsselung erst anhand des endgültigen
Bundles einschließlich Java-Laufzeit eintragen. Keine Angaben allein aus dem
Offline-Betrieb oder aus den Vorbereitungen einer anderen App übernehmen.

Der bestehende normale Mac-Pakethelfer verwendet noch die historische Kennung
de.appfabrik.strickprojekt. Im späteren technischen Releaseauftrag die relevante
Bundle-Konfiguration auf de.appfabrik.knittingprojects ausrichten. Diese
Store-Inhaltsvorbereitung verändert weder die Paketierung noch Datenpfade,
Installationen oder Signieridentitäten. Auch eine native Windows- oder
Mac-Funktionsabnahme wird durch die Bildentwürfe nicht ersetzt.

## Inhalte erneut erzeugen

Aus dem App-Verzeichnis mit Python 3.12 und Pillow, Eclipse Temurin 21.0.13+9,
Xvfb und xdotool. `KNITPROJECT_XVFB` kann auf ein vorhandenes Xvfb zeigen. Die Aufnahme
verwendet ausschließlich ihren eigenen X-Bildschirm und eine temporäre Sammlung.
Für Bilder und Übergabe jeweils einen noch nicht existierenden Ausgabeordner
wählen. Bestehende Lieferungen werden nicht überschrieben.

```bash
export JAVA_HOME="$HOME/.local/share/strickprojekt/jdk-21.0.13+9"
export PATH="$JAVA_HOME/bin:$PATH"
python3 scripts/mac_store_content.py check --check-links
python3 scripts/mac_store_content.py capture --output target/mac-store-content-drafts-neu
```

Die Aufnahme kompiliert die vorhandene App und den separaten Aufnahmehelfer.
Sie führt keine vollständige Testsuite aus. Der synthetische Beispielbestand
wird auf Inhalt, Fotos, Farben, Statuswerte und erneuten Import geprüft.
Bild 4 stammt aus dem tatsächlich abgeschlossenen Export über die Oberfläche.
Alle vier Aufnahmen vor der Übergabe visuell prüfen.

Nach den Prüfungen Quellen lokal committen. Anschließend den Übergabeordner
aus genau diesem Stand erzeugen:

```bash
python3 scripts/mac_store_content.py assemble --captures target/mac-store-content-drafts-neu --output build/releases/mac-store-content/Knitting-Projects-1.0-de-DE-neu
```

Die Übergabe prüft Feldlängen, Bildmaße, Quellstand, Dateihashes und den
synthetischen Import-/Exportnachweis. Sie enthält nur ausdrücklich ausgewählte
Store-Dateien. Persönliche unversionierte JSON-Exporte werden nicht eingelesen.
Es wird kein ZIP erstellt. `SHA256SUMS.txt` erfasst alle gelieferten Dateien.

## Quellen

[Apple: Felder der Versionsseite und App-Prüfung](https://developer.apple.com/help/app-store-connect/reference/app-information/platform-version-information/)

[Apple: Screenshot-Größen](https://developer.apple.com/help/app-store-connect/reference/app-information/screenshot-specifications/)

[Apple: App-Informationen](https://developer.apple.com/help/app-store-connect/reference/app-information/app-information/)
