//package com.zosh.service;
//
//import com.jayway.jsonpath.JsonPath;
//import com.jayway.jsonpath.ReadContext;
//import com.zosh.model.CoinDTO;
//import com.zosh.response.ApiResponse;
//import com.zosh.response.FunctionResponse;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Service
//public class ChatBotServiceImpl implements ChatBotService {
//
//    @Value("${gemini.api.key}")
//    private String API_KEY;
//
//    private static final String GEMINI_URL =
//            "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=";
//
//
//    /* -------------------- Utility -------------------- */
//
//    private double convertToDouble(Object value) {
//        if (value == null) return 0.0;
//        if (value instanceof Integer) return ((Integer) value).doubleValue();
//        if (value instanceof Long) return ((Long) value).doubleValue();
//        if (value instanceof Double) return (Double) value;
//        return 0.0;
//    }
//
//    /* -------------------- CoinGecko API -------------------- */
//
//    public CoinDTO makeApiRequest(String currencyName) {
//
//        String url = "https://api.coingecko.com/api/v3/coins/" + currencyName.toLowerCase();
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
//        Map<String, Object> body = response.getBody();
//
//        if (body == null) return null;
//
//        Map<String, Object> image = (Map<String, Object>) body.get("image");
//        Map<String, Object> marketData = (Map<String, Object>) body.get("market_data");
//
//        CoinDTO dto = new CoinDTO();
//        dto.setId((String) body.get("id"));
//        dto.setSymbol((String) body.get("symbol"));
//        dto.setName((String) body.get("name"));
//        dto.setImage((String) image.get("large"));
//
//        dto.setCurrentPrice(convertToDouble(((Map) marketData.get("current_price")).get("usd")));
//        dto.setMarketCap(convertToDouble(((Map) marketData.get("market_cap")).get("usd")));
//        dto.setMarketCapRank((Integer) body.get("market_cap_rank"));
//        dto.setTotalVolume(convertToDouble(((Map) marketData.get("total_volume")).get("usd")));
//        dto.setHigh24h(convertToDouble(((Map) marketData.get("high_24h")).get("usd")));
//        dto.setLow24h(convertToDouble(((Map) marketData.get("low_24h")).get("usd")));
//        dto.setPriceChange24h(convertToDouble(marketData.get("price_change_24h")));
//        dto.setPriceChangePercentage24h(convertToDouble(marketData.get("price_change_percentage_24h")));
//        dto.setMarketCapChange24h(convertToDouble(marketData.get("market_cap_change_24h")));
//        dto.setMarketCapChangePercentage24h(convertToDouble(marketData.get("market_cap_change_percentage_24h")));
//        dto.setCirculatingSupply(convertToDouble(marketData.get("circulating_supply")));
//        dto.setTotalSupply(convertToDouble(marketData.get("total_supply")));
//
//        return dto;
//    }
//
//    /* -------------------- Gemini Function Call -------------------- */
//
//    public FunctionResponse getFunctionResponse(String prompt) {
//
//        String url = GEMINI_URL + API_KEY;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String body = """
//        {
//          "contents": [{
//            "parts": [{"text": "%s"}]
//          }],
//          "tools": [{
//            "functionDeclarations": [{
//              "name": "getCoinDetails",
//              "parameters": {
//                "type": "OBJECT",
//                "properties": {
//                  "currencyName": { "type": "STRING" },
//                  "currencyData": { "type": "STRING" }
//                },
//                "required": ["currencyName","currencyData"]
//              }
//            }]
//          }]
//        }
//        """.formatted(prompt);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> request = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response =
//                restTemplate.postForEntity(url, request, String.class);
//
//        ReadContext ctx = JsonPath.parse(response.getBody());
//
//        FunctionResponse res = new FunctionResponse();
//        res.setFunctionName(ctx.read("$.candidates[0].content.parts[0].functionCall.name"));
//        res.setCurrencyName(ctx.read("$.candidates[0].content.parts[0].functionCall.args.currencyName"));
//        res.setCurrencyData(ctx.read("$.candidates[0].content.parts[0].functionCall.args.currencyData"));
//
//        return res;
//    }
//
//    /* -------------------- Main Chat Logic -------------------- */
//
//    @Override
//    public ApiResponse getCoinDetails(String prompt) {
//
//        FunctionResponse function = getFunctionResponse(prompt);
//        CoinDTO coin = makeApiRequest(function.getCurrencyName());
//
//        String url = GEMINI_URL + API_KEY;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String body = """
//        {
//          "contents": [
//            { "role": "user", "parts": [{ "text": "%s" }] },
//            {
//              "role": "function",
//              "parts": [{
//                "functionResponse": {
//                  "name": "getCoinDetails",
//                  "response": %s
//                }
//              }]
//            }
//          ]
//        }
//        """.formatted(prompt, new JSONObject(coin).toString());
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response =
//                restTemplate.postForEntity(url, new HttpEntity<>(body, headers), String.class);
//
//        ReadContext ctx = JsonPath.parse(response.getBody());
//        String text = ctx.read("$.candidates[0].content.parts[0].text");
//
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setMessage(text);
//        return apiResponse;
//    }
//
//    /* -------------------- Simple Chat -------------------- */
//
//    @Override
//    public String simpleChat(String prompt) {
//
//        String url = GEMINI_URL + API_KEY;
//
//        JSONObject body = new JSONObject()
//                .put("contents", new JSONArray()
//                        .put(new JSONObject()
//                                .put("parts", new JSONArray()
//                                        .put(new JSONObject().put("text", prompt)))));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response =
//                restTemplate.postForEntity(url,
//                        new HttpEntity<>(body.toString(), headers),
//                        String.class);
//
//        return response.getBody();
//    }
//
//    @Override
//    public CoinDTO getCoinByName(String coinName) {
//        return makeApiRequest(coinName);
//    }
//}
