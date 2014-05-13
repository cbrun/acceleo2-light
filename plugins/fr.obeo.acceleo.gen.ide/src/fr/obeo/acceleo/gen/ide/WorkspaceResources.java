package fr.obeo.acceleo.gen.ide;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class WorkspaceResources {

    /**
     * Gets the default output of the project.
     * 
     * @param project
     *            is the project
     * @return the default output of the project, or null if it doesn't exist
     */
    public static IFolder getOutputFolder(IProject project) {
        final IJavaProject projet = JavaCore.create(project);
        try {
            IPath output = projet.getOutputLocation();
            if (output != null && output.segmentCount() > 1) {
                IFolder folder = project.getWorkspace().getRoot().getFolder(output);
                if (folder.exists()) {
                    return folder;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (JavaModelException e) {
            return null;
        }
    }

}
