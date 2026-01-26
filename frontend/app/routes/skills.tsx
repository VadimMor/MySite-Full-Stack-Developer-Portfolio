"use client";

import { useEffect, useState } from "react";

import api from "~/utils/main-api";

import Skill from "~/components/Skill";

import styles from "~/styles/page_skills.module.scss";

interface Skill {
    name: string;
    description: string;
    experiences: Experience[];
}

interface Experience {
    name: string;
    description: string;
}

export default function Skills() {
    const [skills, setSkills] = useState<Skill[]>([]);
    const [skill_active, setSkill] = useState<number | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        api.skills.getAll()
            .then((data) => {
                setSkills(data);
                setIsLoading(false);
            })
            .catch((err) => {
                console.error("Ошибка при загрузке навыков:", err);
                setIsLoading(false);
            });
    }, []);
    
    return (
        <div className={"container " + styles.container}>
            <h1 className={styles.title}># Мои навыки</h1>
            <div className={styles.line}>===========================================================================================================</div>
            
            <div className={styles.content}>
                {isLoading ? (
                    <p>Загрузка...</p>
                ) : (
                    <>
                        {skills.map((skill, skill_index) => (
                            <Skill
                                key={skill_index}
                                skill_active={skill_active == skill_index}
                                skill={skill}
                                onClick={() => {setSkill(skill_index)}}
                            />
                        ))}
                    </>
                )}
            </div>
        </div>
    )
}