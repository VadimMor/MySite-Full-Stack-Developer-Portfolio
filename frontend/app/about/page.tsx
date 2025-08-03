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

    const openModal = (modalName: string) => setActiveModal(modalName);
    const closeModal = () => setActiveModal(null);

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
                    </div>

                    <sub className={styles.sub}>Inspired by Neovim</sub>
            </section>

            {activeModal === 'email' && <ModalEmail onClose={closeModal} />}
        </>
    )
}