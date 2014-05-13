/*
 * Copyright (c) 2005-2008 Obeo
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 */
package fr.obeo.acceleo.gen.ide;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import fr.obeo.acceleo.gen.template.scripts.IEvalSettings;
import fr.obeo.acceleo.gen.template.scripts.JavaIntegration;
import fr.obeo.acceleo.gen.template.scripts.imports.JavaServiceNotFoundException;
import fr.obeo.acceleo.tools.plugins.AcceleoModuleProvider;
import fr.obeo.acceleo.tools.resources.Resources;

public class JDTAwareJavaIntegration implements JavaIntegration {

    public File getScriptFileInProject(IProject project, IPath importPath) {
        File result = null;
        if (project.exists()) {
            try {
                final IJavaProject javaProject = JavaCore.create(project);
                final IClasspathEntry[] entries = javaProject.getResolvedClasspath(true);
                for (int i = 0; i < entries.length && result == null; i++) {
                    final IClasspathEntry entry = entries[i];
                    if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE && entry.getPath().segmentCount() > 1) {
                        final IFile test = ResourcesPlugin.getWorkspace().getRoot().getFile(entry.getPath().append(importPath));
                        if (test.exists()) {
                            result = test.getLocation().toFile();
                        }
                    }
                }
                for (int i = 0; i < entries.length && result == null; i++) {
                    final IClasspathEntry entry = entries[i];
                    if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT && entry.getPath().segmentCount() == 1) {
                        final IProject entryProject = ResourcesPlugin.getWorkspace().getRoot().getProject(entry.getPath().segment(0));
                        if (entryProject != null && entryProject.exists()) {
                            result = getScriptFileInProject(entryProject, importPath);
                        }
                    }
                }
            } catch (final JavaModelException e) {
                result = null;
            }
            if (result == null) {
                final String[] requiredPluginIDs = Resources.getRequiredPluginIDs(project);
                for (int i = 0; i < requiredPluginIDs.length && result == null; i++) {
                    final IProject bundleProject = ResourcesPlugin.getWorkspace().getRoot().getProject(requiredPluginIDs[i]);
                    if (bundleProject != null && bundleProject.exists()) {
                        result = getScriptFileInProject(bundleProject, importPath);
                    } else if (Platform.getBundle(requiredPluginIDs[i]) != null) {
                        result = AcceleoModuleProvider.getDefault().getFile(requiredPluginIDs[i], importPath);
                    }
                }
            }
        }
        return result;
    }

    public IEvalSettings newEvalJavaService(Object instance, boolean hasScriptContext) {
      return new JDTAwareEvalJavaService(instance, hasScriptContext);
    }

    public IEvalSettings createEvalJavaService(File file, String value) throws JavaServiceNotFoundException {
        return new JDTAwareEvalJavaService(file, value);
    }

    public IEvalSettings newEvalJavaService(File script) throws JavaServiceNotFoundException {
        return new JDTAwareEvalJavaService(script);
    }

}
