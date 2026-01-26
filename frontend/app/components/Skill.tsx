"use client"

import { useEffect, useState } from "react";

import styles from "~/styles/component_skill.module.scss";

interface SkillProps {
    skill_active: boolean;
    skill: Skill;
    onClick: () => void;
}

interface Skill {
    name: string;
    description: string;
    experiences: Experience[];
}

interface Experience {
    name: string;
    description: string;
}

export default function Skill ({skill_active, skill, onClick }: SkillProps) {
    const [typedText, setTypedText] = useState("");
    const [typedExperienceText, setTypedExperienceText] = useState("");
    const [experienceContent, setExperience] = useState<{
        index: number
        content: Experience
    }| null>(null);

    const typeWords = (text: string, setText: (s: string) => void, delay = 100, startDelay = 0) => {
        let index = 0;
        setText("");

        const timeout = setTimeout(() => {
            const words = text.split(" ");
            const interval = setInterval(() => {
                if (index >= words.length - 1) {
                    clearInterval(interval);
                    return;
                }
                setText((prev) => (prev ? prev + " " + words[index] : words[index]));
                index++;
            }, delay);
        }, startDelay);

        return () => clearTimeout(timeout);
    };
    
    useEffect(() => {
        if (!skill_active) {
            setTypedText("");
            return;
        }

        return typeWords(skill.description, setTypedText, 100, 700);
    }, [skill_active, skill.description]);

    useEffect(() => {
        if (!experienceContent) {
            setTypedExperienceText("");
            return;
        }

        return typeWords(experienceContent.content.description, setTypedExperienceText, 100, 700);
    }, [experienceContent]);

    return (
        <div
            className={
                skill_active ? `${styles.active} ${styles.item}` : styles.item
            }
        >
            <h2 onClick={onClick}>
                <span className={styles.icon + " icon-arrow"}></span>
                <span className={styles.text}> -- {skill.name} ---------------------------------------------------------------------------------------------------</span>
            </h2>

            <div className={styles.content}>
                <div className={styles.text}>
                    {typedText}
                    <span className={styles.line}>|</span>
                </div>

                {
                    skill.description.length > 0 ? 
                        <div className={styles.experiences}>
                            <h3>Используемые технологии :</h3>

                            <div className={styles.experiences_menu}>
                                {
                                    skill.experiences.map((experience, experience_index) => (
                                        <h4
                                            key={experience_index}
                                            className={
                                                `${styles.experience_item} 
                                                ${experienceContent?.index == experience_index ? styles.active : ""}`
                                            }
                                            onClick={() => {setExperience({
                                                index: experience_index,
                                                content: experience
                                            })}}
                                        >
                                            [{experience.name}]
                                        </h4>
                                    ))
                                }
                            </div>

                            {
                                experienceContent ? 
                                <div className={styles.description}>
                                    {typedExperienceText}
                                    <span className={styles.line}>|</span>
                                </div> :
                                <></>
                            }
                        </div> :
                        <></>
                }
            </div>
        </div>
    )
}