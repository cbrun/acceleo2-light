package fr.obeo.acceleo.gen.ide;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import fr.obeo.acceleo.gen.template.scripts.imports.EvalJavaService;
import fr.obeo.acceleo.gen.template.scripts.imports.JavaServiceNotFoundException;
import fr.obeo.acceleo.tools.plugins.AcceleoModuleProvider;

public class JDTAwareEvalJavaService extends EvalJavaService {

    public JDTAwareEvalJavaService(File script) throws JavaServiceNotFoundException {
        super(script);
    }

    public JDTAwareEvalJavaService(File script, String className) throws JavaServiceNotFoundException {
        super(script, className);
    }

    public JDTAwareEvalJavaService(Object instance, boolean hasScriptContext) {
        super(instance, hasScriptContext);
    }

    /**
     * Creates the default class loader for the given script.
     * 
     * @param script
     *            is the script
     * @return the default class loader
     */
    @Override
    protected ClassLoader createClassLoader(File script) {
        final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(script.getAbsolutePath()));
        if (file != null && file.isAccessible()) {
            return new AcceleoGenClassLoader(file.getProject(), EvalJavaService.class.getClassLoader());
        }
        final String pluginId = AcceleoModuleProvider.getDefault().getPluginId(script);
        if (pluginId != null) {
            final Bundle bundle = Platform.getBundle(pluginId);
            if (bundle != null) {
                return new AcceleoGenClassLoader(bundle, EvalJavaService.class.getClassLoader());
            }
        }
        return EvalJavaService.class.getClassLoader();
    }

}
