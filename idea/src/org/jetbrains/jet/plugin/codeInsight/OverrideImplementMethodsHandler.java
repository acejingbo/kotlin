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

package org.jetbrains.jet.plugin.codeInsight;

import com.intellij.codeInsight.CodeInsightUtilBase;
import com.intellij.codeInsight.CodeInsightUtilCore;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.ide.util.MemberChooser;
import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageCodeInsightActionHandler;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.lang.descriptors.*;
import org.jetbrains.jet.lang.psi.*;
import org.jetbrains.jet.lang.types.JetType;
import org.jetbrains.jet.lang.types.lang.KotlinBuiltIns;
import org.jetbrains.jet.plugin.caches.resolve.ResolvePackage;
import org.jetbrains.jet.plugin.quickfix.QuickfixPackage;
import org.jetbrains.jet.plugin.util.IdeDescriptorRenderers;
import org.jetbrains.jet.renderer.DescriptorRenderer;
import org.jetbrains.jet.renderer.DescriptorRendererBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.jetbrains.jet.lang.psi.PsiPackage.JetPsiFactory;

public abstract class OverrideImplementMethodsHandler implements LanguageCodeInsightActionHandler {

    private static final DescriptorRenderer OVERRIDE_RENDERER = new DescriptorRendererBuilder()
            .setRenderDefaultValues(false)
            .setModifiers(DescriptorRenderer.Modifier.OVERRIDE)
            .setWithDefinedIn(false)
            .setShortNames(false)
            .setOverrideRenderingPolicy(DescriptorRenderer.OverrideRenderingPolicy.RENDER_OVERRIDE)
            .setUnitReturnType(false)
            .setTypeNormalizer(IdeDescriptorRenderers.APPROXIMATE_FLEXIBLE_TYPES)
            .build();

    private static final Logger LOG = Logger.getInstance(OverrideImplementMethodsHandler.class.getCanonicalName());

    public static List<DescriptorClassMember> membersFromDescriptors(
            JetFile file, Iterable<CallableMemberDescriptor> missingImplementations
    ) {
        List<DescriptorClassMember> members = new ArrayList<DescriptorClassMember>();
        for (CallableMemberDescriptor memberDescriptor : missingImplementations) {
            PsiElement declaration = DescriptorToDeclarationUtil.INSTANCE$.getDeclaration(file, memberDescriptor);
            if (declaration == null) {
                LOG.error("Can not find declaration for descriptor " + memberDescriptor);
            }
            else {
                DescriptorClassMember member = new DescriptorClassMember(declaration, memberDescriptor);
                members.add(member);
            }
        }
        return members;
    }

    public static void generateMethods(
            @NotNull final Editor editor,
            @NotNull final JetClassOrObject classOrObject,
            @NotNull final List<DescriptorClassMember> selectedElements
    ) {
        PsiElement firstGenerated = ApplicationManager.getApplication().runWriteAction(new Computable<PsiElement>() {
            @Override
            public PsiElement compute() {
                JetClassBody body = classOrObject.getBody();
                if (body == null) {
                    JetPsiFactory psiFactory = JetPsiFactory(classOrObject);
                    classOrObject.add(psiFactory.createWhiteSpace());
                    body = (JetClassBody) classOrObject.add(psiFactory.createEmptyClassBody());
                }

                PsiElement afterAnchor = findInsertAfterAnchor(editor, body);

                if (afterAnchor == null) {
                    return null;
                }

                PsiElement firstGenerated = null;

                List<JetElement> elementsToCompact = new ArrayList<JetElement>();
                JetFile file = classOrObject.getContainingJetFile();
                for (JetElement element : generateOverridingMembers(selectedElements, file)) {
                    PsiElement added = body.addAfter(element, afterAnchor);

                    if (firstGenerated == null) {
                        firstGenerated = added;
                    }

                    afterAnchor = added;
                    elementsToCompact.add((JetElement) added);
                }

                ShortenReferences.INSTANCE$.process(elementsToCompact);

                return firstGenerated;
            }
        });

        if (firstGenerated != null) {
            CodeInsightUtilCore.forcePsiPostprocessAndRestoreElement(firstGenerated);
            QuickfixPackage.moveCaretIntoGeneratedElement(editor, firstGenerated);
        }
    }

    @Nullable
    private static PsiElement findInsertAfterAnchor(Editor editor, final JetClassBody body) {
        PsiElement afterAnchor = body.getLBrace();
        if (afterAnchor == null) return null;

        int offset = editor.getCaretModel().getOffset();
        PsiElement offsetCursorElement = PsiTreeUtil.findFirstParent(
                body.getContainingFile().findElementAt(offset),
                new Condition<PsiElement>() {
                    @Override
                    public boolean value(PsiElement element) {
                        return element.getParent() == body;
                    }
                });

        if (offsetCursorElement instanceof PsiWhiteSpace) {
            return removeAfterOffset(offset, (PsiWhiteSpace) offsetCursorElement);
        }

        if (offsetCursorElement != null && offsetCursorElement != body.getRBrace()) {
            return offsetCursorElement;
        }

        return afterAnchor;
    }

    private static PsiElement removeAfterOffset(int offset, PsiWhiteSpace whiteSpace) {
        ASTNode spaceNode = whiteSpace.getNode();
        if (spaceNode.getTextRange().contains(offset)) {
            String beforeWhiteSpaceText = spaceNode.getText().substring(0, offset - spaceNode.getStartOffset());
            if (!StringUtil.containsLineBreak(beforeWhiteSpaceText)) {
                // Prevent insertion on same line
                beforeWhiteSpaceText += "\n";
            }

            JetPsiFactory factory = JetPsiFactory(whiteSpace.getProject());

            PsiElement insertAfter = whiteSpace.getPrevSibling();
            whiteSpace.delete();

            PsiElement beforeSpace = factory.createWhiteSpace(beforeWhiteSpaceText);
            insertAfter.getParent().addAfter(beforeSpace, insertAfter);

            return insertAfter.getNextSibling();
        }

        return whiteSpace;
    }

    private static List<JetElement> generateOverridingMembers(List<DescriptorClassMember> selectedElements, JetFile file) {
        List<JetElement> overridingMembers = new ArrayList<JetElement>();
        for (DescriptorClassMember selectedElement : selectedElements) {
            DeclarationDescriptor descriptor = selectedElement.getDescriptor();
            if (descriptor instanceof SimpleFunctionDescriptor) {
                overridingMembers.add(overrideFunction(file.getProject(), (SimpleFunctionDescriptor) descriptor));
            }
            else if (descriptor instanceof PropertyDescriptor) {
                overridingMembers.add(overrideProperty(file.getProject(), (PropertyDescriptor) descriptor));
            }
        }
        return overridingMembers;
    }

    @NotNull
    private static JetElement overrideProperty(@NotNull Project project, @NotNull PropertyDescriptor descriptor) {
        PropertyDescriptor newDescriptor = (PropertyDescriptor) descriptor.copy(
                descriptor.getContainingDeclaration(),
                Modality.OPEN,
                descriptor.getVisibility(),
                descriptor.getKind(),
                /* copyOverrides = */ true);
        newDescriptor.addOverriddenDescriptor(descriptor);

        StringBuilder body = new StringBuilder();
        String defaultInitializer = CodeInsightUtils.defaultInitializer(descriptor.getType());
        String initializer = defaultInitializer != null ? " = " + defaultInitializer : " = ?";
        if (descriptor.getExtensionReceiverParameter() != null) {
            body.append("\nget()");
            body.append(initializer);
            if (descriptor.isVar()) {
                body.append("\nset(value) {}");
            }
        }
        else {
            body.append(initializer);
        }
        return JetPsiFactory(project).createProperty(OVERRIDE_RENDERER.render(newDescriptor) + body);
    }

    @NotNull
    private static JetNamedFunction overrideFunction(@NotNull Project project, @NotNull FunctionDescriptor descriptor) {
        FunctionDescriptor newDescriptor = descriptor.copy(
                descriptor.getContainingDeclaration(),
                Modality.OPEN,
                descriptor.getVisibility(),
                descriptor.getKind(),
                /* copyOverrides = */ true);
        newDescriptor.addOverriddenDescriptor(descriptor);

        boolean isAbstractFun = descriptor.getModality() == Modality.ABSTRACT;
        StringBuilder delegationBuilder = new StringBuilder();
        if (isAbstractFun) {
            delegationBuilder.append("throw UnsupportedOperationException()");
        }
        else {
            delegationBuilder.append("super<").append(descriptor.getContainingDeclaration().getName());
            delegationBuilder.append(">.").append(descriptor.getName()).append("(");
        }
        boolean first = true;
        if (!isAbstractFun) {
            for (ValueParameterDescriptor parameterDescriptor : descriptor.getValueParameters()) {
                if (!first) {
                    delegationBuilder.append(", ");
                }
                first = false;
                delegationBuilder.append(parameterDescriptor.getName());
            }
            delegationBuilder.append(")");
        }
        JetType returnType = descriptor.getReturnType();
        KotlinBuiltIns builtIns = KotlinBuiltIns.getInstance();

        boolean returnsNotUnit = returnType != null && !builtIns.getUnitType().equals(returnType);
        String body = "{" + (returnsNotUnit && !isAbstractFun ? "return " : "") + delegationBuilder.toString() + "}";

        return JetPsiFactory(project).createFunction(OVERRIDE_RENDERER.render(newDescriptor) + body);
    }

    @NotNull
    public Set<CallableMemberDescriptor> collectMethodsToGenerate(@NotNull JetClassOrObject classOrObject) {
        DeclarationDescriptor descriptor = ResolvePackage.getLazyResolveSession(classOrObject).resolveToDescriptor(classOrObject);
        if (descriptor instanceof ClassDescriptor) {
            return collectMethodsToGenerate((ClassDescriptor) descriptor);
        }
        return Collections.emptySet();
    }

    protected abstract Set<CallableMemberDescriptor> collectMethodsToGenerate(@NotNull ClassDescriptor descriptor);

    private MemberChooser<DescriptorClassMember> showOverrideImplementChooser(
            Project project,
            DescriptorClassMember[] members
    ) {
        MemberChooser<DescriptorClassMember> chooser = new MemberChooser<DescriptorClassMember>(members, true, true, project);
        chooser.setTitle(getChooserTitle());
        chooser.show();
        if (chooser.getExitCode() != DialogWrapper.OK_EXIT_CODE) return null;
        return chooser;
    }

    protected abstract String getChooserTitle();

    @Override
    public boolean isValidFor(Editor editor, PsiFile file) {
        if (!(file instanceof JetFile)) {
            return false;
        }
        PsiElement elementAtCaret = file.findElementAt(editor.getCaretModel().getOffset());
        JetClassOrObject classOrObject = PsiTreeUtil.getParentOfType(elementAtCaret, JetClassOrObject.class);
        return classOrObject != null;
    }

    protected abstract String getNoMethodsFoundHint();

    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, boolean implementAll) {
        PsiElement elementAtCaret = file.findElementAt(editor.getCaretModel().getOffset());
        JetClassOrObject classOrObject = PsiTreeUtil.getParentOfType(elementAtCaret, JetClassOrObject.class);

        assert classOrObject != null : "ClassObject should be checked in isValidFor method";

        Set<CallableMemberDescriptor> missingImplementations = collectMethodsToGenerate(classOrObject);
        if (missingImplementations.isEmpty() && !implementAll) {
            HintManager.getInstance().showErrorHint(editor, getNoMethodsFoundHint());
            return;
        }
        List<DescriptorClassMember> members = membersFromDescriptors((JetFile) file, missingImplementations);

        List<DescriptorClassMember> selectedElements;
        if (implementAll) {
            selectedElements = members;
        }
        else {
            MemberChooser<DescriptorClassMember> chooser = showOverrideImplementChooser(
                    project,
                    members.toArray(new DescriptorClassMember[members.size()]));

            if (chooser == null) {
                return;
            }

            selectedElements = chooser.getSelectedElements();
            if (selectedElements == null || selectedElements.isEmpty()) return;
        }

        PsiDocumentManager.getInstance(project).commitAllDocuments();

        generateMethods(editor, classOrObject, selectedElements);
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        invoke(project, editor, file, false);
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
