package com.augustl.gradle.warlike

import org.gradle.api.Task
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSet
import org.gradle.plugins.ide.idea.IdeaPlugin

import javax.inject.Inject

class WarlikePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply(GroovyPlugin)
        JavaPluginConvention convention = project.getConvention().getPlugin(JavaPluginConvention.class)

        convention.getSourceSets().main {
            output.classesDir = "build/main-web/WEB-INF/classes"
            output.resourcesDir = "build/main-web/WEB-INF/resources"
        }

        SourceSet mainSourceSet = convention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME)

        Task runWarlike = project.tasks.create("runWarlike", WarlikeTask)
        runWarlike.dependsOn("classes")
        runWarlike.description = "Runs an auto-reloading WAR container like environment"
        runWarlike.classpath = mainSourceSet.runtimeClasspath
        runWarlike.classpath += project.buildscript.configurations.classpath
        runWarlike.main = "com.augustl.gradle.warlike.WarlikeServer"
        String springloadedJar = project.buildscript.configurations.classpath.find { it.name.startsWith("springloaded")}
        runWarlike.jvmArgs = ["-javaagent:${springloadedJar}", "-noverify"]
    }
}
