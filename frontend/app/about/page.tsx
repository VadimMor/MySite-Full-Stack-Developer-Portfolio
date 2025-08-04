"use client";

import { useEffect, useState } from 'react';

// Import components
import CommandCard from '@/components/About/CommandCard';
import SocialCard from '@/components/About/SocialCard';

// Import modal
import ModalEmail from '@/components/Modals/AboutModals/ModalEmail';

// Import styles
import styles from '@styles/About/page.module.scss';

export default function About() {
    const [activeModal, setActiveModal] = useState<string | null>(null);

    // Working with modal windows
    const openModal = (modalName: string) => setActiveModal(modalName);
    const closeModal = () => setActiveModal(null);

    // Downloading a CV
    const downloadCV = () => {
        const link = document.createElement('a');
        link.href = '/files/mortilly.pdf'; // путь к файлу
        link.download = 'CV.pdf';    // имя при сохранении
        link.click();
    };

    // Link build site
    const linkSite = () => {
        window.open("https://github.com/VadimMor/MySite-Full-Stack-Developer-Portfolio", "__blank")
    }

    // Обработка нажатия клавиши q
    useEffect(() => {
        const handleKeyDown = (e: KeyboardEvent) => {
            // Игнорируем, если фокус в input, textarea или contentEditable элементе
            const active = document.activeElement;
            const tag = active?.tagName.toLowerCase();
            const isEditable = active?.getAttribute('contenteditable');

            if (tag === 'input' || tag === 'textarea' || isEditable === 'true') {
                return;
            }

            if (e.code === 'KeyQ') {
                closeModal();
            }
        };

        if (activeModal) {
            document.addEventListener('keydown', handleKeyDown);
        }

        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, [activeModal]);

    return (
        <>
            <section className={styles.section}>
                    <h2 className={styles.title}>About page v0.0.1</h2>
                    
                    <h3 className={styles.title}>Me on the web</h3>

                    <div className={styles.cards}>
                        <SocialCard
                            social="Telegram"
                            link={{
                                text: "@Vadim_morilly",
                                link: "https://t.me/Vadim_morilly"
                            }}
                            text="I answer often here"
                        />
                        <SocialCard
                            social="Discord"
                            link={{
                                text: "@mortallywhell",
                                link: "https://discord.com/users/352461766837665792"
                            }}
                            text="Best place to be"
                        />
                    </div>

                    <h3 className={styles.title}>Commands about the site</h3>

                    <div className={styles.cards}>
                        <CommandCard
                            command="email"
                            onEnter={() => openModal('email')}
                            text="To send an email"
                        />
                        <CommandCard
                            command="build site"
                            onEnter={linkSite}
                            text="How I build the site"
                        />
                        <CommandCard
                            command="cv"
                            onEnter={downloadCV}
                            text="To download my CV"
                        />
                    </div>

                    <sub className={styles.sub}>Inspired by Neovim</sub>
            </section>

            {activeModal === 'email' && <ModalEmail onClose={closeModal} />}
        </>
    )
}