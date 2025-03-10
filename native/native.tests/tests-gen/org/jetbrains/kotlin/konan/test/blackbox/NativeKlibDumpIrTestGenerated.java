/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.konan.test.blackbox;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateNativeTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("native/native.tests/testData/klib/dump-ir")
@TestDataPath("$PROJECT_ROOT")
public class NativeKlibDumpIrTestGenerated extends AbstractNativeKlibDumpIrTest {
    @Test
    public void testAllFilesPresentInDump_ir() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/native.tests/testData/klib/dump-ir"), Pattern.compile("^([^_](.+)).kt$"), null, true);
    }

    @Test
    @TestMetadata("class.kt")
    public void testClass() throws Exception {
        runTest("native/native.tests/testData/klib/dump-ir/class.kt");
    }

    @Test
    @TestMetadata("constructor.kt")
    public void testConstructor() throws Exception {
        runTest("native/native.tests/testData/klib/dump-ir/constructor.kt");
    }

    @Test
    @TestMetadata("enum.kt")
    public void testEnum() throws Exception {
        runTest("native/native.tests/testData/klib/dump-ir/enum.kt");
    }

    @Test
    @TestMetadata("field.kt")
    public void testField() throws Exception {
        runTest("native/native.tests/testData/klib/dump-ir/field.kt");
    }

    @Test
    @TestMetadata("fun.kt")
    public void testFun() throws Exception {
        runTest("native/native.tests/testData/klib/dump-ir/fun.kt");
    }

    @Test
    @TestMetadata("typealias.kt")
    public void testTypealias() throws Exception {
        runTest("native/native.tests/testData/klib/dump-ir/typealias.kt");
    }
}
