"use client"

import { useCallback, useEffect, useState } from 'react';

// Импорт стилей
import styles from '~/styles/page_projects.module.scss';

// Импорт компонентов
import Loading from '~/components/Loading';
import Project from '~/components/Project';

// Импорт API
import api from '~/utils/main-api';
import ProjectInfo from '~/components/ProjectInfo';


// Интерфейс массива проектов
interface shortMassiveProject {
    date: Date,
    name: string,
    technologies: {
        name: string
    }[]
}

// Получаем API для работы с навыками
const apiProjects = api.projects;


export default function Projects() {
    // Состояние загрузки данных
    const [isLoading, setLoading] = useState(true);
    // Состояние для хранения списка навыков
    const [projects, setProjects] = useState<shortMassiveProject[]>([]);
    // Состояние номера страницы
    const [page, setPage] = useState(0);
    // Состояние загрузки ещё проектов
    const [hasMore, setHasMore] = useState(true);
    // Состояния для фильтрации и сортировки
    const [activeFilter, setActiveFilter] = useState("date"); // "date" или "name"
    const [activeSort, setActiveSort] = useState("DOWN");    // "UP" или "DOWN"
    // Состояние открытия меню фильтра
    const [openFilter, setOpenFilter] = useState(false);
    const [openSort, setOpenSort] = useState(false);
    // Состояние выбранного проекта
    const [activeProject, setActiveProject] = useState<string | null>(null);

    const loadProjects = useCallback((pageNum: number, isNewConfig: boolean = false) => {
        setLoading(true);
        
        // Собираем строку типа "date_DOWN" прямо перед запросом
        const sortQuery = `${activeFilter}_${activeSort}`;

        apiProjects.getAll(sortQuery, pageNum)
            .then((res) => {
                if (res.length < 10) setHasMore(false);
                else setHasMore(true);

                setProjects(prev => isNewConfig ? res : [...prev, ...res]);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Ошибка загрузки:", err);
                setLoading(false);
            });
    }, [activeFilter, activeSort]);

    // Первая загрузка
    useEffect(() => {
        setPage(0);
        loadProjects(0, true);
    }, [activeFilter, activeSort, loadProjects]);

    const handleLoadMore = () => {
        if (!isLoading && hasMore) {
            const nextPage = page + 1;
            setPage(nextPage);
            loadProjects(nextPage);
        }
    };

    return (
        <div className={`container ${styles.section}`}>
            <div className={styles.container}>
                {/* Заголовок */}
                <div className={styles.title_main}>
                    <h1># Мои проекты</h1>
                    <div>=========================================================================================================================================================================================</div>
                </div>

                {/* Фильтрация вывода проектов */}
                <div className={styles.filtr}>
                    {/* Фильтр */}
                    <div className={styles.filtr_block}>
                        {/* Кнопка выбора */}
                        <button
                            className={`
                                ${styles.filtr_title}
                                ${
                                    openFilter ? styles.active : ''
                                }
                            `}
                            onClick={() => {
                                setOpenSort(false);
                                setOpenFilter(!openFilter);
                            }}
                        >
                            Фильтр:
                            <div>
                                {activeFilter === "date" ? "Дата" : "Название"}
                                <span className={`icon-arrow ${styles.icon}`} />
                            </div>
                        </button>
                        
                        {
                            openFilter ?
                                <>
                                    {/* Список для фильтра */}
                                    <ul>
                                        {/* Фильтрация по дате */}
                                        <li
                                            onClick={() => {
                                                setOpenFilter(false);
                                                setActiveFilter("date");
                                            }}
                                        >
                                            Дата
                                        </li>
                                        {/* Фильтрация по названию */}
                                        <li
                                            onClick={() => {
                                                setOpenFilter(false);
                                                setActiveFilter("name");
                                            }}
                                        >
                                            Название
                                        </li>
                                    </ul>
                                </> : null
                        }
                    </div>

                    {/* Сортировка */}
                    <div className={styles.filtr_block}>
                        {/* Кнопка выбора */}
                        <button
                            className={`
                                ${styles.filtr_title}
                                ${
                                    openSort ? styles.active : ''
                                }
                            `}
                            onClick={() => {
                                setOpenFilter(false);
                                setOpenSort(!openSort);
                            }}
                        >
                            Сортировка:
                            <div>
                                {activeSort === "UP" ? "По возрастанию" : "По убыванию"}
                                <span className={`icon-arrow ${styles.icon}`} />
                            </div>
                        </button>
                        
                        {
                            openSort ?
                                <>
                                    {/* Список для сортировки */}
                                    <ul>
                                        {/* Сортировка по возрастанию */}
                                        <li
                                            onClick={() => {
                                                setOpenSort(false);
                                                setActiveSort("UP");
                                            }}
                                        >
                                            По возрастанию
                                        </li>
                                        {/* Сортировка по убыванию */}
                                        <li
                                            onClick={() => {
                                                setOpenSort(false);
                                                setActiveSort("DOWN")
                                            }}
                                        >
                                            По убыванию
                                        </li>
                                    </ul>
                                </> : null
                        }
                    </div>
                </div>

                {/* Список проектов */}
                <div className={styles.content}>
                    <div className={`
                        ${styles.title}
                    `}>
                        <div>Дата</div>
                        <div>Название</div>
                        <div>Технологии</div>
                    </div>

                    {/* Вывод списка проектов */}
                    {projects.map((project, index) => (
                        <Project
                            key={index}
                            project={project}
                            onClick={() => setActiveProject(project.name)}
                        />
                    ))}

                    {/* Вывод загрузки */}
                    {isLoading && <Loading />}

                    {/* Невидимый триггер в конце списка */}
                    {hasMore && !isLoading && (
                        <div 
                            onMouseEnter={handleLoadMore}
                        />
                    )}
                </div>
            </div>

            <ProjectInfo
                name={activeProject}
            />
        </div>
    )
}