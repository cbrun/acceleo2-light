package fr.obeo.acceleo.gen.template.scripts.imports;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import fr.obeo.acceleo.gen.template.scripts.IEvalSettings;
import fr.obeo.acceleo.gen.template.scripts.JavaIntegration;

public class StdJavaIntergation implements JavaIntegration {

    public File getScriptFileInProject(IProject project, IPath importPath) {
        return null;
    }

    public IEvalSettings createEvalJavaService(File file, String value) throws JavaServiceNotFoundException {
        return new EvalJavaService(file, value);
    }

    public IEvalSettings newEvalJavaService(Object instance, boolean hasScriptContext) {
        return new EvalJavaService(instance, hasScriptContext);
    }

    public IEvalSettings newEvalJavaService(File script) throws JavaServiceNotFoundException {
        return new EvalJavaService(script);
    }

}
