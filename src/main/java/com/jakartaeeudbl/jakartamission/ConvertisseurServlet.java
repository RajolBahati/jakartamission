package com.jakartaeeudbl.jakartamission;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ConvertisseurServlet", urlPatterns = {"/ConvertisseurServlet"})
public class ConvertisseurServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String nom = request.getParameter("nomUtilisateur");
        if (nom == null) nom = "";

        double tauxConversion = 15500.0;

        // Configuration pour éviter les caractères spéciaux bizarres
        DecimalFormatSymbols symboles = new DecimalFormatSymbols(Locale.US);
        symboles.setGroupingSeparator(' ');
        symboles.setDecimalSeparator('.');

        DecimalFormat dfIdr = new DecimalFormat("#,##0 IDR", symboles);
        DecimalFormat dfUsd = new DecimalFormat("$ #,##0.00", symboles);

        // CHANGEMENT ICI : Redirection vers .xhtml
        StringBuilder url = new StringBuilder("index.xhtml");
        url.append("?nomUtilisateur=").append(URLEncoder.encode(nom, StandardCharsets.UTF_8));

        try {
            if ("usdToIdr".equals(action)) {
                String montantStr = request.getParameter("montantUSD");
                
                if (montantStr != null && !montantStr.isEmpty()) {
                    double montant = Double.parseDouble(montantStr);
                    double resultat = montant * tauxConversion;
                    
                    url.append("&montantUSD=").append(montantStr);
                    url.append("&resIdr=").append(URLEncoder.encode(dfIdr.format(resultat), StandardCharsets.UTF_8));
                }
                
                // Préserver les anciennes valeurs
                String oldMontantIDR = request.getParameter("montantIDR");
                String oldResUsd = request.getParameter("resUsd");
                
                if (oldMontantIDR != null && !oldMontantIDR.isEmpty()) {
                    url.append("&montantIDR=").append(oldMontantIDR);
                }
                if (oldResUsd != null && !oldResUsd.isEmpty()) {
                    url.append("&resUsd=").append(URLEncoder.encode(oldResUsd, StandardCharsets.UTF_8));
                }

            } else if ("idrToUsd".equals(action)) {
                String montantStr = request.getParameter("montantIDR");
                
                if (montantStr != null && !montantStr.isEmpty()) {
                    double montant = Double.parseDouble(montantStr);
                    double resultat = montant / tauxConversion;
                    
                    url.append("&montantIDR=").append(montantStr);
                    url.append("&resUsd=").append(URLEncoder.encode(dfUsd.format(resultat), StandardCharsets.UTF_8));
                }
                
                // Préserver les anciennes valeurs
                String oldMontantUSD = request.getParameter("montantUSD");
                String oldResIdr = request.getParameter("resIdr");
                
                if (oldMontantUSD != null && !oldMontantUSD.isEmpty()) {
                    url.append("&montantUSD=").append(oldMontantUSD);
                }
                if (oldResIdr != null && !oldResIdr.isEmpty()) {
                    url.append("&resIdr=").append(URLEncoder.encode(oldResIdr, StandardCharsets.UTF_8));
                }
            }
        } catch (NumberFormatException e) {
            // Ignorer erreur
        }

        response.sendRedirect(url.toString());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // CHANGEMENT ICI AUSSI
        response.sendRedirect("index.xhtml");
    }
}