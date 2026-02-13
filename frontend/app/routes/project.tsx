"use client"

import { useEffect, useMemo, useState } from 'react';
import { Link, useParams } from 'react-router';
import ReactMarkdown from 'react-markdown';

// Импорт стилей
import styles from '~/styles/page_project.module.scss';

// Импорт API
import api from '~/utils/main-api';

// Импорт компонентов
import Loading from '~/components/Loading';


// Интерфейс информации о проекте
interface infoProject {
    name: string,
    description: string,
    create_date: Date,
    url: string,
    technologies: {
        name: string
    }[]
}

// Получаем API для работы с навыками
const apiProjects = api.projects;


export default function Project() {
    // Состояние загрузки данных
    const [isLoading, setLoading] = useState(true);
    // Состояние для хранения данных выбранного проекта
    const [project, setProject] = useState<infoProject>();

    // Инициализация параметров URL
    const params = useParams();
    
    // Декодируем строку
    const projectName = params.name ? decodeURIComponent(params.name) : "";

    // Мемоизированное значение отформатированной даты проекта
    const capitalizedDate = useMemo(() => {
        if (!project?.create_date) return "";

        const date = new Date(project.create_date);
        
        // Форматирует в "день(число) месяц год(число)"
        const rawDate = date.toLocaleDateString('ru-RU', {
            day: 'numeric',
            month: 'long',
            year: 'numeric'
        }).replace(' г.', '');

        // Делает первую букву месяца заглавной
        const firstCharIndex = rawDate.search(/[а-яё]/i);
        if (firstCharIndex === -1) return rawDate;

        return rawDate.substring(0, firstCharIndex) + 
               rawDate[firstCharIndex].toUpperCase() + 
               rawDate.substring(firstCharIndex + 1);
    }, [project]);

    // Загрузка данных проекта при монтировании
    useEffect(() => {
        setLoading(true);

        // Запрос детальной информации о проекте по его имени
        apiProjects.getInfo(projectName)
            .then((res) => {
                setProject(res);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Ошибка загрузки:", err);
                setLoading(false);
            });
    }, [projectName])

    return (
        <div className={`container ${styles.container}`}>
            {/* Левая колонка: Основной контент */}
            <div className={styles.content}>
                {/* Заголовок проекта с декоративной линией */}
                <h1>
                    # {projectName[0].toUpperCase() + projectName.slice(1)}
                    <span>==========================================================================================================================================================================================================================================</span>
                </h1>

                {
                    isLoading ?
                        // Состояние загрузки контента
                        <Loading /> :
                        // Отображение описания в формате Markdown
                        <div className={styles.description}>
                            <ReactMarkdown>
                                {project?.description}
                            </ReactMarkdown>
                        </div>
                }
            </div>
            
            {/* Правая колонка: Краткие сведения */}
            <div className={styles.short}>
                {/* Дата создания */}
                <div className={styles.short_item}>
                    <div className={styles.short_title}>Дата:</div>
                    <div className={styles.short_text}>
                        {
                            isLoading ?
                                <Loading /> : <>{capitalizedDate}</>
                        }
                    </div>
                </div>

                {/* Список используемых технологий */}
                <div className={styles.short_item}>
                    <div className={styles.short_title}>Технологии:</div>
                    <div className={styles.short_text}>
                        {
                            isLoading ?
                                <Loading /> : <>{project?.technologies.map(technology => technology.name).join(", ")}</>
                        }
                    </div>
                </div>

                {/* Прямая ссылка на проект */}
                <div className={styles.short_item}>
                    <div className={styles.short_title}>Ссылка:</div>
                    <div className={styles.short_text}>
                        {
                            isLoading ?
                                <Loading /> : 
                                <Link
                                    to={project?.url || ''}
                                    target='_blank'
                                    title="Перейти на страницу проекта"
                                    aria-label="Перейти на страницу проекта"
                                >
                                    [ссылка]
                                </Link>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}