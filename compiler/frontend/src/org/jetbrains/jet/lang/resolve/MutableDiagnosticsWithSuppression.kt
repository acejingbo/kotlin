/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.lang.resolve

import org.jetbrains.jet.lang.diagnostics.Diagnostic
import java.util.ArrayList
import com.intellij.util.CachedValueImpl
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.PsiElement
import com.intellij.openapi.util.ModificationTracker

//NOTE: copied to support changes depending on IDEA 14 branch
private class CompositeModificationTracker(val additionalTracker: ModificationTracker) : SimpleModificationTracker() {
    override fun getModificationCount(): Long {
        return super.getModificationCount() + additionalTracker.getModificationCount()
    }
}

public class MutableDiagnosticsWithSuppression(
        private val bindingContext: BindingContext,
        private val delegateDiagnostics: Diagnostics = Diagnostics.EMPTY
) : Diagnostics {
    private val diagnosticList = ArrayList<Diagnostic>()

    //NOTE: CachedValuesManager is not used because it requires Project passed to this object
    private val cache = CachedValueImpl(CachedValueProvider {
        val allDiagnostics = delegateDiagnostics.noSuppression().all() + diagnosticList
        CachedValueProvider.Result(DiagnosticsWithSuppression(bindingContext, allDiagnostics), modificationTracker)
    })

    private fun readonlyView() = cache.getValue()!!

    public override val modificationTracker = CompositeModificationTracker(delegateDiagnostics.modificationTracker)

    override fun all(): Collection<Diagnostic> = readonlyView().all()
    override fun forElement(psiElement: PsiElement) = readonlyView().forElement(psiElement)
    override fun noSuppression() = readonlyView().noSuppression()

    //essential that this list is readonly
    fun getOwnDiagnostics(): List<Diagnostic> = diagnosticList

    fun report(diagnostic: Diagnostic) {
        diagnosticList.add(diagnostic)
        modificationTracker.incModificationCount()
    }

    fun clear() {
        diagnosticList.clear()
        modificationTracker.incModificationCount()
    }

}