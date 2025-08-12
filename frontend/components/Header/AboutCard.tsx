"use client";

// import style
import styles from '@styles/Header/AboutCard.module.scss';

// Props
interface AboutCardProps {
    title: string;
    text: string;
}

export default function AboutCard({ title, text } : AboutCardProps) {
    return (
        <div className={styles.card}>
            <div className={styles.title}>{title}</div>
            <div className={styles.text}>{text}</div>
        </div>
    )
}