package com.project.parser.codeparser;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.CompactConstructorDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.RecordPatternExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.TypePatternExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsDirective;
import com.github.javaparser.ast.modules.ModuleOpensDirective;
import com.github.javaparser.ast.modules.ModuleProvidesDirective;
import com.github.javaparser.ast.modules.ModuleRequiresDirective;
import com.github.javaparser.ast.modules.ModuleUsesDirective;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.nodeTypes.NodeWithVariables;
import com.github.javaparser.ast.nodeTypes.SwitchNode;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeRetrieveVisitor extends VoidVisitorAdapter<StringBuilder> {

    private boolean notNullOrEmpty(NodeList<?> nodes) {
        return nodes != null && nodes.isNonEmpty();
    }

    private void printMembers(NodeList<BodyDeclaration<?>> members, StringBuilder codeBuilder) {
        for (BodyDeclaration<?> member : members) {
            member.accept(this, codeBuilder);
        }
    }

    private void printModifiers(NodeList<Modifier> modifiers, StringBuilder codeBuilder) {
        if (modifiers.isNonEmpty()) {
            codeBuilder.append(modifiers.stream().map(Modifier::getKeyword)
                    .map(Modifier.Keyword::asString).collect(Collectors.joining(" ")) + " ");
        }
    }

    private void printTypeParameters(NodeList<TypeParameter> parameters,
            StringBuilder codeBuilder) {

        if (notNullOrEmpty(parameters)) {
            codeBuilder.append("<");

            for (Iterator<TypeParameter> it = parameters.iterator(); it.hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }

            codeBuilder.append(">");
        }
    }

    private void printMemberAnnotations(NodeList<AnnotationExpr> annotations,
            StringBuilder codeBuilder) {
        for (AnnotationExpr annotation : annotations) {
            annotation.accept(this, codeBuilder);
        }
    }

    private void printAnnotations(NodeList<AnnotationExpr> annotations, boolean prefixWithSpace,
            StringBuilder codeBuilder) {
        if (annotations.isEmpty()) {
            return;
        }

        if (prefixWithSpace) {
            codeBuilder.append(" ");
        }

        for (AnnotationExpr annotation : annotations) {
            annotation.accept(this, codeBuilder);
            codeBuilder.append(" ");
        }
    }

    private void printTypeArguments(NodeList<Type> typeArguments, StringBuilder codeBuilder) {
        if (notNullOrEmpty(typeArguments)) {
            codeBuilder.append("<");

            for (Iterator<Type> it = typeArguments.iterator(); it.hasNext();) {
                Type type = it.next();
                type.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }

            codeBuilder.append(">");
        }
    }

    private <T extends Expression> void printArguments(NodeList<T> arguments,
            StringBuilder codeBuilder) {

        codeBuilder.append("(");

        if (notNullOrEmpty(arguments)) {
            for (Iterator<T> it = arguments.iterator(); it.hasNext();) {
                T t = it.next();
                t.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(")");
    }

    private void printSwitchNode(SwitchNode switchNode, StringBuilder codeBuilder) {
        codeBuilder.append("switch(");
        switchNode.getSelector().accept(this, codeBuilder);
        codeBuilder.append(") {");

        if (switchNode.getEntries() != null) {
            for (SwitchEntry entry : switchNode.getEntries()) {
                entry.accept(this, codeBuilder);
            }
        }

        codeBuilder.append("}");
    }

    /**
     * CompilationUnit
     */
    @Override
    public void visit(CompilationUnit unit, StringBuilder codeBuilder) {
        if (unit.getParsed() == Node.Parsedness.UNPARSABLE) {
            codeBuilder.append("???");
            return;
        }

        if (unit.getPackageDeclaration().isPresent()) {
            unit.getPackageDeclaration().get().accept(this, codeBuilder);
        }

        unit.getImports().accept(this, codeBuilder);

        for (Iterator<TypeDeclaration<?>> it = unit.getTypes().iterator(); it.hasNext();) {
            it.next().accept(this, codeBuilder);
        }

        unit.getModule().ifPresent(module -> module.accept(this, codeBuilder));
    }


    // ================
    // ===== Body =====
    // ================

    /**
     * AnnotationDeclaration
     */
    @Override
    public void visit(AnnotationDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);

        codeBuilder.append("@interface ");
        decl.getName().accept(this, codeBuilder);

        codeBuilder.append(" {");
        if (decl.getMembers() != null) {
            printMembers(decl.getMembers(), codeBuilder);
        }
        codeBuilder.append("}");
    }

    /**
     * AnnotationMemberDeclaration
     */
    @Override
    public void visit(AnnotationMemberDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);

        decl.getType().accept(this, codeBuilder);
        codeBuilder.append(" ");
        decl.getName().accept(this, codeBuilder);
        codeBuilder.append("()");

        if (decl.getDefaultValue().isPresent()) {
            codeBuilder.append(" default ");
            decl.getDefaultValue().get().accept(this, codeBuilder);
        }

        codeBuilder.append(";");
    }

    /**
     * ClassOrInterfaceDeclaration
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);

        if (decl.isInterface()) {
            codeBuilder.append("interface ");
        } else {
            codeBuilder.append("class ");
        }
        decl.getName().accept(this, codeBuilder);

        printTypeParameters(decl.getTypeParameters(), codeBuilder);

        if (decl.getExtendedTypes().isNonEmpty()) {
            codeBuilder.append(" extends ");

            for (Iterator<ClassOrInterfaceType> it = decl.getExtendedTypes().iterator(); it
                    .hasNext();) {

                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        if (decl.getImplementedTypes().isNonEmpty()) {
            codeBuilder.append(" implements ");

            for (Iterator<ClassOrInterfaceType> it = decl.getImplementedTypes().iterator(); it
                    .hasNext();) {

                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        if (decl.getPermittedTypes().isNonEmpty()) {
            codeBuilder.append(" permits ");

            for (Iterator<ClassOrInterfaceType> it = decl.getPermittedTypes().iterator(); it
                    .hasNext();) {

                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append("{");
        if (notNullOrEmpty(decl.getMembers())) {
            printMembers(decl.getMembers(), codeBuilder);
        }
        codeBuilder.append("}");
    }

    /**
     * CompactConstructorDeclaration
     */
    @Override
    public void visit(CompactConstructorDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);
        printTypeParameters(decl.getTypeParameters(), codeBuilder);

        if (decl.isGeneric()) {
            codeBuilder.append(" ");
        }

        decl.getName().accept(this, codeBuilder);

        if (notNullOrEmpty(decl.getThrownExceptions())) {
            codeBuilder.append(" throws ");

            for (Iterator<ReferenceType> it = decl.getThrownExceptions().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(" ");
        decl.getBody().accept(this, codeBuilder);
    }

    /**
     * ConstructorDeclaration
     */
    @Override
    public void visit(ConstructorDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);
        printTypeParameters(decl.getTypeParameters(), codeBuilder);

        if (decl.isGeneric()) {
            codeBuilder.append(" ");
        }

        decl.getName().accept(this, codeBuilder);
        codeBuilder.append("(");

        if (decl.getParameters().isNonEmpty()) {
            for (Iterator<Parameter> it = decl.getParameters().iterator(); it.hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(")");

        if (notNullOrEmpty(decl.getThrownExceptions())) {
            codeBuilder.append(" throws ");

            for (Iterator<ReferenceType> it = decl.getThrownExceptions().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(" ");
        decl.getBody().accept(this, codeBuilder);
    }

    /**
     * EnumConstantDeclaration
     */

    @Override
    public void visit(EnumConstantDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);

        decl.getName().accept(this, codeBuilder);

        if (decl.getArguments().isNonEmpty()) {
            printArguments(decl.getArguments(), codeBuilder);
        }

        if (decl.getClassBody().isNonEmpty()) {
            codeBuilder.append(" {");
            printMembers(decl.getClassBody(), codeBuilder);
            codeBuilder.append("}");
        }
    }

    /**
     * EnumDeclaration
     */
    @Override
    public void visit(EnumDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);

        codeBuilder.append("enum ");
        decl.getName().accept(this, codeBuilder);

        if (decl.getImplementedTypes().isNonEmpty()) {
            codeBuilder.append(" implements ");

            for (Iterator<ClassOrInterfaceType> it = decl.getImplementedTypes().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(" {");

        if (decl.getEntries().isNonEmpty()) {
            for (Iterator<EnumConstantDeclaration> it = decl.getEntries().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        if (decl.getMembers().isNonEmpty()) {
            codeBuilder.append(";");
            printMembers(decl.getMembers(), codeBuilder);
        }

        codeBuilder.append("}");
    }

    /**
     * FieldDeclaration
     */
    @Override
    public void visit(FieldDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);

        if (decl.getVariables().isNonEmpty()) {
            decl.getMaximumCommonType().ifPresent(type -> type.accept(this, codeBuilder));
            if (!decl.getMaximumCommonType().isPresent()) {
                codeBuilder.append("???");
            }
        }

        codeBuilder.append(" ");

        for (Iterator<VariableDeclarator> it = decl.getVariables().iterator(); it.hasNext();) {
            it.next().accept(this, codeBuilder);
            if (it.hasNext()) {
                codeBuilder.append(",");
            }
        }

        codeBuilder.append(";");
    }

    /**
     * InitializerDeclaration
     */
    @Override
    public void visit(InitializerDeclaration decl, StringBuilder codeBuilder) {
        if (decl.isStatic()) {
            codeBuilder.append("static ");
        }
        decl.getBody().accept(this, codeBuilder);
    }

    /**
     * MethodDeclaration
     */
    @Override
    public void visit(MethodDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);
        printTypeParameters(decl.getTypeParameters(), codeBuilder);
        if (notNullOrEmpty(decl.getTypeParameters())) {
            codeBuilder.append(" ");
        }

        decl.getType().accept(this, codeBuilder);
        codeBuilder.append(" ");
        decl.getName().accept(this, codeBuilder);

        codeBuilder.append("(");

        decl.getReceiverParameter().ifPresent(rp -> {
            rp.accept(this, codeBuilder);
            if (notNullOrEmpty(decl.getParameters())) {
                codeBuilder.append(",");
            }
        });

        if (notNullOrEmpty(decl.getParameters())) {
            for (Iterator<Parameter> it = decl.getParameters().iterator(); it.hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(")");

        if (notNullOrEmpty(decl.getThrownExceptions())) {
            codeBuilder.append(" throws ");
            for (Iterator<ReferenceType> it = decl.getThrownExceptions().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        if (!decl.getBody().isPresent()) {
            codeBuilder.append(";");
        } else {
            codeBuilder.append(" ");
            decl.getBody().get().accept(this, codeBuilder);
        }
    }

    /**
     * Parameter
     */
    @Override
    public void visit(Parameter p, StringBuilder codeBuilder) {
        printAnnotations(p.getAnnotations(), false, codeBuilder);
        printModifiers(p.getModifiers(), codeBuilder);

        p.getType().accept(this, codeBuilder);

        if (p.isVarArgs()) {
            printAnnotations(p.getVarArgsAnnotations(), false, codeBuilder);
            codeBuilder.append("...");
        }

        if (!(p.getType().isUnknownType())) {
            codeBuilder.append(" ");
        }

        p.getName().accept(this, codeBuilder);
    }

    /**
     * ReceiverParameter
     */
    @Override
    public void visit(ReceiverParameter rp, StringBuilder codeBuilder) {
        printAnnotations(rp.getAnnotations(), false, codeBuilder);
        rp.getType().accept(this, codeBuilder);
        codeBuilder.append(" ");
        rp.getName().accept(this, codeBuilder);
    }

    /**
     * RecordDeclaration
     */
    @Override
    public void visit(RecordDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);
        printModifiers(decl.getModifiers(), codeBuilder);

        codeBuilder.append("record ");
        decl.getName().accept(this, codeBuilder);

        codeBuilder.append("(");

        if (notNullOrEmpty(decl.getParameters())) {
            for (Iterator<Parameter> it = decl.getParameters().iterator(); it.hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(")");

        printTypeParameters(decl.getTypeParameters(), codeBuilder);

        if (decl.getImplementedTypes().isNonEmpty()) {
            codeBuilder.append(" implements ");
            for (Iterator<ClassOrInterfaceType> it = decl.getImplementedTypes().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(" {");
        if (notNullOrEmpty(decl.getMembers())) {
            printMembers(decl.getMembers(), codeBuilder);
        }
        codeBuilder.append("}");
    }

    /**
     * VariableDeclarator
     */
    @Override
    public void visit(VariableDeclarator declarator, StringBuilder codeBuilder) {
        declarator.getName().accept(this, codeBuilder);

        declarator.findAncestor(NodeWithVariables.class)
                .ifPresent(ancestor -> ((NodeWithVariables<?>) ancestor).getMaximumCommonType()
                        .ifPresent(commonType -> {
                            Type type = declarator.getType();
                            ArrayType arrayType = null;

                            for (int i = commonType.getArrayLevel(); i < type
                                    .getArrayLevel(); ++i) {
                                if (arrayType == null) {
                                    arrayType = (ArrayType) type;
                                } else {
                                    arrayType = (ArrayType) arrayType.getComponentType();
                                }
                                printAnnotations(arrayType.getAnnotations(), true, codeBuilder);
                                codeBuilder.append("[]");
                            }
                        }));

        if (declarator.getInitializer().isPresent()) {
            codeBuilder.append(" = ");
            declarator.getInitializer().get().accept(this, codeBuilder);
        }
    }



    // =========================
    // ===== Declarations ======
    // =========================

    /**
     * ArrayCreationLevel
     */
    @Override
    public void visit(ArrayCreationLevel level, StringBuilder codeBuilder) {
        printAnnotations(level.getAnnotations(), true, codeBuilder);

        codeBuilder.append("[");
        if (level.getDimension().isPresent()) {
            level.getDimension().get().accept(this, codeBuilder);
        }
        codeBuilder.append("]");
    }

    /**
     * ImportDeclaration
     */
    @Override
    public void visit(ImportDeclaration id, StringBuilder codeBuilder) {
        codeBuilder.append("import ");

        if (id.isStatic()) {
            codeBuilder.append("static ");
        }

        id.getName().accept(this, codeBuilder);

        if (id.isAsterisk()) {
            codeBuilder.append(".*");
        }

        codeBuilder.append(";");
    }

    /**
     * Modifier
     */
    @Override
    public void visit(Modifier modifier, StringBuilder codeBuilder) {
        codeBuilder.append(modifier.getKeyword().asString());
        codeBuilder.append(" ");
    }

    /**
     * NodeList
     */
    @Override
    public void visit(NodeList list, StringBuilder codeBuilder) {
        if (list.isNonEmpty() && list.get(0) instanceof ImportDeclaration) {
            NodeList<ImportDeclaration> modifiableList = new NodeList<>(list);
            modifiableList.sort(
                    Comparator.comparingInt((ImportDeclaration decl) -> decl.isStatic() ? 0 : 1)
                            .thenComparing(NodeWithName::getNameAsString));
            for (Object node : modifiableList) {
                ((Node) node).accept(this, codeBuilder);
            }
        } else {
            for (Object node : list) {
                ((Node) node).accept(this, codeBuilder);
            }
        }
    }

    /**
     * PackageDeclaration
     */
    @Override
    public void visit(PackageDeclaration pd, StringBuilder codeBuilder) {
        printMemberAnnotations(pd.getAnnotations(), codeBuilder);
        codeBuilder.append("package ");
        pd.getName().accept(this, codeBuilder);
        codeBuilder.append(";");
    }


    // ======================
    // ===== Statements =====
    // ======================

    /**
     * AssertStmt
     */
    @Override
    public void visit(AssertStmt assertStmt, StringBuilder codeBuilder) {
        codeBuilder.append("assert ");
        assertStmt.getCheck().accept(this, codeBuilder);
        if (assertStmt.getMessage().isPresent()) {
            codeBuilder.append(" : ");
            assertStmt.getMessage().get().accept(this, codeBuilder);
        }
        codeBuilder.append(";");
    }

    /**
     * BlockStmt
     */
    @Override
    public void visit(BlockStmt blockStmt, StringBuilder codeBuilder) {
        codeBuilder.append("{");

        if (blockStmt.getStatements() != null) {
            for (Statement stmt : blockStmt.getStatements()) {
                stmt.accept(this, codeBuilder);
            }
        }

        codeBuilder.append("}");
    }

    /**
     * BreakStmt
     */
    @Override
    public void visit(BreakStmt breakStmt, StringBuilder codeBuilder) {
        codeBuilder.append("break");

        if (breakStmt.getLabel().isPresent()) {
            codeBuilder.append(" ");
            breakStmt.getLabel().get().accept(this, codeBuilder);
        }

        codeBuilder.append(";");
    }

    /**
     * CatchClause
     */
    @Override
    public void visit(CatchClause clause, StringBuilder codeBuilder) {
        codeBuilder.append(" catch (");
        clause.getParameter().accept(this, codeBuilder);
        codeBuilder.append(") ");
        clause.getBody().accept(this, codeBuilder);
    }

    /**
     * ContinueStmt
     */
    @Override
    public void visit(ContinueStmt continueStmt, StringBuilder codeBuilder) {
        codeBuilder.append("continue");

        if (continueStmt.getLabel().isPresent()) {
            codeBuilder.append(" ");
            continueStmt.getLabel().get().accept(this, codeBuilder);
        }

        codeBuilder.append(";");
    }

    /**
     * DoStmt
     */
    @Override
    public void visit(DoStmt doStmt, StringBuilder codeBuilder) {
        codeBuilder.append("do ");

        doStmt.getBody().accept(this, codeBuilder);

        codeBuilder.append(" while (");

        doStmt.getCondition().accept(this, codeBuilder);

        codeBuilder.append(");");
    }

    /**
     * EmptyStmt
     */
    @Override
    public void visit(EmptyStmt emptyStmt, StringBuilder codeBuilder) {
        codeBuilder.append(";");
    }

    /**
     * ExplicitConstructorInvocationStmt
     * 
     * Example: class X { X() { super(15); } }
     * 
     * Example: class X { X() { this(1, 2); } }
     */
    @Override
    public void visit(ExplicitConstructorInvocationStmt invokeStmt, StringBuilder codeBuilder) {
        if (invokeStmt.isThis()) {
            printTypeArguments(invokeStmt.getTypeArguments().orElse(null), codeBuilder);
            codeBuilder.append("this");
        } else {
            if (invokeStmt.getExpression().isPresent()) {
                invokeStmt.getExpression().get().accept(this, codeBuilder);
                codeBuilder.append(".");
            }

            printTypeArguments(invokeStmt.getTypeArguments().orElse(null), codeBuilder);
            codeBuilder.append("super");
        }

        printArguments(invokeStmt.getArguments(), codeBuilder);
        codeBuilder.append(";");
    }

    /**
     * ExpressionStmt
     */
    @Override
    public void visit(ExpressionStmt expressionStmt, StringBuilder codeBuilder) {
        expressionStmt.getExpression().accept(this, codeBuilder);
        codeBuilder.append(";");
    }

    /**
     * ForEachStmt
     */
    @Override
    public void visit(ForEachStmt forEachStmt, StringBuilder codeBuilder) {
        codeBuilder.append("for (");
        forEachStmt.getVariable().accept(this, codeBuilder);
        codeBuilder.append(" : ");
        forEachStmt.getIterable().accept(this, codeBuilder);
        codeBuilder.append(") ");
        forEachStmt.getBody().accept(this, codeBuilder);
    }

    /**
     * ForStmt
     */
    @Override
    public void visit(ForStmt forStmt, StringBuilder codeBuilder) {
        codeBuilder.append("for (");

        if (forStmt.getInitialization() != null) {
            for (Iterator<Expression> it = forStmt.getInitialization().iterator(); it.hasNext();) {
                Expression expr = it.next();
                expr.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append("; ");
        if (forStmt.getCompare().isPresent()) {
            forStmt.getCompare().get().accept(this, codeBuilder);
        }

        codeBuilder.append("; ");
        if (forStmt.getUpdate() != null) {
            for (Iterator<Expression> it = forStmt.getUpdate().iterator(); it.hasNext();) {
                Expression expr = it.next();
                expr.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append(") ");
        forStmt.getBody().accept(this, codeBuilder);
    }

    /**
     * IfStmt
     */
    @Override
    public void visit(IfStmt ifStmt, StringBuilder codeBuilder) {
        codeBuilder.append("if (");
        ifStmt.getCondition().accept(this, codeBuilder);
        codeBuilder.append(") ");

        ifStmt.getThenStmt().accept(this, codeBuilder);

        if (ifStmt.getElseStmt().isPresent()) {
            codeBuilder.append("else ");
            ifStmt.getElseStmt().get().accept(this, codeBuilder);
        }
    }

    /**
     * LabeledStmt
     */
    @Override
    public void visit(LabeledStmt labeledStmt, StringBuilder codeBuilder) {
        labeledStmt.getLabel().accept(this, codeBuilder);
        codeBuilder.append(": ");
        labeledStmt.getStatement().accept(this, codeBuilder);
    }

    /**
     * LocalClassDeclarationStmt
     * 
     * A statement consisting of a class declaration
     */
    @Override
    public void visit(LocalClassDeclarationStmt decl, StringBuilder codeBuilder) {
        decl.getClassDeclaration().accept(this, codeBuilder);
    }

    /**
     * LocalRecordDeclarationStmt
     * 
     * Record declaration inside a method
     */
    @Override
    public void visit(LocalRecordDeclarationStmt decl, StringBuilder codeBuilder) {
        decl.getRecordDeclaration().accept(this, codeBuilder);
    }

    /**
     * ReturnStmt
     */
    @Override
    public void visit(ReturnStmt returnStmt, StringBuilder codeBuilder) {
        codeBuilder.append("return");
        if (returnStmt.getExpression().isPresent()) {
            codeBuilder.append(" ");
            returnStmt.getExpression().get().accept(this, codeBuilder);
        }
        codeBuilder.append(";");
    }

    /**
     * SwitchEntry
     * 
     * One case in a switch statement
     */
    @Override
    public void visit(SwitchEntry entry, StringBuilder codeBuilder) {
        String separator = (entry.getType() == SwitchEntry.Type.STATEMENT_GROUP) ? ":" : " ->";

        if (!notNullOrEmpty(entry.getLabels())) {
            codeBuilder.append("default" + separator + " ");
        } else {
            codeBuilder.append("case ");

            for (Iterator<Expression> it = entry.getLabels().iterator(); it.hasNext();) {
                Expression label = it.next();
                label.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }

            if (entry.getLabels().isNonEmpty() && entry.isDefault()) {
                codeBuilder.append(", default");
            }

            if (entry.getGuard().isPresent()) {
                codeBuilder.append(" when ");
                entry.getGuard().get().accept(this, codeBuilder);
            }

            codeBuilder.append(separator + " ");
        }

        if (entry.getStatements() != null) {
            for (Statement statement : entry.getStatements()) {
                statement.accept(this, codeBuilder);
            }
        }
    }

    /**
     * SwitchStmt
     */
    @Override
    public void visit(SwitchStmt switchStmt, StringBuilder codeBuilder) {
        printSwitchNode(switchStmt, codeBuilder);
    }

    /**
     * SynchronizedStmt
     */
    @Override
    public void visit(SynchronizedStmt synchronizedStmt, StringBuilder codeBuilder) {
        codeBuilder.append("synchronized (");
        synchronizedStmt.getExpression().accept(this, codeBuilder);
        codeBuilder.append(") ");
        synchronizedStmt.getBody().accept(this, codeBuilder);
    }

    /**
     * ThrowStmt
     */
    @Override
    public void visit(ThrowStmt throwStmt, StringBuilder codeBuilder) {
        codeBuilder.append("throw ");
        throwStmt.getExpression().accept(this, codeBuilder);
        codeBuilder.append(";");
    }

    /**
     * TryStmt
     */
    @Override
    public void visit(TryStmt tryStmt, StringBuilder codeBuilder) {
        codeBuilder.append("try ");

        if (tryStmt.getResources().isNonEmpty()) {
            codeBuilder.append("(");

            for (Iterator<Expression> it = tryStmt.getResources().iterator(); it.hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }

            codeBuilder.append(") ");
        }

        tryStmt.getTryBlock().accept(this, codeBuilder);

        for (CatchClause clause : tryStmt.getCatchClauses()) {
            clause.accept(this, codeBuilder);
        }

        if (tryStmt.getFinallyBlock().isPresent()) {
            codeBuilder.append(" finally ");
            tryStmt.getFinallyBlock().get().accept(this, codeBuilder);
        }
    }

    /**
     * UnparsableStmt
     */
    @Override
    public void visit(UnparsableStmt unparsable, StringBuilder codeBuilder) {
        codeBuilder.append("???;");
    }

    /**
     * WhileStmt
     */
    @Override
    public void visit(WhileStmt whileStmt, StringBuilder codeBuilder) {
        codeBuilder.append("while (");
        whileStmt.getCondition().accept(this, codeBuilder);
        codeBuilder.append(") ");
        whileStmt.getBody().accept(this, codeBuilder);
    }

    /**
     * YieldStmt
     */
    @Override
    public void visit(YieldStmt yieldStmt, StringBuilder codeBuilder) {
        codeBuilder.append("yield ");
        yieldStmt.getExpression().accept(this, codeBuilder);
        codeBuilder.append(";");
    }


    // =======================
    // ===== Expressions =====
    // =======================

    /**
     * ArrayAccessExpr
     */
    @Override
    public void visit(ArrayAccessExpr arrayAcc, StringBuilder codeBuilder) {
        arrayAcc.getName().accept(this, codeBuilder);
        codeBuilder.append("[");
        arrayAcc.getIndex().accept(this, codeBuilder);
        codeBuilder.append("]");
    }

    /**
     * ArrayCreationExpr
     */
    @Override
    public void visit(ArrayCreationExpr createExpr, StringBuilder codeBuilder) {
        codeBuilder.append("new ");

        createExpr.getElementType().accept(this, codeBuilder);

        for (ArrayCreationLevel level : createExpr.getLevels()) {
            level.accept(this, codeBuilder);
        }

        if (createExpr.getInitializer().isPresent()) {
            codeBuilder.append(" ");
            createExpr.getInitializer().get().accept(this, codeBuilder);
        }
    }

    /**
     * ArrayInitializerExpr
     */
    @Override
    public void visit(ArrayInitializerExpr arrayInit, StringBuilder codeBuilder) {
        codeBuilder.append("{");

        if (arrayInit.getValues() != null && arrayInit.getValues().isNonEmpty()) {
            for (Iterator<Expression> it = arrayInit.getValues().iterator(); it.hasNext();) {
                Expression expr = it.next();
                expr.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }

        codeBuilder.append("}");
    }

    /**
     * AssignExpr
     */
    @Override
    public void visit(AssignExpr assign, StringBuilder codeBuilder) {
        assign.getTarget().accept(this, codeBuilder);
        codeBuilder.append(assign.getOperator().asString());
        assign.getValue().accept(this, codeBuilder);
    }

    /**
     * BinaryExpr
     */
    @Override
    public void visit(BinaryExpr binaryExpr, StringBuilder codeBuilder) {
        binaryExpr.getLeft().accept(this, codeBuilder);
        codeBuilder.append(binaryExpr.getOperator().asString());
        binaryExpr.getRight().accept(this, codeBuilder);
    }

    /**
     * BooleanLiteralExpr
     */
    @Override
    public void visit(BooleanLiteralExpr booleanLiteral, StringBuilder codeBuilder) {
        codeBuilder.append(String.valueOf(booleanLiteral.getValue()));
    }

    /**
     * CastExpr
     */
    @Override
    public void visit(CastExpr castExpr, StringBuilder codeBuilder) {
        codeBuilder.append("(");
        castExpr.accept(this, codeBuilder);
        codeBuilder.append(")");

        castExpr.getExpression().accept(this, codeBuilder);
    }

    /**
     * CharLiteralExpr
     */
    @Override
    public void visit(CharLiteralExpr charLiteral, StringBuilder codeBuilder) {
        codeBuilder.append("'").append(charLiteral.getValue()).append("'");
    }

    /**
     * ClassExpr
     */
    @Override
    public void visit(ClassExpr classExpr, StringBuilder codeBuilder) {
        classExpr.getType().accept(this, codeBuilder);
        codeBuilder.append(".class");
    }

    /**
     * ConditoinalExpr
     */
    @Override
    public void visit(ConditionalExpr conditional, StringBuilder codeBuilder) {
        conditional.getCondition().accept(this, codeBuilder);
        codeBuilder.append("?");
        conditional.getThenExpr().accept(this, codeBuilder);
        codeBuilder.append(":");
        conditional.getElseExpr().accept(this, codeBuilder);
    }

    /**
     * DoubleLiteralExpr
     */
    @Override
    public void visit(DoubleLiteralExpr doubleLiteral, StringBuilder codeBuilder) {
        codeBuilder.append(doubleLiteral.getValue());
    }

    /**
     * EnclosedExpr
     */
    @Override
    public void visit(EnclosedExpr enclosed, StringBuilder codeBuilder) {
        codeBuilder.append("(");
        enclosed.getInner().accept(this, codeBuilder);
        codeBuilder.append(")");
    }

    /**
     * FieldAccessExpr
     * 
     * Example: person.name
     * 
     * scope: person
     * 
     * name: name
     */
    @Override
    public void visit(FieldAccessExpr fieldAccess, StringBuilder codeBuilder) {
        fieldAccess.getScope().accept(this, codeBuilder);
        codeBuilder.append(".");
        fieldAccess.getName().accept(this, codeBuilder);
    }

    /**
     * InstanceOfExpr
     * 
     * Example: obj instanceof String s
     * 
     * pattern: String s
     * 
     * Example: obj instanceof String
     * 
     * type: String
     */
    @Override
    public void visit(InstanceOfExpr instanceExpr, StringBuilder codeBuilder) {
        instanceExpr.getExpression().accept(this, codeBuilder);
        codeBuilder.append(" instanceof ");

        if (instanceExpr.getPattern().isPresent()) {
            instanceExpr.getPattern().get().accept(this, codeBuilder);
        } else {
            instanceExpr.getType().accept(this, codeBuilder);
        }
    }

    /**
     * IntegerLiteralExpr
     */
    @Override
    public void visit(IntegerLiteralExpr integerLiteral, StringBuilder codeBuilder) {
        codeBuilder.append(integerLiteral.getValue());
    }

    /**
     * LambdaExpr
     */
    @Override
    public void visit(LambdaExpr lambdaExpr, StringBuilder codeBuilder) {
        if (lambdaExpr.isEnclosingParameters()) {
            codeBuilder.append("(");
        }

        for (Iterator<Parameter> it = lambdaExpr.getParameters().iterator(); it.hasNext();) {
            it.next().accept(this, codeBuilder);
            if (it.hasNext()) {
                codeBuilder.append(",");
            }
        }

        if (lambdaExpr.isEnclosingParameters()) {
            codeBuilder.append(")");
        }

        codeBuilder.append(" -> ");

        if (lambdaExpr.getBody() instanceof ExpressionStmt) {
            ((ExpressionStmt) lambdaExpr.getBody()).getExpression().accept(this, codeBuilder);
        } else {
            lambdaExpr.getBody().accept(this, codeBuilder);
        }
    }

    /**
     * LongLiteralExpr
     */
    @Override
    public void visit(LongLiteralExpr longLiteral, StringBuilder codeBuilder) {
        codeBuilder.append(longLiteral.getValue());
    }

    /**
     * MarkerAnnotationExpr
     * 
     * Example: @Name
     */
    @Override
    public void visit(MarkerAnnotationExpr markAnnotation, StringBuilder codeBuilder) {
        codeBuilder.append("@");
        markAnnotation.getName().accept(this, codeBuilder);
    }

    /**
     * MemberValuePair
     */
    @Override
    public void visit(MemberValuePair valuePair, StringBuilder codeBuilder) {
        valuePair.getName().accept(this, codeBuilder);
        codeBuilder.append("=");
        valuePair.getValue().accept(this, codeBuilder);
    }

    /**
     * MethodCallExpr
     */
    @Override
    public void visit(MethodCallExpr methodCall, StringBuilder codeBuilder) {
        methodCall.getScope().ifPresent(scope -> {
            scope.accept(this, codeBuilder);
            codeBuilder.append(".");
        });

        printTypeArguments(methodCall.getTypeArguments().orElse(null), codeBuilder);
        methodCall.getName().accept(this, codeBuilder);
        printArguments(methodCall.getArguments(), codeBuilder);
    }

    /**
     * MethodReferenceExpr
     * 
     * Example: a::b
     * 
     * Example List<String>::<Integer>new
     */
    @Override
    public void visit(MethodReferenceExpr methodRef, StringBuilder codeBuilder) {
        if (methodRef.getScope() != null) {
            methodRef.getScope().accept(this, codeBuilder);
        }

        codeBuilder.append("::");

        printTypeArguments(methodRef.getTypeArguments().orElse(null), codeBuilder);

        if (methodRef.getIdentifier() != null) {
            codeBuilder.append(methodRef.getIdentifier());
        }
    }

    /**
     * Name
     * 
     * Example: a.b.c
     * 
     * identifier: c
     * 
     * qualifier: a.b
     */
    @Override
    public void visit(Name name, StringBuilder codeBuilder) {
        if (name.getQualifier().isPresent()) {
            name.getQualifier().get().accept(this, codeBuilder);
            codeBuilder.append(".");
        }
        codeBuilder.append(name.getIdentifier());
    }

    /**
     * NameExpr
     *
     * Use default implementation
     */
    @Override
    public void visit(NameExpr nameExpr, StringBuilder codeBuilder) {
        nameExpr.getName().accept(this, codeBuilder);
    }

    /**
     * NormalAnnotationExpr
     */
    @Override
    public void visit(NormalAnnotationExpr normalAnnotation, StringBuilder codeBuilder) {
        codeBuilder.append("@");
        normalAnnotation.getName().accept(this, codeBuilder);

        codeBuilder.append("(");
        if (normalAnnotation.getPairs() != null) {
            for (Iterator<MemberValuePair> it = normalAnnotation.getPairs().iterator(); it
                    .hasNext();) {

                MemberValuePair pair = it.next();
                pair.accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(",");
                }
            }
        }
        codeBuilder.append(")");
    }

    /**
     * NullLiteralExpr
     */
    @Override
    public void visit(NullLiteralExpr nullExpr, StringBuilder codeBuilder) {
        codeBuilder.append("null");
    }

    /**
     * ObjectCreationExpr
     * 
     * Example: HashMap.Entry<String, Long>(15)
     * 
     * Example: new A().new B()
     */
    @Override
    public void visit(ObjectCreationExpr objectCreation, StringBuilder codeBuilder) {
        if (objectCreation.hasScope()) {
            objectCreation.getScope().get().accept(this, codeBuilder);
            codeBuilder.append(".");
        }

        codeBuilder.append("new ");

        printTypeArguments(objectCreation.getTypeArguments().orElse(null), codeBuilder);
        objectCreation.getType().accept(this, codeBuilder);
        printArguments(objectCreation.getArguments(), codeBuilder);

        if (objectCreation.getAnonymousClassBody().isPresent()) {
            codeBuilder.append("{");
            printMembers(objectCreation.getAnonymousClassBody().get(), codeBuilder);
            codeBuilder.append("}");
        }
    }

    /**
     * RecordPatternExpr
     */
    @Override
    public void visit(RecordPatternExpr recordPattern, StringBuilder codeBuilder) {
        recordPattern.getType().accept(this, codeBuilder);
        printArguments(recordPattern.getPatternList(), codeBuilder);
    }

    /**
     * SimpleName
     */
    @Override
    public void visit(SimpleName simpleName, StringBuilder codeBuilder) {
        codeBuilder.append(simpleName.getIdentifier());
    }

    /**
     * SingleMemberAnnotationExpr
     */
    @Override
    public void visit(SingleMemberAnnotationExpr singleMemberAnnotation,
            StringBuilder codeBuilder) {

        codeBuilder.append("@");
        singleMemberAnnotation.getName().accept(this, codeBuilder);

        codeBuilder.append("(");
        singleMemberAnnotation.getMemberValue().accept(this, codeBuilder);
        codeBuilder.append(")");
    }

    /**
     * StringLiteralExpr
     */
    @Override
    public void visit(StringLiteralExpr stringLiteral, StringBuilder codeBuilder) {
        codeBuilder.append("\"");
        codeBuilder.append(stringLiteral.getValue());
        codeBuilder.append("\"");
    }

    /**
     * SuperExpr
     */
    @Override
    public void visit(SuperExpr superExpr, StringBuilder codeBuilder) {
        if (superExpr.getTypeName().isPresent()) {
            superExpr.getTypeName().get().accept(this, codeBuilder);
            codeBuilder.append(".");
        }
        codeBuilder.append("super");
    }

    /**
     * SwitchExpr
     */
    @Override
    public void visit(SwitchExpr switchExpr, StringBuilder codeBuilder) {
        printSwitchNode(switchExpr, codeBuilder);
    }

    /**
     * TextBlockLiteralExpr
     */
    @Override
    public void visit(TextBlockLiteralExpr textBlock, StringBuilder codeBuilder) {
        codeBuilder.append("\"\"\"");

        textBlock.stripIndentOfLines().forEach(line -> {
            codeBuilder.append("\n");
            codeBuilder.append(line);
        });

        codeBuilder.append("\"\"\"");
    }

    /**
     * ThisExpr
     */
    @Override
    public void visit(ThisExpr thisExpr, StringBuilder codeBuilder) {
        if (thisExpr.getTypeName().isPresent()) {
            thisExpr.getTypeName().get().accept(this, codeBuilder);
            codeBuilder.append(".");
        }
        codeBuilder.append("this");
    }

    /**
     * TypeExpr
     */
    @Override
    public void visit(TypeExpr typeExpr, StringBuilder codeBuilder) {
        if (typeExpr.getType() != null) {
            typeExpr.getType().accept(this, codeBuilder);
        }
    }

    /**
     * TypePatternExpr
     */
    @Override
    public void visit(TypePatternExpr typePattern, StringBuilder codeBuilder) {
        printModifiers(typePattern.getModifiers(), codeBuilder);
        typePattern.getType().accept(this, codeBuilder);
        codeBuilder.append(" ");
        typePattern.getName().accept(this, codeBuilder);
    }

    /**
     * UnaryExpr
     */
    @Override
    public void visit(UnaryExpr unaryExpr, StringBuilder codeBuilder) {
        if (unaryExpr.isPrefix()) {
            codeBuilder.append(unaryExpr.getOperator().asString());
        }

        unaryExpr.getExpression().accept(this, codeBuilder);

        if (unaryExpr.isPostfix()) {
            codeBuilder.append(unaryExpr.getOperator().asString());
        }
    }

    /**
     * VariableDeclarationExpr
     */
    @Override
    public void visit(VariableDeclarationExpr variableDecl, StringBuilder codeBuilder) {
        if (variableDecl.getParentNode().map(ExpressionStmt.class::isInstance).orElse(false)) {
            printMemberAnnotations(variableDecl.getAnnotations(), codeBuilder);
        } else {
            printAnnotations(variableDecl.getAnnotations(), false, codeBuilder);
        }

        printModifiers(variableDecl.getModifiers(), codeBuilder);

        if (variableDecl.getVariables().isNonEmpty()) {
            variableDecl.getMaximumCommonType().ifPresent(type -> type.accept(this, codeBuilder));
        }

        codeBuilder.append(" ");

        for (Iterator<VariableDeclarator> it = variableDecl.getVariables().iterator(); it
                .hasNext();) {
            it.next().accept(this, codeBuilder);
            if (it.hasNext()) {
                codeBuilder.append(",");
            }
        }
    }


    // =================
    // ===== Types =====
    // =================

    /**
     * ArrayType
     */
    @Override
    public void visit(ArrayType type, StringBuilder codeBuilder) {
        List<ArrayType> arrayTypeBuffer = new LinkedList<>();

        Type currentType = type;
        while (currentType instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) currentType;
            arrayTypeBuffer.add(arrayType);
            currentType = arrayType.getComponentType();
        }

        currentType.accept(this, codeBuilder);
        for (ArrayType arrayType : arrayTypeBuffer) {
            printAnnotations(arrayType.getAnnotations(), true, codeBuilder);
            codeBuilder.append("[]");
        }
    }

    /**
     * ClassOrInterfaceType
     */
    @Override
    public void visit(ClassOrInterfaceType type, StringBuilder codeBuilder) {
        if (type.getScope().isPresent()) {
            type.getScope().get().accept(this, codeBuilder);
            codeBuilder.append(".");
        }

        printAnnotations(type.getAnnotations(), false, codeBuilder);
        type.getName().accept(this, codeBuilder);

        if (type.isUsingDiamondOperator()) {
            codeBuilder.append("<>");
        } else {
            printTypeArguments(type.getTypeArguments().orElse(null), codeBuilder);
        }
    }

    /**
     * IntersectionType
     */
    @Override
    public void visit(IntersectionType type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), false, codeBuilder);

        for (Iterator<ReferenceType> it = type.getElements().iterator(); it.hasNext();) {
            it.next().accept(this, codeBuilder);
            if (it.hasNext()) {
                codeBuilder.append(" & ");
            }
        }
    }

    /**
     * PrimitiveType
     */
    @Override
    public void visit(PrimitiveType type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), true, codeBuilder);
        codeBuilder.append(type.getType().asString());
    }

    /**
     * TypeParameter
     */
    @Override
    public void visit(TypeParameter type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), false, codeBuilder);

        type.getName().accept(this, codeBuilder);
        if (notNullOrEmpty(type.getTypeBound())) {
            codeBuilder.append(" extends ");
            for (Iterator<ClassOrInterfaceType> it = type.getTypeBound().iterator(); it
                    .hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(" & ");
                }
            }
        }
    }

    /**
     * UnionType
     */
    @Override
    public void visit(UnionType type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), true, codeBuilder);
        for (Iterator<ReferenceType> it = type.getElements().iterator(); it.hasNext();) {
            it.next().accept(this, codeBuilder);
            if (it.hasNext()) {
                codeBuilder.append(" | ");
            }
        }
    }

    /**
     * UnknownType
     */
    @Override
    public void visit(UnknownType type, StringBuilder codeBuilder) {
        /* Nothing to print */
    }

    /**
     * VarType
     */
    @Override
    public void visit(VarType type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), false, codeBuilder);
        codeBuilder.append("var");
    }

    /**
     * VoidType
     */
    @Override
    public void visit(VoidType type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), false, codeBuilder);
        codeBuilder.append("void");
    }

    /**
     * WildcardType
     */
    @Override
    public void visit(WildcardType type, StringBuilder codeBuilder) {
        printAnnotations(type.getAnnotations(), false, codeBuilder);
        codeBuilder.append("?");

        if (type.getExtendedType().isPresent()) {
            codeBuilder.append(" extends ");
            type.getExtendedType().get().accept(this, codeBuilder);
        }
        if (type.getSuperType().isPresent()) {
            codeBuilder.append(" super ");
            type.getSuperType().get().accept(this, codeBuilder);
        }
    }


    // ===================
    // ===== Modules =====
    // ===================

    private void printPrePostFixOptionalList(NodeList<? extends Visitable> args,
            StringBuilder codeBuilder, String prefix, String separator, String postfix) {
        if (args.isNonEmpty()) {
            codeBuilder.append(prefix);

            for (Iterator<? extends Visitable> it = args.iterator(); it.hasNext();) {
                it.next().accept(this, codeBuilder);
                if (it.hasNext()) {
                    codeBuilder.append(separator);
                }
            }

            codeBuilder.append(postfix);
        }
    }

    /**
     * ModuleDeclaration
     */
    @Override
    public void visit(ModuleDeclaration decl, StringBuilder codeBuilder) {
        printMemberAnnotations(decl.getAnnotations(), codeBuilder);

        if (decl.isOpen()) {
            codeBuilder.append("open ");
        }

        codeBuilder.append("module ");
        decl.getName().accept(this, codeBuilder);
        codeBuilder.append(" {");
        decl.getDirectives().accept(this, codeBuilder);
        codeBuilder.append("}");
    }

    /**
     * ModuleExportsDirective
     */
    @Override
    public void visit(ModuleExportsDirective dir, StringBuilder codeBuilder) {
        codeBuilder.append("exports ");
        dir.getName().accept(this, codeBuilder);
        printPrePostFixOptionalList(dir.getModuleNames(), codeBuilder, " to ", ", ", "");
        codeBuilder.append(";");
    }

    /**
     * ModuleOpensDirective
     */
    @Override
    public void visit(ModuleOpensDirective dir, StringBuilder codeBuilder) {
        codeBuilder.append("opens ");
        dir.getName().accept(this, codeBuilder);
        printPrePostFixOptionalList(dir.getModuleNames(), codeBuilder, " to ", ", ", "");
        codeBuilder.append(";");
    }

    /**
     * ModuleProvidesDirective
     */
    @Override
    public void visit(ModuleProvidesDirective dir, StringBuilder codeBuilder) {
        codeBuilder.append("provides ");
        dir.getName().accept(this, codeBuilder);
        printPrePostFixOptionalList(dir.getWith(), codeBuilder, " with ", ", ", "");
        codeBuilder.append(";");
    }

    /**
     * ModuleRequiresDirective
     */
    @Override
    public void visit(ModuleRequiresDirective dir, StringBuilder codeBuilder) {
        codeBuilder.append("requires ");
        printModifiers(dir.getModifiers(), codeBuilder);
        dir.getName().accept(this, codeBuilder);
        codeBuilder.append(";");
    }

    /**
     * ModuleUsesDirective
     */
    @Override
    public void visit(ModuleUsesDirective dir, StringBuilder codeBuilder) {
        codeBuilder.append("uses ");
        dir.getName().accept(this, codeBuilder);
        codeBuilder.append(";");
    }

}
