"use client"

// Импорт стилей
import styles from '~/styles/component_loading.module.scss';

export default function Loading() {
    return (
        <div className={styles.content}>
            Загрузка
            <div className={styles.line}/>
        </div>
    )
}