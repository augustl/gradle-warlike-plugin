package com.augustl.gradle.warlike

import org.eclipse.jetty.annotations.AnnotationConfiguration
import org.eclipse.jetty.plus.webapp.EnvConfiguration
import org.eclipse.jetty.plus.webapp.PlusConfiguration
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.FragmentConfiguration
import org.eclipse.jetty.webapp.MetaInfConfiguration
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.webapp.WebInfConfiguration
import org.eclipse.jetty.webapp.WebXmlConfiguration

class WarlikeServer {
    public static void main(String[] args) {
        Server server = new Server(new Integer(args[0]))

        WebAppContext context = new WebAppContext()
        context.setResourceBase("build/main-web")
        context.setConfigurations([
            new AnnotationConfiguration(),
            new WebXmlConfiguration(),
            new WebInfConfiguration(),
            new PlusConfiguration(),
            new MetaInfConfiguration(),
            new FragmentConfiguration(),
            new EnvConfiguration()
        ] as Configuration[])
        context.setContextPath("/")
        context.setParentLoaderPriority(true)

        ResourceHandler resourceHandler = new ResourceHandler()
        resourceHandler.setResourceBase("src/main/webapp")

        HandlerList handlers = new HandlerList();
        handlers.setHandlers([context, resourceHandler] as Handler[])
        server.setHandler(handlers)
        server.start()
    }
}