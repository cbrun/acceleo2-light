package fr.obeo.acceleo.gen.template.scripts;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public interface IScriptFinder {
    File getScriptFileInProject(IProject project, IPath importPath);
}
