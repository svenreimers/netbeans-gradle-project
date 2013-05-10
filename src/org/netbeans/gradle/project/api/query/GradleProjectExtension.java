package org.netbeans.gradle.project.api.query;

import java.util.List;
import org.openide.util.Lookup;

/**
 * Defines an extension of a particular Gradle project. Instances of this class
 * are expected to be created by a {@link GradleProjectExtensionQuery}.
 * <P>
 * Instances of this interface must be safe to be called by multiple threads
 * concurrently but they are not required to be
 * <I>synchronization transparent</I> unless otherwise noted.
 *
 * @see GradleProjectExtensionQuery
 */
public interface GradleProjectExtension {
    /**
     * Returns the list of model types needed to be requested from the Tooling
     * API of Gradle. The actually loaded models will be made available for
     * this extension through the {@link #modelsLoaded(Lookup) modelsLoaded}
     * method call.
     * <P>
     * Each element of the returned list defines a single model which is
     * required by this extension. A single model is defined by a preference
     * order of models where only the first available model is loaded.
     * <P>
     * For example if you return [[ModelA.class], [ModelB1.class, ModelB2.class]],
     * then if ModelB1 is available, ModelB2 will not be loaded. It is also
     * possible that models are not available, and the extension must be able
     * to handle this case.
     *
     * @return the list of model types needed to be requested from the Tooling
     *   API of Gradle. This method never returns {@code null} and none of its
     *   elements can be {@code null} (not even the class instances).
     *
     * @see #modelsLoaded(Lookup)
     */
    public Iterable<List<Class<?>>> getGradleModels();

    /**
     * Returns the lookup which is to be added to the project's lookup. That is,
     * the objects contained in the returned lookup will be possible to be
     * queried through the project's lookup.
     * <P>
     * The following queries are known and used by the Gradle plugin itself:
     * <ul>
     *  <li>{@link org.netbeans.gradle.project.api.nodes.GradleProjectContextActions}</li>
     *  <li>{@link org.netbeans.gradle.project.api.nodes.GradleProjectExtensionNodes}</li>
     * </ul>
     *
     * @return the lookup which is to be added to the project's lookup. This
     *   method may never return {@code null}.
     */
    public Lookup getExtensionLookup();

    /**
     * Called whenever the models for the associated Gradle project has been
     * (re)loaded. An invocation of this method invalidates previous invocation
     * of this method and the {@code modelsLoaded} method may not be called
     * concurrently by multiple threads for the same project.
     *
     * @param modelLookup the {@code Lookup} containing the available models
     *   loaded via the Tooling API of Gradle. If a model requested by this
     *   extension is not available on the lookup then it could not be loaded
     *   via the Tooling API. It is also possible that additional models are
     *   available on this lookup but this method must only rely on models, this
     *   extension explicitly requested. This argument cannot be {@code null}.
     *
     * @see #getGradleModels()
     */
    public void modelsLoaded(Lookup modelLookup);
}