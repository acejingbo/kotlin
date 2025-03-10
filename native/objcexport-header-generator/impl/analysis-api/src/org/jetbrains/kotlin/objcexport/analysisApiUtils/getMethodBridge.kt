package org.jetbrains.kotlin.objcexport.analysisApiUtils

import org.jetbrains.kotlin.analysis.api.KtAnalysisSession
import org.jetbrains.kotlin.analysis.api.symbols.KtCallableSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KtFunctionLikeSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KtFunctionSymbol
import org.jetbrains.kotlin.analysis.api.types.KtFunctionalType
import org.jetbrains.kotlin.analysis.api.types.KtType
import org.jetbrains.kotlin.backend.konan.KonanPrimitiveType
import org.jetbrains.kotlin.backend.konan.objcexport.*
import org.jetbrains.kotlin.objcexport.*

/**
 * [org.jetbrains.kotlin.backend.konan.objcexport.ObjCExportMapperKt.bridgeMethodImpl]
 */
context(KtAnalysisSession, KtObjCExportSession)
internal fun KtFunctionLikeSymbol.getMethodBridge(): MethodBridge {

    val valueParameters = mutableListOf<MethodBridgeValueParameter>()

    val receiver = if (isConstructor && isArray) {
        MethodBridgeReceiver.Factory
    } else if (isTopLevel) {
        MethodBridgeReceiver.Static
    } else {
        MethodBridgeReceiver.Instance
    }

    this.valueParameters.forEach {
        valueParameters += bridgeParameter(it.returnType)
    }

    if (this is KtFunctionSymbol && isSuspend) {
        valueParameters += MethodBridgeValueParameter.SuspendCompletion(false)
    }

    return MethodBridge(
        bridgeReturnType(),
        receiver,
        valueParameters
    )
}

/**
 * [ObjCExportMapper.bridgeParameter]
 */
context(KtAnalysisSession, KtObjCExportSession)
private fun bridgeParameter(type: KtType): MethodBridgeValueParameter {
    return MethodBridgeValueParameter.Mapped(bridgeType(type))
}

/**
 * [ObjCExportMapper.bridgeType]
 */
context(KtAnalysisSession)
private fun bridgeType(
    type: KtType,
): TypeBridge {

    return if (type.isPrimitive) {
        val objCType = when {
            type.isBoolean -> ObjCValueType.BOOL
            type.isChar -> ObjCValueType.UNICHAR
            type.isByte -> ObjCValueType.CHAR
            type.isShort -> ObjCValueType.SHORT
            type.isInt -> ObjCValueType.INT
            type.isLong -> ObjCValueType.LONG_LONG
            type.isFloat -> ObjCValueType.FLOAT
            type.isDouble -> ObjCValueType.DOUBLE
            type.isUByte -> ObjCValueType.UNSIGNED_CHAR
            type.isUShort -> ObjCValueType.UNSIGNED_SHORT
            type.isUInt -> ObjCValueType.UNSIGNED_INT
            type.isULong -> ObjCValueType.UNSIGNED_LONG_LONG
            else ->
                /**
                 * Handle [KonanPrimitiveType.NON_NULL_NATIVE_PTR] and [KonanPrimitiveType.VECTOR128]
                 */
                TODO()
        }
        ValueTypeBridge(objCType)
    } else if (type.isFunctionType) {
        bridgeFunctionType(type)
    } else {
        ReferenceBridge
    }
}

/**
 * [ObjCExportMapper.bridgeFunctionType]
 */
context(KtAnalysisSession)
private fun bridgeFunctionType(type: KtType): TypeBridge {

    val numberOfParameters: Int
    val returnType: KtType

    if (type is KtFunctionalType) {
        numberOfParameters = type.parameterTypes.size
        returnType = type.parameterTypes.last()
    } else {
        numberOfParameters = 0
        returnType = type
    }

    val returnsVoid = returnType.isUnit || returnType.isNothing
    return BlockPointerBridge(numberOfParameters, returnsVoid)
}

/**
 * [ObjCExportMapper.bridgeReturnType]
 */
context(KtAnalysisSession, KtObjCExportSession)
private fun KtCallableSymbol.bridgeReturnType(): MethodBridge.ReturnValue {

    val convertExceptionsToErrors = false // TODO: Add exception handling and return MethodBridge.ReturnValue.WithError.ZeroForError

    if (isArray) {
        return MethodBridge.ReturnValue.Instance.FactoryResult
    } else if (isConstructor) {
        val result = MethodBridge.ReturnValue.Instance.InitResult
        if (convertExceptionsToErrors) {
            MethodBridge.ReturnValue.WithError.ZeroForError(result, successMayBeZero = false)
        } else {
            return result
        }
    } else if (returnType.isSuspendFunctionType) {
        return MethodBridge.ReturnValue.Suspend
    }

    //TODO: handle hashCode
//    descriptor.containingDeclaration.let { it is ClassDescriptor && KotlinBuiltIns.isAny(it) } &&
//            descriptor.name.asString() == "hashCode" -> {
//        assert(!convertExceptionsToErrors)
//        MethodBridge.ReturnValue.HashCode
//    }

    //TODO: handle getter
//    descriptor is PropertyGetterDescriptor -> {
//        assert(!convertExceptionsToErrors)
//        MethodBridge.ReturnValue.Mapped(bridgePropertyType(descriptor.correspondingProperty))
//    }

    if (returnType.isUnit || returnType.isNothing) {
        return if (convertExceptionsToErrors) {
            MethodBridge.ReturnValue.WithError.Success
        } else {
            MethodBridge.ReturnValue.Void
        }
    }


    val returnTypeBridge = bridgeType(returnType)
    val successReturnValueBridge = MethodBridge.ReturnValue.Mapped(returnTypeBridge)

    return if (convertExceptionsToErrors) {
        val canReturnZero = !returnTypeBridge.isReferenceOrPointer() || returnType.canBeNull
        MethodBridge.ReturnValue.WithError.ZeroForError(
            successReturnValueBridge,
            successMayBeZero = canReturnZero
        )
    } else {
        successReturnValueBridge
    }
}

/**
 * [org.jetbrains.kotlin.backend.konan.objcexport.ObjCExportMapperKt.isReferenceOrPointer]
 */
private fun TypeBridge.isReferenceOrPointer(): Boolean = when (this) {
    ReferenceBridge, is BlockPointerBridge -> true
    is ValueTypeBridge -> this.objCValueType == ObjCValueType.POINTER
}
