package com.news.beacon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.beacon.clients.GeminiClient;
import com.news.beacon.entity.GeminiResponse;
import com.news.beacon.entity.NewsSemanticResponse;
import com.news.beacon.entity.NewsSemanticRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NewsSemanticService {

    final GeminiClient geminiClient;
    NewsSemanticService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public List<NewsSemanticResponse> getSemanticAnalysis(List<NewsSemanticRequest> news) {

        String prompt = preparePrompt(news);
        GeminiResponse geminiResponse = geminiClient.generateContent(prompt);
        String result = cleanJson(geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText());

        List<NewsSemanticResponse> categorizedNews = null;
        if (isValidJson(result)) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                categorizedNews = objectMapper.readValue(result, new TypeReference<List<NewsSemanticResponse>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid JSON response from LLM.");
        }

        return categorizedNews;
    }

    private static String preparePrompt(List<NewsSemanticRequest> news) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Assign a category and company name for each news item from the list below. ");
        promptBuilder.append("Use the following categories:\n");
        promptBuilder.append("New Initiative by Company, Bad News for Company, Company Profile Damaged, Stock Market Crash, Stock Market Rise, Best time to Buy Stock, Best time to Sell Stock, Leadership Changes or Challenges, Product or Service Launch, Partnership or Acquisition Announcement, Regulatory or Legal Issues, Earnings or Revenue Growth, Earnings or Revenue Miss, Industry-Wide Growth Trend, Industry-Wide Decline Trend, Major Investment by Company, Layoffs or Workforce Reduction, Government Policy Impact, Competitor Gains or Threats, Supply Chain Challenges, Debt or Liquidity Issues, Consumer Demand Surge, Consumer Demand Drop, Speculation of Mergers or Acquisitions, Global Economic Influence on Stock, Major Scandal or Ethical Issue, Positive Technological Breakthrough, Negative Technological Failure, Share Buyback or Dividend Increase, Unusual Stock Price Movement, Credit Rating Upgrade or Downgrade, Market Rumors Impacting Sentiment, Other.\n\n");
        promptBuilder.append("If no company name is found, use 'n/a'.\n\n");
        promptBuilder.append("Return the output in valid JSON format. Here's an example structure of the output");
        String fluShot = "[{\"newsId\":\"12as34\",\"category\":\"Earnings or Revenue Miss\",\"companyName\":\"Nvidia\"},{\"newsId\":\"12as34\",\"category\":\"Product or Service Launch\",\"companyName\":\"GSC Game World\"}]";
        promptBuilder.append(fluShot);
        promptBuilder.append("Input:\n");

        for (NewsSemanticRequest item : news) {
            promptBuilder.append(String.format(
                    "{\n\"newsId\": \"%s\",\n\"publishedAt\": \"%s\",\n\"source\": \"%s\",\n\"title\": \"%s\",\n\"description\": \"%s\",\n\"articleUrl\": \"%s\",\n\"author\": \"%s\"\n},\n",
                    item.getNewsId(), item.getPublishedAt(), item.getSource(), item.getTitle(), item.getDescription(), item.getArticleUrl(), item.getAuthor()
            ));
        }
        promptBuilder.append("\n\nMake Sure the Output is valid JSON keys as per the provided sample of output JSON\n");
        promptBuilder.append("Output Requirements\n1-Provide only the JSON array without any enclosing code block formatting (e.g., no ```json).\n2-The response should be a plain JSON array in valid format.");
        return promptBuilder.toString();
    }

    private static boolean isValidJson(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.isArray();
        } catch (IOException e) {
            return false;
        }
    }

    private static String cleanJson(String response) {
        return response.replace("```json", "")
                .replace("```", "")
                .trim();
    }
}
