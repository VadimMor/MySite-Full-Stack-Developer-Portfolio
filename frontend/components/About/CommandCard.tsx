"use client";

// Import style
import styles from '@styles/About/CommandCard.module.scss';

interface CommandCardProps {
    command: string;
    text: string;
    onEnter?: () => void;
}

export default function CommandCard({ command, text, onEnter  } : CommandCardProps) {
    return (
        <div className={styles.card}>
            <div className={styles.command}>
                type :{command}
                <button
                    className={styles.btn}
                    onClick={onEnter}
                >
                    &#60;enter&#62;
                </button>    
            </div>

            <div className={styles.text}>{text}</div>
        </div>
    )
}