"use client";

import Link from 'next/link';

// Import components
import AboutCard from '@components/Header/AboutCard';
import MenuButton from '@components/Header/MenuButton';

// Import style
import styles from '@styles/Header/Header.module.scss';

const islandFishingTextArt = " _   _   ___   ____   _____  __  __ \n" + 
                              "| | | | / _ \\ |  _ \\ |_   _||  \\/  |\n" +
                              "| | | |/ /_\\ \\| | | |  | |  | \\  / |\n" +
                              "| | | ||  _  || | | |  | |  | |\\/| |\n" +
                              "| \\_/ || | | || |_| | _| |_ | |  | |\n" +
                              "\\_____/\\_| |_/|____/ |_____||_|  |_|" ;

function TextArt ({label, text}: any) {
  return (
    <pre
      aria-label={label}
      className={`${styles.logo}`}
    >{text}</pre>
  );
}

export default function Header() {
    const menuLinks = [
        {text: 'Articles', link: '/'},
        {text: 'My projects', link: '/projects'},
        {text: 'About', link: '/about'},
        {text: 'Skills', link: '/skills'},
        {text: 'Talks', link: '/talk'},
    ]

    return (
        <header className={`${styles.header}`}>
            {/* top content */}
            <div className={`${styles.top}`}>
                {/* Primary */}
                <div className={`${styles.primary}`}>
                    {/* Logo */}
                    <h1>
                        <pre
                            aria-label="Logo"
                            className={`${styles.logo}`}
                        >
                            {islandFishingTextArt}
                        </pre>
                    </h1>


                    {/* About */}
                    <div className={`${styles.about}`}>
                        <AboutCard
                            title='Name'
                            text='Vadim Uniatitskiy'
                        />
                        <AboutCard
                            title='Location'
                            text='Russia'
                        />
                        <AboutCard
                            title='Handle'
                            text='@Vadim_morilly'
                        />
                        <AboutCard
                            title='Editor'
                            text='VS Code'
                        />
                    </div>
                </div>

                {/* Secondary */}
                <div className={styles.secondary}>
                    <Link
                        href='/'
                        className={styles.link}
                    >
                        <span className={`${styles.icon} icon-Frame`}  />
                        <span>How I build this site</span>
                    </Link>
                    <a
                        href='https://github.com/VadimMor/MySite-Full-Stack-Developer-Portfolio'
                        target='__blank'
                        className={styles.link}
                    >
                        <span className={`${styles.icon} icon-Github`} />
                        <span>Github</span>
                    </a>
                </div>
            </div>

            {/* Menu */}
            <nav className={styles.menu}>
                {
                    menuLinks.map((item, index) => {
                        return (
                            <MenuButton
                                id={index+1}
                                text={item.text}
                                link={item.link}
                            />
                        );
                    })
                }
            </nav>
        </header>
    )
}