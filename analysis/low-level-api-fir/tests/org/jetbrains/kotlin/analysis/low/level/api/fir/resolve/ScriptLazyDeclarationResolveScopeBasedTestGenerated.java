/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.low.level.api.fir.resolve;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/low-level-api-fir/testData/lazyResolveScopes")
@TestDataPath("$PROJECT_ROOT")
public class ScriptLazyDeclarationResolveScopeBasedTestGenerated extends AbstractScriptLazyDeclarationResolveScopeBasedTest {
    @Test
    public void testAllFilesPresentInLazyResolveScopes() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/low-level-api-fir/testData/lazyResolveScopes"), Pattern.compile("^(.+)\\.(kts)$"), null, true);
    }

    @Test
    @TestMetadata("anonymousObjectScript.kts")
    public void testAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/anonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("classDeclarationsScript.kts")
    public void testClassDeclarationsScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/classDeclarationsScript.kts");
    }

    @Test
    @TestMetadata("classFromStatement.kts")
    public void testClassFromStatement() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/classFromStatement.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithImplicitTypeInsideAnonymousObjectScript.kts")
    public void testDelegateOverrideWithImplicitTypeInsideAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithImplicitTypeInsideAnonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithImplicitTypeInsideAnonymousObjectWithSubstitutionScript.kts")
    public void testDelegateOverrideWithImplicitTypeInsideAnonymousObjectWithSubstitutionScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithImplicitTypeInsideAnonymousObjectWithSubstitutionScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithImplicitTypeInsideClassScript.kts")
    public void testDelegateOverrideWithImplicitTypeInsideClassScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithImplicitTypeInsideClassScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithImplicitTypeInsideClassWithSubstitutionScript.kts")
    public void testDelegateOverrideWithImplicitTypeInsideClassWithSubstitutionScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithImplicitTypeInsideClassWithSubstitutionScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithoutImplicitTypeInsideAnonymousObjectScript.kts")
    public void testDelegateOverrideWithoutImplicitTypeInsideAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithoutImplicitTypeInsideAnonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithoutImplicitTypeInsideAnonymousObjectWithSubstitutionScript.kts")
    public void testDelegateOverrideWithoutImplicitTypeInsideAnonymousObjectWithSubstitutionScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithoutImplicitTypeInsideAnonymousObjectWithSubstitutionScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithoutImplicitTypeInsideClassScript.kts")
    public void testDelegateOverrideWithoutImplicitTypeInsideClassScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithoutImplicitTypeInsideClassScript.kts");
    }

    @Test
    @TestMetadata("delegateOverrideWithoutImplicitTypeInsideClassWithSubstitutionScript.kts")
    public void testDelegateOverrideWithoutImplicitTypeInsideClassWithSubstitutionScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/delegateOverrideWithoutImplicitTypeInsideClassWithSubstitutionScript.kts");
    }

    @Test
    @TestMetadata("intersectionOverride2Script.kts")
    public void testIntersectionOverride2Script() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/intersectionOverride2Script.kts");
    }

    @Test
    @TestMetadata("intersectionOverrideScript.kts")
    public void testIntersectionOverrideScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/intersectionOverrideScript.kts");
    }

    @Test
    @TestMetadata("intersectionOverrideWithImplicitTypeInsideAnonymousObjectScript.kts")
    public void testIntersectionOverrideWithImplicitTypeInsideAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/intersectionOverrideWithImplicitTypeInsideAnonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("intersectionOverrideWithoutImplicitTypeInsideAnonymousObjectScript.kts")
    public void testIntersectionOverrideWithoutImplicitTypeInsideAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/intersectionOverrideWithoutImplicitTypeInsideAnonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("localClassScript.kts")
    public void testLocalClassScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/localClassScript.kts");
    }

    @Test
    @TestMetadata("substitutionOverrideWithImplicitTypeInsideAnonymousObjectScript.kts")
    public void testSubstitutionOverrideWithImplicitTypeInsideAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/substitutionOverrideWithImplicitTypeInsideAnonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("substitutionOverrideWithImplicitTypeInsideClassScript.kts")
    public void testSubstitutionOverrideWithImplicitTypeInsideClassScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/substitutionOverrideWithImplicitTypeInsideClassScript.kts");
    }

    @Test
    @TestMetadata("substitutionOverrideWithoutImplicitTypeInsideAnonymousObjectScript.kts")
    public void testSubstitutionOverrideWithoutImplicitTypeInsideAnonymousObjectScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/substitutionOverrideWithoutImplicitTypeInsideAnonymousObjectScript.kts");
    }

    @Test
    @TestMetadata("substitutionOverrideWithoutImplicitTypeInsideClassScript.kts")
    public void testSubstitutionOverrideWithoutImplicitTypeInsideClassScript() throws Exception {
        runTest("analysis/low-level-api-fir/testData/lazyResolveScopes/substitutionOverrideWithoutImplicitTypeInsideClassScript.kts");
    }
}
