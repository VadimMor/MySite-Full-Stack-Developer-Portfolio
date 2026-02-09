"use client"

import { useState } from 'react';

// Импорт хука
import useTypewriterWords from '~/utils/hooks/useTypewriterWords';

// Импорт стилей
import styles from '~/styles/component_skill.module.scss';

// Импорт компонентов
import Experience from '~/components/Experience';
import Line from '~/components/Line';


// Интерфейс переданных данных в компонент
interface SkillProps {
    skill: SkillInfo,
    active: boolean,
    onClick: () => void
}

// Интерфейс для описания навыка
interface SkillInfo {
    name: string,
    description: string,
    experiences: Experience[]
}

// Интерфейс для описания технологии
interface Experience {
    name: string,
    description: string
}


export default function Skill({ skill, active, onClick }: SkillProps) {
    // Логика анимации печатающегося текста для навыка
    const {
        text: animatedSkillDescription,
        isDone: isSkillDone
    } = useTypewriterWords(
        skill.description, 
        200,
        1000,
        active
    );
    


    return (
        <div className={`
            ${styles.container}
            ${active ? styles.active : ''}
        `}>
            {/* Заголовок компонента */}
            <h2
                onClick={onClick}
            >
                <div className={`icon-arrow ${styles.icon}`} />
                --
                &ensp;{skill.name}&ensp;
                ----------------------------------------------------------------------------
            </h2>

            {/* Контент для навыка */}
            <div className={styles.content}>
                {/* Описание навыка */}
                <div className={styles.description}>
                    {animatedSkillDescription}
                    { !isSkillDone && <Line /> }
                </div>
 
                {/* Список технологий */}
                <Experience
                    experiences={skill.experiences}
                />
            </div>
        </div>
    )
}