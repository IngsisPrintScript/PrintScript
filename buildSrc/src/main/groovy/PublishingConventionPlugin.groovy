import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

class PublishingConventionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def sha = System.getenv("GITHUB_SHA")
        if (sha != null) {
          project.version = "1.0.0-" + sha.substring(0, 7)
        } else {
          project.version = "1.0.0-SNAPSHOT"
        }
        project.plugins.apply('java')
        project.plugins.apply('maven-publish')

        project.publishing {
            publications {
                gpr(MavenPublication) {
                    from project.components.java
                    artifactId = project.name.toLowerCase()
                }
            }
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = project.uri("https://maven.pkg.github.com/IngsisPrintScript/PrintScript")
                    credentials {
                        username = System.getenv('GITHUB_ACTOR') ?: project.findProperty('gprUser')
                        password = System.getenv('GITHUB_TOKEN') ?: project.findProperty('gprToken')
                    }
                }
            }
        }
    }
}
