/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2023 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.maven.plugins.slinginitialcontenttransform.contentparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.wcm.tooling.commons.contentpackagebuilder.element.ContentElement;

class JsonContentLoaderTest {

  @Test
  void testSample() throws IOException {
    ContentElement root = loadJson("src/test/resources/input/sample.json");

    assertEquals(Map.of(
        "jcr:primaryType", "nt:unstructure",
        "prop1", "value1",
        "customNamespace:prop2", "value2"
    ), root.getProperties());

    ContentElement children = root.getChildren().get("children");
    assertEquals(List.of("child005", "child001", "child003", "child002"),
        List.copyOf(children.getChildren().keySet()));

    ContentElement child001 = children.getChildren().get("child001");
    assertEquals(List.of("child001c", "child001d", "child001b", "child001a"),
        List.copyOf(child001.getChild("children").getChildren().keySet()));
  }

  @Test
  void testDialogSnippet() throws IOException {
    ContentElement root = loadJson("src/test/resources/input/dialogSnippet.json");

    ContentElement dialogItems = root.getChild("cq:dialog")
        .getChild("content").getChild("items").getChild("tabs").getChild("items");

    assertEquals(List.of("media", "cta1", "cta2"),
        List.copyOf(dialogItems.getChildren().keySet()));
  }

  private ContentElement loadJson(String path) throws IOException {
    File jsonFile = new File(path);
    JsonContentLoader underTest = new JsonContentLoader();
    try (FileInputStream is = new FileInputStream(jsonFile)) {
      return underTest.load(is);
    }
  }

}
