package io.github.GarotoCowboy.quarkussocial.rest.dto;

import io.github.GarotoCowboy.quarkussocial.domain.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime dataTime;

    public static PostResponse fromEntity(Post post) {
        var response = new PostResponse();
        response.setText(post.getText());
        response.setDataTime(post.getDataTime());

        return response;
    }
}
