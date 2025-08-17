"use client";

import { useEffect, useState } from "react";

// Импорт компонентов
import Skill from "@/components/Skills/Skill";

// Импорт функций RestAPI
import createBackRestAPI from "@/services/BackRestAPI";

// Импорт стилей
import styles from '@styles/Skills/skills.module.scss';

interface SkillProps {
    name: string;
    description: string;
    experiences: {
        name: string;
        description: string;
    }[];
}

export default function Skills() {
    const [skills, setSkills] = useState<SkillProps[] | null>(null);
    const restApi = createBackRestAPI();


    useEffect(() => {
        const fetchSkills = async () => {
            try {
                const skillsData = await restApi.getMassiveSkills();
                setSkills(skillsData);
            } catch (error) {
                console.error("Error in component while fetching skills:", error);
            }
        };

        fetchSkills();
    }, []);

    return (
        <div className={styles.container}>
            <h2># The currently selected / focused project</h2>
            
            <div className={styles.symbol}>============================================================================================</div>

            <div className={styles.skills}>
                {skills ? (
                    skills.map((skill, index) => (
                        <Skill key={index} data={skill} />
                    ))
                ) : (
                    <div className={styles.load}>
                        Loading
                        <span>|</span>
                    </div>
                )}
            </div>
        </div>
    )
}