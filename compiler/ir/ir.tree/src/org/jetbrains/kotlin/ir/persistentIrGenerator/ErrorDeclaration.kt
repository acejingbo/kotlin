/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.persistentIrGenerator


internal fun PersistentIrGenerator.generateErrorDeclaration() {
    writeFile("PersistentIrErrorDeclaration.kt", renderFile("org.jetbrains.kotlin.ir.declarations.persistent") {
        lines(
            id,
            +"@OptIn(" + ObsoleteDescriptorBasedAPI + "::class)",
            +"internal class PersistentIrErrorDeclaration(",
            arrayOf(
                startOffset,
                endOffset,
                +"override val descriptor: " + DeclarationDescriptor
            ).join(separator = ",\n").indent(),
            +") : " + baseClasses("ErrorDeclaration") + " " + block(
                id,
                lastModified,
                loweredUpTo,
                values,
                createdOn,
                id,
                parentField,
                +"override var originField: " + IrDeclarationOrigin + " = IrDeclarationOrigin.DEFINED",
                removedOn,
                annotationsField,
            ),
            id,
        )()
    })

    writeFile("carriers/ErrorDeclarationCarrier.kt", renderFile("org.jetbrains.kotlin.ir.declarations.persistent.carriers") {
        carriers(
            "ErrorDeclaration",
        )()
    })
}