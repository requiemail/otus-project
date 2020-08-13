package ru.otus.project.masterPass.shell;

import lombok.RequiredArgsConstructor;
import org.jline.reader.LineReader;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InputReader {

    private final LineReader lineReader;

    public String prompt(String prompt) {
        return lineReader.readLine(prompt + ": ");
    }

    public Long selectFromList(String headingMessage,
                               String promptMessage,
                               Map<Long, String> options) {
        String answer;
        Set<Long> allowedAnswers = new HashSet<>(options.keySet());
        System.out.println(headingMessage);
        do {
            for (Map.Entry<Long, String> option : options.entrySet()) {
                System.out.println(String.format("  [%d] %s", option.getKey(), option.getValue()));
            }
            answer = lineReader.readLine(String.format("%s: ", promptMessage));
        } while (!answer.matches("[0-9]+") || !allowedAnswers.contains(Long.parseLong(answer)));
        return Long.parseLong(answer);
    }

    public String promptWithOptions(String prompt,
                                    String defaultValue,
                                    List<String> optionsAsList) {
        String answer;
        List<String> allowedAnswers = optionsAsList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        if (StringUtils.hasText(defaultValue)) {
            allowedAnswers.add("");
        }
        do {
            answer = lineReader.readLine(String.format("%s %s: ", prompt, optionsAsList)).toUpperCase();
        } while (!allowedAnswers.contains(answer) && !"".equals(answer));

        if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

}
