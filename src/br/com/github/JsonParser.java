package br.com.github;

import java.util.List;

public interface JsonParser {

    List<? extends Content> parse(String string);
}
