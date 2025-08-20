import styles from '@styles/Loading.module.scss';

export default function Loading() {
    return (
        <div className={styles.load}>
            Loading
            <span>|</span>
        </div>
    )
}