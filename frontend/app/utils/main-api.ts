// Адрес API
const API_BASE = "http://localhost:8080/api-v0.2";


// Методы навыков
interface Skill {
    name: string,
    description: string,
    experiences: {
        name: string,
        description: string,
    }[]
}

// Методы проектов
interface shortMassiveProject {
    date: Date,
    name: string,
    technologies: {
        name: string
    }[]
}

interface shortInfoProject {
    name: string,
    description: string
}

interface infoProject {
    name: string,
    description: string,
    create_date: Date,
    url: string,
    technologies: {
        name: string
    }[]
}

// Методы постов
interface shortMassivePost {
    id: number,
    name: string,
    date: Date,
    categories: {
        name: string
    }[]
}

interface infoPost {
    name: string,
    description: string,
    url: string,
    length: number,
    date: Date,
    categories: {
        name: string
    }[]
}

const api = {
    // Запросы для навыков
    skills: {
        // Получение массива навыков
        getAll: async (): Promise<Skill[]> => {
            const res = await fetch(`${API_BASE}/skill/all`);
            return res.json();
        }
    },

    // Запросы для проектов
    projects: {
        // Получение массива проектов по 20 штук
        getAll: async (
            sort: string = "date_DOWN",
            page: number = 0
        ): Promise<shortMassiveProject[]> => {
            const params = new URLSearchParams({
                sort,
                page: page.toString(),
            });

            const res = await fetch(`${API_BASE}/project/all?${params}`);
            return res.json();
        },

        // Получение краткой информации о проекте
        getShort: async (
            name: string
        ): Promise<shortInfoProject> => {
            const res = await fetch(`${API_BASE}/project/short/${name}`);
            return res.json();
        },

        // Получение полной информации о проекте
        getInfo: async (
            name: string
        ): Promise<infoProject> => {
            const res = await fetch(`${API_BASE}/project/${name}`);
            return res.json();
        }
    },

    // Запрос для постов
    posts: {
        // Получение массива постов по 20
        getAll: async (
            sort: string = "date_DOWN",
            page: number = 0
        ): Promise<shortMassivePost[]> => {
            const params = new URLSearchParams({
                sort,
                page: page.toString(),
            });

            const res = await fetch(`${API_BASE}/post/all?${params}`);
            return res.json();
        },

        // Получение полной информации поста
        getInfo: async (
            name: string
        ): Promise<infoPost> => {
            const res = await fetch(`${API_BASE}/post/${name}`);
            return res.json();
        }
    }
}

export default api;