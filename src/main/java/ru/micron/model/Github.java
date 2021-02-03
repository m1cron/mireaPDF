package ru.micron.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Github {

    private final String name;
    private final String path;
    private final String url;
    private final String download_url;
    private final String type;

}
