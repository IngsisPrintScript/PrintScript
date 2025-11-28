import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

class PublishingConventionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
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

