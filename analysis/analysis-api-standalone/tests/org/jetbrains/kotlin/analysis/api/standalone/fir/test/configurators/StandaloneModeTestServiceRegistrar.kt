/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.configurators

import com.intellij.mock.MockProject
import com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem
import org.jetbrains.kotlin.analysis.api.KtAnalysisApiInternals
import org.jetbrains.kotlin.analysis.api.lifetime.KtLifetimeTokenProvider
import org.jetbrains.kotlin.analysis.api.standalone.KtAlwaysAccessibleLifetimeTokenProvider
import org.jetbrains.kotlin.analysis.api.standalone.base.project.structure.LLFirStandaloneLibrarySymbolProviderFactory
import org.jetbrains.kotlin.analysis.low.level.api.fir.project.structure.LLFirLibrarySymbolProviderFactory
import org.jetbrains.kotlin.analysis.project.structure.KtBinaryModule
import org.jetbrains.kotlin.analysis.project.structure.ProjectStructureProvider
import org.jetbrains.kotlin.analysis.providers.KotlinPsiDeclarationProviderFactory
import org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticPsiDeclarationProviderFactory
import org.jetbrains.kotlin.analysis.test.framework.services.KtTestProjectStructureProvider
import org.jetbrains.kotlin.analysis.test.framework.services.environmentManager
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestServiceRegistrar
import org.jetbrains.kotlin.test.services.TestServices

@OptIn(KtAnalysisApiInternals::class)
public object StandaloneModeTestServiceRegistrar : AnalysisApiTestServiceRegistrar() {
    override fun registerProjectServices(project: MockProject, testServices: TestServices) {
        project.apply {
            registerService(KtLifetimeTokenProvider::class.java, KtAlwaysAccessibleLifetimeTokenProvider::class.java)
            registerService(LLFirLibrarySymbolProviderFactory::class.java, LLFirStandaloneLibrarySymbolProviderFactory::class.java)
        }
    }

    override fun registerProjectModelServices(project: MockProject, testServices: TestServices) {
        val projectStructureProvider = ProjectStructureProvider.getInstance(project)
        val binaryModules =
            (projectStructureProvider as? KtTestProjectStructureProvider)?.allKtModules?.filterIsInstance<KtBinaryModule>()
                ?: emptyList()
        val projectEnvironment = testServices.environmentManager.getProjectEnvironment()
        project.apply {
            registerService(
                KotlinPsiDeclarationProviderFactory::class.java,
                KotlinStaticPsiDeclarationProviderFactory(
                    this,
                    binaryModules,
                    projectEnvironment.environment.jarFileSystem as CoreJarFileSystem
                )
            )
        }
    }
}
