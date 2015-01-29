package com.augustl.gradle.warlike

import org.eclipse.jetty.annotations.AnnotationConfiguration
import org.eclipse.jetty.plus.webapp.EnvConfiguration
import org.eclipse.jetty.plus.webapp.PlusConfiguration
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.util.resource.Resource
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.FragmentConfiguration
import org.eclipse.jetty.webapp.MetaInfConfiguration
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.webapp.WebInfConfiguration
import org.eclipse.jetty.webapp.WebXmlConfiguration

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

        ResourceHandler resourceHandler = new CustomResourceHandler()
        resourceHandler.setResourceBase("src/main/webapp")

        HandlerList handlers = new HandlerList();
        handlers.setHandlers([resourceHandler, context] as Handler[])
        server.setHandler(handlers)
        server.start()
    }

    static final class CustomResourceHandler extends ResourceHandler {
        @Override
        void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            if (baseRequest.isHandled()) {
                return
            }

            if (request.getRequestURI().equals("/")) {
                Resource resource = getResource(request);
                if (getWelcome(resource)) {
                    super.handle(target, baseRequest, request, response)
                } else {
                    return
                }
            } else {
                super.handle(target, baseRequest, request, response)
            }
        }
    }
}