package com.jakartaeeudbl.jakartamission.business;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/pages/*") // Le filtre s'applique à toutes les pages dans le dossier /pages/
public class SessionControlFilter implements Filter {
   
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre si nécessaire
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // On vérifie la présence de l'attribut "user" (clé créée dans SessionManager)
        String user = (String) httpRequest.getSession().getAttribute("user");
       
        if (user == null) {
            // Si l'utilisateur n'est pas connecté, redirection forcée vers l'accueil (index)
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
        } else {
            // Si l'attribut existe, on laisse passer la requête vers la page demandée
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources du filtre
    }
}