// original bundle
assert new File(basedir, "target/sling-initial-content-transform-maven-plugin-transform-bundle-1.0.0-SNAPSHOT.jar").exists();

// transformed bundle
assert new File(basedir, "target/sling-initial-content-transform-maven-plugin-transform-bundle-1.0.0-SNAPSHOT-bundle.jar").exists();

// extracted content package
assert new File(basedir, "target/sling-initial-content-transform-maven-plugin-transform-bundle-1.0.0-SNAPSHOT-content.zip").exists();

return true;
