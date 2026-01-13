package main.backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractEmailContext {
    private String to;                    // ✅ Нужно - получатель
    private String subject;               // ✅ Нужно - тема письма
    private String templateLocation;      // ✅ Нужно - путь к шаблону
    private Map<String, Object> context;  // ✅ Нужно - переменные для шаблона
}
