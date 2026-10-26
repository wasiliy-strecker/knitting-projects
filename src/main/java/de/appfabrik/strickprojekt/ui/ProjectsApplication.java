package de.appfabrik.strickprojekt.ui;

import de.appfabrik.strickprojekt.application.ProjectService;
import de.appfabrik.strickprojekt.persistence.AppDataDirectory;
import de.appfabrik.strickprojekt.persistence.SqliteProjectRepository;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public final class ProjectsApplication extends Application {
  private final AsyncTasks tasks = new AsyncTasks();

  @Override
  public void start(Stage stage) {
    try {
      String configured = getParameters().getNamed().get("data-dir");
      Path directory = configured == null ? AppDataDirectory.current() : Path.of(configured);
      if (configured != null && configured.isBlank()) {
        throw new IllegalArgumentException("Empty data directory");
      }
      ProjectService service =
          new ProjectService(new SqliteProjectRepository(directory.resolve("strickprojekt.db")));
      new MainWindow(stage, service, tasks).show();
    } catch (RuntimeException exception) {
      Alert alert =
          new Alert(
              Alert.AlertType.ERROR,
              "StrickProjekt konnte nicht gestartet werden. Bitte prüfe den angegebenen Datenpfad.");
      alert.setHeaderText("Start nicht möglich");
      alert.showAndWait();
      javafx.application.Platform.exit();
    }
  }

  @Override
  public void stop() {
    tasks.close();
  }
}
