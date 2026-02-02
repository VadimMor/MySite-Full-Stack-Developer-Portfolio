"use client"

import { useEffect, useState } from "react";
import { data, Link } from "react-router";

import Project from "~/components/Project";

import styles from "~/styles/page_projects.module.scss";

import api from "~/utils/main-api";

interface Project {
    date: Date,
    name: string,
    technologies: Technology[]
}

interface Technology {
    name: string
}

interface ShortProject {
    name: string,
    description: string
}

export default function Projects() {
    const [projects, setProjects] = useState<Project[]>([]);
    const [project, setProject] = useState<ShortProject>();
    const [isLoading, setIsLoading] = useState(true);

    const getShortInfoProject = (name: string) => {
        api.projects.getShort(name)
            .then((data) => {
                console.log(data)
                setProject(data);
            })
            .catch((err) => {
                console.error("Ошибка при загрузке проекта:", err);
            })
    }

    useEffect(() => {
        api.projects.getAll()
            .then((data) => {
                setProjects(data);
                setIsLoading(false);
            })
            .catch((err) => {
                console.error("Ошибка при загрузке проектов:", err);
                setIsLoading(false);
            });
    }, []);

    return (
        <div className={`container ${styles.content}`}>
            <div className={styles.projects}>
                    {isLoading ? (
                        <p>Загрузка...</p>
                    ) : 
                        <>
                            {
                                projects.map((project, project_index) => (
                                    <Project
                                        onClick={() => getShortInfoProject(project.name)}
                                        key={project_index}
                                        {...project}
                                    />
                                ))
                            }
                        </>
                    }
            </div>

            <div className={styles.project}>
                {
                    project == null ? <h2># Выберите проект</h2> : (
                        <>
                            <h2># {project.name}</h2>
                            <div>{project.description}</div>
                            <div>
                                <Link
                                    to={{
                                        pathname: `/project/${project.name.split(" ").join("--")}`,
                                    }}
                                >
                                    Посмотреть полностью 
                                    <span className={styles.icon + " icon-arrow"} />
                                </Link>
                            </div>
                        </>
                    )
                }
            </div>
        </div>
    )
}