"use client"

// Импорт стилей
import styles from '@styles/Home/PostCard.module.scss';

interface PostProps {
    name: string;
}

interface PostListProps {
    post?: PostProps
}

export default function Post({ post }: PostListProps) {
    return (
        <div className={styles.card}>
            {
                post ? null : (
                    <>~</>
                )
            }
        </div>
    )
}