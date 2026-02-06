"use client"

import { Link } from "react-router"

import styles from '~/styles/page_notFound.module.scss';

const text = '   █████   ██████     █████\n' +
             '  ██  ██  ██  ████   ██  ██\n' +
             ' ██   ██  ██ ██ ██  ██   ██\n' +
             '█████████ ████  ██ █████████\n' +
             '      ██   ██████        ██'

export default function NotFound() {
    return (
        <div className={`container ${styles.main}`}>
            <h1>
                <pre>{text}</pre>
            </h1>

            <h2>
                Здесь ничего не происходит
            </h2>

            <div className={styles.menu}>
                <Link
                    to="/"
                >
                    [Главная]
                </Link>

                
                <Link
                    to="/news"
                >
                    [Новости]
                </Link>
                
                <Link
                    to="/projects"
                >
                    [Проекты]
                </Link>
            </div>
        </div>
    )
}