"use client"

import { Link } from 'react-router';

// Импорт стилей
import styles from '~/styles/component_post.module.scss';


// Интерфейс
interface PostProps {
    post: shortMassivePost
}

// Интерфейс массива постов
interface shortMassivePost {
    id: number,
    name: string,
    date: Date,
    categories: {
        name: string
    }[]
}


export default function Posts({ post }: PostProps) {
    // Создаем дату
    const date = new Date(post.date);
    
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
        <Link
            to={`${post.id}`}
            title={post.name}
            aria-label={post.name}
            className={styles.container}
        >
            <div>{capitalizedDate}</div>
            <div>{post.name}</div>
            <div>
                {post.categories.map(category => category.name).join(", ")}
            </div>
            <div></div>
        </Link>
    )
}