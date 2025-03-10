package org.jetbrains.kotlin.objcexport

import org.jetbrains.kotlin.analysis.api.KtAnalysisSession
import org.jetbrains.kotlin.analysis.api.symbols.KtFunctionLikeSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KtPropertySetterSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KtTypeParameterSymbol
import org.jetbrains.kotlin.backend.konan.cKeywords
import org.jetbrains.kotlin.backend.konan.objcexport.*

context(KtAnalysisSession, KtObjCExportSession)
internal fun KtFunctionLikeSymbol.translateToObjCParameters(baseMethodBridge: MethodBridge): List<ObjCParameter> {
    fun unifyName(initialName: String, usedNames: Set<String>): String {
        var unique = initialName.toValidObjCSwiftIdentifier()
        while (unique in usedNames || unique in cKeywords) {
            unique += "_"
        }
        return unique
    }

    val valueParametersAssociated = baseMethodBridge.valueParametersAssociated(this)

    val parameters = mutableListOf<ObjCParameter>()

    val usedNames = mutableSetOf<String>()

    valueParametersAssociated.forEach { (bridge: MethodBridgeValueParameter, parameter: KtTypeParameterSymbol?) ->
        val candidateName: String = when (bridge) {
            is MethodBridgeValueParameter.Mapped -> {
                if (parameter == null) throw IllegalStateException("Parameter shouldn't be null")
                when {
                    this is KtPropertySetterSymbol -> "value"
                    else -> parameter.getObjCName().name(false)
                }
            }
            MethodBridgeValueParameter.ErrorOutParameter -> "error"
            is MethodBridgeValueParameter.SuspendCompletion -> "completionHandler"
        }

        val uniqueName = unifyName(candidateName, usedNames)
        usedNames += uniqueName

        val type = when (bridge) {
            is MethodBridgeValueParameter.Mapped -> TODO("Fetch KtType from KtTypeParameterSymbol: $parameter")
            MethodBridgeValueParameter.ErrorOutParameter ->
                ObjCPointerType(ObjCNullableReferenceType(ObjCClassType("NSError")), nullable = true)

            is MethodBridgeValueParameter.SuspendCompletion -> {
                val resultType = if (bridge.useUnitCompletion) {
                    null
                } else {
                    when (val it = this.returnType.translateToObjCReferenceType()) {
                        is ObjCNonNullReferenceType -> ObjCNullableReferenceType(it, isNullableResult = false)
                        is ObjCNullableReferenceType -> ObjCNullableReferenceType(it.nonNullType, isNullableResult = true)
                    }
                }
                ObjCBlockPointerType(
                    returnType = ObjCVoidType,
                    parameterTypes = listOfNotNull(
                        resultType,
                        ObjCNullableReferenceType(ObjCClassType("NSError"))
                    )
                )
            }
        }

        val origin: ObjCExportStubOrigin? = null
        val todo: Nothing? = null
        parameters += ObjCParameter(uniqueName, origin, type, todo)
    }
    return parameters
}

/**
 * Not implemented
 */
internal fun KtTypeParameterSymbol.getObjCName(): ObjCExportName {
    TODO()
}