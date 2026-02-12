// useTypewriterWords.ts
import { useEffect, useState } from 'react';

export default function useTypewriterWords(
    text: string,
    wordDelay = 120,
    startDelay = 0,
    isActive = false,
    resetKey: any = null // Ключ для сброса
) {
    const [visibleWords, setVisibleWords] = useState<string[]>([]);
    const [isDone, setIsDone] = useState(false);

    useEffect(() => {
        let interval: NodeJS.Timeout | null = null;
        let timeout: NodeJS.Timeout | null = null;

        // Сброс происходит при изменении любого параметра из массива зависимостей
        setVisibleWords([]);
        setIsDone(false);

        if (!isActive || !text) return;

        const words = text.split(' ');
        let index = 0;

        timeout = setTimeout(() => {
            interval = setInterval(() => {
                // Используем функциональный апдейт для безопасности
                setVisibleWords((prev) => [...prev, words[index]]);
                index++;

                if (index >= words.length) {
                    if (interval) clearInterval(interval);
                    setIsDone(true);
                }
            }, wordDelay);
        }, startDelay);

        return () => {
            if (interval) clearInterval(interval);
            if (timeout) clearTimeout(timeout);
        };
    }, [text, wordDelay, startDelay, isActive, resetKey]); // Добавили resetKey сюда

    return {
        text: visibleWords.join(' '),
        isDone
    };
}