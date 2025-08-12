"use client";

import { usePathname } from 'next/navigation';
import Link from "next/link";

// Import style
import styles from '@styles/Header/MenuButton.module.scss';

interface MenuButtonProps {
    id: number;
    text: string;
    link: string;
}

export default function MenuButton({ id, text, link } : MenuButtonProps) {
    const pathname = usePathname();
    const isActive = pathname === link || pathname.startsWith(`${link}/`);

    return (
        <Link
            href={link}
            key={id}
            className={`${styles.link} ${isActive ? styles.active : ''}`}
        >
            {`(${id}) ${text}`}
        </Link>
    )
}