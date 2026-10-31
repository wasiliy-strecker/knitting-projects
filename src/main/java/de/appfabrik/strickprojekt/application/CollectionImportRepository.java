package de.appfabrik.strickprojekt.application;

/** Transactional import port, separate from ordinary single-entry operations. */
public interface CollectionImportRepository {
  ImportResult importProjects(ProjectImportSource source);
}
