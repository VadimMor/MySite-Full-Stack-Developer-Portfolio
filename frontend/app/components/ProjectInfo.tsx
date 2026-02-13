"use client"

import { useEffect, useState } from 'react';
import { Link } from 'react-router';
import ReactMarkdown from 'react-markdown';

// Импорт стилей
import styles from '~/styles/component_projectInfo.module.scss';

// Импорт компонентов
import Loading from '~/components/Loading';

// Импорт API
import api from '~/utils/main-api';


// Интерфейс переданных данных в компонент
interface ProjectInfoProps {
    name: string | null
}

// Интерфейс краткой информации о проекте
interface ShortInfoProject {
    name: string,
    description: string
}

// Получаем API для работы с навыками
const apiProjects = api.projects;


export default function ProjectInfo({ name }: ProjectInfoProps) {
    // Состояние загрузки данных
    const [isLoading, setLoading] = useState(true);
    // Состояние для хранения данных выбранного проекта
    const [project, setProject] = useState<ShortInfoProject>();

    // Эффект для загрузки данных при изменении выбранного имени проекта
    useEffect(() => {
        if (name) {
            // Сбрасываем старые данные и включаем индикатор загрузки
            setProject(undefined);
            setLoading(true);
    
            // Запрос краткой информации о проекте по имени
            apiProjects.getShort(name)
                .then((res) => {
                    setProject(res);
                    setLoading(false);
                })
                .catch((err) => {
                    console.error("Ошибка загрузки:", err);
                    setLoading(false);
                });
        }
    }, [name])
    
    return (
        <div className={styles.info}>
            {
                !name ?
                    // Заглушка, если проект не выбран
                    <>
                        <div className={styles.title}>
                            <h2># Выбреите проект</h2>
                            <div>===============================================================</div>
                        </div>

                        <div>
                            Выберите проект, чтобы увидет краткую информацию о нём
                        </div>
                    </> : isLoading ?
                    // Состояние загрузки данных проекта
                    <>
                        <div className={styles.title}>
                            <h2># {name}</h2>
                            <div>===============================================================</div>
                        </div>

                        <Loading />
                    </> :
                    // Вывод основной информации о проекте
                    <>
                        {/* Заголовок */}
                        <div className={styles.title}>
                            <h2># {name}</h2>
                            <div>===============================================================</div>
                        </div>

                        {/* Отображение описания в формате Markdown */}
                        {
                            project?.description && (
                                <div className={styles.markdown}>
                                    <ReactMarkdown>
                                        {project.description}
                                    </ReactMarkdown>
                                </div>
                            )
                        }

                        {/* Ссылка на полную страницу проекта */}
                        <Link
                            to={`${name}`}
                            className={styles.link}
                        >
                            Посмотреть ещё
                            <span className={`icon-arrow ${styles.icon}`} />
                        </Link>
                    </>
            }
        </div>
    )
} 