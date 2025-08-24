"use client"

import Image from 'next/image';
import Link from "next/link";
import { useEffect, useState } from "react";

// Импорт компонентов
import Project from "@/components/Projects/Project";
import Loading from "@/components/Loading";

// Импорт функций RestAPI
import createBackRestAPI from "@/services/BackRestAPI";

// Импорт стилей
import styles from '@styles/Projects/Projects.module.scss';

// Импорт изображений
import Arrow from '@/public/Arrow.svg';

interface PageProps {
    projects: ProjectProps[];
    last_page: boolean
}

interface ProjectProps {
    name: string;
    description: string;
    date_create: Date;
    technologies: Technology[];
}

interface Technology {
    name: string;
}

export default function Projects() {
    const [pageProjects, setPageProjects] = useState<PageProps | null>(null);
    const [projectActive, setProjects] = useState<ProjectProps | null>(null);
    const restApi = createBackRestAPI();


    useEffect(() => {
        const fetchPageProjects = async () => {
            try {
                const projectsData = await restApi.getMassiveProjects(0);
                setPageProjects(projectsData);
                setProjects(projectsData.projects[0])
            } catch (error) {
                console.error("Error in component while fetching projects:", error);
            }
        };

        fetchPageProjects();
    }, []);

    const onSetProject = (project: ProjectProps) => {
        setProjects(project)
    }

    return (
        <div className={styles.container}>
            <div className={styles.menu}>
                <div className={styles.menu_header}>
                    <div>Date</div>
                    <div>Project Name</div>
                    <div>Technology</div>
                </div>
                {
                    pageProjects ? pageProjects.projects.map((project, index) => (
                        <Project
                            data={project} 
                            key={index}
                            onClick={() => onSetProject(project)}
                        />
                    )) : (
                        <Loading />
                    )

                }
            </div>

            <div className={styles.content}>
                {
                    projectActive ? (
                        <>
                            <h3># { projectActive.name.split("-").join(" ") }</h3>
                            
                            <div>{ projectActive.description }</div>

                            <Link
                                href={`/projects/${projectActive.name}`}
                                className={styles.link}
                            >
                                See details
                                <Image
                                    src={Arrow}
                                    alt="Arrow icon"
                                    width={14}
                                    height={8}
                                />
                            </Link>
                        </>
                    ) : (
                        <Loading />
                    )
                }
            </div>
        </div>
    )
}