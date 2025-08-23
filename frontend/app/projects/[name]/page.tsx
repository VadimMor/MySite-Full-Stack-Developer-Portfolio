"use client";

import { useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import rehypeHighlight from "rehype-highlight";
import { notFound, useRouter } from 'next/navigation';

// Импорт функций RestAPI
import createBackRestAPI from "@/services/BackRestAPI";

// Импорт стилей
import styles from '@styles/Projects/FullProject.module.scss';
import "highlight.js/styles/github.css";
import "github-markdown-css";
import Loading from "@/components/Loading";

interface ProjectPageProps {
  params: Promise<{ name: string }>
}

interface ProjectProps {
    name: string;
    description: string;
    date_create: Date;
    technologies: Technology[];
    posts: string 
}

interface Technology {
    name: string;
}

export default function fullProject({ params }:  ProjectPageProps) {
    const [project, setProject] = useState<ProjectProps | null>(null);
    const restApi = createBackRestAPI();
    const router = useRouter();

    useEffect(() => {
        const fetchProject = async () => {
            try {
                const response = await restApi.getProject((await params).name);
                setProject(response);
            } catch (error) {
                console.error("Error in component while fetching projects:", error);
                 router.push('/not-found');
            }
        }

        fetchProject();
    }, [])


    return (
        <div className={styles.container}>
            {
                project ? (
                    <>
                        <h2># {project.name}</h2>
                        <div className={styles.content}>
                            <div className={styles.full}>
                                <div className={styles.line}>==================================================================================================================================</div>
                            
                                <div className={styles.markdown_body}>
                                    <ReactMarkdown
                                        remarkPlugins={[remarkGfm]}
                                        rehypePlugins={[rehypeHighlight]}
                                    >
                                        {project.description.replace(/^# .*\n/, "")}
                                    </ReactMarkdown>
                                </div>
                            </div>

                            <div className={styles.small}>
                                <div className={styles.block}>
                                    <span className={styles.title}>Date</span>
                                    <span className={styles.muted}>
                                        { new Date(project.date_create).toLocaleDateString("en-GB", { day: "numeric", month: "long", year: "numeric" }) }
                                    </span>
                                </div>
                                <div className={styles.block}>
                                    <span className={styles.title}>Technology</span>
                                    <span className={styles.muted}>
                                        {
                                            project.technologies.map(t => t.name).join(", ")
                                        }
                                    </span>
                                </div>
                            </div>
                        </div>
                    </>
                ) : (
                    <Loading />
                )
            }
        </div>
    )
}