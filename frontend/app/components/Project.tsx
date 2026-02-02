"use client"

import styles from "~/styles/component_project.module.scss";

interface ProjectProps {
    date: Date,
    name: string,
    technologies: Technology[]
    onClick: () => void;
}

interface Technology {
    name: string
}

const month = ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"]

export default function Project({ date, name, technologies, onClick } : ProjectProps) {
    return (
        <div
            className={styles.block}
            onClick={onClick}
        >
            <div>
                {(() => {
                    const [year, monthIndex, day] = date
                        .split("T")[0]
                        .split("-");

                    return `${day} ${month[Number(monthIndex) - 1]} ${year}`;
                })()}
            </div>

            <div>
                {name}
            </div>

            <div>
                {
                    technologies.map(t => t.name).join(", ")
                }
            </div>
        </div>
    )
}