"use client"

// импорт стилей
import styles from '@styles/Projects/Project.module.scss';

interface ProjectProps {
    name: string;
    description: string;
    date_create: Date;
    technologies: Technology[];
}

interface Technology {
    name: string;
}

export default function Project({ data, onClick }: { data: ProjectProps, onClick?: () => void }) {
    const formattedDate = new Date(data.date_create).toLocaleDateString("en-GB", {
        day: "numeric",
        month: "long",
        year: "numeric",
    });

    return (
        <div onClick={onClick} className={styles.card}>
            <div className={styles.date}>{ formattedDate }</div>
            
            <div>{ data.name.split("-").join(" ") }</div>

            <div className={styles.technologies}>
                {
                    data.technologies.map(t => t.name).join(", ")
                }
            </div>
        </div>
    )
}