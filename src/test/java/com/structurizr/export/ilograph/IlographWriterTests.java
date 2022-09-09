package com.structurizr.export.ilograph;

import com.structurizr.Workspace;
import com.structurizr.export.AbstractExporterTests;
import com.structurizr.export.Diagram;
import com.structurizr.export.WorkspaceExport;
import com.structurizr.export.dot.DOTExporter;
import com.structurizr.model.CustomElement;
import com.structurizr.model.Model;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.CustomView;
import com.structurizr.view.ThemeUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class IlographWriterTests extends AbstractExporterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./src/test/structurizr-36141-workspace.json"));
        IlographExporter ilographExporter = new IlographExporter();
        WorkspaceExport export = ilographExporter.export(workspace);

        String expected = readFile(new File("./src/test/java/com/structurizr/export/ilograph/36141.ilograph"));
        assertEquals(expected, export.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./src/test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        IlographExporter ilographExporter = new IlographExporter();
        WorkspaceExport export = ilographExporter.export(workspace);

        String expected = readFile(new File("./src/test/java/com/structurizr/export/ilograph/54915.ilograph"));
        assertEquals(expected, export.getDefinition());
    }

    @Test
    public void test_renderCustomElements() throws Exception {
        Workspace workspace = new Workspace("Name", "Description");
        Model model = workspace.getModel();

        CustomElement a = model.addCustomElement("A");
        CustomElement b = model.addCustomElement("B", "Custom", "Description");
        a.uses(b, "Uses");

        WorkspaceExport export = new IlographExporter().export(workspace);
        assertEquals("resources:\n" +
                "\n" +
                "  - id: \"1\"\n" +
                "    name: \"A\"\n" +
                "    subtitle: \"\"\n" +
                "    backgroundColor: \"#dddddd\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "  - id: \"2\"\n" +
                "    name: \"B\"\n" +
                "    subtitle: \"[Custom]\"\n" +
                "    description: \"Description\"\n" +
                "    backgroundColor: \"#dddddd\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "perspectives:\n" +
                "  - name: Static Structure\n" +
                "    relations:\n" +
                "      - from: \"1\"\n" +
                "        to: \"2\"\n" +
                "        label: \"Uses\"\n" +
                "        color: \"#707070\"\n\n", export.getDefinition());
    }

}