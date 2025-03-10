/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */
package org.jetbrains.kotlinx.jso.compiler.backend

import org.jetbrains.kotlin.backend.common.DeclarationTransformer
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower
import org.jetbrains.kotlin.builtins.StandardNames
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.impl.*
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.util.OperatorNameConventions
import org.jetbrains.kotlinx.jso.compiler.resolve.JsSimpleObjectPluginKey
import org.jetbrains.kotlinx.jso.compiler.resolve.StandardIds

private class MoveExternalInlineFunctionsWithBodiesOutsideLowering(private val context: IrPluginContext) : DeclarationTransformer {
    private val jsFunction = context.referenceFunctions(StandardIds.JS_FUNCTION_ID).single()
    private val EXPECTED_ORIGIN = IrDeclarationOrigin.GeneratedByPlugin(JsSimpleObjectPluginKey)

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun transformFlat(declaration: IrDeclaration): List<IrDeclaration>? {
        val file = declaration.file
        val parent = declaration.parentClassOrNull

        if (parent == null || declaration !is IrSimpleFunction || declaration.origin != EXPECTED_ORIGIN) return null

        file.declarations.add(declaration)

        declaration.body = when (declaration.name) {
            StandardNames.DATA_CLASS_COPY, OperatorNameConventions.INVOKE -> declaration.generateBodyForFactoryAndCopyFunction()
            else -> error("Unexpected function with name `${declaration.name.identifier}`")
        }

        declaration.parent = file
        declaration.isExternal = false

        return emptyList()
    }

    private fun IrSimpleFunction.generateBodyForFactoryAndCopyFunction(): IrBlockBody {
        val declaration = this
        return context.irFactory.createBlockBody(startOffset, declaration.endOffset).apply {
            statements += IrReturnImpl(
                declaration.startOffset,
                declaration.endOffset,
                declaration.returnType,
                declaration.symbol,
                IrCallImpl(
                    declaration.startOffset,
                    declaration.endOffset,
                    declaration.returnType,
                    jsFunction,
                    0,
                    1,
                ).apply {
                    val jsObject = "{ ${declaration.valueParameters.joinToString(", ") { "${it.name.identifier}:${it.name.identifier}" }} }"
                    putValueArgument(0, jsObject.toIrConst(context.irBuiltIns.stringType))
                }
            )
        }
    }
}

open class JsObjectLoweringExtension : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        MoveExternalInlineFunctionsWithBodiesOutsideLowering(pluginContext).lower(moduleFragment)
    }
}
