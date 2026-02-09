"use client"

import { useEffect, useState } from 'react';

// Импорт компонентов
import Loading from '~/components/Loading';
import Skill from '~/components/Skill';

// Импорт API
import api from '~/utils/main-api';

// Импорт стилей
import styles from '~/styles/page_skills.module.scss';


// Интерфейс для описания навыка
interface Skill {
    name: string,
    description: string,
    experiences: Experience[]
}

// Интерфейс для описания опыта работы
interface Experience {
    name: string,
    description: string,
}


// Получаем API для работы с навыками
const apiSkills = api.skills;


export default function Skills() {
    // Состояние загрузки данных
    const [isLoading, setLoading] = useState(true);
    // Состояние для хранения списка навыков
    const [skills, setSkills] = useState<Skill[]>([]);
    // Состояние активного навыка
    const [active, setActive] = useState<number | null>(null);


    // useEffect для загрузки данных при монтировании компонента
    useEffect(() => {
        // Загрузка навыков из API
        apiSkills.getAll()
            // Успешная загрузка
            .then((res) => {
                setSkills(res);
                setLoading(false);
            })
            // Ошибка
            .catch((err) => {
                console.error("Ошибка загрузки навыков");
                setLoading(true);
            })
    }, []) // Выполняется только при монтировании
    

    return (
        <div className={styles.container}>
            {/* Заголовок */}
            <div className={styles.title}>
                <h1># Мои навыки</h1>
                <div className={styles.line}>===========================================================================================================</div>
            </div>
            
            {/* Список навыков */}
            <div className={styles.content}>
                {
                    // Проверка состояния загрузки
                    isLoading ?
                        // Отображение компонента загрузки
                        <Loading /> :
                        // Отображение списка навыков
                        <>
                            {
                                skills.map((skill, skill_index) => (
                                    <Skill
                                        key={skill_index}
                                        skill={skill}
                                        active={active == skill_index}
                                        onClick={() => setActive(skill_index)}
                                    />
                                ))
                            }
                        </>
                }
            </div>
        </div>
    )
}