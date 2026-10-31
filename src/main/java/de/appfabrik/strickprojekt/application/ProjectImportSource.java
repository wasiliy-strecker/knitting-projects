package de.appfabrik.strickprojekt.application;

import de.appfabrik.strickprojekt.domain.ProjectDraft;
import java.util.function.Consumer;

/** Supplies validated entries one at a time. Failures must abort the whole import. */
@FunctionalInterface
public interface ProjectImportSource {
  void read(Consumer<ProjectDraft> accept);
}
