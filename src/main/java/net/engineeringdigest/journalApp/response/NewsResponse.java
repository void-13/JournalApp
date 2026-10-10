package net.engineeringdigest.journalApp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("articles")
    private List<Article> articles;
}
