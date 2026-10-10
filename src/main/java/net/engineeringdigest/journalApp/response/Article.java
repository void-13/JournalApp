package net.engineeringdigest.journalApp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    @JsonProperty("source")
    private Source source;

    @JsonProperty("author")
    private String author;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}
