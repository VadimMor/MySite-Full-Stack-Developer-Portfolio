package main.backend.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class MarkdownUtils {
    public Integer countWordsInString(String markdownContent) {
        log.trace("Starting word count. Initial content received:\n{}", markdownContent);
        if (markdownContent == null || markdownContent.trim().isEmpty()) {
            log.trace("MARKDOWN content is empty or null. Returning 0.");
            return 0;
        }

        // --- Этап 1: Очистка синтаксиса Markdown ---
        String cleanText = markdownContent;

        // 1. Удаление ссылок и изображений: [Текст](URL) и ![Текст](URL)
        cleanText = cleanText.replaceAll("!\\[.*?\\]\\(.*?\\)", " ");
        log.trace("After removing images: {}", cleanText.substring(0, Math.min(cleanText.length(), 100)) + "...");

        cleanText = cleanText.replaceAll("\\[.*?\\]\\(.*?\\)", " ");
        log.trace("After removing links: {}", cleanText.substring(0, Math.min(cleanText.length(), 100)) + "...");

        // 2. Удаление заголовков и горизонтальных линий: #, ##, ###, ---, ***
        cleanText = cleanText.replaceAll("^[#=\\-*]{1,6}\\s*$", "");
        cleanText = cleanText.replaceAll("^[#*\\-]{1,6}", " ");
        log.trace("After removing headings/rules: {}", cleanText.substring(0, Math.min(cleanText.length(), 100)) + "...");

        // 3. Удаление кода и цитат: `inline code`, ```code block```, > quote
        cleanText = cleanText.replaceAll("`.*?`", " "); // Inline code
        cleanText = cleanText.replaceAll("```[\\s\\S]*?```", " "); // Code blocks
        cleanText = cleanText.replaceAll("^>\\s*", " "); // Blockquotes
        log.trace("After removing code/quotes: {}", cleanText.substring(0, Math.min(cleanText.length(), 100)) + "...");

        // 4. Удаление списков: *, -, + в начале строки
        cleanText = cleanText.replaceAll("^[\\*\\-\\+]\\s*", " ");
        log.trace("After removing list markers: {}", cleanText.substring(0, Math.min(cleanText.length(), 100)) + "...");

        // 5. Удаление жирного/курсива: **слово**, *слово*, __слово__, _слово_
        cleanText = cleanText.replaceAll("[*_]{1,2}", " ");
        log.trace("After removing formatting: {}", cleanText.substring(0, Math.min(cleanText.length(), 100)) + "...");

        // Разбиваем по одному или более не-словесным символам, включая пробелы и знаки пунктуации.
        log.trace("Final cleaned text (for counting): {}", cleanText);
        String[] words = cleanText.split("[\\s\\p{Punct}]+");

        // Возвращаем количество непустых элементов.
        long wordCount = Arrays.stream(words)
                .filter(s -> !s.isEmpty())
                .count();

        log.trace("Final word count result: {} words.", wordCount);
        return (int) wordCount;
    }
}
