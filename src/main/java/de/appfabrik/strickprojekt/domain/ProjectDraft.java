package de.appfabrik.strickprojekt.domain;

/** Complete replacement, including one optional immutable photo and the status. */
public record ProjectDraft(
    String name,
    String material,
    String color,
    String location,
    String note,
    Photo photo,
    ProjectStatus status) {
  /** A new project starts planned unless the user chooses another status. */
  public ProjectDraft(
      String name, String material, String color, String location, String note, Photo photo) {
    this(name, material, color, location, note, photo, ProjectStatus.PLANNED);
  }

  public ProjectDraft {
    if (status == null) {
      throw new IllegalArgumentException("Bitte wähle einen Projektstatus aus.");
    }
    name = name == null ? "" : name.strip();
    if (name.codePoints().noneMatch(c -> !Character.isWhitespace(c) && !Character.isSpaceChar(c))) {
      throw new IllegalArgumentException("Bitte gib einen Projektnamen ein.");
    }
    material = material == null ? "" : material.strip();
    color = color == null ? "" : color.strip();
    location = location == null ? "" : location.strip();
    note = note == null ? "" : note;
  }

  public Project withId(long id) {
    return new Project(id, name, material, color, location, note, photo != null, status);
  }
}
