package com.aluraSpring.screenmatchSeries.service;

public class ConsultaChatGPT {

    public void ejemploChatGpt(){
        System.out.println("con estos metodos se usa ChatGpt");
    }

    /*
    * Se usa estas dependencias para usar ChatGpt
    * <dependency>
			<groupId>com.theokanning.openai-gpt3-java</groupId>
			<artifactId>service</artifactId>
			<version>0.14.0</version>
		</dependency>
    * */

    // Para poder usar a esta consulta, deberiamos
    // instanciar esta clase y pasar el texto que deseamos
    // traducir a español como ARGUMENTO
    /*public static String obtenerTraduccion(String texto){
        OpenAiService service = new OpenAiService(tuAPIKEY);


        CompletionRequest requesicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requesicion);
        return respuesta.getChoices().get(0).getText();
    }

*/
}
