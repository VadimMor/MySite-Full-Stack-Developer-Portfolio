"use client"

import { useState } from 'react';

// Импорт стилей
import styles from '~/styles/component_experience.module.scss';

// Импорт хука
import useTypewriterWords from '~/utils/hooks/useTypewriterWords';

// Импорт компонентов
import Line from '~/components/Line';


// Интерфейс переданных данных в компонент
interface ExperienceProps {
    experiences: Experience[]
}

// Интерфейс для описания технологии
interface Experience {
    name: string,
    description: string
}

// Интерфейс для активной технологии
interface ActiveExperience {
    id: number,
    description: string
}


export default function Experience({ experiences }: ExperienceProps) {
    // Состояние для хранения выбранной технологии
    const [activeExperience, setActiveExperience] = useState<ActiveExperience | null>(null);
    
    // Логика анимации для детального описания выбранной технологии
    const {
        text: animatedExperienceDescription,
        isDone: isExperienceDone
    } = useTypewriterWords(
        activeExperience?.description || '', 
        200,
        500,
        !!activeExperience,
        activeExperience?.id
    );


    return (
        <div className={styles.container}>
            {/* Список технологий */}
            <div className={styles.experiences}>
                {/* Вывод списка технологий */}
                {
                    experiences.map((experience, experience_index) => (
                        <div
                            key={experience_index}
                            className={`
                                ${styles.item}
                                ${
                                    experience_index == activeExperience?.id ? styles.active : ''
                                }
                            `}
                            // Выбор технологии для выбора описания
                            onClick={() => setActiveExperience({ 
                                id: experience_index, 
                                description: experience.description 
                            })}
                        >
                            [{experience.name}]
                        </div>
                    ))
                }
            </div>

            {/* Описание технологии */}
            {
                !!activeExperience ? 
                    <div className={styles.description}>
                        {animatedExperienceDescription}
                        { activeExperience && !isExperienceDone && <Line /> }
                    </div> : null
            }
        </div>
    )
}