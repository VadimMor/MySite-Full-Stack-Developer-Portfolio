"use client";

import Image from 'next/image';

import styles from '@styles/Skills/skill.module.scss';

// Импорт изображений
import Arrow from '@/public/Arrow.svg';
import { useEffect, useState } from 'react';

interface SkillProps {
    name: string;
    description: string;
    experiences: ExperienceProps[]
}

interface ExperienceProps {
    name: string;
    description: string;
}

export default function Skill({ data }: { data: SkillProps }, ) {
    const [isOpen, setIsOpen] = useState<boolean | null>(null);
    const [experienceActive, setExperienceActive] = useState<number | null>(null);
    const [experience, setExperience] = useState<ExperienceProps | null>(null);
    
    useEffect(() => {
        const fetchDesc = () => {
            setExperienceActive(0);
            setExperience(data.experiences[0]);
        }

        fetchDesc()
    }, [])

    const activeExperience = (index: number, experience_content: ExperienceProps) => {
        setExperienceActive(index);
        setExperience(experience_content);
    }

    const toggleOpen = () => {
        setIsOpen(!isOpen);
    };

    return (
        <div className={`${styles.card} ${isOpen ? styles.active : null}`}>
            <button className={styles.btn_title} onClick={toggleOpen}>
                <Image
                    src={Arrow}
                    alt="Arrow icon"
                    width={14}
                    height={8}
                />
                <span>--</span>
                <span>{ data.name }</span>
                <span>---------------------------------------------------------------------------</span>
            </button>

            {
                isOpen ? (
                    <div className={styles.content}>
                        <div>{ data.description }</div>

                        <div>
                            <div className={styles.experience_title}>Experience in:</div>

                            <nav className={styles.experience_menu}>
                                {
                                    data.experiences.map((experience, index) => (
                                        <button
                                            key={index}
                                            className={`${styles.experience_btn} ${index == experienceActive ? styles.active : null}`}
                                            onClick={() => activeExperience(index, experience)}
                                        >
                                            [{experience.name}]
                                        </button>
                                    ))
                                }
                            </nav>

                            <div className={styles.experience_content}>
                                <h3># { experience?.name }</h3>
                                <div>{ experience?.description }</div>
                            </div>
                        </div>
                    </div>
                ) : null
            }
        </div>
    )
}