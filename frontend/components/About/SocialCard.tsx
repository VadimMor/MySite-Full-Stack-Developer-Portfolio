"use client";

// Import style
import styles from '@styles/About/SocialCard.module.scss';

// Props
interface SocialCardProps {
    social: string;
    link: {
        text: string;
        link: string;
    };
    text: string;
}

export default function SocialCard({ social, link, text} : SocialCardProps) {
    return (
        <div className={styles.card}>
            <div className={styles.social}>{social}</div>
            <a href={link.link} className={styles.link} target='__blank'>{link.text}</a>
            <div className={styles.text}>{text}</div>
        </div>
    )
}