package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.response.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Value("${news.api.key}")
    private String apiKey;

    public NewsResponse getNewsForCountry(String country) {
        String finalUrl = appCache.APP_CACHE.get("news_api_url").replace("<country>", country).replace("<apiKey>", apiKey);
        ResponseEntity<NewsResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, NewsResponse.class);
        return response.getBody();
    }
}
