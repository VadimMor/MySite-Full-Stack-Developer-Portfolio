"use client"

// Импорт стилей
import styles from '~/styles/component_project.module.scss';


// Интерфейс
interface ProjectProps {
    project: shortMassiveProject,
    onClick: () => void
}

// Интерфейс для массива проектов
interface shortMassiveProject {
    date: Date,
    name: string,
    technologies: {
        name: string
    }[]
}


export default function Project({ project, onClick }: ProjectProps) {
    // Создаем дату
    const date = new Date(project.date);
    
    // Выводим формат даты в "день(число) месяц год(число)""
    const rawDate = date.toLocaleDateString('ru-RU', {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    }).replace(' г.', '');

    // Находим индекс первой буквы
    const firstCharIndex = rawDate.search(/[а-яё]/i);

    // Формируем итоговую строку с большой буквы в названии месяца
    const capitalizedDate = firstCharIndex !== -1 
        ? rawDate.substring(0, firstCharIndex) + 
        rawDate[firstCharIndex].toUpperCase() + 
        rawDate.substring(firstCharIndex + 1)
        : rawDate;

    return (
        <div
            className={styles.container}
            onClick={onClick}
        >
            <div>{capitalizedDate}</div>
            <div>{project.name}</div>
            <div>
                {project.technologies.map(technology => technology.name).join(", ")}
            </div>
            <div></div>
        </div>
    )
}