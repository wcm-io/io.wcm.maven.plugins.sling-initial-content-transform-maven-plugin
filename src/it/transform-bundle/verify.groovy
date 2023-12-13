def baseFileName = "target/sling-initial-content-transform-maven-plugin-transform-bundle-1.0.0-SNAPSHOT"

def expectedXmlContent = """<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<jcr:root xmlns:jcr="http://www.jcp.org/jcr/1.0" xmlns:cq="http://www.day.com/jcr/cq/1.0" xmlns:crx="http://www.day.com/crx/1.0" xmlns:customNamespace="http://customnamespace.org" xmlns:dam="http://www.day.com/dam/1.0" xmlns:granite="http://www.adobe.com/jcr/granite/1.0" xmlns:nt="http://www.jcp.org/jcr/nt/1.0" xmlns:oak="http://jackrabbit.apache.org/oak/ns/1.0" xmlns:rep="internal" xmlns:sling="http://sling.apache.org/jcr/sling/1.0" xmlns:slingevent="http://sling.apache.org/jcr/event/1.0" xmlns:wcmio="http://wcm.io/ns" customNamespace:prop2="value2" jcr:primaryType="nt:unstructure" prop1="value1">
  <children jcr:primaryType="nt:unstructured">
    <child005 jcr:primaryType="nt:unstructured" prop1="value5"/>
    <child001 jcr:primaryType="nt:unstructured" prop1="value1">
      <children jcr:primaryType="nt:unstructured">
        <child001c jcr:primaryType="nt:unstructured" prop1="value-c"/>
        <child001d jcr:primaryType="nt:unstructured" prop1="value-d"/>
        <child001a jcr:primaryType="nt:unstructured" prop1="value-a"/>
        <child001b jcr:primaryType="nt:unstructured" prop1="value-b"/>
      </children>
    </child001>
    <child003 jcr:primaryType="nt:unstructured" prop1="value3"/>
    <child002 jcr:primaryType="nt:unstructured" prop1="value2"/>
  </children>
</jcr:root>
"""

// original bundle
File jarFile = new File(basedir, "${baseFileName}.jar")
assert jarFile.exists()

// transformed bundle
File jarBundleFile = new File(basedir, "${baseFileName}-bundle.jar")
assert jarBundleFile.exists()

// extracted content package
File contentPackageFile = new File(basedir, "${baseFileName}-content.zip")
assert contentPackageFile.exists()

// validate XML output
try (zipFile = new java.util.zip.ZipFile(contentPackageFile)) {
  zipFile.entries().each {
    if (it.name == "jcr_root/apps/integration-test/sample/.content.xml") {
      def xmlContent = zipFile.getInputStream(it).text.replace("\r\n", "\n")
      assert xmlContent == expectedXmlContent
    }
  }
}

return true
