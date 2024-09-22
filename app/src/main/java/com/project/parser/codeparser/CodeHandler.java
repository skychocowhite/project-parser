package com.project.parser.codeparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.utils.Pair;

public class CodeHandler {

    private static final Logger logger = LoggerFactory.getLogger(CodeHandler.class);


    private String savePath;

    public CodeHandler(String savePath) {
        File saveRoot = new File(savePath);

        if (!saveRoot.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Save path %s is not a directory or not exist", savePath));
        }

        this.savePath = savePath;
    }

    public void printNodeStructure(Node node, int depth) {
        for (int i = 1; i <= depth; ++i) {
            System.out.print("  ");
        }

        System.out.println(node.getClass().getSimpleName() + ": " + node);

        for (Node child : node.getChildNodes()) {
            printNodeStructure(child, depth + 1);
        }
    }

    public void parseProject(String fileRootPath, String saveProjectName) throws IOException {
        File sourceRoot = new File(fileRootPath);

        if (!sourceRoot.exists() || !sourceRoot.isDirectory()) {
            throw new IllegalArgumentException(String.format(
                    "The project path %s does not exist or is not a directory.", fileRootPath));
        }

        Queue<Pair<String, String>> queue = new LinkedList<>();
        queue.add(new Pair<>(fileRootPath, savePath + "/" + saveProjectName));

        while (!queue.isEmpty()) {
            String curFilePath = queue.element().a;
            String curSavePath = queue.element().b;
            queue.remove();

            Path path = Paths.get(curFilePath);
            if (Files.isDirectory(path)) {
                parseDirectory(curFilePath, curSavePath, queue);
            } else {
                parseFile(curFilePath, curSavePath);
            }
        }
    }

    private void parseDirectory(String dirPath, String saveDirName,
            Queue<Pair<String, String>> pathQueue) throws IOException {
        logger.info("Parse directory {}", dirPath);

        // Parse Java files in current folder
        try (Stream<Path> paths = Files.list(Paths.get(dirPath))) {
            paths.filter(path -> path.toString().endsWith(".java")).forEach(path -> {
                pathQueue.add(new Pair<>(path.toString(),
                        Paths.get(saveDirName).resolve(path.getFileName()).toString()));
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        // Parse subdirectories
        try (Stream<Path> paths = Files.list(Paths.get(dirPath))) {
            paths.filter(Files::isDirectory).forEach(path -> {
                pathQueue.add(new Pair<>(path.toString(),
                        Paths.get(saveDirName).resolve(path.getFileName()).toString()));
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void parseFile(String filePath, String saveFileName) {
        CompilationUnit cu;

        try {
            logger.info("Parse file {}", filePath);
            cu = CodeParser.parseFile(filePath);
            cu.getAllComments().forEach(Comment::remove);

            // Check our visitor works correctly
            CodeParser.retrieveCode(cu);

            logger.info("Save file to {}", saveFileName);
            saveAST(cu, saveFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAST(Node cu, String filePath) throws IOException {
        NodeJson jsonFile = convertNodeToJson(cu);

        ObjectMapper mapper = new ObjectMapper();

        // Create corresponding directories if not exist
        try {
            Path dirPath = Paths.get(filePath).getParent();
            if (dirPath != null && !Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        try {
            mapper.writeValue(
                    new File(filePath.substring(0, filePath.lastIndexOf(".")) + "-ast.json"),
                    jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private NodeJson convertNodeToJson(Node node) {
        String code = node.toString().replaceAll("\\s+", " ");
        NodeJson json = new NodeJson(node.getClass().getSimpleName(), code);

        for (Node child : node.getChildNodes()) {
            json.addNode(convertNodeToJson(child));
        }

        return json;
    }

    public static class NodeJson {
        private String nodeType;
        private String code;
        private List<NodeJson> children;

        public NodeJson(String nodeType, String code) {
            this.nodeType = nodeType;
            this.code = code;
            this.children = new LinkedList<>();
        }

        public void addNode(NodeJson child) {
            this.children.add(child);
        }

        public String getNodeType() {
            return nodeType;
        }

        public String getCode() {
            return code;
        }

        public List<NodeJson> getChildren() {
            return children;
        }
    }
}
