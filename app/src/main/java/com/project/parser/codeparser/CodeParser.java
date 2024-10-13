package com.project.parser.codeparser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class CodeParser {
    private CodeParser() {}

    // Parse code file
    public static CompilationUnit parseFile(String fileName)
            throws IOException, NoSuchElementException {
        try {
            ParserConfiguration parserConf = new ParserConfiguration();
            parserConf.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_14);
            JavaParser javaParser = new JavaParser(parserConf);
            return javaParser.parse(Paths.get(fileName)).getResult().orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static CompilationUnit parseCode(String code) throws NoSuchElementException {
        try {
            ParserConfiguration parserConf = new ParserConfiguration();
            parserConf.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_14);
            JavaParser javaParser = new JavaParser(parserConf);
            return javaParser.parse(code).getResult().orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static boolean compareRetrieveResult(CompilationUnit originUnit, String retrievedCode) {
        return originUnit.toString().replaceAll("\\s+", "")
                .equals(retrievedCode.replaceAll("\\s+", ""));
    }

    public static String retrieveCode(CompilationUnit cu) {
        VoidVisitor<StringBuilder> visitor = new CodeRetrieveVisitor();
        StringBuilder stringBuilder = new StringBuilder();

        cu.accept(visitor, stringBuilder);

        // Check original and retrieved codes
        String retrievedCode = stringBuilder.toString();
        cu.getAllComments().forEach(Comment::remove);
        if (!compareRetrieveResult(cu, retrievedCode)) {
            throw new IllegalArgumentException("Original and retrieved codes not match");
        }

        return stringBuilder.toString();
    }
}
