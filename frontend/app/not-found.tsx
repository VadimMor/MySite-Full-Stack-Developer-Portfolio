"use client";

import Link from "next/link";

// Импорт стилей
import styles from '@styles/Not-found.module.scss';

const islandFishingTextArt = "███████  ██████  ██   ██ ██████         ██████        ███████  ██████  ██   ██ ██████ \n" +
                             "██      ██    ██ ██   ██ ██   ██       ██  ████       ██      ██    ██ ██   ██ ██   ██\n" +
                             "█████   ██    ██ ██   ██ ██████  █████ ██ ██ ██ █████ █████   ██    ██ ██   ██ ██████ \n" +
                             "██      ██    ██ ██   ██ ██   ██       ███   ██       ██      ██    ██ ██   ██ ██   ██\n" +
                             "██       ██████   █████  ██   ██        ██████        ██       ██████   █████  ██   ██\n"

export default function NotFound() {
    return (
        <div className={styles.container}>
            <h1>
                <pre
                    aria-label="Logo"
                    className={`${styles.logo}`}
                >
                    {islandFishingTextArt}
                </pre>
            </h1>

            <h2>Nothing happens here</h2>
            <h2>But there is something neat over there:</h2>

            <nav>
                <Link href={"/"} className={styles.link}>[my posts]</Link>
                <Link href={"/projects"} className={styles.link}>[projects]</Link>
                <Link href={"/about"} className={styles.link}>[about]</Link>
            </nav>
        </div>
    )
}