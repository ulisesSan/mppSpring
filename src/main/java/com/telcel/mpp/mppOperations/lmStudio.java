package com.telcel.mpp.mppOperations;

import java.util.List;

import com.telcel.mpp.models.MppModel;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class lmStudio {
        public String gemmaResponse(List<MppModel> mppModel ){
                ChatLanguageModel model = OpenAiChatModel.builder()
                .baseUrl("http://localhost:1234/v1") // La dirección de tu LM Studio
                .apiKey("no-necesaria") // LM Studio no pide API Key, pero la librería sí pide un string
                .modelName("google/gemma-3-4b") // El nombre del modelo que cargaste
                .build();

        String respuesta = model.generate("Analiza este proyecto y responde en español: " + mppModel);
        System.out.println(respuesta);
        return respuesta;
        }
}
