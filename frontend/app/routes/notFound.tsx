"use client"

import { Link } from "react-router"

// Импорт стилей
import styles from '~/styles/page_notFound.module.scss';

// Кодовый текст
const text = '   █████   ██████     █████\n' +
             '  ██  ██  ██  ████   ██  ██\n' +
             ' ██   ██  ██ ██ ██  ██   ██\n' +
             '█████████ ████  ██ █████████\n' +
             '      ██   ██████        ██'

export default function NotFound() {
    return (
        <div className={`container ${styles.main}`}>
            {/* Красивый текст */}
            <h1>
                <pre>{text}</pre>
            </h1>

            <h2>
                Здесь ничего не происходит
            </h2>

            {/* Меню */}
            <div className={styles.menu}>
                <Link
                    to="/"
                    title="Перейти на главную страницу"
                    aria-label="Перейти на главную страницу"
                >
                    [Главная]
                </Link>

                
                <Link
                    to="/news"
                    title="Перейти на страницу новостей"
                    aria-label="Перейти на страницу новостей"
                >
                    [Новости]
                </Link>
                
                <Link
                    to="/projects"
                    title="Перейти на страницу проектов"
                    aria-label="Перейти на страницу проектов"
                >
                    [Проекты]
                </Link>
            </div>
        </div>
    )
}