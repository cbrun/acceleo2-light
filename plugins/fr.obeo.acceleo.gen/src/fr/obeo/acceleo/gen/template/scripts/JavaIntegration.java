package fr.obeo.acceleo.gen.template.scripts;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import fr.obeo.acceleo.gen.template.scripts.imports.JavaServiceNotFoundException;

public interface JavaIntegration {
    File getScriptFileInProject(IProject project, IPath importPath);

    IEvalSettings newEvalJavaService(Object instance, boolean hasScriptContext);
    
    
    IEvalSettings createEvalJavaService(File script, String value) throws JavaServiceNotFoundException;


    IEvalSettings newEvalJavaService(File script) throws JavaServiceNotFoundException;
}
