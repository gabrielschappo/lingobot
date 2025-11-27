package com.example.lingobot.service;

import com.example.lingobot.dto.GeminiRequest;
import com.example.lingobot.dto.GeminiResponse;
import com.example.lingobot.model.ExercicioLog;
import com.example.lingobot.repository.ExercicioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class TutorService {

    private final ExercicioRepository repositorio;
    private final RestTemplate restTemplate;

    @Value("${gemini.api-key}")
    private String apiKey;

    private final String URL_GOOGLE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    private final List<String> TEMAS = Arrays.asList(
            "Viagens e Aeroporto",
            "Culinária e Restaurantes",
            "Tecnologia e Computadores",
            "Animais de Estimação",
            "Filmes e Cinema",
            "Esportes e Exercícios",
            "Trabalho e Escritório",
            "Música e Shows",
            "Clima e Estações do Ano",
            "Compras e Roupas",
            "Sentimentos e Emoções",
            "Rotina Matinal"
    );

    public TutorService(ExercicioRepository repositorio) {
        this.repositorio = repositorio;
        this.restTemplate = new RestTemplate();
    }

    private String chamarGemini(String prompt) {
        try {
            if (apiKey == null || apiKey.trim().isEmpty()) {
                return "Erro: Configure sua API Key.";
            }

            String urlFinal = URL_GOOGLE + "?key=" + apiKey;
            GeminiRequest requestBody = new GeminiRequest(prompt);

            GeminiResponse response = restTemplate.postForObject(urlFinal, requestBody, GeminiResponse.class);

            if (response != null && !response.getCandidates().isEmpty()) {
                return response.getCandidates().get(0).getContent().getParts().get(0).getText();
            }
        } catch (HttpClientErrorException e) {
            System.err.println("ERRO GOOGLE: " + e.getResponseBodyAsString());
            return "Erro na IA: " + e.getStatusText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro interno.";
        }
        return "A IA não respondeu.";
    }

    public String gerarDesafio() {
        String temaSorteado = TEMAS.get(new Random().nextInt(TEMAS.size()));

        String prompt = String.format(
                "Gere uma frase em Inglês (nível A2 - Básico/Intermediário) sobre o tema: '%s'. " +
                        "A frase deve ser útil para conversação no dia a dia. " +
                        "Não use frases muito curtas ou óbvias. " +
                        "Retorne APENAS a frase em inglês, sem traduções ou explicações.",
                temaSorteado
        );

        return chamarGemini(prompt);
    }

    public ExercicioLog corrigirExercicio(String fraseOriginal, String traducaoUsuario) {
        String prompt = String.format(
                "Atue como um professor de inglês nativo. " +
                        "Frase original em inglês: '%s'. " +
                        "Tradução feita pelo aluno (PT-BR): '%s'. " +
                        "Analise a tradução. Dê uma nota de 0 a 10. " +
                        "Se estiver errado, explique o porquê de forma amigável e curta. " +
                        "Se estiver certo, elogie. " +
                        "Formato de resposta: 'Nota: [X] \n\n Comentário: [Texto]'",
                fraseOriginal, traducaoUsuario
        );

        String respostaIA = chamarGemini(prompt);

        ExercicioLog log = new ExercicioLog();
        log.setFraseOriginal(fraseOriginal);
        log.setRespostaUsuario(traducaoUsuario);
        log.setFeedbackIA(respostaIA);
        log.setNota("N/A");

        return repositorio.save(log);
    }
}