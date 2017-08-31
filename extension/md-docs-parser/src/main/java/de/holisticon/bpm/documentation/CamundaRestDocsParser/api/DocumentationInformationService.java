package de.holisticon.bpm.documentation.CamundaRestDocsParser.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.UTF8Writer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.vladsch.flexmark.ast.Node;
import de.holisticon.bpm.documentation.CamundaRestDocsParser.interpreter.DocumentInterpreter;
import de.holisticon.bpm.documentation.CamundaRestDocsParser.parser.DocumentParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DocumentationInformationService {

    private DocumentParser documentParser;

    private DocumentInterpreter documentInterpreter;

    @Value("${camunda.reference.base}")
    private String docBase;

    DocumentationInformationService(DocumentParser documentParser, DocumentInterpreter documentInterpreter) {
        this.documentParser = documentParser;
        this.documentInterpreter = documentInterpreter;
    }

    @PostConstruct
    public void run() throws Exception {
        List<RestOperation> apis = getDocumentations();
        UTF8Writer utf8Writer = new UTF8Writer(new BufferedOutputStream(System.out));
        YAMLFactory yf = new YAMLFactory();
        YAMLGenerator generator = yf.createGenerator(System.out);
        generator.setCodec(new ObjectMapper());
            generator.writeObject(apis);
    }

    private List<RestOperation> getDocumentations() {
        File dir = new File(docBase);
        WildcardFileFilter fileFilter = new WildcardFileFilter("*.md");
        Collection<File> files = FileUtils.listFiles(dir, fileFilter, TrueFileFilter.INSTANCE);
        log.info("Reading from " + files.size() + " resources");
        return files.stream()
                .map(File::getAbsolutePath)
                .map(this::getDocumenation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private RestOperation getDocumenation(String filename) {
        Path path = Paths.get(filename);
        try {
            String fileContents = new String(Files.readAllBytes(path));
            Map<String, Node> parsedTree = documentParser.parse(fileContents);
            if (parsedTree != null)
                return documentInterpreter.interpret(parsedTree);
        } catch (IOException e) {
        }
        return null;
    }
}
