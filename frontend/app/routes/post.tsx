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


// Интерфейс информации о посте
interface infoPost {
    name: string,
    description: string,
    url: string,
    length: number,
    date: Date,
    categories: {
        name: string
    }[]
}

// Получаем API для работы с навыками
const apiPost = api.posts;


export default function Project() {
    // Состояние загрузки данных
    const [isLoading, setLoading] = useState(true);
    // Состояние для хранения данных выбранного проекта
    const [post, setPost] = useState<infoPost>();

    // Инициализация параметров URL
    const params = useParams();
    
    // Декодируем строку
    const projectName = params.name ? decodeURIComponent(params.name) : "";

    // Мемоизированное значение отформатированной даты проекта
    const capitalizedDate = useMemo(() => {
        if (!post?.date) return "";

        const date = new Date(post.date);
        
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
    }, [post]);

    // Загрузка данных проекта при монтировании
    useEffect(() => {
        setLoading(true);

        // Запрос детальной информации о проекте по его имени
        apiPost.getInfo(projectName)
            .then((res) => {
                console.log(res)
                setPost(res);
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
                    # {post?.name ? (post.name[0].toUpperCase() + post.name.slice(1)) : <Loading />}
                    <span>==========================================================================================================================================================================================================================================</span>
                </h1>

                {
                    isLoading ?
                        // Состояние загрузки контента
                        <Loading /> :
                        // Отображение описания в формате Markdown
                        <div className={styles.description}>
                            <ReactMarkdown>
                                {post?.description}
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

                {/* Список используемых категорий */}
                <div className={styles.short_item}>
                    <div className={styles.short_title}>Категории:</div>
                    <div className={styles.short_text}>
                        {
                            isLoading ?
                                <Loading /> : <>{post?.categories.map(category => category.name).join(", ")}</>
                        }
                    </div>
                </div>

                {/* Длинна поста */}
                <div className={styles.short_item}>
                    <div className={styles.short_title}>Длинна:</div>
                    <div className={styles.short_text}>
                        {
                            isLoading ?
                                <Loading /> : <>{post?.length}</>
                        }
                    </div>
                </div>

                {
                    post?.url ? 
                        <div className={styles.short_item}>
                            <div className={styles.short_title}>Ссылка:</div>
                            <div className={styles.short_text}>
                                {
                                    isLoading ?
                                        <Loading /> : 
                                        <Link
                                            to={post?.url || ''}
                                            target='_blank'
                                            title="Перейти на страницу/сайт проекта"
                                            aria-label="Перейти на страницу/сайт проекта"
                                        >
                                            [ссылка]
                                        </Link>
                                }
                            </div>
                        </div> : null
                }
                {/* Ссылка для поста */}
            </div>
        </div>
    )
}