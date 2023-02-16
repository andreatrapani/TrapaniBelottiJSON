/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.belotti.es37;

/**
 *
 * @author ALESSANDROBELOTTI
 */
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Es37 {

    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        try ( FileReader reader = new FileReader("esami.json")) {
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                // Get paziente object
                JsonObject paziente = jsonObject.get("paziente").getAsJsonObject();
                String nome = paziente.get("nome").getAsString();
                String cognome = paziente.get("cognome").getAsString();
                String codiceFiscale = paziente.get("codice fiscale").getAsString();
                String sesso = paziente.get("sesso").getAsString();
                JsonElement etaElement = paziente.get("età");
                int eta = etaElement != null ? etaElement.getAsInt() : 0;

                // Get esame object
                JsonObject esame = jsonObject.get("esame").getAsJsonObject();
                String data = esame.get("data").getAsString();
                String codice = esame.get("codice").getAsString();
                String numeroMatricolo = esame.get("numero di matricolo").getAsString();
                String denominazioneAnalisi = esame.get("denominazione dell'analisi").getAsString();
                JsonElement valoreNumUnitaMisuraElement = esame.get("valore numero e unità di misura del risultato");
                int valoreNumUnitaMisura = valoreNumUnitaMisuraElement != null ? valoreNumUnitaMisuraElement.getAsInt() : 0;
                int valoreMinimo = esame.get("valore minimo").getAsInt();
                int valoreMassimo = esame.get("valore massimo").getAsInt();

                // Get prilievo sangue and luogo
                String prilievoSangue = jsonObject.get("prilievo sangue").getAsString();
                JsonObject luogo = jsonObject.get("luogo").getAsJsonObject();

                // Print out the extracted data
                System.out.println("Nome: " + nome);
                System.out.println("Cognome: " + cognome);
                System.out.println("Codice fiscale: " + codiceFiscale);
                System.out.println("Sesso: " + sesso);
                System.out.println("Età: " + eta);
                System.out.println("Data: " + data);
                System.out.println("Codice: " + codice);
                System.out.println("Numero di matricolo: " + numeroMatricolo);
                System.out.println("Denominazione dell'analisi: " + denominazioneAnalisi);
                System.out.println("Valore numero e unità di misura del risultato: " + valoreNumUnitaMisura);
                System.out.println("Valore minimo: " + valoreMinimo);
                System.out.println("Valore massimo: " + valoreMassimo);
                System.out.println("Prilievo sangue: " + prilievoSangue);
                System.out.println("Luogo: " + luogo);
            }

            LocalDateTime startDateTime = LocalDateTime.parse("2022-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            LocalDateTime endDateTime = LocalDateTime.parse("2022-12-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println("\n\nEsami effettuati nel periodo " + startDateTime + " - " + endDateTime + ":\n");
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                JsonObject esame = jsonObject.get("esame").getAsJsonObject();
                String data = esame.get("data").getAsString();
                LocalDateTime esameDateTime = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (esameDateTime.isAfter(startDateTime) && esameDateTime.isBefore(endDateTime)) {
                    System.out.println("Codice esame: " + esame.get("codice").getAsString());
                    System.out.println("Denominazione dell'analisi: " + esame.get("denominazione dell'analisi").getAsString());
                    System.out.println("Valore numero e unità di misura del risultato: " + esame.get("valore numero e unità di misura del risultato").getAsString());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
