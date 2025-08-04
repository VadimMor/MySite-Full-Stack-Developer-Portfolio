"use client"

import { useRef, useEffect, useState } from "react"

// Import style
import styles from '@styles/Modal/ModalEmail.module.scss';

interface ModalEmailProps {
    onClose: () => void;
}

export default function ModalEmail({ onClose }: ModalEmailProps) {
    const messageRef = useRef<HTMLSpanElement>(null);

    const handleSubmit = () => {
        const msg = messageRef.current?.innerText ?? ""
        console.log("User message:", msg)
    }

    useEffect(() => {
        const handleKeyDown = (e: KeyboardEvent) => {
            // Проверяем, что нет активного инпута/textarea, чтобы не мешать набору текста
            const activeTag = document.activeElement?.tagName.toLowerCase()
            if (activeTag === "input" || activeTag === "textarea" || activeTag === "span") return

            if (e.code === 'KeyY') {
                e.preventDefault()
                handleSubmit()
            } else if (e.code === 'KeyN') {
                e.preventDefault()
                onClose()
            }
        }

        window.addEventListener("keydown", handleKeyDown)
        return () => {
            window.removeEventListener("keydown", handleKeyDown)
        }
    }, [handleSubmit, onClose])

    return (
        <div className={styles.modal}>
            <div className={styles.title}>
                <h4 className={styles.text}>Email</h4>
                <button onClick={onClose} className={styles.btn}>[Q] close</button>
            </div>

            <form className={styles.form}>
                <div  className={styles.box}>
                    <label className={styles.text}>What’s your name: </label>
                    <input type="text" placeholder="Vadim Uniatitskiy" className={styles.input} />
                </div>
                
                <div  className={styles.box}>
                    <label className={styles.text}>Email address: </label>
                    <input type="email" placeholder="example@example.com" className={styles.input} />
                </div>
                
                <div  className={styles.box}>
                    <label className={styles.text}>Topic: </label>
                    <input type="text" placeholder="Some question, idk" className={styles.input} />
                </div>
                
                <div  className={styles.box}>
                    <label className={styles.text}>Your message: </label>
                    <span
                        ref={messageRef}
                        className={styles.editable}
                        contentEditable
                        data-placeholder="The message comes here and it can go to multiple lines"
                        suppressContentEditableWarning
                    />
                </div>
            </form>

            <div className={styles.btns}>
                Send:&#160;
                <button onClick={handleSubmit} className={styles.btn}>[Y]es</button>
                &#160;/&#160;
                <button onClick={(e) => { e.preventDefault(); onClose(); }} className={styles.btn}>[N]o</button>
            </div>
        </div>
    )
}
