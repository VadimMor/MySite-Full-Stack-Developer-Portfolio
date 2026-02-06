import { NavLink } from "react-router";

import styles from "~/styles/header.module.scss"

const logo = "__       __  ___   _____  _______  ___   ___\n" +
             "\\ \\     / / / _ \\ |  _  \\|__   __||   \\ /   |\n" +
             " \\ \\   / / / /_\\ \\| | \\ |   | |   | |\\ V /| |\n" +
             "  \\ \\ / /  |  _  || | | |   | |   | | \\ / | |\n" +
             "   \\ V /   | | | || |_/ | __| |__ | |  V  | |\n" +
             "    \\_/    \\_| |_/|_____/|_______||_|     |_|"

export default function Header() {
    return (
        <header className={styles.header}>
            <div className={styles.top}>
                <div className={styles.left}>
                    <NavLink to="" className={styles.logo}>
                        <pre>{logo}</pre>
                    </NavLink>

                    <div className={styles.content}>
                        <div className={styles.names}>
                            <p>Имя:</p>
                            <p>Локация:</p>
                            <p>Ник:</p>
                        </div>

                        <div className={styles.text}>
                            <p>Унятицкий Вадим</p>
                            <a href="https://yandex.ru/maps/-/CLd3IZ3A" target="_blank">Россия</a>
                            <a href="https://t.me/Vadim_morilly" target="_blank">@Vadim_morilly</a>
                        </div>
                    </div>
                </div>

                <div className={styles.right}>
                    <a href="https://github.com/VadimMor" target="_blank">
                        <span className={
                            styles.icon +
                            " icon-github"
                        }/>
                        Github
                    </a>
                </div>
            </div>
            
            <div className={styles.menu}>
                <NavLink
                    to=""
                    className={({ isActive }) => 
                        [
                            styles.link_menu,
                            isActive ? styles.active : "",
                        ].join(" ")
                    }
                >
                        (1) Главная
                </NavLink>
                <NavLink
                    to="/skills"
                    className={({ isActive }) => 
                        [
                            styles.link_menu,
                            isActive ? styles.active : "",
                        ].join(" ")
                    }
                >
                        (2) Навыки
                </NavLink>
                <NavLink
                    to="/posts"
                    className={({ isActive }) => 
                        [
                            styles.link_menu,
                            isActive ? styles.active : "",
                        ].join(" ")
                    }
                >
                        (3) Новости
                </NavLink>
                <NavLink
                    to="/projects"
                    className={({ isActive }) => 
                        [
                            styles.link_menu,
                            isActive ? styles.active : "",
                        ].join(" ")
                    }
                >
                        (4) Проекты
                </NavLink>
                <NavLink
                    to="/contacts"
                    className={({ isActive }) => 
                        [
                            styles.link_menu,
                            isActive ? styles.active : "",
                        ].join(" ")
                    }
                >
                        (5) Контакты
                </NavLink>
            </div>
        </header>
    )
}