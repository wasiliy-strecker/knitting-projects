package de.appfabrik.strickprojekt.domain;

/** Lightweight list entry. Image bytes are loaded only for the selected entry. */
public record Project(
    long id,
    String name,
    String material,
    String color,
    String location,
    String note,
    boolean hasPhoto,
    ProjectStatus status) {
  public Project {
    java.util.Objects.requireNonNull(status, "status");
  }
}
