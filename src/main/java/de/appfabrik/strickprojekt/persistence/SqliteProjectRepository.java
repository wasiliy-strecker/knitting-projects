package de.appfabrik.strickprojekt.persistence;

import de.appfabrik.strickprojekt.application.PhotoException;
import de.appfabrik.strickprojekt.application.PhotoImporter;
import de.appfabrik.strickprojekt.application.ProjectRepository;
import de.appfabrik.strickprojekt.application.StorageException;
import de.appfabrik.strickprojekt.domain.Photo;
import de.appfabrik.strickprojekt.domain.Project;
import de.appfabrik.strickprojekt.domain.ProjectDraft;
import de.appfabrik.strickprojekt.domain.ProjectStatus;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteConfig;

/** Opens and closes one JDBC connection per operation. No connection escapes this class. */
public final class SqliteProjectRepository implements ProjectRepository {
  private final Path database;

  public SqliteProjectRepository(Path database) {
    this.database = database.toAbsolutePath().normalize();
  }

  private Connection connect() throws SQLException {
    SQLiteConfig config = new SQLiteConfig();
    config.setBusyTimeout(2000);
    return config.createConnection("jdbc:sqlite:" + database);
  }

  @Override
  public void initialize() {
    try {
      Path parent = database.getParent();
      if (parent == null) {
        throw new StorageException(StorageException.Code.PATH);
      }
      Files.createDirectories(parent);
      try (Connection connection = connect()) {
        connection.setAutoCommit(false);
        try (var statement = connection.createStatement()) {
          int version;
          try (var result = statement.executeQuery("PRAGMA user_version")) {
            result.next();
            version = result.getInt(1);
          }
          if (version > 1) {
            throw new StorageException(StorageException.Code.NEWER_SCHEMA);
          }
          if (version == 0) {
            statement.executeUpdate(
                """
                CREATE TABLE IF NOT EXISTS projects (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  name TEXT NOT NULL CHECK (length(trim(name)) > 0),
                  material TEXT NOT NULL DEFAULT '',
                  color TEXT NOT NULL DEFAULT '',
                  location TEXT NOT NULL DEFAULT '',
                  note TEXT NOT NULL DEFAULT '',
                  photo BLOB CHECK (photo IS NULL OR length(photo) <= 10485760),
                  status TEXT NOT NULL DEFAULT 'planned'
                    CHECK (status IN ('planned', 'started', 'finished'))
                )
                """);
            statement.executeUpdate("PRAGMA user_version = 1");
          }
          connection.commit();
        } catch (SQLException | RuntimeException exception) {
          connection.rollback();
          throw exception;
        }
      }
    } catch (IOException | SQLException exception) {
      throw new StorageException(StorageException.Code.OPEN, exception);
    }
  }

  @Override
  public List<Project> findAll() {
    try (Connection connection = connect();
        var statement = connection.createStatement();
        var result =
            statement.executeQuery(
                "SELECT id, name, material, color, location, note, status, photo IS NOT NULL AS has_photo FROM projects")) {
      List<Project> projects = new ArrayList<>();
      while (result.next()) {
        projects.add(
            new Project(
                result.getLong("id"),
                result.getString("name"),
                result.getString("material"),
                result.getString("color"),
                result.getString("location"),
                result.getString("note"),
                result.getBoolean("has_photo"),
                ProjectStatus.fromStoredValue(result.getString("status"))));
      }
      return List.copyOf(projects);
    } catch (IllegalArgumentException exception) {
      throw new StorageException(StorageException.Code.STATUS, exception);
    } catch (SQLException exception) {
      throw failure(exception);
    }
  }

  @Override
  public Photo findPhoto(long id) {
    try (Connection connection = connect();
        var statement = connection.prepareStatement("SELECT photo FROM projects WHERE id = ?")) {
      statement.setLong(1, id);
      try (var result = statement.executeQuery()) {
        if (!result.next()) {
          throw new StorageException(StorageException.Code.MISSING);
        }
        byte[] bytes = result.getBytes(1);
        return bytes == null ? null : PhotoImporter.decode(bytes);
      }
    } catch (SQLException exception) {
      throw failure(exception);
    } catch (PhotoException exception) {
      throw new StorageException(StorageException.Code.PHOTO, exception);
    }
  }

  @Override
  public Project create(ProjectDraft draft) {
    try (Connection connection = connect();
        PreparedStatement statement =
            connection.prepareStatement(
                "INSERT INTO projects (name, material, color, location, note, photo, status) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id")) {
      bind(statement, draft);
      try (ResultSet result = statement.executeQuery()) {
        if (!result.next()) {
          throw new StorageException(StorageException.Code.CREATE);
        }
        return draft.withId(result.getLong(1));
      }
    } catch (SQLException exception) {
      throw failure(exception);
    }
  }

  @Override
  public Project update(long id, ProjectDraft draft) {
    try (Connection connection = connect();
        PreparedStatement statement =
            connection.prepareStatement(
                "UPDATE projects SET name = ?, material = ?, color = ?, location = ?, note = ?, photo = ?, status = ? WHERE id = ?")) {
      bind(statement, draft);
      statement.setLong(8, id);
      requireExisting(statement.executeUpdate());
      return draft.withId(id);
    } catch (SQLException exception) {
      throw failure(exception);
    }
  }

  @Override
  public void delete(long id) {
    try (Connection connection = connect();
        PreparedStatement statement =
            connection.prepareStatement("DELETE FROM projects WHERE id = ?")) {
      statement.setLong(1, id);
      requireExisting(statement.executeUpdate());
    } catch (SQLException exception) {
      throw failure(exception);
    }
  }

  private static void bind(PreparedStatement statement, ProjectDraft draft) throws SQLException {
    statement.setString(1, draft.name());
    statement.setString(2, draft.material());
    statement.setString(3, draft.color());
    statement.setString(4, draft.location());
    statement.setString(5, draft.note());
    statement.setBytes(6, draft.photo() == null ? null : draft.photo().bytes());
    statement.setString(7, draft.status().storedValue());
  }

  private static void requireExisting(int affected) {
    if (affected != 1) {
      throw new StorageException(StorageException.Code.MISSING);
    }
  }

  private static StorageException failure(SQLException exception) {
    return new StorageException(StorageException.Code.OPERATION, exception);
  }
}
