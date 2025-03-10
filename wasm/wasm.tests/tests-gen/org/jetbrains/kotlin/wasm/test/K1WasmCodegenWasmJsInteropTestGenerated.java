/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.wasm.test;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateWasmTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/codegen/boxWasmJsInterop")
@TestDataPath("$PROJECT_ROOT")
public class K1WasmCodegenWasmJsInteropTestGenerated extends AbstractK1WasmCodegenWasmJsInteropTest {
    @Test
    public void testAllFilesPresentInBoxWasmJsInterop() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/testData/codegen/boxWasmJsInterop"), Pattern.compile("^(.+)\\.kt$"), null, TargetBackend.WASM, true);
    }

    @Test
    @TestMetadata("callingWasmDirectly.kt")
    public void testCallingWasmDirectly() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/callingWasmDirectly.kt");
    }

    @Test
    @TestMetadata("defaultValues.kt")
    public void testDefaultValues() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/defaultValues.kt");
    }

    @Test
    @TestMetadata("externalTypeOperators.kt")
    public void testExternalTypeOperators() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/externalTypeOperators.kt");
    }

    @Test
    @TestMetadata("externals.kt")
    public void testExternals() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/externals.kt");
    }

    @Test
    @TestMetadata("externalsWithUnsigned.kt")
    public void testExternalsWithUnsigned() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/externalsWithUnsigned.kt");
    }

    @Test
    @TestMetadata("functionTypes.kt")
    public void testFunctionTypes() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/functionTypes.kt");
    }

    @Test
    @TestMetadata("imperativeWrapperInitialised.kt")
    public void testImperativeWrapperInitialised() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/imperativeWrapperInitialised.kt");
    }

    @Test
    @TestMetadata("imperativeWrapperUninitialised.kt")
    public void testImperativeWrapperUninitialised() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/imperativeWrapperUninitialised.kt");
    }

    @Test
    @TestMetadata("jsCode.kt")
    public void testJsCode() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsCode.kt");
    }

    @Test
    @TestMetadata("jsExport.kt")
    public void testJsExport() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsExport.kt");
    }

    @Test
    @TestMetadata("jsModule.kt")
    public void testJsModule() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsModule.kt");
    }

    @Test
    @TestMetadata("jsModuleWithQualifier.kt")
    public void testJsModuleWithQualifier() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsModuleWithQualifier.kt");
    }

    @Test
    @TestMetadata("jsQualifier.kt")
    public void testJsQualifier() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsQualifier.kt");
    }

    @Test
    @TestMetadata("jsToKotlinAdapters.kt")
    public void testJsToKotlinAdapters() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsToKotlinAdapters.kt");
    }

    @Test
    @TestMetadata("jsTypes.kt")
    public void testJsTypes() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsTypes.kt");
    }

    @Test
    @TestMetadata("kotlinToJsAdapters.kt")
    public void testKotlinToJsAdapters() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/kotlinToJsAdapters.kt");
    }

    @Test
    @TestMetadata("kt59082.kt")
    public void testKt59082() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/kt59082.kt");
    }

    @Test
    @TestMetadata("kt59084.kt")
    public void testKt59084() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/kt59084.kt");
    }

    @Test
    @TestMetadata("lambdaAdapterNameClash.kt")
    public void testLambdaAdapterNameClash() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/lambdaAdapterNameClash.kt");
    }

    @Test
    @TestMetadata("longStrings.kt")
    public void testLongStrings() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/longStrings.kt");
    }

    @Test
    @TestMetadata("nameClash.kt")
    public void testNameClash() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/nameClash.kt");
    }

    @Test
    @TestMetadata("noExceptions.kt")
    public void testNoExceptions() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/noExceptions.kt");
    }

    @Test
    @TestMetadata("nullableExternRefs.kt")
    public void testNullableExternRefs() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/nullableExternRefs.kt");
    }

    @Test
    @TestMetadata("types.kt")
    public void testTypes() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/types.kt");
    }

    @Test
    @TestMetadata("vararg.kt")
    public void testVararg() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/vararg.kt");
    }

    @Test
    @TestMetadata("wasmExport.kt")
    public void testWasmExport() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/wasmExport.kt");
    }

    @Test
    @TestMetadata("wasmImport.kt")
    public void testWasmImport() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/wasmImport.kt");
    }
}
