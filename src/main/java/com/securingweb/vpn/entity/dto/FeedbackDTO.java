package com.securingweb.vpn.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class FeedbackDTO {
    private String title;
    private String note;
}
