package com.augustl.gradle.warlike

import org.eclipse.jetty.annotations.AnnotationConfiguration
import org.eclipse.jetty.plus.webapp.EnvConfiguration
import org.eclipse.jetty.plus.webapp.PlusConfiguration
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.util.resource.ResourceCollection
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.FragmentConfiguration
import org.eclipse.jetty.webapp.MetaInfConfiguration
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.webapp.WebInfConfiguration
import org.eclipse.jetty.webapp.WebXmlConfiguration
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer
import org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter

class WarlikeServer {
    public static void main(String[] args) {
        Server server = new Server(new InetSocketAddress(new InetSocketAddress(0).getAddress(), new Integer(args[0])))

        WebAppContext context = new WebAppContext()
        ResourceCollection resources = new ResourceCollection(["build/main-web", "src/main/webapp"] as String[])
        context.setBaseResource(resources)
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

        WebSocketUpgradeFilter filter = WebSocketUpgradeFilter.configureContext(context)
        ServerContainer jettyContainer = new ServerContainer(filter, filter.getFactory(), server.getThreadPool())
        context.addBean(jettyContainer)
        context.setAttribute(javax.websocket.server.ServerContainer.class.getName(), jettyContainer)

        HandlerList handlers = new HandlerList();
        handlers.setHandlers([context] as Handler[])
        server.setHandler(handlers)
        server.start()
    }
}